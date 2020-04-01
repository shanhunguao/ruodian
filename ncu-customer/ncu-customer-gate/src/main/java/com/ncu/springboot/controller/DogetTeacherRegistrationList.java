package com.ncu.springboot.controller;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ncu.springboot.DTO.DogetTeacherRegistrationListDTPO;

@RestController
@RequestMapping("/doRequest")
public class DogetTeacherRegistrationList {

	@RequestMapping("/dogetTeacherRegistrationList")
	public JSONObject dogetTeacherRegistrationList(DogetTeacherRegistrationListDTPO param) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost("http://oa.ncu.edu.cn/api/ncdx/register/doGetTeacherRegistrationList");
		CloseableHttpClient client = HttpClients.createDefault();
		StringEntity entity = new StringEntity(JSON.toJSONString(param),"utf-8");//解决中文乱码问题    
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		String respContent = null;
		HttpResponse resp = client.execute(httpPost);
		if(resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he,"UTF-8");
		}
		JSONObject jsonObject1 =JSONObject.parseObject(respContent);
		return jsonObject1;

	}

}
