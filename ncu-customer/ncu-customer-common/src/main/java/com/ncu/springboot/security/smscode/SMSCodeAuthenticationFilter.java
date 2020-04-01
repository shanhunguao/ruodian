package com.ncu.springboot.security.smscode;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.error.LoginErrorCode;
import com.ncu.springboot.biz.rs.BaseResponseBody;
import com.ncu.springboot.core.util.JwtTokenUtil;
import com.ncu.springboot.security.CustomUserDetails;
import com.ncu.springboot.security.constants.SecurityConstants;
import com.ncu.springboot.security.exception.MobileNotFoundException;
import com.ncu.springboot.security.exception.SMSCodeErrorException;

public class SMSCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
	
	private boolean postOnly = true;
	
	public SMSCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN__PROCESSING_URL_MOBILE, "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		System.out.println("dddddddddddddddddddddddddddddddd");
		
//		if (postOnly && !request.getMethod().equals("POST")) {
//			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//		}
//		
//		String mobile = obtainMobile(request);
//		
//		if (Objects.isNull(mobile)) {
//			mobile = "";
//		}
//		
//		mobile = mobile.trim();
		
		String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        String smsCode = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_SMSCODE);
        
        if (smsCode != null && smsCode.length() == SecurityConstants.SMSCODE_SIZE) {
			SMSCodeAuthenticationToken authRequest = new SMSCodeAuthenticationToken(mobile, smsCode);
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
        } else {
        	throw new SMSCodeErrorException("验证码错误");
        }
        
	}
	
	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}
	
	protected void setDetails(HttpServletRequest request, SMSCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
	
	public void setMobileParamter(String mobileParameter) {
		Assert.hasText(mobileParameter, "Moblie parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}
	
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	
	public final String getMobileParameter() {
		return mobileParameter;
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
    	
    	if (failed instanceof MobileNotFoundException) {
    		ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.MOBILE_ERROR.getErrorCode()));
    		ajaxResponseBody.setMessage(LoginErrorCode.MOBILE_ERROR.getErrorMsg());
    	} else if (failed instanceof SMSCodeErrorException) {
    		ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.SMSCODE_ERROR.getErrorCode()));
            ajaxResponseBody.setMessage(LoginErrorCode.SMSCODE_ERROR.getErrorMsg());
    	}
    	
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
        response.getWriter().flush();
    }

}
