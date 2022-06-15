package com.example.userws.constant;


/**
 * Security Terms
 */
public final class SecurityConstants {

  // Signing key for HS512 algorithm
  // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
  public static final String JWT_SECRET = "+MbQeShVmYq3t6w9z$C&F)J@NcRfUjWnZr4u7x!A%D*G-KaPdSgVkYp2s5v8y/B?";

  // JWT token defaults
  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer";
  public static final String TOKEN_TYPE = "JWT";
  public static final String TOKEN_ISSUER = "secure-api";
  public static final String TOKEN_AUDIENCE = "secure-app";
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final String ROLE_USER = "ROLE_USER";

}
