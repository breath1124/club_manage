package cn.zucc.edu.club.security;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.zucc.edu.club.entity.Result;
import cn.zucc.edu.club.service.impl.UserDetailServiceImpl;
import cn.zucc.edu.club.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginSuccessHeadler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    //登录成功时处理
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();

        //生成JWT,并放置到请求头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        httpServletResponse.setHeader(jwtUtils.getHeader(),jwt);

        UserDetails userDetails = userDetailService.loadUserByUsername(authentication.getName());
        Result success = Result.success(userDetails);
        String json = JSONUtil.toJsonStr(success);

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        out.flush();
        out.close();
    }
}
