package com.ncu.springboot.provider.user.bizservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ncu.springboot.biz.entity.Consumer;
import com.ncu.springboot.biz.entity.ControlUser;
import com.ncu.springboot.biz.rs.Res;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.provider.oauth2.service.impl.UserService;

@Component
@Service
public class UserBizServiceImpl implements UserBizService {

    @Resource
    private UserService userService;

    @Override
    public Res insert(User user) {
        return userService.insert(user);
    }

    @Override
    public int remove(int id) {
        return userService.remove(id);
    }

    @Override
    public int update(User user) {
        return userService.update(user);
    }

    @Override
    public User getDataById(int id) {
        return userService.getDataById(id);
    }

    @Override
    public int updateUserState(int id, int state) {
        return userService.updateUserState(id, state);
    }

    @Override
    public Page<User> userList(Map<String, Object> map, int pageNum, int pageSize) {
        return userService.userList(map, pageNum, pageSize);
    }

    @Override
    public boolean checkMobileExist(String mobile) {

        return userService.checkMobileExist(mobile);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userService.checkEmailExist(email);
    }

    @Override
    public boolean checkIdCardExist(String idCard) {
        return userService.checkIdCardExist(idCard);
    }

    @Override
    public int updatePassword(String password, int id) {
        return userService.updatePassword(password, id);
    }


    @Override
    public String findDeptId(String userCode) {
        return userService.findDeptId(userCode);
    }

    @Override
    public User findUser(String userCode) {
        return userService.findUser(userCode);
    }

    @Override
    public Consumer findConsumer(String userCode) {
        return userService.findConsumer(userCode);
    }

    @Override
    public ControlUser findAppConsumer(String userCode) {
        return userService.findAppConsumer(userCode);
    }

    @Override
    public String getUserCodeByUnionId(String union_id) {
        return userService.getUserCodeByUnionId(union_id);
    }

    @Override
    public User checkOpenId(String WXopenId, String QQopenId) {
        return userService.checkOpenId(WXopenId, QQopenId);
    }

    @Override
    public void binding(String id, String WXopenId, String QQopenId) {
        userService.binding(id, WXopenId, QQopenId);
    }


    @Override
    public void unbindingWx(String id) {
        userService.unbindingWx(id);
    }

    @Override
    public void unbindingQQ(String id) {
        userService.unbindingQQ(id);
    }

    @Override
    public List<Integer> findIds(List<User> user) {
        return userService.findIds(user);

    }

    @Override
    public List<User> selectUser(List<User> userList) {
        return userService.selectUser(userList);
    }

    @Override
    public int bindingAvatar(User user) {
        return userService.bindingAvatar(user);
    }

    @Override
    public User findId(String id) {
        return userService.findId(id);
    }

    @Override
    public Integer updateLog(String userId, String ip) {
        return userService.updateLog(userId, ip);
    }


}
