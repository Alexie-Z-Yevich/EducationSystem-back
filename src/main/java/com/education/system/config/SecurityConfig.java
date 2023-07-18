package com.education.system.config;

import com.education.system.security.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    CaptchaFilter captchaFilter;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authentication -> authentication);
    }

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.ico",
    };

    // 定义一个 SecurityFilterChain bean，用于配置安全过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(URL_WHITELIST).permitAll().anyRequest().authenticated())
                .formLogin(form -> form.successHandler(loginSuccessHandler).failureHandler(loginFailureHandler))
                .logout(logout -> logout.logoutSuccessHandler(jwtLogoutSuccessHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    // 配置密码编码器
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


