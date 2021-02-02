package com.praxis.staffy.utilerias.filtros;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.praxis.staffy.utilerias.exception.InvalidRequestTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class JwtUtil {
	
	private static String claveMaestra = "ljmnxbcytlkxjewi9wuie4yu7uyniruiewohxjkl-K2oiu8978AQWERT899IMBqweretryty-839048IDJ-SKLDCMJiuweriowemcx09213ui";
	
	public static String generarToken(Authentication auth) {
		log.info("Generando Token.");
		List<String> roles = new ArrayList<>();
		
		auth.getAuthorities().forEach(auto -> {
			roles.add(auto.getAuthority());
		});
		
		SignatureAlgorithm algoritmoSeguridad = SignatureAlgorithm.HS256;
		byte[] claveSecreta = Base64.getEncoder().encode(claveMaestra.getBytes());
		Key signingKey = new SecretKeySpec(claveSecreta, algoritmoSeguridad.getJcaName());
	     
	    JwtBuilder builder = Jwts.builder()
	            .setSubject(auth.getName())
	            .claim("rol",roles)
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 2))) // 2 horas 1000 * 60 * 60 * 2
				.setIssuedAt(new Date(System.currentTimeMillis())).signWith(signingKey)
				.setHeaderParam("typ", "JWT");
	    
	    return builder.compact();
	}

	private static Claims decodificarToken(String jwt) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(Base64.getEncoder().encode(claveMaestra.getBytes()))
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		
		return claims;
	}
	
	public static UsernamePasswordAuthenticationToken validarToken(HttpServletRequest request) {
		
        String token = request.getHeader("Authorization");
        Claims claims = null;
        
        if (token != null) {
        	
        	try {
    			claims = decodificarToken(token.replace("Bearer ", ""));
    		}catch(ExpiredJwtException ex) {
    			log.info("Token expirado.");
    			throw new InvalidRequestTokenException("Token expirado.");
    		} catch(JwtException ex) {
    			log.info("Token corrupto.");
    			throw new InvalidRequestTokenException("Token corrupto.");
    		}
        	
        	return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
        	
        } else {
        	throw new InvalidRequestTokenException("Token invalido.");
        }
        
	}
}