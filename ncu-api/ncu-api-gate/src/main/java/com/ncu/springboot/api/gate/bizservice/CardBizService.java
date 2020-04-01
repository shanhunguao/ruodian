package com.ncu.springboot.api.gate.bizservice;


import java.util.Map;

import com.ncu.springboot.api.gate.entity.Student;
import com.ncu.springboot.api.gate.entity.Teacher;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.biz.rs.Res;

public interface CardBizService {

    Res getUserInfo(String userId, String cardId, String adminId);

    String addCard(Student student, Teacher teacher, String imgPath, String admin);

    Res editImgPath(String imgPath, String userId, String admin);

    Res addTempCard(TempPersion tempPersion, String imgPath, String admin);

    Map<String, Object> getTempPersionInfo(String mobile, String idCard);

    Res getUserInfoList(String userId, String adminId, String mobile, String userName);

    Res getUserInfoListByTeacher(String deptId, String userId, String mobile, String userName, Integer pageNum,
                                 Integer pageSize);

    Res getUserInfoListByTeacherCount(String deptId, String userId, String mobile, String userName);
}
