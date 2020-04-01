package com.ncu.springboot.api.oauth2.bizservice;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.biz.entity.Consumer;
import com.ncu.springboot.biz.entity.ControlUser;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.rs.Res;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;


public interface UserBizService {
//	/**
//	  *    用户注册
//	 * @param user
//	 */
//	int registerUser(User user) throws UserColumnDuplicateException;
//
//	/**
//	  *    删除用户
//	 * @param usercode
//	 */
//	void deleteUser(String usercode);
//
//	/**
//	  *    修改用户登录密码
//	 * @param usercode
//	 * @param old_passwd
//	 * @param new_passwd
//	 */
//	void updateUserPasswd(String usercode, String old_passwd, String new_passwd);
//
//	/**
//	  *    修改用户手机号码
//	 * @param usercode
//	 * @param phone
//	 */
//	void updateUserPhoneNum(String usercode, String phone_num);
//
//	/**
//	  *    修改用户邮箱
//	 * @param usercode
//	 * @param email
//	 */
//	void updateUserEmail(String usercode, String email);
//
//	Page<User> findAllUsers(int pageNum, int pageSize);


    boolean checkMobileExist(String mobile);

    boolean checkEmailExist(String email);

    boolean checkIdCardExist(String idCard);

    User getDataById(int id);

    Res insert(User user); // 增加

    int remove(int id); // 删除

    int update(User user); // 修改

    Page<User> userList(@Param("entity") Map<String, Object> map, @Param("pageNum") int pageNum,
                        @Param("pageSize") int pageSize);

    int updateUserState(int id, int state); // 修改用户状态

    int updatePassword(String password, int id); // 修改用户密码

    String findDeptId(String userCode);

    User findUser(String userCode);

    Consumer findConsumer(String userCode);

    ControlUser findAppConsumer(String userCode);

    String getUserCodeByUnionId(String union_id);

    // 检查微信唯一id查询数据库是否有绑定的用户
    User checkOpenId(String WXopenId, String QQopenId);

    //根据用户主键ID绑定微信用户openId
    void binding(String id, String WXopenId, String QQopenId);

    void unbindingWx(String id);

    void unbindingQQ(String id);

    // 根据用户名集合查询用户ID集合
    List<Integer> findIds(List<User> user);

    List<User> selectUser(List<User> userList);

    //	用户绑定头像
    int bindingAvatar(User user);

    User findId(String id);

    Integer updateLog(String userId, String ip);


}
