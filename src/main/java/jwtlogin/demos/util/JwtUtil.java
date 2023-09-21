package jwtlogin.demos.util;

import cn.hutool.core.lang.UUID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Enki
 * @ClassName JwtUtil
 * @Description: JWT工具
 * @Date 2023/9/21 21:08
 * @Version 1.0
 */
public class JwtUtil {
    //    设置有效时间
    public static final Long JWT_TTL = 72 * 60 * 60 * 1000L;
    //    设置密钥
    public static final String KEY = "JWTLOGIN";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * 生成jtw
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT() {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        //Payload
        Map<String, Object> claims = new HashMap<String, Object>();
        long expMillis = System.currentTimeMillis() + JWT_TTL;
        Date expDate = new Date(expMillis);
        //负载
        claims.put("id", "123456");
        claims.put("userName", "admin");
        return Jwts.builder()
                .setHeader(map)
                .setClaims(claims)
                .setId(getUUID())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expMillis))
                .setIssuer("HH")
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    private static Claims getClaimsFromJWT(String jwt) {
//        Claims claims = null;
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) {
        String jwt = createJWT();
        System.out.println(jwt);
        System.out.println(getClaimsFromJWT(jwt));

    }


}
