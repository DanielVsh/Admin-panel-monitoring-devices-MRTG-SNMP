package danielvishnievskyi.bachelorproject.security.utils;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlgorithmUtil {
  private static final String secretJWT = ".RwTxMB3qR#XV*djaY.Uc7@C;Q3ibAVr3i6w9tw#?y657AYGeRgr&/q;k/HUWZ[$";


  public static Algorithm getSecuredJWT() {
    return Algorithm.HMAC256(secretJWT);
  }
}
