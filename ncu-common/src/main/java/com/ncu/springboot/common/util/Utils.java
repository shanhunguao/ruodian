package com.ncu.springboot.common.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;


public class Utils {

	//获取当前时间字符串形式 YYYY-MM-DD hh:mm:ss
	public static String getDateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}

	//根据入参返回时间格式
	public static String getDateTimeFormat(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}

	//根据入参返回时间格式
	public static Date getFormatDateTime(String format,String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//获取当前时间timestamp对象
	public static Timestamp getTimeStamp() {
		return new Timestamp(new Date().getTime());
	}

	//字符串转timestamp对象
	public static Timestamp getTimesStamp(String timeStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		try {  
			ts = Timestamp.valueOf(timeStr);  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return ts;
	}

	//根据uuid生成编号
	public static String getCodeByUUId() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
		}
		return String.format("%010d", hashCodeV);
	}

	public static double getDistanceMeter(Double[] gpsFrom, Double[] gpsTo){
		//Latitude,Longitude
		GlobalCoordinates source = new GlobalCoordinates(gpsFrom[0], gpsFrom[1]);
		GlobalCoordinates target = new GlobalCoordinates(gpsTo[0], gpsTo[1]);

		//创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
		GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);

		return geoCurve.getEllipsoidalDistance();
	}

	//对象转map
	public static List<Map<String, Object>> objectToMap(List<?> objs) {
		if (objs == null || objs.size() < 1) {
			return null;
		}

		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		try {
			for (Object obj : objs) {

				if (obj == null) {
					continue;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				Field[] declaredFields = obj.getClass().getDeclaredFields();

				for (Field field : declaredFields) {
					field.setAccessible(true);
					if (field.get(obj)!=null) {
						map.put(field.getName(), field.get(obj).toString());
					}

				}

				maps.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maps;
	}

	//将list转成键值对
	public static Map<String, String> listToMap(List<Map<String, Object>> list,String key,String value) {
		Map<String, String> map = new HashMap<String, String>();
		for (Map<String, Object> data : list) {
			//理论上相同key对应相同的value，所以即便重复也不是替换
			map.put(data.get(key).toString(), data.get(value).toString());
		}
		return map;

	}

	public static boolean checkObjAllFieldsIsNull(Object object) {
		if (null == object) {
			return true;
		}
		try {
			for (Field f : object.getClass().getDeclaredFields()) {
				if("serialVersionUID".equals(f.getName().toString())) {
					continue;
				}
				f.setAccessible(true);
				if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}


}
