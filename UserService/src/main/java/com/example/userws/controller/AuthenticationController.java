package com.example.userws.controller;

import com.example.userws.constant.ResponseMessageConstant;
import com.example.userws.constant.SecurityConstants;
import com.example.userws.dto.request.LoginRequest;
import com.example.userws.dto.request.RefreshTokenRequest;
import com.example.userws.dto.response.LoginResponse;
import com.example.userws.dto.response.LogoutResponse;
import com.example.userws.dto.response.RefreshTokenResponse;
import com.example.userws.exception.InvalidUserException;
import com.example.userws.service.AuthService;
import com.example.userws.utils.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

  private final AuthService authService;

  @Autowired
  public AuthenticationController(AuthService authService) {
    this.authService = authService;
  }


  /**
   * API returns Login response
   *
   * @param login current user Authentication
   * @return loginResponse
   */
  @PostMapping
  public LoginResponse returnLoginResponse(@RequestBody LoginRequest login) {
    try {
      return authService.login(login);
    } catch (BadCredentialsException e) {
      log.info("Bad Credentials");
      throw new InvalidUserException("Invalid credentials.");

    } catch (AuthenticationException ex) {
      return new LoginResponse(ResponseMessageConstant.ERROR, "", "", "", ResponseMessageConstant.FAILURE_LOGIN);
    }
  }

  /**
   * Method to logout and return to login page
   *
   * @return LogoutResponse
   */
  @GetMapping("/logout")
  @PreAuthorize("hasAnyRole('" + SecurityConstants.ROLE_ADMIN + "','" + SecurityConstants.ROLE_USER + "')")
  public LogoutResponse logout(HttpServletRequest req) {
    JwtUtils.removeToken(JwtUtils.getToken(req));
    return new LogoutResponse(ResponseMessageConstant.SUCCESS, ResponseMessageConstant.SUCCESS_LOGOUT);
  }

  @PostMapping("/refreshToken")
  public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
    return authService.refreshToken(refreshTokenRequest);
  }
}