
/**   
 * @Title: TextUtils.java 
 * @Package: com.app.utils 
 * @Description: TODO
 * @author lenovo  
 * @date 2016年10月19日 下午2:59:12 
 * @version 1.3.1 
 */


package com.evangeline.util;

import java.util.List;

import com.evangeline.entity.Film;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/** 
 * @Description 设置 TextView显示文字的样式
 * @author Selena Wong
 * @date 2016年10月19日 下午2:59:12 
 * @version V1.3.1
 */

public class TextViewUtils {
	
	
	public static void setDrawerTvSize(TextView tv,String price){
		String text =price+"元" ;
		Spannable sp = new SpannableStringBuilder( text );
		sp.setSpan(new AbsoluteSizeSpan(30), 0, text.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(24), text.length()-1, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		tv.setText(sp);
	}

	public static void setMultipleTvSize(TextView tv,String price){
		String text =price+"元" ;
		Spannable sp = new SpannableStringBuilder( text );
		sp.setSpan(new AbsoluteSizeSpan(40), 0, text.length()-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(28), text.length()-1, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		tv.setText(sp);
	}

	public static void refreshPriceTv( List<Film> films,TextView tv){
		int sumPrice=0;
		for(Film film: films ){
			sumPrice +=film.getProPrice();
		}
		String sPrice=FloatUtil.ShowPrice(sumPrice);
		setDrawerTvSize(tv, sPrice);
	}
}
