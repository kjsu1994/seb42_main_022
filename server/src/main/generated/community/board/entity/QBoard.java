package community.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 585849128L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final community.config.QAuditingFields _super = new community.config.QAuditingFields(this);

    public final StringPath boardCreator = createString("boardCreator");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final ListPath<community.comment.entity.Comment, community.comment.entity.QComment> comments = this.<community.comment.entity.Comment, community.comment.entity.QComment>createList("comments", community.comment.entity.Comment.class, community.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> creatorLevel = createNumber("creatorLevel", Integer.class);

    public final StringPath delegateImagePath = createString("delegateImagePath");

    public final EnumPath<Board.KindOfBoard> kindOfBoard = createEnum("kindOfBoard", Board.KindOfBoard.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final community.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath title = createString("title");

    public final ListPath<UploadFile, QUploadFile> uploadFiles = this.<UploadFile, QUploadFile>createList("uploadFiles", UploadFile.class, QUploadFile.class, PathInits.DIRECT2);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new community.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

