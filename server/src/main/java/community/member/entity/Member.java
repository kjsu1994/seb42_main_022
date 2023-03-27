package community.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import community.board.entity.Board;
import community.board.entity.UploadFile;
import community.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    String password;
//    private String likeStatus; // 좋아요 눌렀는지 여부

    @Column(nullable = false)
    boolean google;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int point;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int treeCount;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int boardCount;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int commentCount;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore // 순환참조 방지
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) //생명주기 동일하게 관리
    @JoinColumn(name = "level_id")
    private Level level;

    public Member(String email, String name, String phone, String password){
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.password=password;
    }

    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_QUIT("회원탈퇴");

        @Getter
        private String status;

        MemberStatus(String status){
            this.status=status;
        }
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}
