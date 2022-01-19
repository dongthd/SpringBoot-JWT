package vn.security.jwt;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import vn.security.userprincal.UserPrinciple;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.Secret}")
	private String jwtSecret;
	
	@Value("${jwt.Expiration}")
	private int jwtExpiration;
	
	public String createToken(Authentication authentication) {
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userPrinciple.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

	}
	
	public String doGenerateToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("dev")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5*60*60*1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature -> Message: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid format token -> Message {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired -> Message {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported -> Message {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty -> Message {}", e.getMessage());
		}
		return false;
	}

	public String getUserNameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
