package community.auth.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    @GetMapping("/reissue")
    public ResponseEntity<Map<String, String>> reIssueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Refresh");
        Map<String, String> tokens = authService.refresh(authorizationHeader);
        response.setHeader("Authorization", tokens.get("Authorization"));
        if(tokens.get("Refresh") != null)
            response.setHeader("Refresh", tokens.get("Refresh"));
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("deleteRefreshToken")
    public ResponseEntity deleteRefreshToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Refresh");
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(authorizationHeader).get();
        refreshTokenRepository.delete(refreshToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
