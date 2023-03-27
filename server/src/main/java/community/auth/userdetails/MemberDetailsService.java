package community.auth.userdetails;

import community.auth.utils.CustomAuthorityUtils;
import community.exception.BusinessLogicException;
import community.exception.ExceptionCode;
import community.member.entity.Member;
import community.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static community.exception.ExceptionCode.*;

@Component
public class MemberDetailsService implements UserDetailsService { // 계정정보, 유저 인증 데이터를 불러옴
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberByEmail = memberRepository.findByEmail(username);
        Member findMember = memberByEmail.orElseThrow(() ->
                new BusinessLogicException(MEMBER_NOT_FOUND));


        return new MemberDetails(findMember);
    }
    
    /*멤버 정보를 세팅해주는 부분*/
    private final class MemberDetails extends Member implements UserDetails {

        MemberDetails(Member member) {
            setMemberId(member.getMemberId());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
            setName(member.getName());
            setLevel(member.getLevel());
            setPoint(member.getPoint());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}