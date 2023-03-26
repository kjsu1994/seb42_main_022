package community.board.mapper;

import community.board.dto.BoardDto;
import community.board.dto.UploadDto;
import community.board.entity.Board;
import community.board.entity.UploadFile;
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
    date = "2023-03-27T07:12:29+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardPostToBoard(BoardDto.Post boardPostDto) {
        if ( boardPostDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setTitle( boardPostDto.getTitle() );
        board.setContents( boardPostDto.getContents() );

        return board;
    }

    @Override
    public Board boardPatchToBoard(BoardDto.Patch boardPatchDto) {
        if ( boardPatchDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setTitle( boardPatchDto.getTitle() );
        board.setContents( boardPatchDto.getContents() );

        return board;
    }

    @Override
    public BoardDto.DetailPageResponse boardToBoardDetailPageResponse(Board board, List<UploadDto> uploadDto) {
        if ( board == null && uploadDto == null ) {
            return null;
        }

        BoardDto.DetailPageResponse detailPageResponse = new BoardDto.DetailPageResponse();

        if ( board != null ) {
            detailPageResponse.setBoardId( board.getBoardId() );
            detailPageResponse.setTitle( board.getTitle() );
            detailPageResponse.setContents( board.getContents() );
            detailPageResponse.setLikeCount( board.getLikeCount() );
            detailPageResponse.setViewCount( board.getViewCount() );
            detailPageResponse.setMember( memberToResponse( board.getMember() ) );
            detailPageResponse.setCreatorLevel( board.getCreatorLevel() );
            detailPageResponse.setComments( commentListToInfoResponseList( board.getComments() ) );
            detailPageResponse.setCreatedAt( board.getCreatedAt() );
            detailPageResponse.setModifiedAt( board.getModifiedAt() );
        }
        List<UploadDto> list1 = uploadDto;
        if ( list1 != null ) {
            detailPageResponse.setUploadDto( new ArrayList<UploadDto>( list1 ) );
        }

        return detailPageResponse;
    }

    @Override
    public List<BoardDto.TotalPageListResponse> boardToBoardListResponse(List<Board> boards) {
        if ( boards == null ) {
            return null;
        }

        List<BoardDto.TotalPageListResponse> list = new ArrayList<BoardDto.TotalPageListResponse>( boards.size() );
        for ( Board board : boards ) {
            list.add( boardToTotalPageListResponse( board ) );
        }

        return list;
    }

    @Override
    public List<BoardDto.RankResponse> boardToBoardRankListResponse(List<Board> board) {
        if ( board == null ) {
            return null;
        }

        List<BoardDto.RankResponse> list = new ArrayList<BoardDto.RankResponse>( board.size() );
        for ( Board board1 : board ) {
            list.add( boardToBoardRankResponse( board1 ) );
        }

        return list;
    }

    @Override
    public BoardDto.RankResponse boardToBoardRankResponse(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.RankResponse rankResponse = new BoardDto.RankResponse();

        rankResponse.setBoardId( board.getBoardId() );
        rankResponse.setTitle( board.getTitle() );
        rankResponse.setLikeCount( board.getLikeCount() );
        rankResponse.setViewCount( board.getViewCount() );

        return rankResponse;
    }

    @Override
    public BoardDto.TotalPageResponse boardToBoardTotalPageResponse(Board board, List<UploadDto> uploadDto) {
        if ( board == null && uploadDto == null ) {
            return null;
        }

        BoardDto.TotalPageResponse totalPageResponse = new BoardDto.TotalPageResponse();

        if ( board != null ) {
            totalPageResponse.setBoardId( board.getBoardId() );
            totalPageResponse.setTitle( board.getTitle() );
            totalPageResponse.setContents( board.getContents() );
            totalPageResponse.setLikeCount( board.getLikeCount() );
            totalPageResponse.setViewCount( board.getViewCount() );
            totalPageResponse.setDelegateImagePath( board.getDelegateImagePath() );
            totalPageResponse.setMember( memberToResponse( board.getMember() ) );
            totalPageResponse.setCreatedAt( board.getCreatedAt() );
            totalPageResponse.setModifiedAt( board.getModifiedAt() );
        }
        List<UploadDto> list = uploadDto;
        if ( list != null ) {
            totalPageResponse.setUploadDto( new ArrayList<UploadDto>( list ) );
        }

        return totalPageResponse;
    }

    @Override
    public List<UploadDto> uploadFilesToUploadDtoList(List<UploadFile> uploadFiles) {
        if ( uploadFiles == null ) {
            return null;
        }

        List<UploadDto> list = new ArrayList<UploadDto>( uploadFiles.size() );
        for ( UploadFile uploadFile : uploadFiles ) {
            list.add( uploadFileToUploadDto( uploadFile ) );
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

    protected CommentDto.InfoResponse commentToInfoResponse(Comment comment) {
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

    protected List<CommentDto.InfoResponse> commentListToInfoResponseList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto.InfoResponse> list1 = new ArrayList<CommentDto.InfoResponse>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToInfoResponse( comment ) );
        }

        return list1;
    }

    protected BoardDto.TotalPageListResponse boardToTotalPageListResponse(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.TotalPageListResponse totalPageListResponse = new BoardDto.TotalPageListResponse();

        totalPageListResponse.setBoardId( board.getBoardId() );
        totalPageListResponse.setTitle( board.getTitle() );
        totalPageListResponse.setContents( board.getContents() );
        totalPageListResponse.setBoardCreator( board.getBoardCreator() );
        totalPageListResponse.setCreatorLevel( String.valueOf( board.getCreatorLevel() ) );
        totalPageListResponse.setLikeCount( board.getLikeCount() );
        totalPageListResponse.setViewCount( board.getViewCount() );
        totalPageListResponse.setDelegateImagePath( board.getDelegateImagePath() );
        totalPageListResponse.setCreatedAt( board.getCreatedAt() );
        totalPageListResponse.setModifiedAt( board.getModifiedAt() );

        return totalPageListResponse;
    }

    protected UploadDto uploadFileToUploadDto(UploadFile uploadFile) {
        if ( uploadFile == null ) {
            return null;
        }

        UploadDto uploadDto = new UploadDto();

        uploadDto.setFileId( uploadFile.getFileId() );
        uploadDto.setFileName( uploadFile.getFileName() );
        uploadDto.setImagePath( uploadFile.getImagePath() );

        return uploadDto;
    }
}
