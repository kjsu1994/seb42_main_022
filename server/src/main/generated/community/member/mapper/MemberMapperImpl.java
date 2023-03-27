package community.member.mapper;

import community.member.dto.LevelDto;
import community.member.dto.MemberDto;
import community.member.entity.Level;
import community.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T19:31:58+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( requestBody.getName() );
        member.setEmail( requestBody.getEmail() );
        member.setPhone( requestBody.getPhone() );
        member.setPassword( requestBody.getPassword() );

        return member;
    }

    @Override
    public Member memberPatchToMember(MemberDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member member = new Member();

        member.setMemberId( requestBody.getMemberId() );
        member.setMemberStatus( requestBody.getMemberStatus() );
        member.setName( requestBody.getName() );
        member.setPhone( requestBody.getPhone() );
        member.setPassword( requestBody.getPassword() );

        return member;
    }

    @Override
    public MemberDto.Response memberToMemberResponse(Member member, LevelDto levelDto) {
        if ( member == null && levelDto == null ) {
            return null;
        }

        long memberId = 0L;
        String email = null;
        String name = null;
        String phone = null;
        String point = null;
        String treeCount = null;
        Member.MemberStatus memberStatus = null;
        if ( member != null ) {
            memberId = member.getMemberId();
            email = member.getEmail();
            name = member.getName();
            phone = member.getPhone();
            point = String.valueOf( member.getPoint() );
            treeCount = String.valueOf( member.getTreeCount() );
            memberStatus = member.getMemberStatus();
        }
        LevelDto levelDto1 = null;
        levelDto1 = levelDto;

        MemberDto.Response response = new MemberDto.Response( memberId, email, name, phone, point, treeCount, memberStatus, levelDto1 );

        return response;
    }

    @Override
    public List<MemberDto.Response> membersToMemberResponses(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.Response> list = new ArrayList<MemberDto.Response>( members.size() );
        for ( Member member : members ) {
            list.add( memberToResponse( member ) );
        }

        return list;
    }

    @Override
    public LevelDto levelToLevelResponse(Level level) {
        if ( level == null ) {
            return null;
        }

        LevelDto levelDto = new LevelDto();

        levelDto.setUserName( level.getUserName() );
        levelDto.setLevel( level.getLevel() );
        levelDto.setLevelExp( level.getLevelExp() );
        levelDto.setTotalExp( level.getTotalExp() );

        return levelDto;
    }

    @Override
    public List<LevelDto> levelsToLevelResponseList(List<Level> levels) {
        if ( levels == null ) {
            return null;
        }

        List<LevelDto> list = new ArrayList<LevelDto>( levels.size() );
        for ( Level level : levels ) {
            list.add( levelToLevelResponse( level ) );
        }

        return list;
    }

    @Override
    public List<MemberDto.donationRanks> membersToDonationRanks(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberDto.donationRanks> list = new ArrayList<MemberDto.donationRanks>( members.size() );
        for ( Member member : members ) {
            list.add( memberTodonationRanks( member ) );
        }

        return list;
    }

    protected MemberDto.Response memberToResponse(Member member) {
        if ( member == null ) {
            return null;
        }

        long memberId = 0L;
        String email = null;
        String name = null;
        String phone = null;
        String point = null;
        String treeCount = null;
        Member.MemberStatus memberStatus = null;

        memberId = member.getMemberId();
        email = member.getEmail();
        name = member.getName();
        phone = member.getPhone();
        point = String.valueOf( member.getPoint() );
        treeCount = String.valueOf( member.getTreeCount() );
        memberStatus = member.getMemberStatus();

        LevelDto levelDto = null;

        MemberDto.Response response = new MemberDto.Response( memberId, email, name, phone, point, treeCount, memberStatus, levelDto );

        return response;
    }

    protected MemberDto.donationRanks memberTodonationRanks(Member member) {
        if ( member == null ) {
            return null;
        }

        String name = null;
        String treeCount = null;

        name = member.getName();
        treeCount = String.valueOf( member.getTreeCount() );

        MemberDto.donationRanks donationRanks = new MemberDto.donationRanks( name, treeCount );

        return donationRanks;
    }
}
