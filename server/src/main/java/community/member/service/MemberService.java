package community.member.service;

import community.auth.jwt.JwtTokenizer;
import community.auth.utils.CustomAuthorityUtils;
import community.exception.BusinessLogicException;
import community.exception.ExceptionCode;
import community.member.entity.Level;
import community.member.entity.Member;
import community.member.repository.MemberRepository;
import community.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@ToString
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CustomBeanUtils customBeanUtils;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    public Member createMember(Member member){
        verifyExistsEmail(member.getEmail());

        String password=member.getPassword();
        String encryptedPassword=passwordEncoder.encode(password);
        member.setPassword(encryptedPassword);
        List<String> roles=authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
        Member savedMember = memberRepository.save(member);

        savedMember.setPoint(10000);

        // 레벨관련 로직
        Level level = new Level(); // 회원이 생성되면 레벨테이블을 만든다.
        level.setMember(savedMember); // 레벨 - 생성된회원 테이블 연결
        level.setUserName(savedMember.getName()); // 회원이름 연결
        member.setLevel(level); // 레벨 - 회원 테이블 연결


        return savedMember;
    }

    public Member updateMember(Member member){
        Member findMember = findVerifiedMember(member.getMemberId());
    
        /*비밀번호 변경*/
        String password=member.getPassword();
        String encryptedPassword=passwordEncoder.encode(password);
        member.setPassword(encryptedPassword);

        Member updatedMember = (Member) customBeanUtils.copyNonNullProperties(member, findMember); //dto -> member 복사

        return memberRepository.save(updatedMember);
    }

    public void deleteMember(long memberId){
        Member findMember=findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember=
                memberRepository.findById(memberId);
        Member findMember=
                optionalMember.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    public Member findVerifiedEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private void verifyExistsEmail(String email){
        Optional<Member> member= memberRepository.findByEmail(email);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    /*회원전체 정보 페이지*/
    public Page<Member> memberPage(Pageable pageable){
      return memberRepository.findAll(pageable);
    }

    /* 나무기부
     1번 누를때마다 300포인트씩 차감 */
    public Member donateTree(long memberId){
        Member member = findMember(memberId);

        if(member.getPoint() >= 300) { // 300포인트 이상일 경우만 실행
            member.setPoint(member.getPoint() - 300);
            member.setTreeCount(member.getTreeCount() + 1);
            memberRepository.save(member);
        }
        return member;
    }
}