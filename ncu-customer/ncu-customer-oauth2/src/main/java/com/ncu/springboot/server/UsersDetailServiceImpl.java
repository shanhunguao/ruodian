package com.ncu.springboot.server;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.ncu.springboot.api.oauth2.bizservice.TbPermissionService;
import com.ncu.springboot.api.oauth2.bizservice.TbUserService;
import com.ncu.springboot.api.oauth2.entity.TbPermission;
import com.ncu.springboot.api.oauth2.entity.TbUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by zhoufan
 * @Date 2020/3/30 14:32
 */
@Service
public class UsersDetailServiceImpl implements UserDetailsService {

    @Reference
    private TbUserService tbUserService;
    @Reference
    private TbPermissionService tbPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser tbUser = tbUserService.getByUsername(username);
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (tbUser != null) {
            // 声明用户授权,获取用户权限
            List<TbPermission> tbPermissions = tbPermissionService.selectByUserId(tbUser.getId());
            tbPermissions.forEach(tbPermission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getPermissionEnname());
                grantedAuthorities.add(grantedAuthority);
            });
            System.out.println("grantedAuthorities = " + grantedAuthorities);
            // 由框架完成认证工作
            return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
        }
        return null;
    }
}
