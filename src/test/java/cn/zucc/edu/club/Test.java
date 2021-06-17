package cn.zucc.edu.club;

import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.service.ActivityService;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class Test {

    @Autowired
    private ActivityService activityService;

    private long time = 1000 * 60 * 60 * 24;
    private String signature = "admin";

    @org.junit.Test
    public void jwt() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", "tom")
                .claim("role", "admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();

        System.out.println(jwtToken);
    }

    @org.junit.Test
    public void parse() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2MjM4NDg0MjcsImp0aSI6IjJjYWQ2ZWU0LTFhN2YtNDFjYS1iYmMzLTVjM2NmMzY5ODM5NiJ9.G1KdVcXJLUx0f2ThYQRzmoclby0mpwwqmy67Anb0EZM";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }


    @org.junit.Test
    public void test() {
        PageInfo<Activity> activities = activityService.findStuByPage(0, 5);
        System.out.println(activities.getSize());
    }

}
