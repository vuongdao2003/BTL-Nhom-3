    package com.example.demo.untils;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Base64;

    @Component
    public class JwtUntilHelper {
        @Value("${jwt.privateKey}")
        private String privateKey;
        public String generateToken(String data,String role) {
            SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            String token = Jwts.builder().setSubject(data).claim("role",role).signWith(secretKey).compact();
            return token;
        }

        public boolean verifyToken(String token) {
            try {
                SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
                Jwts.parserBuilder().setSigningKey(Key)
                        .build().parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        public String getEmailFromToken(String token) {
            SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Claims claims = Jwts.parserBuilder().setSigningKey(Key)
                    .build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        }
        public String getRoleFromToken(String token) {
            SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Claims claims =Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(token).getBody();
            return claims.get("role", String.class);
        }
    }
