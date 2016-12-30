package com.evangeline.loopscrollimage;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujun on 16-1-17.
 * 閫傞厤鍣ㄥ熀绫�
 */
public abstract class BaseAdapter {

    /**
     * 鑾峰彇鏁版嵁涓暟
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 鑾峰彇itemView
     *
     * @param pos
     * @param itemView
     * @param containerView
     * @return
     */
    public abstract View getItemView(int pos, View itemView, ViewGroup containerView);

}
