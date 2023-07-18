package com.education.system.config;

import cn.hutool.json.JSONUtil;
import com.education.system.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail("请先登录");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
