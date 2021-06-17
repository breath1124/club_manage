package cn.zucc.edu.club.security;

import cn.hutool.core.util.StrUtil;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.StudentService;
import cn.zucc.edu.club.service.impl.UserDetailServiceImpl;
import cn.zucc.edu.club.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Autowired
    private JwtTokenUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private StudentService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(jwtUtils.getHeader());

        if(StrUtil.isBlankOrUndefined(header)){
            chain.doFilter(request,response);
            return;
        }
        //工具类解析JWT
        Claims token = jwtUtils.getClaimsByToken(header);

        if (token == null){
            throw new JwtException("token异常");
        }

        if (jwtUtils.isExpiration(token)){
            log.info("jwt过期");
            throw new JwtException("token过期");
        }

        String username = token.getSubject();

        //查询数据库-用户名关联的用户
        Student user = sysUserService.getByStuNum(username);

        //获取用户权限等信息
        UsernamePasswordAuthenticationToken authentication =
                // 参数: 用户名 密码 权限信息
//                new UsernamePasswordAuthenticationToken(username,null,userDetailsService.getUserAuthority(user.getStuId()));
                new UsernamePasswordAuthenticationToken(username,null, null);

        //后续security就能获取到当前登录的用户信息了，也就完成了用户认证。
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request,response);
    }
}

