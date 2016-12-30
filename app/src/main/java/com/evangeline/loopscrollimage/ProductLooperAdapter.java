package com.evangeline.loopscrollimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.util.List;

import com.evangeline.entity.Film;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by Selena Wong
 */
public class ProductLooperAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    protected List<Film> data;
    private OnLooperItemClickListener listener;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
    private int pics[] =new int[]{R.drawable.pink_1,R.drawable.pink_2,R.drawable.pink_3,R.drawable.pink_4,
            R.drawable.pink_5,R.drawable.pink_6 };

    public ProductLooperAdapter(Context context, List<Film> data,OnLooperItemClickListener listener,	
    		ImageLoader imageLoader, DisplayImageOptions options) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.listener = listener;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public View getItemView(final int pos, View itemView, ViewGroup containerView) {
        ViewHolder viewHolder = null;
        if (itemView == null) {
            itemView = layoutInflater.inflate(R.layout.item_loopercircle, containerView,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) itemView.findViewById(R.id.coverflow_image); 
            itemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemView.getTag();
        }
        Film circleItem = data.get(pos%data.size());
        viewHolder.index =pos%data.size();
        viewHolder.imageView.setImageDrawable(null);
	/*	imageLoader.displayImage(data.get(pos%data.size())
				.getProUrl(), viewHolder.imageView, options);*/
        viewHolder.imageView.setImageResource(pics[pos%6]);
        final View moveView = itemView;
    	viewHolder.imageView.setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listener.OnLooperItemClick(moveView, pos%data.size());
			}
         	});
        return itemView;
    }

   public  static class ViewHolder {
        private View itemView;
        private ImageView imageView;
        private int index;//存放当前View的position,后面获取选中的Data的position
        public int getIndex(){
        	return index;
        }
        public ImageView getImageView(){
        	return imageView;
        }
    }
    public interface OnLooperItemClickListener{
    	public void OnLooperItemClick(View view, int position);
    }
}
