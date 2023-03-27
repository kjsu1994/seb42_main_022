package community.auth.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import community.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();

        response.setCharacterEncoding("UTF-8"); // objectMapper.writeValueAsString 한글깨지는 것에 대한 대응코드

        /*MemberDetails에서 유저정보를 가져와서 리스폰스에 뿌려주는 부분*/
        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("memberId", member.getMemberId());
        loginResponse.put("email", member.getEmail());
        loginResponse.put("roles", member.getRoles());
        loginResponse.put("level", member.getLevel().getLevel()); //추가
        loginResponse.put("name", member.getName());
        loginResponse.put("point", member.getPoint());


        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(loginResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(responseBody);

        log.info("# Authenticated successfully");
        log.info("name:{}, email: {}, role: {}", member.getName(), member.getEmail(), member.getRoles() );
    }
}
