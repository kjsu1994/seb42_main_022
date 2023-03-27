package community.auth.refreshtoken;

import community.auth.jwt.JwtTokenizer;
import community.auth.oauth2.Oauth2MemberSuccessHandler;
import community.exception.BusinessLogicException;
import community.exception.ExceptionCode;
import community.member.entity.Member;
import community.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final Oauth2MemberSuccessHandler oauth2MemberSuccessHandler;

    public Map<String, String> refresh(String refreshToken) {

        // 유효성 검사
        if(!jwtTokenizer.validateToken(refreshToken)){
            throw new BusinessLogicException(ExceptionCode.NOT_VALIDATE);
        }
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payloadJWT = refreshToken.split("\\.")[1];
        String info = new String(decoder.decode(payloadJWT));
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(info);


        // 토큰 재발급
        long now = System.currentTimeMillis();
        String email = (String)jsonArray.get("sub");
        RefreshToken findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));
        Member member = memberRepository.findByEmail(email).get();
        String accessToken = oauth2MemberSuccessHandler.delegateAccessToken(member);
        Map<String, String> accessTokenResponseMap = new HashMap<>();

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        long refreshExpireTime = (long) jsonArray.get("exp") * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        if (diffDays < 1) {
            String newRefreshToken = oauth2MemberSuccessHandler.delegateRefreshToken(member);
            accessTokenResponseMap.put("Refresh", newRefreshToken);
            findRefreshToken.setRefreshToken(newRefreshToken);
            refreshTokenRepository.save(findRefreshToken);
        }
        accessTokenResponseMap.put("Authorization", "Bearer " + accessToken);
        return accessTokenResponseMap;
    }
}

