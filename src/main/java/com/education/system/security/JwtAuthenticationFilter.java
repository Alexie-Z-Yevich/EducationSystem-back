package com.education.system.security;

import cn.hutool.core.util.StrUtil;
import com.education.system.entity.StuMessage;
import com.education.system.service.StuMessageService;
import com.education.system.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    StuMessageService stuMessageService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtils.getClaimByToken(jwt);
        if (claims == null) {
            throw new JwtException("token异常");
        }

        if (jwtUtils.isTokenExpired(claims)) {
            throw new JwtException("token已过期");
        }

        String stuId = claims.getSubject();
        // 获取用户的权限信息

        StuMessage stuMessage = stuMessageService.getByStuId(stuId);

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(stuId, null, userDetailsService.getUserAuthorities(stuMessage.getId()));


        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
