package community.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import community.comment.entity.Comment;
import community.config.AuditingFields;
import community.like.entity.BoardLike;
import community.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
public class Board extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;
    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String contents; //텍스트 에디터 text
    @Column(nullable = false)
    private int likeCount;
    private String boardCreator; //게시글 작성자
    private int creatorLevel; //작성자 레벨
    private String delegateImagePath; //게시판 대표이미지 URL
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount;
    @Enumerated(value = EnumType.STRING) //Enum값이 string Type으로 들어감
    @Column(length = 20)
    private Board.KindOfBoard kindOfBoard; //게시판 분류
    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    @JsonIgnore // 순환참조 방지
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환참조 방지
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore // 순환참조 방지
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true) // 게시판과 생명주기 동일하게 관리
    @ToString.Exclude // ToString
    private List<UploadFile> uploadFiles = new ArrayList<>();
    @JsonIgnore // 순환참조 방지
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true) // 게시판과 생명주기 동일하게 관리
    @ToString.Exclude // ToString
    private List<BoardLike> boardLikes = new ArrayList<>();  // 좋아요 누르면 삭제안되는 오류방지


    //equals 해시코드 기능 생성 / id가 같으면 같은게시물로 취급
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return boardId!=null && boardId.equals(board.boardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId);
    }

    /*게시판 분류에 대한 Enum*/
    public enum KindOfBoard{
        GREEN_ACTIVE("녹색활동"),
        FREE_BOARD("자유게시판"),
        ECO_REVIEW("에코리뷰");
        @Getter
        private String status;
        KindOfBoard(String status){
            this.status=status;
        }
    }
}
