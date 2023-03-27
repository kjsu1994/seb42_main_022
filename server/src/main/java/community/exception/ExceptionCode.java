package community.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_LOGIN(402,"login, please"),
    REFRESH_TOKEN_NOT_FOUND(424, "RefreshToken not found"),
    NOT_VALIDATE(423, "Not validate token"),
    BOARD_CANNOT_CHANGE(403,"게시판을 수정할 권한이 없습니다."),
    COMMENT_CANNOT_CHANGE(403,"질문을 수정할 권한이 없습니다."),

    MEMBER_NOT_FOUND(404, "고객의 정보를 찾을 수 없습니다."),
    BOARD_NOT_FOUND(404,"게시판의 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(404,"질문의 정보를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND(404, "토큰 정보를 찾을 수 없습니다."),
    NOT_AUTHORIZED(404, "not authorized, 인증받지 않은 접근입니다"),
    PASSWORD_NOT_CONFIRMED(404, "암호가 확인되지 않습니다."),

    MEMBER_EXISTS(409, "고객 정보가 이미 존재합니다."),
    BOARD_EXISTS(409, "게시판 정보가 이미 존재합니다."),
    COMMENT_EXISTS(409,"댓글이 이미 존재합니다."),

    LIKE_NOT_ACCEPTED(400, "회원당 한번만 좋아요를 누를 수 있습니다.");
    @Getter
    private final int status;


    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
