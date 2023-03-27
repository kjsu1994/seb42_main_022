package community.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -370148426L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final NumberPath<Integer> boardCount = createNumber("boardCount", Integer.class);

    public final ListPath<community.board.entity.Board, community.board.entity.QBoard> boards = this.<community.board.entity.Board, community.board.entity.QBoard>createList("boards", community.board.entity.Board.class, community.board.entity.QBoard.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final ListPath<community.comment.entity.Comment, community.comment.entity.QComment> comments = this.<community.comment.entity.Comment, community.comment.entity.QComment>createList("comments", community.comment.entity.Comment.class, community.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final BooleanPath google = createBoolean("google");

    public final QLevel level;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<Member.MemberStatus> memberStatus = createEnum("memberStatus", Member.MemberStatus.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> treeCount = createNumber("treeCount", Integer.class);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.level = inits.isInitialized("level") ? new QLevel(forProperty("level"), inits.get("level")) : null;
    }

}

