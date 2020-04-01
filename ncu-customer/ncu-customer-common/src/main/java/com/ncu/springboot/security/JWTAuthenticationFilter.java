package com.ncu.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.BaseResponseBody;
import com.ncu.springboot.core.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
    	
        /*User loginUser = null;
        
        try {
            loginUser = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

//        String usercode = request.getParameter("usercode");
        String username = request.getParameter("username");
        System.out.println("username = " + username);
        String password = request.getParameter("password");
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        CustomUserDetails jwtUser = (CustomUserDetails) authResult.getPrincipal();
        System.out.println("jwtUser:" + jwtUser.toString());
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), false);
        User user = new User();
        user.setId(jwtUser.getId());
        user.setUsercode(jwtUser.getUsername());
        user.setPassword(jwtUser.getPassword());
        user.setRoles(jwtUser.getRoles());
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);

        request.setAttribute("user", user);

        request.getRequestDispatcher("/loginSuccess").forward(request, response);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        BaseResponseBody ajaxResponseBody = new BaseResponseBody();
        ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.USER_LOGIN_FAILED.getErrorCode()));
        ajaxResponseBody.setMessage(LoginErrorCode.USER_LOGIN_FAILED.getErrorMsg());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
        response.getWriter().flush();
    }

}
