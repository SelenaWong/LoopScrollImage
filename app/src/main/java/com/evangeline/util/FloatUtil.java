
/**   
 * @Title: PrictUtil.java 
 * @Package: com.app.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年10月18日 下午1:16:52 
 * @version 1.3.1 
 */


package com.evangeline.util;

import java.text.DecimalFormat;

/** 
 * @Description 价格显示转换类,交易价格是以分为基本单位 
 * @author Selena Wong
 * @date 2016年10月18日 下午1:16:52 
 * @version V1.3.1
 */

public class FloatUtil {
	

	/***
	 * 
	 * @Description 将float类型的价格保留2位小数表示 
	 * @author Selena Wong
	 * @param number
	 * @return
	 */
	public static String FloatToStrPrice(float number){
		DecimalFormat df = new DecimalFormat("0.00");
		String price= df.format(number);
		return price;
	}
	
	
	/***
	 * 
	 * @Description 将字符串转换为float的实际价格
	 * @author Selena Wong
	 * @param price
	 * @return
	 */
	public static float  StrToFloatPrice(String price){
		int IntPrice = Integer.parseInt(price);
		float number=0.0f;
		number = IntPrice/100 + 0.01f*(IntPrice%100);
		return number;
	}

	
	/***
	 * 
	 * @Description 显示价格 （350显示为3.5元）
	 * @author Selena Wong
	 * @param price
	 * @return
	 */
	public static String ShowPrice(String price){
		float number = StrToFloatPrice(price);
		String sPrice = FloatToStrPrice(number);
		return sPrice;
	}
	
	/***
	 * 
	 * @Description 显示价格 （350显示为3.5元）
	 * @author Selena Wong
	 * @param price
	 * @return
	 */
	public static String ShowPrice(int price){
		float number = price/100 + 0.01f*(price%100);
		String sPrice = FloatToStrPrice(number);
		return sPrice;
	}
	
}
