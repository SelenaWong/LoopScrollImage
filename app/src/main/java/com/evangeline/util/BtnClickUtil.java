/**    
 * 文件名：ButtonClickUtil.java    
 *    
 * 版本信息：    
 * 日期：2015年8月17日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.evangeline.util;


/**
 * 
 * 类名称：ButtonClickUtil 类描述： 按钮快速点击 创建人：wesley 创建时间：2015年8月17日 下午8:21:59
 * 
 */
public class BtnClickUtil {

	private static long mLastClickTime = 0;
	private static long mLastClickRequestTime = 0;

	/**
	 * 按钮避免重复网络请求
	 * 
	 */
	public static boolean IsFastClickRequest() {

		long currentTime = System.currentTimeMillis();
		long time = currentTime - mLastClickRequestTime;
		if (currentTime - mLastClickRequestTime > 0
				&& currentTime - mLastClickRequestTime < 2000){
			return true;
		}
		mLastClickRequestTime = currentTime;
		return false;
	}

	public static boolean IsFastClick() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - mLastClickTime > 0
				&& currentTime - mLastClickTime < 1000){
			return true;
		}
		mLastClickTime = currentTime;
		return false;
	}
}
