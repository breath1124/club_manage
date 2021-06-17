package cn.zucc.edu.club.security;

import cn.hutool.json.JSONUtil;
import cn.zucc.edu.club.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info("权限不够！！");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
        ServletOutputStream out = httpServletResponse.getOutputStream();

        Result fail = Result.fail(e.getMessage());
        out.write(JSONUtil.toJsonStr(fail).getBytes("UTF-8"));

        out.flush();
        out.close();
    }
}
