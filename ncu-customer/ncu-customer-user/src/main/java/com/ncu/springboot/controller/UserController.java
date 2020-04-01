package com.ncu.springboot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.core.util.StringUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserBizService userBizService;

    @RequestMapping("/list")
    public Res list(Map<String, Object> param, int pageNum,
                    int pageSize) {
        List<User> list = userBizService.userList(param, pageNum, pageSize);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rows", list);
        return Res.SUCCESS(jsonMap);
    }

    @RequestMapping("/from")
    public Res from(int id) {
        User user = userBizService.getDataById(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fromObject", user);
        return Res.SUCCESS(map);
    }


    @RequestMapping("/save")
    public Res save(User user) {
        try {
            Res x = checkUser(user);
            if (x != null) return x;
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode("admin123"));
            return userBizService.insert(user);
        } catch (Exception e) {
            return Res.ERROR("用户添加失败");
        }
    }

    /**
     * 用户手机号，邮箱，身份证唯一校验
     */
    private Res checkUser(User user) {
        if (!StringUtil.isEmpty(user.getMobile())) {
            if (userBizService.checkMobileExist(user.getMobile())) {
                return Res.ERROR("用户手机号已存在");
            }
        }
        if (!StringUtil.isEmpty(user.getEmail())) {
            if (userBizService.checkEmailExist(user.getEmail())) {
                return Res.ERROR("用户邮箱已存在");
            }
        }
        if (!StringUtil.isEmpty(user.getIdCard())) {
            if (userBizService.checkIdCardExist(user.getIdCard())) {
                return Res.ERROR("用户身份证号已存在");
            }
        }
        return null;
    }


    @RequestMapping("/remove")
    public Res remove(int id) {
        if (userBizService.remove(id) > 0) {
            return Res.SUCCESS();
        }
        return Res.ERROR();
    }

    @RequestMapping("/update")
    public Res update(User user) {
        try {
            Res x = checkUser(user);
            if (x != null) return x;
            if (userBizService.update(user) > 0) {
                return Res.SUCCESS();
            }
            return Res.ERROR();
        } catch (Exception e) {
            return Res.ERROR("用户修改失败");
        }

    }

    @RequestMapping("/updateAvatar")
    public Res updateAvatar(User user) {
        String avatar = user.getAvatar();
        if (!StringUtil.isEmpty(avatar)) {
            if (userBizService.bindingAvatar(user) > 0) {
                return Res.SUCCESS("绑定用户头像成功");
            }
            return Res.ERROR("绑定用户头像失败");
        }
        return Res.ERROR("绑定用户头像失败");
    }


    @RequestMapping("/updateState")
    public Res updateState(int id, int state) {
        if (User.USERSTATE_ONE == state) {
            userBizService.updateUserState(id, state);
            return Res.SUCCESS("启用");
        } else if (User.USERSTATE_TWO == state) {
            userBizService.updateUserState(id, state);
            return Res.SUCCESS("禁用");
        }
        return Res.ERROR();
    }

    /**
     * 修改用户密码
     *
     * @Parmeter id 用户ID oldPwd 旧密码 newPwd 新密码
     */
    @RequestMapping("/updatePassword")
    public Res changePwd(String id, String oldPwd, String newPwd) {
        User user = userBizService.findId(id);
        String pass = user.getPassword();
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        //比对原始密码
        if (bcryptPasswordEncoder.matches(oldPwd, pass)) {
            //密码加密后修改
            String hashPass = bcryptPasswordEncoder.encode(newPwd);
            user.setPassword(hashPass);
            //修改密码
            if (userBizService.update(user) > 0) {
                return Res.SUCCESS("修改密码成功");
            }
            return Res.ERROR("修改密码失败");
        } else {
            return Res.ERROR("用户密码和原密码不一致");
        }

    }


}
