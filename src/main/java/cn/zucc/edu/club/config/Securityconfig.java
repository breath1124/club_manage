package cn.zucc.edu.club.config;

import cn.zucc.edu.club.security.*;
import cn.zucc.edu.club.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解标注 哪些方法需要鉴权 @PreAuthorize("hasRole('admin')") @PreAuthorize("hasAuthority('sys:user:save')")
public class Securityconfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHeadler loginSuccessHeadler;

    @Autowired
    private CaptchaFilter captchaFilter;

    //JWT异常处理
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // 其他异常处理
    @Autowired
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;

    //自定义JWT识别
    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }


    //数据库加密方式
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //数据库获取用户信息
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    //登出
    @Autowired
    private JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

    //跨域配置
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    //不需要拦截的白名单
    private static final String[] URL_WHITELIST={
            "/login",
            "/logout",
            "/captcha",
            "/*.ico",
            "/swagger-ui.html",
            "/doc.html",
            "/student/login",
            "/student/add",
            "/webjars/**",
            "/swagger-resources",
            "/v2/api-docs"
    };

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域 关闭csrf
        http.cors().and().csrf().disable()
                //登录配置
                .formLogin()
                .successHandler(loginSuccessHeadler) //自定义登录成功时处理
                .failureHandler(loginFailureHandler) //自定义登录失败时处理
                // 登出配置
                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler) //登出成功时处理
                //禁用Session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不生成session
                //配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll() //白名单中路径 不需要拦截
                .anyRequest().authenticated()
                //异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //JWT异常处理
                .accessDeniedHandler(jwtAccessDeniedHandler)//其他异常处理
                //配置自定义过滤器
                .and()
                //自定义JWT识别过滤器
                .addFilter(jwtAuthenticationFilter())
                //自定义验证码过滤器 在 账户密码过滤器之前执行
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }


    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //数据库认证方式
        auth.userDetailsService(userDetailsService);
    }

}
