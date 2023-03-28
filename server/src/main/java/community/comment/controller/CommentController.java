package community.comment.controller;

import community.board.service.BoardService;
import community.comment.dto.CommentDto;
import community.comment.entity.Comment;
import community.comment.mapper.CommentMapper;
import community.comment.service.CommentService;
import community.globaldto.MultiResponseDto;
import community.globaldto.SingleResponseDto;
import community.like.dto.CommentLikeDto;
import community.like.service.CommentLikeService;
import community.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentMapper mapper;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final MemberService memberService;
    private final BoardService boardService;


    @PostMapping //post
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDto.Post commentPostDto) {

        Comment postComment = mapper.commentPostDtoToComment(commentPostDto);

        postComment.setMember(memberService.findVerifiedMember(commentPostDto.getMemberId()));
        postComment.setBoard(boardService.findBoard(commentPostDto.getBoardId()));

        Comment comment = commentService.createComment(postComment);
        Comment comment1 = commentService.findComment(comment.getCommentId());

        CommentDto.InfoResponse response = mapper.commentToCommentInfoResponse(comment1);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }
    //patch
    @PatchMapping("/{comment-id}")
    public ResponseEntity<?> patchComment(@Valid @RequestBody CommentDto.Patch commentPatchDto,
                                          @Positive @PathVariable("comment-id") long commentId) {
        commentPatchDto.setCommentId(commentId);

        Comment requestComment = mapper.commentPatchDtoToComment(commentPatchDto);
        Comment comment = commentService.updateComment(requestComment);
        CommentDto.InfoResponse response = mapper.commentToCommentInfoResponse(comment);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
    //get
    @GetMapping("/{comment-id}")
    public ResponseEntity<?> getComment(@PathVariable("comment-id") long commentId) {
        Comment comment = commentService.findComment(commentId);

        CommentDto.InfoResponse response = mapper.commentToCommentInfoResponse(comment);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    //gets
    @GetMapping
    public ResponseEntity getComments(@RequestParam int page, int size) {
        Page<Comment> pageComments = commentService.findComments(page - 1, size);

        List<Comment> comments = pageComments.getContent();
        List<CommentDto.InfoResponse> response = mapper.commentToCommentResponses(comments);

        return new ResponseEntity<>(new MultiResponseDto<>(response, pageComments), HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{comment-id}")
    public ResponseEntity<?> deleteComment(@PathVariable("comment-id") long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{comment-id}/Like")  // 좋아요
    public ResponseEntity<?> upLikeComment(@PathVariable("comment-id") long commentId,
                                           @Valid @RequestBody CommentLikeDto requestBody) {

        Comment likeComment = commentLikeService.commentLikeUp(requestBody.getMemberId(), commentId);
        CommentDto.Response response = mapper.commentToCommentResponse(likeComment);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
