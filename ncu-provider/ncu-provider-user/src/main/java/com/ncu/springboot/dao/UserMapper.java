package com.ncu.springboot.dao;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.biz.entity.*;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.ncu.springboot.mybatis.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {

    int remove(int id); // 删除

    User getDataById(int id); // 根据id查找数据（单条数据）

    Page<User> userList(@Param("entity") Map<String, Object> map, @Param("pageNum") int pageNum,
                        @Param("pageSize") int pageSize); // 根据条件查询分页列表数据

    int updateUserState(int id, int state); // 修改用户状态

    int updatePassword(String password, int id); // 修改用户密码

    String checkWxUser(@Param("openId") String openId); // 根据微信唯一id查询数据库是否有绑定的用户

    int updateWxOpendid(@Param("openId") String openId, @Param("userCode") String userCode, @Param("password") String password); // wx根据用户账号密码修改openId值

    String checkQqUser(@Param("openId") String openId); // wx根据微信唯一id查询数据库是否有绑定的用户

    int updateQqOpendid(@Param("openId") String openId, @Param("userCode") String userCode, @Param("password") String password); // qq根据用户账号密码修改openId值

    String findDeptId(String userCode);

    User findUser(String userCode);

    Consumer findConsumer(String userCode);

    String getUserCodeByUnionId(String union_id);

    Teacher findTeacher(String userCode);

    Student findStudent(String userCode);

    Temporary findTemporary(String userCode);

    User checkOpenId(@Param("WXopenId") String WXopenId, @Param("QQopenId") String QQopenId);

    int binding(@Param("id") String id, @Param("WXopenId") String WXopenId, @Param("QQopenId") String QQopenId);

    int unbindingWx(String userCode);

    int unbindingQQ(String userCode);

    List<Integer> findIds(@Param("list") List<User> user);

    int bindingRole(@Param("userId") Integer userId, @Param("roleId") String roleId);

    User checkUserCode(String userCode);

    User selectId(String userCode);

    //删除用户关联的角色
    int delRole(Integer userId);

    //添加用户关联的角色
    int addRole(@Param("userId") Integer userId, @Param("roleId") String roleId);

    int updateRoleId(@Param("roleId") String roleId, @Param("userCode") String userCode);

    User findId(String id);

    String findUserCode(String id);

    //更新IP地址
    Integer updateLog(@Param("userId") String userId, @Param("ip") String ip);

    List<String> findDeptIds(String userCode);
}
