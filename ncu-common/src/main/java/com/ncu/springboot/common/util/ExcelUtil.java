package com.ncu.springboot.common.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {


	/***
	 * 
	 * @param datas 数据
	 * @param title	sheet名字
	 * @param response 相应
	 * @throws IOException 
	 */
	public static void export(List<Map<String,Object>> datas,String title,List<String> columns,HttpServletResponse response,List<String> keys,String fileName ) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个Excel表单,参数为sheet的名字
		HSSFSheet sheet = workbook.createSheet(title);

		//创建表头
		setTitle(workbook, sheet,columns);

		//新增行，并且遍历添加数据
		int rowNum = 1;
		if(datas.size()>0) {
			for (Map<String, Object> data:datas) {
				HSSFRow row = sheet.createRow(rowNum);
				for (int i=0;i<keys.size();i++) {
					//如果存在这个key值
					if (data.containsKey(keys.get(i))) {
						row.createCell(i).setCellValue(data.get(keys.get(i)).toString());
					}else {
						row.createCell(i).setCellValue("");
					}

				}
				rowNum++;
			}
		}
		fileName = fileName+"_"+Utils.getDateTimeFormat("YYYYMMDDhhmmss")+".xls";
		//清空response  
		response.reset();  
		//设置response的Header  
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName);  
		response.addHeader("Access-Control-Allow-Origin", "*"); 
		OutputStream os = null;
		os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/vnd.ms-excel;charset=utf-8"); 
		//将excel写入到输出流中
		workbook.write(os);
		os.flush();
		os.close();
	}

	/***
	 * 
	 * @param workbook
	 * @param sheet
	 * @param columns 字段名称
	 */
	private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet,List<String> columns){

		HSSFRow row = sheet.createRow(0);
		//设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);

		HSSFCell cell;
		for (int i=0;i<columns.size();i++) {
			sheet.setColumnWidth(i, 15*256);        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
			cell = row.createCell(i);
			cell.setCellValue(columns.get(i));
			cell.setCellStyle(style);
		}
	}

	public static List<Map<String, String>> excelToData(InputStream inputStream,List<String> keys) {
 		List<Map<String, String>> rows = new ArrayList<>();
		Map<String,String> cells = new HashMap<String, String>();
		Workbook workbook = null;

		try {
			workbook = new HSSFWorkbook(inputStream);//Excel 2003
			inputStream.close();
			//工作表对象
			Sheet sheet = workbook.getSheetAt(0);
			Row row = null;
			for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
				row = sheet.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					cells.put(keys.get(j), row.getCell(j).toString());
				}
				rows.add(cells);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

}
