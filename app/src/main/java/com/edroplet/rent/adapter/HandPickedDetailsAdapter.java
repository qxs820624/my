package com.edroplet.rent.adapter;

import java.util.ArrayList;

import com.edroplet.rent.R;
import com.edroplet.rent.beans.HandPickedDetailData;
import com.edroplet.rent.utils.ImageCache;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @name HandPickedDetailsAdapter
 * @Descripation精选内容实体的适配器 <br>
 *		1、<br>
 *		2、<br>
 *      3、<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class HandPickedDetailsAdapter extends BaseAdapter {
	private Context context;
	private LruCache<String,Bitmap> lruCache;
	private ArrayList<HandPickedDetailData> list;
	public HandPickedDetailsAdapter(Context context) {
		this.context =context;
		lruCache=ImageCache.GetLruCache(context);
	}
	
	public void BindData(ArrayList<HandPickedDetailData> list){
		this.list=list;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder =null;
		if (convertView==null) {
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.hand_picked_detail_item,null);
			viewHolder.image=(ImageView)convertView.findViewById(R.id.jingxuan_detail_list_image);
			viewHolder.text=(TextView) convertView.findViewById(R.id.jingxuna_detail_content);
		
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		HandPickedDetailData handPickedDetailData =list.get(arg0);
		//viewHolder.poi.setText(handPickedDetailData.getPoi());
		viewHolder.text.setText(handPickedDetailData.getText());
		viewHolder.image.setTag(handPickedDetailData.getImage());
		viewHolder.image.setImageResource(R.drawable.defaultcovers);
		new ImageCache(context, lruCache,viewHolder.image, handPickedDetailData.getImage(),"OnTheWay",800,1080);
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView image;
		public TextView text;
		public TextView poi;
		
	}

}
