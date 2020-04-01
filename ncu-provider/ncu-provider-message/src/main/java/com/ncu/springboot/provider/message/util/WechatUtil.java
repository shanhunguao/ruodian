package com.ncu.springboot.provider.message.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ncu.springboot.common.util.HttpRequestUtil;
import com.ncu.springboot.provider.message.entity.Article;
import com.ncu.springboot.provider.message.entity.Text;
import com.ncu.springboot.provider.message.entity.WeChatTokenResponse;
import com.ncu.springboot.provider.message.entity.WechatSendMessageRequest;
import com.ncu.springboot.provider.message.entity.WechatSendMessageResponse;
import com.ncu.springboot.provider.message.entity.WechatUploadMediaResponse;

@Component
public class WechatUtil {

	//返回消息42001token超时,40014错误

	@Value("${weChat.corpid}")
	private String corpid;

	@Value("${weChat.corpsecret}")
	private String corpsecret;
	
	@Value("${weChat.agentId}")
	private Integer agentId;

	@Value("${weChat.getTokenUrl}")
	private String getTokenUrl;

	@Value("${weChat.sendMessageUrl}")
	private String sendMessageUrl;

	@Value("${weChat.uploadMediaUrl}")
	private String uploadMediaUrl;

	@Value("${fileServer.url}")
	private String fileServerUrl;

	private static String Accsstoken;


	private WeChatTokenResponse getAccsstoken() {
		return JSONObject.parseObject(HttpRequestUtil.doGet(getTokenUrl+"?corpid="+corpid+"&corpsecret="+corpsecret),WeChatTokenResponse.class);
	}

	public WechatSendMessageResponse sendMessage(String message,String title,List<String> filePath,List<String> tousers,String url) throws Exception {
		if(Accsstoken==null) {
			WeChatTokenResponse weChatTokenResponse = getAccsstoken();
			if(weChatTokenResponse.getErrcode()==0) {
				Accsstoken = weChatTokenResponse.getAccess_token();
			}else {
				throw new Exception("wechat get accsstoken error");
			}
		}

		WechatSendMessageRequest wechatSendMessageRequest = new WechatSendMessageRequest();
		wechatSendMessageRequest.setAgentid(agentId);

		List<String> users = new ArrayList<String>();

		if(tousers.size()<1000) {
			StringBuffer str = new StringBuffer();
			for (String touser : tousers) {
				str.append(touser+"|");
			}
			users.add(str.toString());
		}else {
			StringBuffer str = new StringBuffer();
			int count = 0;
			for (String touser : tousers) {
				str.append(touser+"|");
				if (++count == 1000) {
					String user = str.substring(0,str.length()-1).toString();
					users.add(user);
					count = 0;
				}
			}
		}
		WechatSendMessageResponse result = null;
		if (message!=null&!"".equals(message)) {
			Text text = new Text();
			text.setContent(message);
			wechatSendMessageRequest.setText(text);
			wechatSendMessageRequest.setMsgtype("text");
			if(filePath!=null&&filePath.size()>0) {//图文消息
				List<Article> articles = new ArrayList<Article>();
				wechatSendMessageRequest.setMsgtype("news");
				for (String path : filePath) {
					Article article = new Article();
					article.setTitle(title);
					article.setPicurl(path);
					article.setUrl(url);
					article.setDescription(message);
					articles.add(article);
				}
				wechatSendMessageRequest.setArticles(articles);
			}
			for(String touser:users) {
				wechatSendMessageRequest.setTouser(touser);
				result = JSONObject.parseObject(HttpRequestUtil.doPost(sendMessageUrl+"?access_token="+Accsstoken,JSON.toJSONString(wechatSendMessageRequest)),WechatSendMessageResponse.class);
				if(result.getErrcode()!=0) {
					throw new Exception(result.getErrmsg());
				}
			}
		}

		//如果是图文消息
		if(filePath!=null&&filePath.size()>0&&message==null&&"".equals(message)) {
			List<Integer> ids = new ArrayList<Integer>();
			for (String path : filePath) {
				WechatUploadMediaResponse wechatUploadMediaResponse = uploadMedia(fileServerUrl+path);
				ids.add(Integer.parseInt(wechatUploadMediaResponse.getMedia_id()));
			}
			wechatSendMessageRequest.setMedia_id(ids);
			wechatSendMessageRequest.setMsgtype("image");
			for(String touser:users) {
				wechatSendMessageRequest.setTouser(touser);
				result = JSONObject.parseObject(HttpRequestUtil.doPost(sendMessageUrl+"?access_token="+Accsstoken,JSON.toJSONString(wechatSendMessageRequest)),WechatSendMessageResponse.class);
				if(result.getErrcode()!=0) {
					throw new Exception(result.getErrmsg());
				}
			}
		}
		return result;
	}

	public WechatUploadMediaResponse uploadMedia(String urlPath) throws Exception {
		String result = null;
		String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1);
		// 获取网络图片
		URL mediaUrl = new URL(urlPath);
		HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
		meidaConn.setDoOutput(true);
		meidaConn.setRequestMethod("GET");

		URL urlObj = new URL(uploadMediaUrl+"?access_token="+Accsstoken+"&type=image");
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(meidaConn.getInputStream());
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		meidaConn.disconnect();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		WechatUploadMediaResponse wechatUploadMediaResponse = JSON.parseObject(result,WechatUploadMediaResponse.class);
		return wechatUploadMediaResponse;
	}
}
