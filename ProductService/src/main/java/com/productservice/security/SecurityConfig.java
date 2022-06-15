package com.productservice.security;


import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

/**
 * SecurityConfiguration  class  it is responsible for  giving access to specific url, specific resource specific role
 *
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Method to  giving access to specific url, specific resource to specific role
   *
   * @param http HttpSecurity
   * @throws Exception is thrown if user is not authenticated
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET,"/api/v1/products/**").permitAll()
        .antMatchers(HttpMethod.POST,"/api/v1/products/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.DELETE,"/api/v1/products/**").hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PUT,"/api/v1/products/**").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher("/login?logout"))
        .logoutSuccessUrl("/").permitAll()
        .and().csrf().disable()
        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.headers().frameOptions().sameOrigin();
  }

  @Bean
  public RequestDataValueProcessor requestDataValueProcessor() {
    return new CsrfRequestDataValueProcessor();
  }

  /**
   * Method CorsConfigurationSource for allowing resource files
   *
   * @return source CorsConfigurationSource
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.applyPermitDefaultValues();
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
