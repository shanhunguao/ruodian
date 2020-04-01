package com.ncu.springboot.security.filter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.security.exception.ValidateCodeException;
import com.ncu.springboot.security.handler.ValidateFailureHandler;
import com.ncu.springboot.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

	@Reference
	private RoleCacheBizService roleCacheHelper;
	@Resource
	private RedisUtil redisUtil;

	@Autowired
	private ValidateFailureHandler validateFailureHandler;

/*	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab");
		String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        String smsCode = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_SMSCODE);

        if (smsCode != null && smsCode.length() == SecurityConstants.SMSCODE_SIZE) {

        	if (smsCode.equals("123456")) {
	        	// 如果请求头中有token，则进行解析，并且设置认证信息
	            SecurityContextHolder.getContext().setAuthentication(getAuthentication(mobile));
	            super.doFilter(request, response, filterChain);
        	} else {
        		BaseResponseBody ajaxResponseBody = new BaseResponseBody();
                ajaxResponseBody.setCode(String.valueOf(LoginErrorCode.SMSCODE_ERROR.getErrorCode()));
                ajaxResponseBody.setMessage(LoginErrorCode.SMSCODE_ERROR.getErrorMsg());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().println(objectMapper.writeValueAsString(ajaxResponseBody));
                response.getWriter().flush();
        		return;
        	}
        } else {
        	filterChain.doFilter(request, response);
            return;
        }
	}

	// 这里从token中获取用户信息并新建一个token
	private SMSCodeAuthenticationToken getAuthentication(String mobile) {
		if (mobile != null) {
			return new SMSCodeAuthenticationToken(mobile, new ArrayList<>());
		}
		return null;
	}*/

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;


	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		if (StringUtils.equalsIgnoreCase("/system/login", httpServletRequest.getRequestURI())
				&& StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
			try {
				String usercode =httpServletRequest.getParameter("usercode");
				String imageCode = httpServletRequest.getParameter("imageCode");
				if (imageCode!=null) {
					validateCode(usercode,imageCode);
				}
			} catch (ValidateCodeException e) {
				validateFailureHandler.onValidateFailure(httpServletRequest, httpServletResponse, e);
				return;
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private void validateCode(String usercode,String codeInRequest) throws ServletRequestBindingException {
		String codeInSession = (String)redisUtil.get("SESSION_KEY_IMAGE_CODE"+usercode);
		if (codeInSession == null) {
			throw new ValidateCodeException("验证码无效！");
		}
		if (!StringUtils.equalsIgnoreCase(codeInSession, codeInRequest)) {
			throw new ValidateCodeException("验证码不正确！");
		}
		redisUtil.del("SESSION_KEY_IMAGE_CODE"+usercode);

	}

}
