package com.edroplet.rent.adapter;

import java.util.ArrayList;

import com.edroplet.rent.R;
import com.edroplet.rent.beans.ActivitiesData;
import com.edroplet.rent.utils.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @name TopicsAdapter
 * @Descripation 专题<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class ActivitiesAdapter extends BaseAdapter {
	private ArrayList<ActivitiesData> data;
	private Context context;
	private LruCache<String,Bitmap> lruCache;
	public void BindData(ArrayList<ActivitiesData> data){
		
		this.data = data;
	}
	
	public ActivitiesAdapter(Context context){
		this.context=context;
		lruCache = ImageCache.GetLruCache(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHoleder viewHoleder =null;
		if (view==null) {
			viewHoleder =new ViewHoleder();
			view = LayoutInflater.from(context).inflate(R.layout.topic_item, null);
			viewHoleder.imageView=(ImageView) view.findViewById(R.id.zhuanti_main_image);
			view.setTag(viewHoleder);
		}else {
			viewHoleder = (ViewHoleder) view.getTag();
		}
		ActivitiesData activitiesData =data.get(position);
		viewHoleder.imageView.setImageResource(R.drawable.defaultcovers);
		viewHoleder.imageView.setTag(activitiesData.getIamge());
		new ImageCache(context, lruCache, viewHoleder.imageView, activitiesData.getIamge(),"OnTheWay",800, 400);
		
		return view;
	}
	
	private class ViewHoleder{
		public ImageView imageView;
		
	}

}
