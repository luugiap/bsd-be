package com.example.bds.security;

import com.example.bds.entity.rbac.Users;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.checksums.Algorithm;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Component

public class JwtUltil {

    @Value("${security.jwt.secret}")
    private String base64Secret;


    private SecretKey secretKey;

    @PostConstruct
    public SecretKey init() {
           secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
           return secretKey;
    }

    public String generateToken(Users user) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*1000))
                .setSubject(user.getUsername())
                .setIssuer("bds")
                .signWith(secretKey)
                .compact();

    }

    public String generateRefreshToken(Users user) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*10000))
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .setIssuer("bds")
                .signWith(secretKey)
                .compact();

    }

    public String generateTokenSecret(Users user) {
        try {
            //Khởi tạo header với thuật toán HS512
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

            //khởi tạo claim

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("bds")
                    .expirationTime(new Date(System.currentTimeMillis() + 60*60*1000))
                    .issueTime(new Date())
                    .build();

            //tạo payload
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(jwsHeader,payload);

            MACSigner macSigner = new MACSigner(secretKey.getEncoded());

            jwsObject.sign(macSigner);
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public Claims parseToken(String token) {
        return
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

    }
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token) {
        String username = parseToken(token).getSubject();
        try {
            if(isTokenExpired(token)) {
                return false;
            }
            if(username == null) {
                return false;
            }

        }
        catch (Exception e) {
            return false;
        }
        return true;
    }


}
