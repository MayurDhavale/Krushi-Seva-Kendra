package com.krushisevakendra.billing.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Create Secret Key
    private static final String SECRET_KEY ="myVerySecureSecretKeyForKrushiSevaKendraBillingApplication123456";

    //Convert String → SecretKey
    private SecretKey getSignInKey(){

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    //Create generateToken
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    private String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
    }

    //Extract Username
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Generic Claim Extractor
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //extract Expiration
    public Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }

    //token expired or not
    private boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    //Token Valid or Not
    public boolean isTokenValid(String token, UserDetails userDetails){

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
