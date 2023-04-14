package danielvishnievskyi.bachelorproject.security.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Getter
@Setter
public class AuthUtil {
  private static final String secretJWT = ".RwTxMB3qR#XV*djaY.Uc7@C;Q3ibAVr3i6w9tw#?y657AYGeRgr&/q;k/HUWZ[$";
  private static final Integer accessTokenTime = 1000 * 60 * 10;
  private static final Integer refreshTokenTime = 1000 * 60 * 60 * 24;

  public static Algorithm getSecuredJWT() {
    return Algorithm.HMAC256(secretJWT);
  }
  public static Date getAccessTokenExpiresTime() {
    return new Date(System.currentTimeMillis() + accessTokenTime);
  }
  public static Date getRefreshTokenExpiresTime() {
    return new Date(System.currentTimeMillis() + refreshTokenTime);
  }
}
