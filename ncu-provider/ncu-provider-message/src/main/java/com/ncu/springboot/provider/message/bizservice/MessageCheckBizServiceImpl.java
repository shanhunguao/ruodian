package com.ncu.springboot.provider.message.bizservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ncu.springboot.api.message.bizservice.MessageCheckBizService;
import com.ncu.springboot.api.message.entity.MessageCheck;
import com.ncu.springboot.api.message.entity.MessageLog;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.message.entity.TbMessage;
import com.ncu.springboot.api.message.entity.TbMessageTo;
import com.ncu.springboot.api.oauth2.bizservice.DeptBizService;
import com.ncu.springboot.api.oauth2.bizservice.EmployeeBizService;
import com.ncu.springboot.biz.entity.UserInfos;
import com.ncu.springboot.biz.rs.Res;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.core.util.DateUtil;
import com.ncu.springboot.provider.message.queue.MessageStatus;
import com.ncu.springboot.provider.message.service.MessageCheckService;
import com.ncu.springboot.provider.message.service.MessageService;

@Component
@Service
public class MessageCheckBizServiceImpl implements MessageCheckBizService{

	@Reference
	private DeptBizService deptBizService;

	@Resource
	private MessageService messageService;

	@Resource
	private MessageCheckService messageCheckService;

	@Resource
	private MessageLogBizserverImpl messageLogBizServiceImpl;

	@Reference
	private EmployeeBizService EmployeeBizService;

	@Transactional
	public MessageSendResult check(Integer messageId, String employeeId,boolean check) {

		List<Map<String, Object>> message = messageService.queryList( messageId,null,null, null, null, null, MessageStatus.WAIT_CHECK,null,null);
		MessageSendResult result = new MessageSendResult();

		if (message.size()<1||message==null) {
			return result.setCode("1").setMsg("该条消息数据出错或已经审核！");
		}
		List<MessageCheck> messageChecks = messageCheckService.queryList(employeeId, null);
		boolean flag = false;

		if (messageChecks.get(0).getDeptId()==message.get(0).get("deptId")) {
			flag = true;
		}else {
			List<String> childIds = new ArrayList<String>();
			childIds.add(messageChecks.get(0).getDeptId().toString());
			childIds = deptBizService.getChild(childIds);
			childIds.add(messageChecks.get(0).getDeptId().toString());
			childIds = childIds.stream().distinct().collect(Collectors.toList());//去重
			flag = isCheck(childIds,message.get(0).get("deptId").toString());
		}
		if (!flag) {
			return result.setCode("1").setMsg("用户无权限审核该条数据！");
		}


		if (!check) {
			for (Map<String, Object> map : message) {
				TbMessageTo tbMessageTo = new TbMessageTo();
				tbMessageTo.setId(Integer.parseInt(map.get("toId").toString()));
				tbMessageTo.setStatus(MessageStatus.CHECK_FAIL);
				messageService.editTbMessageTo(tbMessageTo);
			}
			MessageLog messageLog = new MessageLog();
			messageLog.setMessageId(messageId);
			messageLog.setOperatePersionId(employeeId);
			messageLog.setOperate("审核驳回");
			messageLog.setOperateTime(Utils.getTimeStamp());
			messageLogBizServiceImpl.addMessageLog(messageLog);
			return result.setCode("0").setMsg("消息已驳回！");
		}

		TbMessage tbMessage = new TbMessage();
		if (message.get(0).get("content")!=null) {
			tbMessage.setContent(message.get(0).get("content").toString());
		}else if (message.get(0).get("url")!=null) {
			tbMessage.setUrl(message.get(0).get("url").toString());
		}else if (message.get(0).get("subject")!=null) {
			tbMessage.setSubject(message.get(0).get("subject").toString());
		}
		tbMessage.setMessageType(Integer.parseInt((message.get(0).get("messageType").toString())));
		String sendTime = message.get(0).get("sendTime").toString();
		List<String> filePath = new ArrayList<String>();
		if (message.get(0).get("filePath")!=null) {
			for (String path : message.get(0).get("filePath").toString().split(",")) {
				filePath.add(path);
			}
		}

		List<TbMessageTo> to = new ArrayList<TbMessageTo>();

		for (Map<String, Object> map : message) {
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setMessageId(messageId);
			tbMessageTo.setId(Integer.parseInt(map.get("toId").toString()));
			tbMessageTo.setReceiveTo(map.get("receiveTo").toString());
			to.add(tbMessageTo);
		}
		result = messageService.sendMessage(to,DateUtil.strToLong(sendTime, DateUtil.YYYYMMDDHHMMSS),filePath,tbMessage);
		MessageLog messageLog = new MessageLog();
		messageLog.setMessageId(messageId);
		messageLog.setOperatePersionId(employeeId);
		messageLog.setOperate("审核通过");
		messageLog.setOperateTime(Utils.getTimeStamp());
		messageLogBizServiceImpl.addMessageLog(messageLog);
		return result;
	}

	@Override
	public List<MessageCheck> queryList(String employeeId, Integer deptId) {
		return messageCheckService.queryList(employeeId, deptId);
	}

	@Override
	public Res addMessageCheck(List<MessageCheck> messageChecks) {

		for (MessageCheck messageCheck : messageChecks) {
			UserInfos userInfo = new UserInfos();
			userInfo.setEmployeeId(messageCheck.getEmployeeId());
			List<UserInfos> userInfos = EmployeeBizService.find(userInfo);
			if (userInfos!=null && userInfos.size()>0) {
				if(!userInfos.get(0).getDeptId().equals(messageCheck.getEmployeeId())) {
					return Res.ERROR("审核人仅限本部门！该条数据有误！"+messageCheck.toString());
				}
			}
		}
		return Res.SUCCESS(messageCheckService.addMessageCheck(messageChecks));
	}

	@Override
	public Integer delMessageCheck(List<Integer> ids) {
		return messageCheckService.delMessageCheck(ids);
	}

	//递归查看该部门是否在树内
		public boolean isCheck(List<String> childIds,String deptId) {
			for (int i = 0; i < childIds.size(); i++) {
				if (childIds.get(i)==deptId||deptId.equals(childIds.get(i))) {
					return true;
				}
			}
			return false;
		}


}
