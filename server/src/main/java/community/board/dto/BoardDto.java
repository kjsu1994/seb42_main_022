package community.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import community.board.entity.UploadFile;
import community.comment.dto.CommentDto;
import community.member.dto.LevelDto;
import community.member.dto.MemberDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {
    @Getter
    @Setter //@ModelAttribute 사용하기위함
    @NoArgsConstructor //@ModelAttribute 사용하기위함
    @ToString
    public static class Post {
        @Positive
        @NotNull
        private Long memberId;
        @NotBlank(message = "제목을 작성해주세요")
        @Size(max = 100, message = "100자 이내로 작성해 주세요.")
        private String title;

        //        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        @Size(max = 5000, message = "5000자 이내로 작성해주세요.")
        private String contents;

        public Post(Long memberId, String title, String contents) {
            this.memberId = memberId;
            this.title = title;
            this.contents = contents;
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @ToString
    public static class Patch {
        private Long questionId;
        @NotBlank(message = "제목을 작성해주세요")
        private String title;

        @Size(max = 5000, message = "5000자 이내로 작성해주세요.")
//        @NotBlank(message = "내용은 공백이 아니어야 합니다.")
        private String contents;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TotalPageResponse { //Post, Patch
        @JsonProperty("board_id")
        private Long boardId;
        private String title;
        private String contents;
        @JsonProperty("like_count")
        private int likeCount;
        @JsonProperty("view_count")
        private int viewCount;
        private String delegateImagePath;
        private MemberDto.Response member;
        private List<UploadDto> uploadDto;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
        @JsonProperty("modified_at")
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TotalPageListResponse { //전체조회 및 검색
        @JsonProperty("board_id")
        private Long boardId;
        private String title;
        private String contents;
        private String boardCreator; //게시글 작성자
        private String creatorLevel; //작성자 레벨정보
        @JsonProperty("like_count")
        private int likeCount;
        @JsonProperty("view_count")
        private int viewCount;
        private String delegateImagePath;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
        @JsonProperty("modified_at")
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DetailPageResponse { //단건조회, 댓글 포함
        @JsonProperty("board_id")
        private Long boardId;
        private String title;
        private String contents;
        @JsonProperty("like_count")
        private int likeCount;
        @JsonProperty("view_count")
        private int viewCount;
        private MemberDto.Response member;
        private int creatorLevel; //작성자 레벨
        private List<UploadDto> uploadDto;

        private List<CommentDto.InfoResponse> comments;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
        @JsonProperty("modified_at")
        private LocalDateTime modifiedAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RankResponse { //랭킹 및 좋아요 조회
        @JsonProperty("board_id")
        private Long boardId;
        private String title;
        @JsonProperty("like_count")
        private int likeCount;
        @JsonProperty("view_count")
        private int viewCount;
    }
}