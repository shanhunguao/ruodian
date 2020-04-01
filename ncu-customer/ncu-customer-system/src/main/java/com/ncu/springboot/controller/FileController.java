package com.ncu.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ncu.springboot.biz.rs.Res;

/**
 * @Created by zhoufan
 * @Date 2019/11/8 11:20
 */
@Controller
@RequestMapping("/file")
public class FileController {
	@Value("${resource.filePath}")
	private String filePath;
	@Value("${resource.imgPath}")
	private String imgPath;
	@Value("${resource.imgUrl}")
	private String imgUrl;

	@RequestMapping("/upload")
	@ResponseBody
	public Res upload(MultipartFile file) {
		if (file == null) {
			return Res.ERROR("上传失败，请选择文件");
		}
		// 获取上传文件名,包含后缀
		String originalFilename = file.getOriginalFilename();
		// 获取后缀
		String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
		/*
		 * if (checkFile(substring)) { return Res.ERROR("不支持该文件格式"); }
		 */
		// 保存的文件名
		String dFileName = UUID.randomUUID() + substring;
		// 保存路径
		// springboot 默认情况下只能加载 resource文件夹下静态资源文件
		String path = filePath;
		// 生成保存文件
		File uploadFile = new File(path + dFileName);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		// 将上传文件保存到路径
		try {
			file.transferTo(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Res.SUCCESS("上传文件成功", dFileName);
	}

	/**
	 * 判断是否为允许的上传文件类型,true表示允许
	 */
	/*
	 * private boolean checkFile(String fileName) { //设置允许上传文件类型 String suffixList =
	 * ".doc,.docx,.pdf"; // 获取文件后缀 if
	 * (suffixList.contains(fileName.trim().toLowerCase())) { return false; } return
	 * true; }
	 */
	@RequestMapping("/uploadImg")
	@ResponseBody
	public Res uploadImg(MultipartFile file) {
		if (file == null) {
			return Res.ERROR("上传失败，请选择文件");
		}
		// 获取上传文件名,包含后缀
		String originalFilename = file.getOriginalFilename();
		String contentType = file.getContentType();
		// 获取后缀
		String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (checkImg(contentType)) {
			return Res.ERROR("不支持该文件格式");
		}
		// 保存的文件名
		String dFileName = UUID.randomUUID() + substring;
		// 保存路径
		// springboot 默认情况下只能加载 resource文件夹下静态资源文件
		String path = imgPath;
		// 生成保存文件
		File uploadFile = new File(path + dFileName);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		// 将上传文件保存到路径
		try {
			file.transferTo(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Res.SUCCESS("上传图片成功", dFileName);
	}

	/*
	 * 判断是否为允许的上传图片类型,true表示允许
	 */
	private boolean checkImg(String fileName) {
		// 设置允许上传文件类型
		String suffixList = "image/";
		return !fileName.trim().toLowerCase().contains(suffixList);
	}

	/**
	 * 访问本地磁盘图片
	 *
	 * @Parmeter pathName 图片名称
	 */
	@RequestMapping(value = "/showImg")
	public void showImg(HttpServletRequest request, HttpServletResponse response, String pathName) {
		File imgFile = new File(imgPath + pathName);
		FileInputStream fin = null;
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			fin = new FileInputStream(imgFile);
			byte[] arr = new byte[1024 * 10];
			int n;
			while ((n = fin.read(arr)) != -1) {
				output.write(arr, 0, n);
			}
			output.flush();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			output.close();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getParameter("path");
		if (path == null || path.trim().isEmpty()) {
			return;
		}

		path = filePath + "/" + path;

		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		
		InputStream in = null;
		OutputStream out = null;
		
		try {
			String fileName = path.substring(path.lastIndexOf("/") + 1);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + file.length());
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes(), "utf-8"));

			in = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[4096];
			out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
