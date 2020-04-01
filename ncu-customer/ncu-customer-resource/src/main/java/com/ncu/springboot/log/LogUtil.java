package com.ncu.springboot.log;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.system.bizservice.SysLogService;
import com.ncu.springboot.api.system.entity.SysLog;
import com.ncu.springboot.api.system.util.HttpContextUtil;
import com.ncu.springboot.common.util.Utils;
import com.ncu.springboot.core.annotation.Mark;

@Component
public class LogUtil<T>{

	@Reference
	private SysLogService syslogService;

	public Map<String, String> getMarkString(T object) throws IllegalArgumentException, IllegalAccessException {
		if (isBaseType4Java(object.getClass())) {
			return null;
		}
		Field[] fields = object.getClass().getDeclaredFields();
		Map<String, String> map = new HashMap<String, String>();
		for (Field field : fields) {
			field.setAccessible(true);
			Mark mark = field.getAnnotation(Mark.class);
			if (mark!=null) {
				String key = mark.name();
				if(field.get(object)!=null) {
					String value = field.get(object).toString();
					if (value != null && !"".equals(value)) {
						map.put(key, value);
					}
				}

			}
		}
		return map;
	}

	public boolean isBaseType4Java(Class obj) {
		if(char.class.getName().equalsIgnoreCase(obj.getName())
				||String.class.getName().equalsIgnoreCase(obj.getName())
				||Byte.class.getName().equalsIgnoreCase(obj.getName())
				||Short.class.getName().equalsIgnoreCase(obj.getName())
				||Long.class.getName().equalsIgnoreCase(obj.getName())
				||Integer.class.getName().equalsIgnoreCase(obj.getName())
				||Float.class.getName().equalsIgnoreCase(obj.getName())
				||Double.class.getName().equalsIgnoreCase(obj.getName())
				||Boolean.class.getName().equalsIgnoreCase(obj.getName())) {
			return true;
		}
		return false;
	}

	public void saveLog(String type,String operate,T object,Integer[] ids) {
		SysLog syslog = new SysLog();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String url = request.getContextPath()+request.getServletPath();


		String ip = HttpContextUtil.getIpAddress();
		//获取用户名
		String usercode = (String) request.getParameter("userCode");

		syslog.setOperation(operate);
		syslog.setUsername(usercode);
		syslog.setMethod(url);
		if("delete".equals(type)) {
			StringBuffer param = new StringBuffer();
			param.append("删除对象主键id为：");
			if (ids.length>0) {
				for (Integer id : ids) {
					param.append(id.toString());
				}
			}
			syslog.setParams(param.substring(0, param.length()-1).toString());
		}else {
			Map<String, String> params = null;

			try {
				params = getMarkString(object);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			syslog.setParams(params.toString());
		}

		syslog.setOperationType(new Long(0));

		syslog.setIp(ip);
		syslog.setCreateTime(Utils.getTimeStamp());
		syslogService.saveLog(syslog);
	}
}
