package community.auth.dto;

import community.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMemberDto implements Serializable { //세션을 직렬화 하기 위한 Dto
    private String name;
    private String email;

    public SessionMemberDto(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
    }
}