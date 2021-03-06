package com.project.eaproject.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * JwtUtils Class which contains utility function related to json web token
 *
 * @version 1.0.0
 */
@Component
public class JwtUtils {

  private static List<String> jwtTokens = new ArrayList<>();

  /**
   * Method add token to list
   *
   * @param token String token value is passed
   */
  public static void addToken(String token) {
    jwtTokens.add(token);
  }

  /**
   * Method check token validity
   *
   * @param token String token value
   * @return boolean
   */
  public static boolean isValidToken(String token) {
//    return jwtTokens.contains(token);
    return true;
  }

  public String getSubject(String token) {
    return Jwts.parser()
        .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }


  public boolean validateToken(String token) throws Exception {
    try {
      Jwts.parser()
          .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
          .parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      throw new Exception(e.getMessage());
    } catch (MalformedJwtException e) {
      throw new MalformedJwtException(e.getMessage());
    } catch (ExpiredJwtException e) {
      throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
    } catch (UnsupportedJwtException e) {
      throw new UnsupportedJwtException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }


  private String getString(Authentication authentication, long validityDay) {
    List<String> roles = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

    String token = Jwts.builder()
        .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
        .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
        .setIssuer(SecurityConstants.TOKEN_ISSUER)
        .setAudience(SecurityConstants.TOKEN_AUDIENCE)
        .setSubject(authentication.getName())
        .setExpiration(new Date(System.currentTimeMillis() + validityDay))
        .claim("rol", roles)
        .compact();

    return token;
  }


  /**
   * Method getToken from request header
   *
   * @param request Takes request
   * @return header as a string
   */
  public static String getToken(HttpServletRequest request) {
    return request.getHeader(SecurityConstants.TOKEN_HEADER);
  }

}
