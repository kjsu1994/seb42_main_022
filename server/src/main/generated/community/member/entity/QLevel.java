package community.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLevel is a Querydsl query type for Level
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLevel extends EntityPathBase<Level> {

    private static final long serialVersionUID = -567044376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLevel level1 = new QLevel("level1");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> levelExp = createNumber("levelExp", Integer.class);

    public final NumberPath<Long> levelId = createNumber("levelId", Long.class);

    public final QMember member;

    public final NumberPath<Integer> requiredExp = createNumber("requiredExp", Integer.class);

    public final NumberPath<Integer> totalExp = createNumber("totalExp", Integer.class);

    public final StringPath userName = createString("userName");

    public QLevel(String variable) {
        this(Level.class, forVariable(variable), INITS);
    }

    public QLevel(Path<? extends Level> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLevel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLevel(PathMetadata metadata, PathInits inits) {
        this(Level.class, metadata, inits);
    }

    public QLevel(Class<? extends Level> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

