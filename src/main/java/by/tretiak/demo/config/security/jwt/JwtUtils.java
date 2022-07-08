package by.tretiak.demo.config.security.jwt;

import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.service.security.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.rmi.ServerException;
import java.security.AccessControlException;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expirationMs}")
    private int expirationJwt;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationJwt)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public boolean validateJwtToken(String jwt) throws ServerException, AccessControlException {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt);
            return true;
        }catch (ExpiredJwtException | MalformedJwtException e) {
            throw new AccessControlException(ExceptionMessageSource.getMessage(ExceptionMessageSource.NOT_AUTHORIZED));
        } catch (Exception e) {
            throw new ServerException(ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR));
        }
    }

    public String getUsernameFromJwt(String jwt) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(jwt).getBody().getSubject();
    }

}
