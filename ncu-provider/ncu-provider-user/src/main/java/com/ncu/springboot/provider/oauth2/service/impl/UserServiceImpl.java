package com.ncu.springboot.provider.oauth2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.ncu.springboot.biz.entity.*;
import com.ncu.springboot.common.constant.GateConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.StringUtil;
import com.ncu.springboot.dao.UserMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;


    @Override
    public User getDataById(int id) {
        return userMapper.getDataById(id);

    }

    @Override
    @Transactional
    public Res insert(User user) {
        if (!StringUtil.isEmpty(user.getEmployee().getEmployeeId())) {
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                userMapper.insertSelective(user);
                User user1 = userMapper.selectId(user.getUsercode());
                //		设置用户初始化密码
                for (Role role : user.getRoles()) {
                    userMapper.bindingRole(user1.getId(), role.getRoleId());
                }

                return Res.SUCCESS();
            }
            return Res.ERROR("角色不能为空");
        }
        return Res.ERROR("员工不能为空");
    }


    @Override
    @Transactional
    public int remove(int id) {
        userMapper.delRole(id);
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int update(User user) {
        userMapper.delRole(user.getId());
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Role role : user.getRoles()) {
                userMapper.addRole(user.getId(), role.getRoleId());
            }

        }
        return userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    public int updateUserState(int id, int state) {
        return userMapper.updateUserState(id, state);
    }

    @Override
    public Page<User> userList(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.userList(map, pageNum, pageSize);
    }

    @Override
    public boolean checkMobileExist(String mobile) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("mobile", mobile);
        if (userMapper.selectCountByExample(example) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkEmailExist(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        if (userMapper.selectCountByExample(example) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIdCardExist(String idCard) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("idCard", idCard);
        if (userMapper.selectCountByExample(example) >= 1) {
            return true;
        }
        return false;
    }

    public static boolean valudatePassword(String plainPassword, String password) {
        return password.equals((plainPassword));
    }

    @Override
    public int updatePassword(String password, int id) {
        return userMapper.updatePassword(password, id);
    }


    @Override
    public String findDeptId(String userCode) {
        return userMapper.findDeptId(userCode);
    }

    @Override
    public User findUser(String userCode) {
        return userMapper.findUser(userCode);
    }

    @Override
    public Consumer findConsumer(String userCode) {
        return userMapper.findConsumer(userCode);
    }

    @Override
    public ControlUser findAppConsumer(String userCode) {
        if (userCode != null && userCode.length() == 6) {
            Teacher teacher = userMapper.findTeacher(userCode);
            setDeptIds(teacher.getRole().getRoleId(), teacher);
            return teacher;
        }
        Student student = userMapper.findStudent(userCode);
        setDeptIds(student.getRole().getRoleId(), student);
        return student;
    }

    /**
     * 设置部门管理员管理的部门权限
     */
    private void setDeptIds(String roleId, ControlUser controlUser) {
        if (GateConstant.ROLE_DEPT_ADMIN.equals(roleId)) {
            controlUser.setDeptIds(userMapper.findDeptIds(controlUser.getUserCode()));
        }
    }

    @Override
    public String getUserCodeByUnionId(String union_id) {
        return userMapper.getUserCodeByUnionId(union_id);
    }

    @Override
    public User checkOpenId(String WXopenId, String QQopenId) {
        return userMapper.checkOpenId(WXopenId, QQopenId);
    }

    @Override
    public void binding(String id, String WXopenId, String QQopenId) {
        userMapper.binding(id, WXopenId, QQopenId);
    }


    @Override
    public void unbindingWx(String id) {
        userMapper.unbindingWx(id);
    }

    @Override
    public void unbindingQQ(String id) {
        userMapper.unbindingQQ(id);
    }

    @Override
    public List<Integer> findIds(List<User> user) {
        return userMapper.findIds(user);
    }

    public List<User> selectUser(List<User> userList) {
        List<User> users = new ArrayList<User>();
        for (User user : userList) {
            if (user != null) {
                users.addAll(userMapper.select(user));
            }
        }
        //去重
        List<User> newList = users.stream().distinct().collect(Collectors.toList());
        return newList;
    }

    @Override
    public int bindingAvatar(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findId(String id) {
        return userMapper.findId(id);
    }

    @Override
    public Integer updateLog(String userId, String ip) {
        return userMapper.updateLog(userId, ip);
    }




}
