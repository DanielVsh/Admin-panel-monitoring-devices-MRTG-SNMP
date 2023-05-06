package danielvishnievskyi.bachelorproject.security.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;

import java.util.Date;

/**
 * AuthUtil is a utility class that provides methods to manage JWT tokens.
 *
 * @author [Daniel Vishnievskyi].
 */
@Getter
public class AuthUtil {
  // The secret key used to sign the JWT tokens
  private static final String secretJWT = ".RwTxMB3qR#XV*djaY.Uc7@C;Q3ibAVr3i6w9tw#?y657AYGeRgr&/q;k/HUWZ[$";
  // The time in milliseconds until the access token expires (10 minutes)
  private static final Integer accessTokenTime = 1000 * 60 * 10;
  // The time in milliseconds until the refresh token expires (24 hours)
  private static final Integer refreshTokenTime = 1000 * 60 * 60 * 24;

  /**
   * Returns a new HMAC256 Algorithm object with the secret key used to sign JWT tokens.
   *
   * @return the Algorithm object
   */
  public static Algorithm getSecuredJWT() {
    return Algorithm.HMAC256(secretJWT);
  }

  /**
   * Returns the expiration time for access tokens.
   * The expiration time is calculated as the current time plus the accessTokenTime field.
   *
   * @return the Date object representing the expiration time
   */
  public static Date getAccessTokenExpiresTime() {
    return new Date(System.currentTimeMillis() + accessTokenTime);
  }

  /**
   * Returns the expiration time for refresh tokens.
   * The expiration time is calculated as the current time plus the refreshTokenTime field.
   *
   * @return the Date object representing the expiration time
   */
  public static Date getRefreshTokenExpiresTime() {
    return new Date(System.currentTimeMillis() + refreshTokenTime);
  }
}
