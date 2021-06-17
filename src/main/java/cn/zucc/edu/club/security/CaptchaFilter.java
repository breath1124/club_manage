package cn.zucc.edu.club.security;

import cn.zucc.edu.club.entity.Const;
import cn.zucc.edu.club.security.exception.CaptchaException;
import cn.zucc.edu.club.util.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    //自定义Security登录成功或失败处理
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String url = httpServletRequest.getRequestURI();

        //只需要拦截 /login POST请求
        if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")){
            try{
                //校验验证码
                vaildata(httpServletRequest);
            }catch (CaptchaException e){
                //如果不正确,就跳转到认证失败处理器
                loginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }
        }

        //验证成功 则继续往下面走
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Autowired
    private RedisUtil redisUtil;

    //校验验证码
    private void vaildata(HttpServletRequest request){
        //用户输入的验证码
        String code = request.getParameter("code");
        //验证码 在Redis中的Key
        String token = request.getParameter("token");

        if (StringUtils.isBlank(token) || StringUtils.isBlank(code)){
            throw new CaptchaException("验证码错误");
        }

        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,token))){
            throw new CaptchaException("验证码错误");
        }

        //清除redis 验证码一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY,token);
    }

}
