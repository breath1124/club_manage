package cn.zucc.edu.club.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Data
@Component
public class JwtTokenUtils {

    //过期时间
    private Long expire = 3600L * 1000 * 24;
    //private long EXPIRATION = 3600L * 24;

    //密钥
    private String secret = "tfdt5g4df86vr2srg45vdtg852s1f2sv";
    //private String SECRET = "ClubByTf";

    //JWT名称
    private String header = "Authorization";
    //public String header = "Authorization";

    public String TOKEN_PREFIX = "Bearer ";

    //签发者
    private String ISS = "tengfei";

    // 角色的key
    private String ROLE_CLAIMS = "rol";


    public String generateToken(String username) {
//        long expiration = isRememberMe ? EXPIRATION : EXPIRATION_REMEMBER;
        HashMap<String, Object> map = new HashMap<>();
//        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secret)
//                .setClaims(map)
                .setIssuer(ISS)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }

    // 从token中获取用户名
    public String getUsername(String token) {
        return getClaimsByToken(token).getSubject();
    }

    // 获取用户角色
    public String getUserRole(String token) {
        return (String) getClaimsByToken(token).get(ROLE_CLAIMS);
    }

    // 是否已过期
    public boolean isExpiration(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public Claims getClaimsByToken(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secret) //密钥
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }
}
