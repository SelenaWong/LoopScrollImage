package com.evangeline.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * @author dfs212
 * 
 */
public class ViewUtil {

	public static int getDensity(Context context){
		final float densityDpi = context.getResources().getDisplayMetrics().density*160;
		return (int)(densityDpi/160);
	}
	
	public static String  getWindowWidth(Context context){
		// ��ȡ��Ļ�ֱ���
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		String dumpsysPlayInfo= "width"+dm.widthPixels +"height="+dm.heightPixels+""+"density"+dm.density+"densityDpi="+dm.densityDpi;
		return dumpsysPlayInfo;
	}
	
	
	/**
	 * dpתpx
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * pxתdp
	 * @param context
	 * @param px
	 * @return
	 */
	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
}
