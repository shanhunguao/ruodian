package com.ncu.springboot.security;

import com.ncu.springboot.security.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ncu.springboot.security.config.SMSCodeAuthenticationSecurityConfig;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    /**
     * 注入认证处理类，处理不同用户跳转到不同的页面
     */
    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;

    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SMSCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

//    @Autowired
//    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
//        auth.authenticationProvider(provider);
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 用户授权操作
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();

        // 去掉 CSRF
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭session
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()//定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers(new String[]{"/user/sendSMS", "/loginbymobile", "/sms/sendSmsCode", "/html/**", "/login", "/user/findAll", "/static/**"}).permitAll()
                .and()

                .authorizeRequests()
                .anyRequest()
                .permitAll()
//                .access("@rbacauthorityservice.hasPermission(request,authentication)") // RBAC 动态 url 认证
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);


        // 记住我
        /*http.rememberMe().rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService).tokenValiditySeconds(1000);*/
    }

}
