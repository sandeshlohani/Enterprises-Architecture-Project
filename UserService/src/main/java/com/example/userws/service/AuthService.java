package com.example.userws.service;

import com.example.userws.dto.request.LoginRequest;
import com.example.userws.dto.request.RefreshTokenRequest;
import com.example.userws.dto.response.LoginResponse;
import com.example.userws.dto.response.RefreshTokenResponse;

public interface AuthService {
  LoginResponse login(LoginRequest loginRequest);

  RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception;
}
