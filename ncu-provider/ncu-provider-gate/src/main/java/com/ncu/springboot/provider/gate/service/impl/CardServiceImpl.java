package com.ncu.springboot.provider.gate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import com.ncu.springboot.api.gate.entity.Card;
import com.ncu.springboot.api.gate.entity.Student;
import com.ncu.springboot.api.gate.entity.Teacher;
import com.ncu.springboot.api.gate.entity.TempPersion;
import com.ncu.springboot.api.oauth2.bizservice.UserBizService;
import com.ncu.springboot.biz.entity.User;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.dao.CardMapper;
import com.ncu.springboot.dao.StudentMapper;
import com.ncu.springboot.dao.TeacherMapper;
import com.ncu.springboot.dao.TempPersionMapper;
import com.ncu.springboot.provider.gate.service.CardService;

import org.springframework.dao.DuplicateKeyException;

import tk.mybatis.mapper.entity.Example;

@Service
public class CardServiceImpl implements CardService {

	@Resource
	private CardMapper cardMapper;

	@Resource
	private StudentMapper studentMapper;

	@Resource
	private TeacherMapper teacherMapper;

	@Reference
	private UserBizService userBizService;
	
	@Resource
	private TempPersionMapper tempPersionMapper;
	
	public Map<String, Object> getUserInfo(String userId,String cardId) {
		Map<String, Object> info = new HashMap<String, Object>();
		if((userId==null||"".equals(userId))&&(cardId==null||"".equals(cardId))) {
			return null;
		}
		info = cardMapper.getInfoByStudent(userId,cardId);
		if (info==null) {
			info = cardMapper.getInfoByTeacher(userId,cardId);
			if (info==null) {
				return null;
			}else {
				info.put("type", 0);
			}
		}else {
			info.put("type", 1);
		}
		return info;
	}

	public String addCard(Student student, Teacher teacher,String imgPath) {
		String userId = null;
		String type = null;
		Card card = new Card();
		if (student.getUserId()!=null&&!"".equals(student.getUserId())) {
			userId = student.getUserId();
			Example example = new Example(Student.class);
			example.createCriteria().andCondition("user_id = ", userId);
			studentMapper.updateByExampleSelective(student, example);
			type = "1";
		}
		if (teacher.getUserId()!=null&&!"".equals(teacher.getUserId())) {
			userId = teacher.getUserId();
			Example example = new Example(Teacher.class);
			example.createCriteria().andCondition("user_id = ",userId);
			teacherMapper.updateByExampleSelective(teacher, example);
			type = "0";
		}
		if (userId != null) {
			card.setUserId(userId);
			card.setType(type);
			card.setCardId(Utils.getCodeByUUId()+Utils.getCodeByUUId());
			card.setStatus("1");
			card.setCreateTime(Utils.getTimeStamp());
			card.setImgPath(imgPath);
			try {
				cardMapper.insert(card);
			} catch (DuplicateKeyException e) {
				return "1";
			}
			Integer id = userBizService.findUser(userId).getId();
			User user = new User();
			user.setId(id);
			user.setAvatar(imgPath);
			userBizService.update(user);
		}else {
			return "0";
		}
		return card.getCardId();
	}

	@Override
	public Res editImgPath(String imgPath, String userId) {
		//更改tb_user头像
		Integer id = userBizService.findUser(userId).getId();
		User user = new User();
		user.setId(id);
		user.setAvatar(imgPath);
		userBizService.update(user);
		//更改头像
		Card card = new Card();
		card.setImgPath(imgPath);
		Example example = new Example(Card.class);
		example.createCriteria().andCondition("user_id = ", userId);
		return Res.SUCCESS(cardMapper.updateByExampleSelective(card, example));
	}

	@Override
	public Integer addTempPersion(TempPersion tempPersion) {
		return tempPersionMapper.insert(tempPersion);
	}

	@Override
	public Integer addTempCard(Card card) {
		return cardMapper.insert(card);
	}

	@Override
	public Map<String, Object> getTempPersionInfo(String mobile, String idCard,String cardId) {
		return tempPersionMapper.getTempPersionInfo(mobile,idCard,cardId);
	}

	public List<Map<String, Object>> getUserInfoList(String userId,String mobile,String userName,List<String> deptIds) {
		List<Map<String, Object>> data = cardMapper.getUserInfoListByTeacher(userId,mobile,userName,deptIds);
		data.addAll(cardMapper.getUserInfoListByStudent(userId, mobile, userName,deptIds));
		return data;
	}

	@Override
	public List<Map<String, Object>> getUserInfoListByTeacher(String deptId, String userId, String mobile, String userName, Integer pageNum, Integer pageSize) {
		if (pageNum!=null || pageSize!=null) {
			PageHelper.startPage(pageNum, pageSize);
		}
		return cardMapper.getUserInfoListByTeachers(deptId,userId,mobile,userName);
	}

	@Override
	public Res getUserInfoListByTeacherCount(String deptId, String userId, String mobile, String userName) {
		return Res.SUCCESS(cardMapper.getUserInfoListByTeacherCount(deptId,userId,mobile,userName));
	}


}
