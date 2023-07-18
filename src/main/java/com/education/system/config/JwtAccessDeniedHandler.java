package com.education.system.config;

import cn.hutool.json.JSONUtil;
import com.education.system.common.lang.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail(accessDeniedException.getMessage());

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
