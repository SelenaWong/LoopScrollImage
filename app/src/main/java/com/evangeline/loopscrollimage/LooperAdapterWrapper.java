package com.evangeline.loopscrollimage;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujun on 16-1-17.
 * 瀹炵幇寰幆婊氬姩鐨勯�傞厤鍣�
 */
public class LooperAdapterWrapper extends BaseAdapter {


    private BaseAdapter adapter;
    private int maxCount;

    /**
     * @param maxCount 鏈�澶у彲瑙佹暟
     * @param adapter
     */
    public LooperAdapterWrapper(int maxCount, BaseAdapter adapter) {
        this.maxCount = maxCount;
        this.adapter = adapter;
    }


    @Override
    public int getCount() {
        int count = adapter.getCount();
        if (count >= maxCount) {
            count = maxCount + 2;
        } 
        return count;
    }

    @Override
    public View getItemView(int pos, View itemView, ViewGroup containerView) {
        if (pos == 0) {
            return adapter.getItemView(adapter.getCount() - 1, itemView, containerView);
        } else if (pos == getCount() - 1) {
            return adapter.getItemView(0, itemView, containerView);
        }
        return adapter.getItemView(pos - 1, itemView, containerView);
    }


    public View getItemView(int pos, int firstVisiblePos, View itemView, ViewGroup containerView) {

        int realPos = 0;
        int realCount = adapter.getCount();
        if( realCount>=maxCount){
        	  if (pos == 0) {
                  realPos = firstVisiblePos - 1;
                  if (realPos < 0) {

                      realPos += getDataCount();
                  }
              } else if (pos == maxCount + 1) {
                  realPos = firstVisiblePos + maxCount;

                  if (realPos > getDataCount() - 1) {
                      realPos -= getDataCount();
                  }

              } else {
                  realPos = firstVisiblePos + pos - 1;
              }
        }else{
        	  if (pos == 0) {
                  realPos = firstVisiblePos - 1;
                  if (realPos < 0) {
                      realPos += getDataCount();
                  }
              } else if (pos == realCount + 1) {
                  realPos = firstVisiblePos + realCount;
                  if (realPos > getDataCount() - 1) {
                      realPos -= getDataCount();
                  }

              } else {
                  realPos = (firstVisiblePos + pos - 1)%realCount;
              }
        }
        Log.d("looperAdapter", pos + "_____" + firstVisiblePos + "________" + realPos);
        return adapter.getItemView(realPos, itemView, containerView);
    }

    public int getDataCount() {
        return adapter.getCount();
    }
}
