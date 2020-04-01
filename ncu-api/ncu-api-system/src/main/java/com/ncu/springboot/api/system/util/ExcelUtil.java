package com.ncu.springboot.api.system.util;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


public class ExcelUtil {


    /***
     *
     * @param datas 数据
     * @param title	sheet名字
     * @param response 相应
     * @throws IOException
     */
    public static void export(List<Map<String,Object>> datas,String title,List<String> columns,HttpServletResponse response,List<String> keys ) throws IOException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个Excel表单,参数为sheet的名字
        HSSFSheet sheet = workbook.createSheet(title);

        //创建表头
        setTitle(workbook, sheet,columns);

        //新增行，并且遍历添加数据
        int rowNum = 1;
        for (Map<String, Object> data:datas) {
            HSSFRow row = sheet.createRow(rowNum);
            for (int i=0;i<keys.size();i++) {
                row.createCell(i).setCellValue((String)data.get(keys.get(i)));
            }
            rowNum++;
        }
        String fileName = "log.xls";
        //清空response
        response.reset();
        //设置response的Header
        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
        OutputStream os = null;
        os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/vnd.ms-excel;charset=gb2312");
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


}
