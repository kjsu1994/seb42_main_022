package community.member.controller;

import community.globaldto.SingleResponseDto;
import community.member.dto.LevelDto;
import community.member.dto.MemberDto;
import community.member.entity.Level;
import community.member.entity.Member;
import community.member.mapper.MemberMapper;
import community.member.service.LevelService;
import community.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/members")
@RestController
@Validated
@ToString
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;
    private final LevelService levelService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody){
        System.out.println(requestBody.toString());
        Member member=mapper.memberPostToMember(requestBody);
        System.out.println(member.toString());
        Member createdMember=memberService.createMember(member);
        System.out.println(createdMember.toString());

        Level level = levelService.memberlevel(createdMember); //만들어진 회원에 대한 레벨테이블
        LevelDto levelResponse = mapper.levelToLevelResponse(level); // 해당 레벨 리스폰스

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponse(createdMember, levelResponse)), HttpStatus.CREATED);
    }
    @PostMapping("/donation/{member-id}") // 나무심기 버튼용
    public ResponseEntity postDonation(@PathVariable("member-id") @Positive long memberId){
        Member member = memberService.donateTree(memberId);

        Level level = levelService.memberlevel(member);
        LevelDto levelResponse = mapper.levelToLevelResponse(level);

        MemberDto.Response response = mapper.memberToMemberResponse(member, levelResponse);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody){

        Member member=mapper.memberPatchToMember(requestBody);
        member.setMemberId(memberId);
        Member updatedMember=memberService.updateMember(member);

        Level level = levelService.memberlevel(updatedMember);
        LevelDto levelResponse = mapper.levelToLevelResponse(level);

        MemberDto.Response response= mapper.memberToMemberResponse(updatedMember, levelResponse);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(
            @PathVariable("member-id") @Positive long memberId){
        Member member=memberService.findMember(memberId);

        Level level = levelService.memberlevel(member);
        LevelDto levelResponse = mapper.levelToLevelResponse(level);

        return new ResponseEntity<>(
                mapper.memberToMemberResponse(member, levelResponse),HttpStatus.OK
        );
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>("회원탈퇴 완료.",HttpStatus.NO_CONTENT);
    }

    /*기부 랭킹 페이지*/
    @GetMapping("/donationRanks")
    public ResponseEntity<?> donationRanks(@PageableDefault(size = 10, sort = "treeCount", direction = Sort.Direction.DESC) Pageable pageable) //페이지 기본값
    {
        Page<Member> memberPage = memberService.memberPage(pageable);
        List<Member> members = memberPage.getContent();
        List<MemberDto.donationRanks> response = mapper.membersToDonationRanks(members);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*레벨 랭킹 페이지*/
    @GetMapping("/levelRanks")
    public ResponseEntity<?> rankBoards(@PageableDefault(size = 10, sort = "totalExp", direction = Sort.Direction.DESC) Pageable pageable) //페이지 기본값
    {
        Page<Level> levelPage = levelService.levelRanks(pageable);
        List<Level> levels = levelPage.getContent();
        List<LevelDto> response = mapper.levelsToLevelResponseList(levels);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
