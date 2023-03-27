package community.auth.refreshtoken;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class RefreshToken {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long refreshTokenId;
    @Column(nullable = false)
    private String refreshToken;
}
