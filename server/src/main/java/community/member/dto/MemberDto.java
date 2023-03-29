package community.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import community.member.entity.Member;
import lombok.*;

import javax.sound.midi.Patch;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class MemberDto {
    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Post{
        @NotBlank
        @Email
        private String email;

        @NotBlank(message = "이름은 공백이 아니여야 합니다.")
        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        @NotBlank(message = "패스워드를 입력해 주세요(최소 8자 최대 12자)")
        @Pattern(regexp = "[(a-zA-Z0-9)`~!@#\\$%\\^&*\\(\\)-_=\\+]{8,12}", message = "영문자와 숫자, !@#$%^&*()_+-=만 사용 가능합니다 ")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private  long memberId;

        private String name;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다")
        private String phone;

        private String password;

        private Member.MemberStatus memberStatus;

        public void setMemberId(long memberId){
            this.memberId=memberId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Response{
        @JsonProperty("member_id")
        private long memberId;
        private String email;
        private String name;
        private String phone;
        private String point;
        private String treeCount;
        private String profileUrl;
        @JsonProperty("member_status")
        private Member.MemberStatus memberStatus;
//        private List<String> roles; //role이 user뿐이라 필요없음
        private LevelDto levelDto;
        public String getMemberStatus(){
            return memberStatus.getStatus();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class donationRanks{ //기부 랭킹 Response
        private String name;
        private String treeCount;
    }
}

