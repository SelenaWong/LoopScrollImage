package com.evangeline.loopscrollimage;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;



public class LooperScrollContainer extends ViewGroup {

    private static final String TAG = LooperScrollContainer.class.getName();

    private static final float SCALE_RATIO = 1.2f;
    private static final int SCREEN_SHOW_COUNT = 5;
    private static final float SCALE_FACTOR = 0.4f;
    private static final int STATUS_REST = 0;
    private static final int STATUS_MOVE = 1;

    private int status = STATUS_REST;
    private int selectedPos;
    private int childWidth=0;
    private int childHeight= 0;

    private int retainWidth=0;

    private int offsetX=0;
    private int downX, downY;
    private int lastX, lastY;
    private int touchSlop;
    private boolean canLoop;
    private int maxCount;
    private int firstVisiblePos;
    private int centerIndex = 0;
    private View centerView =null;
    private Context mContext;

    private LooperAdapterWrapper looperAdapterWrapper;
    private List<View> viewlist = new LinkedList<View>();
    private OnScrollSelectedListener onSelectedListener;
    private View lastShakeView;
    private boolean itemClickFlag = false;
    private boolean idleScroll =false;

    public LooperScrollContainer(Context context) {
        super(context);
        init(context);
    }

    public LooperScrollContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LooperScrollContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LooperScrollContainer);
        maxCount = ta.getInt(R.styleable.LooperScrollContainer_max_visible_count, SCREEN_SHOW_COUNT);
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
    	this.mContext = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        selectedPos = 0;
    }

    public boolean getItemClickFlag(){
        return itemClickFlag;
    }
    public void setListener(OnScrollSelectedListener listener){
    	this.onSelectedListener = listener;
    }

    public void setIdleScorll(boolean canScroll){
        idleScroll = canScroll;
    }


    /***
     *
     * Description in order to achive circle looper ,we set the first child in negative childWidth.
     * Then when the childCount less than screenCount ,we should  use (deltaCount+1) multiply offset compute the first child position
     *
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     * @see ViewGroup#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int left = -1 * childWidth;
        int top = centerY - childHeight / 2;
        float maxRatio = 0;
        int realCount = viewlist.size();
        centerView =null;
        centerIndex =-1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = viewlist.get(i);
            ProductLooperAdapter.ViewHolder viewHold = (ProductLooperAdapter.ViewHolder) child.getTag();
            int index = viewHold.getIndex();
            if (i == 0) {
            	if(realCount>=SCREEN_SHOW_COUNT){
            		left += retainWidth;
            	}else{
            		int deltaCount = (SCREEN_SHOW_COUNT- realCount)/2;
            		/*left += 190+(deltaCount-1)*retainWidth/2 +deltaCount*childWidth/2;*/
            		left += retainWidth;
            		left = left + (retainWidth+childWidth)*(deltaCount+1);
            	}
            } else {
                left += childWidth+retainWidth;
            }

            if (i == 0) {
                left += offsetX;
            }

            if (i == getChildCount() / 2 || i == getChildCount() / 2 + 1) {
                left += retainWidth / 2;
            }

            float scaleRatio =0.83f;
            if(realCount>=SCREEN_SHOW_COUNT)
            {
            	if(i==SCREEN_SHOW_COUNT/2+1)
            	{
            		scaleRatio = 1.0f;
            		centerIndex=index;
            		centerView = child;
            		if(onSelectedListener!=null){
            			onSelectedListener.onSelected(child, centerIndex);
            		}
            	}
            }else
            {
            	if(realCount%2==0&&i==realCount/2){
            		scaleRatio = 1.0f;
            		centerIndex =index;
            		centerView = child;
            		if(onSelectedListener!=null){
            			onSelectedListener.onSelected(child, centerIndex);
            		}
            	}else if(realCount%2==1&&i==realCount/2){
            		scaleRatio = 1.0f;
            		centerIndex =index;
            		centerView = child;
            		if(onSelectedListener!=null){
            			onSelectedListener.onSelected(child, centerIndex);
            		}
            	}
            }
            child.setScaleX(scaleRatio);
            child.setScaleY(scaleRatio);
            int top2 =   (int)(height/2- childHeight*scaleRatio/2);
            child.layout(left, top2, left + childWidth, (int)(top2 + childHeight));
        }
        reorderZ();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        childWidth = width / SCREEN_SHOW_COUNT;
        childHeight = height;
        retainWidth = width - childWidth * SCREEN_SHOW_COUNT;
        Log.d("measurewidth", "childWidth = "+childWidth + ", width= " + width + ",retainWidth = " +retainWidth+", childWidth * SCREEN_SHOW_COUNT = " + childWidth * SCREEN_SHOW_COUNT);
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    /**
     * 璋冩暣灞傜骇
     */
    private void reorderZ() {
        int centerIndex = getChildCount() / 2;
        for (int i = 1; i < centerIndex; i++) {
            viewlist.get(i).bringToFront();
        }
        for (int i = viewlist.size() - 2; i >= centerIndex; i--) {
            viewlist.get(i).bringToFront();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            viewlist.add(child);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (status != STATUS_MOVE) {
                    if (Math.abs(x - downX) > touchSlop) {
                        lastX = x;
                        lastY = y;
                        status = STATUS_MOVE;
                        return true;
                    } else {
                        status = STATUS_REST;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                status = STATUS_REST;
        }
        lastX = x;
        lastY = y;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        if(!idleScroll){
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(onSelectedListener!=null){
                    onSelectedListener.onActionEvent(ev);
                }
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (status != STATUS_MOVE) {
                    if (Math.abs(x - downX) > touchSlop) {
                        status = STATUS_MOVE;
                        return true;
                    } else {
                        status = STATUS_REST;
                    }
                } else {
                   // mHandler.removeCallbacks(shakeRunnable);
                    moveBy(x - lastX);
                }
                break;
            case MotionEvent.ACTION_UP:
            	Log.d(TAG, "autoMove");
                autoMove(null );
               /* mHandler.postDelayed(shakeRunnable,500);*/
                status = STATUS_REST;
                if(onSelectedListener!=null){
                    onSelectedListener.onActionEvent(ev);
                }
        }
        lastX = x;
        lastY = y;
        return true;
    }

    private Handler mHandler = new Handler(){

    };

    public Runnable shakeRunnable = new Runnable() {
        @Override
        public void run() {
            if(centerView!=null){
                Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                ImageView imv = (ImageView)centerView.findViewById(R.id.coverflow_image);
                imv.startAnimation(shake);
                lastShakeView = centerView;
            }
        }
    };


    private void moveBy(int deltaX) {
        offsetX += deltaX;
        adjustViewOrder();
        requestLayout();
    }

    public void moveTo( View view){
    	int deltaX = getMeasuredWidth()/2 -(view.getLeft()+view.getWidth()/2);
        //mHandler.removeCallbacks(shakeRunnable);
        moveBy(deltaX);
        itemClickFlag =true;
        autoMove(view);
    }


    private void autoMove( final View view) {
        if(lastShakeView!=null){
            ImageView imv = (ImageView)lastShakeView.findViewById(R.id.coverflow_image);
            imv.clearAnimation();
        }
        int from = offsetX;
        int to = from;
        int retainX = offsetX % childWidth;
        if (Math.abs(retainX) > childWidth / 2) {
            if (retainX > 0) {
                to += childWidth - retainX;
            } else {
                to -= childWidth + retainX;
            }
        } else {
            to -= retainX;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offsetX = (int)(animation.getAnimatedValue());
                if (animation.getAnimatedFraction() == 1) {
                    adjustViewOrder();
                    offsetX = 0;
                }
                requestLayout();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				if(view!=null){
	        		Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
	    			ImageView imv = (ImageView)view.findViewById(R.id.coverflow_image);
	        		imv.startAnimation(shake);
                    lastShakeView = view;
				}
			}
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
			}
		});

        if(Math.abs(retainX) > childWidth / 2){
        	 valueAnimator.setDuration(50);//50
        }else{
        	valueAnimator.setDuration(500);//50
        }
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }



    private void adjustViewOrder() {
        View view = null;
        if(viewlist.size()==0||looperAdapterWrapper==null){
            return;
        }
        if (offsetX <= -1 * childWidth) {
            view = viewlist.get(0);
            viewlist.remove(0);
            viewlist.add(view);
            offsetX += childWidth;
            selectedPos++;
        } else if (offsetX >= childWidth) {
            view = viewlist.remove(viewlist.size() - 1);
            viewlist.add(0, view);
            offsetX -= childWidth;
            selectedPos--;
        }
        int dataCount = looperAdapterWrapper.getDataCount();
        if (selectedPos >= dataCount) {
            selectedPos = 0;
        } else if (selectedPos < 0) {
            selectedPos += dataCount;
        }
        firstVisiblePos = selectedPos - 0;
        if (firstVisiblePos < 0) {
            firstVisiblePos += dataCount;
        }
        if (view != null) {
            int pos = viewlist.indexOf(view);
            looperAdapterWrapper.getItemView(pos, firstVisiblePos, view, LooperScrollContainer.this);
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        viewlist.clear();
        removeAllViews();
        looperAdapterWrapper = new LooperAdapterWrapper(SCREEN_SHOW_COUNT, adapter);
        for (int i = 0; i < looperAdapterWrapper.getCount(); i++) {
            View itemView = looperAdapterWrapper.getItemView(i, firstVisiblePos, null, this);
            viewlist.add(itemView);
            addView(itemView);
        }
        offsetX = 0;
        requestLayout();
    }

    public int getSelectedIndex(){
    	return centerIndex;
    }

    public View getSelectedView(){
    	return centerView;
    }

    /***
     *
     * @param index
     * @param totalSize
     * @Description when user press the keyboard then the mapping image move to screen center.
     */
    public void aisleKeyEvent( int index,int totalSize){
        if(index!=centerIndex) {
            int offsetIndex =centerIndex-index;
            int deltaX = offsetIndex * (childWidth+retainWidth);
            Log.e(TAG, "offsetIndex= "+offsetIndex +", aisleKeyEvent deltaX=" + deltaX +"retainWidth="+retainWidth );
            //mHandler.removeCallbacks(shakeRunnable);
            offsetX=0;
            int moveNum = Math.abs(offsetIndex);
            if(offsetIndex>=0){
                while(offsetIndex>0 ){
                    offsetX = childWidth;
                    adjustViewOrder();
                    offsetIndex--;
                }
                requestLayout();
            }else{
                while(offsetIndex<0 ){
                    offsetX = -childWidth;
                    adjustViewOrder();
                    offsetIndex++;
                }
            }
            requestLayout();
        }
    }

    public interface  OnScrollSelectedListener{
    	public void onSelected(View view, int position);
        public void onActionEvent(MotionEvent event);
    }
}
