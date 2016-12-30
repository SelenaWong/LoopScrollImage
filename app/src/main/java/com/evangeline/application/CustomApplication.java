/**    
 * 文件名：CustomApplication.java    
 *    
 * 版本信息：    
 * 日期：2015年11月11日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.evangeline.application;

import java.io.IOException;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;


/***
 * 
 * @Description 
 * @author Selena Wong
 * @date 2016年5月9日 上午10:42:52 
 * @version V1.3.1
 */
public class CustomApplication extends Application {
	
	private Handler handler;
	private String session=null;
	public  boolean isNetEnable=true;
	public final static int NET_STATE_NORMAL = 3001;
	public final static int NET_STATE_ERR = 3002;
	private String TAG="CustomApplication";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		UILApplication.initImageLoader(getApplicationContext());
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}

	/**
	 * @return isNetEnable
	 */
	
	public  boolean isNetEnable() {
		return isNetEnable;
	}

	/** 
	 * @param isNetEnable 要设置的 isNetEnable 
	 */
	
	public void setNetEnable(boolean isNetEnable) {
		this.isNetEnable = isNetEnable;
	}

	/**
	 * @return handler
	 */
	
	public Handler getHandler() {
		return handler;
	}


	
	/** 
	 * @param handler 要设置的 handler 
	 */
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}


	
	/**
	 * @return session
	 */
	
	public String getSession() {
		return session;
	}


	
	/** 
	 * @param session 要设置的 session 
	 */
	
	public void setSession(String session) {
		this.session = session;
	}


	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.w(TAG, "onTerminate");

	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.w(TAG, "onLowMemory");
	}

}
