package danielvishnievskyi.bachelorproject.security.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthUtil {
  private static final String secretJWT = ".RwTxMB3qR#XV*djaY.Uc7@C;Q3ibAVr3i6w9tw#?y657AYGeRgr&/q;k/HUWZ[$";
  private static final int accessTokenTime =1000 * 30 * 60 * 1000;
  private static final int refreshTokenTime = 2 * 60 * 60 * 1000;

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
