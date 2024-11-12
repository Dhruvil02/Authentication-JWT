package com.example.jwt_auth_api.services;

import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
//Build Token, decode Token
@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String SecretKey;
    @Value("${security.jwt.expiration-time}")
    private long expiration;

    //Get Decoded SecretKey in Key fromat
    private Key getSignInKey(){
        byte[] encodedKey=Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(encodedKey);
    }
    private Claims getALLClaims(String Token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(Token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getALLClaims(token);
        return claimsResolver.apply(claims);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername (String token){
        return extractClaim(token, Claims::getSubject);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public boolean isTokenValid (String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
        ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // public String generateToken(Authentication authentication) {
    //     String username = authentication.getName();
    //     Date now = new Date();
    //     Date expiryDate = new Date(now.getTime() + expiration);

    //     return Jwts.builder()
    //             .setSubject(username)
    //             .setIssuedAt(new Date())
    //             .setExpiration(expiryDate)
    //             .signWith(getSignInKey(), SignatureAlgorithm.HS256)
    //             .compact();
    // }

    //helper of buildToken()
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, expiration);
    }

    public long getExpiration(){
        return expiration;
    }
}
