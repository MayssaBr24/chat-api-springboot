package tn.essat.messaging.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.essat.messaging.model.Utilisateur;

@Component
public class GestionToken {

    @Value("${auth.expiration:604800}")
    private Long TOKEN_VALIDITY;

    @Value("${auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(Utilisateur user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("iduser",user.getId())
            .setIssuedAt(new Date())
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
            .compact();
    }

    public String getUserNameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }

    public boolean isTokenValid(String token, Utilisateur user) {
        String username = getUserNameFromToken(token);
        return username != null && username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}
