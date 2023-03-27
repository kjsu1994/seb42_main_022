package community.comment.mapper;

import community.comment.dto.CommentDto;
import community.comment.entity.Comment;
import community.member.dto.LevelDto;
import community.member.dto.MemberDto;
import community.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T14:31:53+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentDto.Post commentPostDto) {
        if ( commentPostDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( commentPostDto.getCommentId() );
        comment.setContents( commentPostDto.getContents() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(CommentDto.Patch commentPatchDto) {
        if ( commentPatchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( commentPatchDto.getCommentId() );
        comment.setContents( commentPatchDto.getContents() );

        return comment;
    }

    @Override
    public CommentDto.Response commentToCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response response = new CommentDto.Response();

        response.setCommentId( comment.getCommentId() );
        response.setContents( comment.getContents() );
        response.setLikeCount( comment.getLikeCount() );
        response.setCreatedAt( comment.getCreatedAt() );
        response.setModifiedAt( comment.getModifiedAt() );
        response.setMember( memberToResponse( comment.getMember() ) );

        return response;
    }

    @Override
    public CommentDto.InfoResponse commentToCommentInfoResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.InfoResponse infoResponse = new CommentDto.InfoResponse();

        infoResponse.setCommentId( comment.getCommentId() );
        infoResponse.setContents( comment.getContents() );
        infoResponse.setLikeCount( comment.getLikeCount() );
        infoResponse.setCreatedAt( comment.getCreatedAt() );
        infoResponse.setModifiedAt( comment.getModifiedAt() );
        infoResponse.setMember( memberToResponse( comment.getMember() ) );
        infoResponse.setCreatorLevel( comment.getCreatorLevel() );

        return infoResponse;
    }

    @Override
    public List<CommentDto.InfoResponse> commentToCommentResponses(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto.InfoResponse> list = new ArrayList<CommentDto.InfoResponse>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentInfoResponse( comment ) );
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
}
