package com.ncu.springboot.security;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.RoleCacheBizService;
import com.ncu.springboot.api.cache.bizservice.UserCacheBizService;
import com.ncu.springboot.biz.entity.Permission;

@Component("rbacauthorityservice")
public class RbacAuthorityService {
	
	@Reference
	private RoleCacheBizService roleCacheHelper;
	
	@Reference
	private UserCacheBizService userCacheHelper;
    
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    	
    	String requestUrl = request.getRequestURI();
    	System.out.println("requestUrl is " + requestUrl);
        
        String token = (String) authentication.getPrincipal();
        
        System.out.println(token);
 
        boolean hasPermission  = false;
        
        String usercode = userCacheHelper.getUsercodeByToken(token);
        
        List<Object> role_ids = roleCacheHelper.getRoleIdsByUsercode(usercode);
        
        if (role_ids.size() == 0) {
        	return false;
        } else {
        	AntPathMatcher antPathMatcher = new AntPathMatcher();
        	
//        	Set<String> urls = new HashSet();
            // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
        	for (int i = 0; i < role_ids.size(); i++) {
        		Set<Object> permission_ids = roleCacheHelper.getPermissionIdsByRoleId((String) role_ids.get(i));
        		if (permission_ids == null) {
        			continue;
        		}
        		
        		for (Object permission_id : permission_ids) {
        			Permission permission = roleCacheHelper.getPermissionByPermissionId((String) permission_id);
        			if (antPathMatcher.match("/springboot" + permission.getUrl(), requestUrl)) {
//        				hasPermission = true;
        				return true;
        			}
        		}
        		
        	}
 
            return hasPermission;
        }
 
    }
    
}
