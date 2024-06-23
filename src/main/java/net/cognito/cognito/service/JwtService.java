package net.cognito.cognito.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.cognito.cognito.config.EnvironmentVariables;
import net.cognito.cognito.dto.UserPayload;
import net.cognito.cognito.model.User;
import net.cognito.cognito.model.enums.Role;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String PAYLOAD = "payload";

    public String generateToken(User user) {
        UserPayload userPayload = new UserPayload();
        userPayload.setId(user.getId());
        userPayload.setName(user.getName());
        userPayload.setEmail(user.getEmail());
        userPayload.setRole(user.getRole().name());

        return Jwts
                .builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)))
                .claim(PAYLOAD, userPayload)
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get(PAYLOAD, Map.class).get("id")).toString();
    }

    public Role extractUserRole(String token) {
        String role = extractClaim(token, claims -> claims.get(PAYLOAD, Map.class).get("role")).toString();
        return Role.valueOf(role);
    }

    public boolean isValid(String token, User user) {
        String userId = extractUserId(token);
        return userId.equals(user.getId()) && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(
                EnvironmentVariables.getEnvVariable("JWT_SECRET_KEY")
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
