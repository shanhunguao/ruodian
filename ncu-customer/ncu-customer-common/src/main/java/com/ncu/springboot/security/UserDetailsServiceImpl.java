package com.ncu.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.MessageCacheBizService;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.dao.UserSecurityMapper;
import com.ncu.springboot.security.exception.MobileNotFoundException;
import com.ncu.springboot.security.exception.SMSCodeErrorException;

@Component
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserSecurityMapper userMapper;

    @Reference
    private MessageCacheBizService messageCacheHelper;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
        System.out.println("usercode = " + usercode);

        //通过username查询用户
        User user = userMapper.getUserInfoAndRolesByUsercode(usercode);

        if (user == null) {
            //仍需要细化处理
            throw new UsernameNotFoundException("该用户不存在");
        }

        System.out.println(user);
 
        /*Set authoritiesSet = new HashSet();
        // 模拟从数据库中获取用户角色
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
 
        authoritiesSet.add(authority);
        user.setAuthorities(authoritiesSet);*/

        return new CustomUserDetails(user);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile, String smsCode) throws MobileNotFoundException {

        String cacheSmsCode = messageCacheHelper.getSMSCodeByMobile(mobile);
        if (cacheSmsCode == null || !cacheSmsCode.equals(smsCode)) {
            throw new SMSCodeErrorException("验证码错误");
        }

        //通过username查询用户
        User user = userMapper.getUserInfoAndRolesByMobile(mobile);
        if (user == null) {
            //仍需要细化处理
            throw new MobileNotFoundException("该手机号不存在");
        }

        System.out.println(user);

        System.out.println("qqqqqqqqqqqqqqqqqqqqqqq");
 
        /*Set authoritiesSet = new HashSet();
        // 模拟从数据库中获取用户角色
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
 
        authoritiesSet.add(authority);
        user.setAuthorities(authoritiesSet);*/

        return new CustomUserDetails(user);
    }

}
