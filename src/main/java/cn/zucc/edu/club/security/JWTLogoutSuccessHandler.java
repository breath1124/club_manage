package cn.zucc.edu.club.security;

import cn.hutool.json.JSONUtil;
import cn.zucc.edu.club.entity.Result;
import cn.zucc.edu.club.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //如果权限信息不为空 需要进行一个手动的登出操作
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(httpServletRequest,httpServletResponse,authentication);
        }

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();

        //生成JWT,并放置到请求头中
        httpServletResponse.setHeader(jwtUtils.getHeader(),"");

        Result fail = Result.success("登出成功");

        out.write(JSONUtil.toJsonStr(fail).getBytes("UTF-8"));

        out.flush();
        out.close();
    }
}

