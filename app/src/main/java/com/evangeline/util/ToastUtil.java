/**    
 * 文件名：ToastUtil.java    
 *    
 * 版本信息：    
 * 日期：2015年8月17日    
 * Copyright 足下 Corporation 2015     
 * 版权所有    
 *    
 */
package com.evangeline.util;

import org.json.JSONObject;


import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.evangeline.loopscrollimage.R;

/**    
 *      
 * 类名称：ToastUtil    
 * 类描述： Toast显示提示信息，避免重复创建
 * 创建人：Selena Wong   
 * 创建时间：2015年8月17日 下午8:15:21           
 *     
 */
public class ToastUtil {
     public static Toast mToast=null;
     public ToastUtil(){
    	 
     }

     public static void ShowText(Context context ,String msg){
    	 try{
    		 if(context==null){
    			 return;
    		 }
	    	  if(null==mToast){
	    		  mToast= new Toast(context);
	    		  LayoutInflater inflater = LayoutInflater.from(context);
	    		  View view = inflater.inflate(R.layout.toast_layout,null);
	    		  TextView mContentTv = (TextView) view.findViewById(R.id.tv_toast_content);
				  mContentTv.setPadding(20,10,20,10);
				  mContentTv.setTextSize(24);
	    		  mContentTv.setText(msg);
				  view.setBackgroundResource(R.drawable.alert_toast_bg);
	    		  mToast.setView(view);
	    	  }else{
	    		  View view = mToast.getView();
	    		  TextView mContentTv = (TextView) view.findViewById(R.id.tv_toast_content);
				  mContentTv.setPadding(20,10,20,10);
				  mContentTv.setTextSize(24);
	    		  mContentTv.setText(msg);
	    	  }
	    	  mToast.setDuration(Toast.LENGTH_SHORT);
			  mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP,40,816);//848
	    	  mToast.show();
    	 }catch(Exception ex){
    		 Log.i("appLog",ex.toString());
    	 }
     }
     
     
     public static void Cancel(){
    	 if(null!=mToast){
    		 mToast.cancel();
    	 }
     }
}
