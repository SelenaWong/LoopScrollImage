package com.evangeline.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.evangeline.loopscrollimage.LooperScrollContainer;
import com.evangeline.loopscrollimage.ProductLooperAdapter;
import com.evangeline.application.CustomApplication;
import com.evangeline.application.UILApplication;
import com.evangeline.entity.Film;
import com.evangeline.entity.FilmTest;
import com.evangeline.loopscrollimage.R;
import com.evangeline.util.CommonUtil;
import com.evangeline.util.FloatUtil;
import com.evangeline.util.TextViewUtils;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends Activity  {

    @Bind(R.id.looperSrollContainer)
    LooperScrollContainer mScrollContainer;
    ProductLooperAdapter mAdapter;
    @Bind(R.id.tv_product_price)
    TextView mPriceTv;

    private boolean blFirstLauncher = true;
    private MyHandler mHandler;
    private List<Film> mData = new ArrayList<Film>( );
    private CustomApplication app;
    private static String TAG = "MainActivity";
    private boolean blNeedRefresh = true;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate MainActivity");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setViewEnable(false);
        app = (CustomApplication) getApplicationContext();
        mHandler = new MyHandler();
        app.setHandler(mHandler);
        setListener();
        mHandler.post(runnable);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        loadView( );
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i(TAG, "onResume MainActivity");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        try {
            Log.i(TAG, "onStop MainActivity");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            Log.i(TAG, "onDestroy MainActivity");
            mHandler.removeCallbacks(runnable);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            ButterKnife.unbind(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*************************************
     * start UI operate
     **********************************************************/

    /***
     * @Description add scroll listener to LooperContainer
     * @author Selena Wong
     */
    public void setListener() {

        mScrollContainer.setListener(new LooperScrollContainer.OnScrollSelectedListener() {
            @Override
            public void onSelected(View view, final int position) {
                // TODO Auto-generated method stub
                try {
                    new Thread() {
                        public void run() {
                            //这儿是耗时操作，完成之后更新UI；
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mData != null && position >= 0 && position < mData.size()) {
                                        String price = FloatUtil.ShowPrice(mData.get(position).getProPrice());
                                        TextViewUtils.setMultipleTvSize(mPriceTv, price);
                                    }
                                }
                            });
                        }
                    }.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onActionEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    default:
                        break;
                }
            }
        });
    }


    public void loadView() {
        try {
            clearView();
            mPriceTv.setText("");
            mData = FilmTest.getProducts();
            mAdapter = new ProductLooperAdapter(this, mData, new ProductLooperAdapter.OnLooperItemClickListener() {
                @Override
                public void OnLooperItemClick(View view, int position) {
                    // TODO Auto-generated method stub
                    mScrollContainer.moveTo(view);

                }
            }, ImageLoader.getInstance(), UILApplication.getDisplayImageOptions());

            ViewTreeObserver vto = mScrollContainer.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // TODO Auto-generated method stub
                    mScrollContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int width = mScrollContainer.getWidth();
                    mScrollContainer.setAdapter(mAdapter);
                }
            });
            mScrollContainer.setIdleScorll(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void clearView() {
        mScrollContainer.setIdleScorll(false);
    }

    public void setViewEnable(boolean enable) {
        mScrollContainer.setIdleScorll(enable);
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    /*************************************
     * start mqtt
     ***********************************************************/
    private class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case CustomApplication.NET_STATE_NORMAL:
                        app.setNetEnable(true);
                        break;
                    case CustomApplication.NET_STATE_ERR:
                        app.setNetEnable(false);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                mHandler.postDelayed(this, 10000);
                app.setNetEnable(CommonUtil.isNetworkAvailable(getApplicationContext()));
                if (!app.isNetEnable()) {
                    Message msg = new Message();
                    msg.what = CustomApplication.NET_STATE_ERR;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = CustomApplication.NET_STATE_NORMAL;
                    mHandler.sendMessage(msg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

}
