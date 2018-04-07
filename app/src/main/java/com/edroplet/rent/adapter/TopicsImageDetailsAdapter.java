package com.edroplet.rent.adapter;

import java.util.ArrayList;
import java.util.Random;

import com.edroplet.rent.R;
import com.edroplet.rent.beans.HandPickedData;
import com.edroplet.rent.beans.UserInfo;
import com.edroplet.rent.utils.ImageCache;

import com.edroplet.rent.views.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @name TopicsImageDetailsAdapter
 * @Descripation 图片专题的适配器<br>
 * @author edroplet
 * @date  2018-04-06
 * @version 1.0
 */
public class TopicsImageDetailsAdapter extends BaseAdapter {
	private ArrayList<HandPickedData> datas;
	private Context context;
	private LruCache<String, Bitmap> lruCache;
	private final static String IMAGEHOME = "http://img.117go.com/timg/p308/";
	private final static String AVATAR = "http://img.117go.com/demo27/img/ava66/";

	public void BindData(ArrayList<HandPickedData> datas) {
		this.datas = datas;
	}

	public TopicsImageDetailsAdapter(Context context) {
		this.context = context;
		lruCache = ImageCache.GetLruCache(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.topic_detail_image_item, null);
			viewHolder.disCitys = (TextView) view
					.findViewById(R.id.zhuanti_address);
			viewHolder.favoriteCount = (TextView) view
					.findViewById(R.id.zhuanti_commentCount);
			viewHolder.image = (ImageView) view
					.findViewById(R.id.gridview_imageview);
			viewHolder.title = (TextView) view.findViewById(R.id.zhuanti_title);
			viewHolder.viewCount = (TextView) view
					.findViewById(R.id.zhuanti_likeCount);
			viewHolder.userCircleImageView = (CircleImageView) view
					.findViewById(R.id.user_circleImageView);
			viewHolder.content = (TextView) view
					.findViewById(R.id.text_content);
			viewHolder.publishdate = (TextView) view
					.findViewById(R.id.publish_date);
			viewHolder.user_name = (TextView) view.findViewById(R.id.user_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		HandPickedData handPickedData = datas.get(position);
		viewHolder.favoriteCount.setText(handPickedData.getCmtCount());
		viewHolder.title.setText(handPickedData.getTitle());
		viewHolder.viewCount.setText(handPickedData.getViewCount());
		if (handPickedData.getForeword().length()>100) {
			content = handPickedData.getForeword().substring(0,100)+"...";
			viewHolder.content.setText(content);
		}else {
			viewHolder.content.setText(content);
		}
		
		viewHolder.publishdate.setText(handPickedData.getPubdate());

		StringBuffer stringBuffer = new StringBuffer();
		
		if (handPickedData.getDispCities().length > 0) {
			for (int i = 0; i < handPickedData.getDispCities().length; i++) {
				stringBuffer.append(handPickedData.getDispCities()[i]).append(
						"->");
				if (i == handPickedData.getDispCities().length) {
					stringBuffer.append(handPickedData.getDispCities()[i]);
				}

			}
			viewHolder.disCitys.setText(handPickedData.getDispCities()[0]); // stringBuffer.toString()
		}
		UserInfo userInfo = handPickedData.getUserInfo();
		viewHolder.user_name.setText(userInfo.getNickname());
		viewHolder.image.setTag(IMAGEHOME + handPickedData.getImage());
		viewHolder.image.setImageResource(R.drawable.defaultcovers);
		new ImageCache(context, lruCache, viewHolder.image, IMAGEHOME
				+ handPickedData.getImage(), "OnTheWay", 800, 400);
		viewHolder.userCircleImageView.setTag(AVATAR + userInfo.getAvatar());
		new ImageCache(context, lruCache, viewHolder.userCircleImageView,
				AVATAR + userInfo.getAvatar(), "OnTheWay", 800, 400);
		return view;
	}

	/* 其中计算项高的方法是从staggeredGridView提供的sample中提出来的，就是根据位置得到一个随机的比例 */
	private final Random mRandom = new Random();
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private String content;

	private double getPositionRatio(final int position) {
		double ratio = sPositionHeightRatios.get(position, 0.0);
		if (ratio == 0) {
			ratio = getRandomHeightRatio();
			sPositionHeightRatios.append(position, ratio);
		}
		return ratio;
	}

	private double getRandomHeightRatio() {
		return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
													// the width
	}

	private class ViewHolder {

		private CircleImageView userCircleImageView;
		public TextView pictureCount;
		public TextView favoriteCount;
		public TextView disCitys;
		public TextView viewCount;
		public ImageView image;
		public TextView title;
		public TextView content;
		public TextView publishdate;
		public TextView user_name;

	}

}
