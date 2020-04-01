package com.ncu.springboot.provider.message.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ncu.springboot.api.cache.bizservice.MessageCacheBizService;
import com.ncu.springboot.api.message.entity.MessageSendResult;
import com.ncu.springboot.api.message.template.MessageTemplate;
import com.ncu.springboot.provider.message.constants.UnicomSMSCodeConstants;
import com.ncu.springboot.provider.message.service.CodeSender;

@Service
public class UnicomSMSCodeSender implements CodeSender {
	
	@Reference
	private MessageCacheBizService messageCacheHelper;

	@Override
	public MessageSendResult sendCode(String mobile, String code) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(UnicomSMSCodeConstants.URL);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); // 设置传输的数据格式

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("SpCode", UnicomSMSCodeConstants.SpCode));
		params.add(new BasicNameValuePair("LoginName", UnicomSMSCodeConstants.LoginName));
		params.add(new BasicNameValuePair("Password", UnicomSMSCodeConstants.Password));
		params.add(new BasicNameValuePair("MessageContent", MessageTemplate.generateMessage(MessageTemplate.SMSCODE, code)));
		params.add(new BasicNameValuePair("UserNumber", mobile));
		params.add(new BasicNameValuePair("SerialNumber", "12345678954128745128"));
		params.add(new BasicNameValuePair("ScheduleTime", ""));
		params.add(new BasicNameValuePair("ExtendAccessNum", ""));
		params.add(new BasicNameValuePair("f", "1"));

		String resultCode = "-1";
		String resultDescription = "发送失败，请重新发送";
		try {

			httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();
			
			String sendResult = EntityUtils.toString(entity, "GBK");
			if (sendResult != null && !sendResult.isEmpty()) {
				String[] results = sendResult.split("&");
				if (results != null && results.length > 0) {
					for (String result : results) {
						if (result.contains("result")) {
							resultCode = result.substring(result.indexOf('=') + 1);
						} else if (result.contains("description")) {
							resultDescription = result.substring(result.indexOf('=') + 1);
						}
					}
				}
			}
			
			if (resultCode.equals("0")) {
				messageCacheHelper.setSMSCodeByMobile(mobile, code);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new MessageSendResult(resultCode, resultDescription);
	}

}
