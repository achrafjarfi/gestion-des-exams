package test.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import test.bo.Compte;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private  final String SECRET_KEY="413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
    public String extractUsername(String token){
        return  extractClaim(token , Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return  Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSigninKey(){
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(Compte user, Map<String,Object> extractClaims){
        String token= Jwts
                .builder()
                .subject(user.getLogin())
                .claims(extractClaims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+14*60*60*1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }
    public boolean isValid(String token, UserDetails user){
        String username=extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }
}
