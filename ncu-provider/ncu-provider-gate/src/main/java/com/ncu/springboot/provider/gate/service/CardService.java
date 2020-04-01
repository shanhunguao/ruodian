package com.ncu.springboot.provider.gate.service;

import java.util.List;
import java.util.Map;

import com.ncu.springboot.api.gate.entity.Card;
import com.ncu.springboot.api.gate.entity.Student;
import com.ncu.springboot.api.gate.entity.Teacher;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.biz.rs.Res;

public interface CardService {

	Map<String, Object> getUserInfo(String userId,String cardId);

	String addCard(Student student,Teacher teacher,String imgPath);
	
	Res editImgPath(String imgPath,String userId);
	
	Integer addTempPersion(TempPersion tempPersion);
	
	Integer addTempCard(Card card);
	
	Map<String, Object> getTempPersionInfo(String mobile,String idCard, String cardId);
	
	List<Map<String, Object>> getUserInfoList(String userId,String mobile,String userName, List<String> deptIds);

	List<Map<String, Object>> getUserInfoListByTeacher(String deptId, String userId,String mobile,String userName, Integer pageNum, Integer pageSize);

	Res getUserInfoListByTeacherCount(String deptId, String userId, String mobile, String userName);
}
