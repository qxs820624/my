package com.edroplet.rent.activities;

import java.util.ArrayList;

import com.edroplet.rent.BaseActivity;
import com.edroplet.rent.R;
import com.edroplet.rent.adapter.HandPickedDetailsAdapter;
import com.edroplet.rent.beans.HandPickedData;
import com.edroplet.rent.beans.HandPickedDetailData;
import com.edroplet.rent.clients.ClientApi;
import com.edroplet.rent.utils.ImageCache;
import com.edroplet.rent.utils.LoadingAinm;
//import com.zdp.aseo.content.AseoZdpAseo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @name HandPickedDetailsActivity
 * @Descripation <br>
 *               1、<br>
 *               2、<br>
 *               3、<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class HandPickedDetailsActivity extends BaseActivity {
	private ImageView mainImage;
	private TextView mainText;
	private ListView listView;
	private HandPickedDetailsAdapter adapter;
	private LruCache<String, Bitmap> lruCache;
	private RelativeLayout loadrRelativeLayout;
	private LinearLayout dataLinearLayout;
	private Button viewCount, commentButton, shareButton;
	private String startId;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hand_picked_details);
		initViews();
		new DownData().execute();

	}

	private void initViews() {
		View headerView = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.hand_picked_details_header, null);
		mainImage = (ImageView) headerView
				.findViewById(R.id.jingxuan_detail_main_image);
		mainText = (TextView) headerView
				.findViewById(R.id.jingxuan_detail_main_txt);
		listView = (ListView) findViewById(R.id.jingxuan_detail_listview);
		commentButton = (Button) findViewById(R.id.CommentCount);
		shareButton = (Button) findViewById(R.id.share);
		viewCount = (Button) findViewById(R.id.likeCount);
		adapter = new HandPickedDetailsAdapter(getApplicationContext());
		dataLinearLayout = (LinearLayout) findViewById(R.id.list);
		LoadingAinm.ininLoding(HandPickedDetailsActivity.this);
		title = (TextView) findViewById(R.id.detail_main_title);
		loadrRelativeLayout = (RelativeLayout) findViewById(R.id.lodingRelativeLayout);
		Intent intent = getIntent();
		final HandPickedData handPickedData = (HandPickedData) intent
				.getSerializableExtra("HandPickedData");
		lruCache = ImageCache.GetLruCache(getApplicationContext());
		mainImage.setTag("http://img.117go.com/timg/p308/"
				+ handPickedData.getImage());
		new ImageCache(getApplicationContext(), lruCache, mainImage,
				"http://img.117go.com/timg/p308/" + handPickedData.getImage(),
				"OnTheway", 800, 400);
		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		mainText.setText(handPickedData.getForeword());
		listView.addHeaderView(headerView);
		startId = intent.getStringExtra("id");
		title.setText(handPickedData.getTitle());
		viewCount.setText(handPickedData.getViewCount());
		commentButton.setText(handPickedData.getCmtCount());
		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 跳转到第三方分享界面
				Intent intent = new Intent(HandPickedDetailsActivity.this,
						ShareActivity.class);
				intent.putExtra("shareContent", handPickedData.getForeword());
				startActivity(intent);
			}
		});

	}

	class DownData extends AsyncTask<Void, Void, ArrayList<HandPickedDetailData>> {

		@Override
		protected ArrayList<HandPickedDetailData> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getJingxuanDetailDatas(startId);
		}

		@Override
		protected void onPostExecute(ArrayList<HandPickedDetailData> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				loadrRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(HandPickedDetailsActivity.this, "网络异常,请检查！", Toast.LENGTH_LONG)
						.show();
			} else {
				adapter.BindData(result);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadrRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
			}

		}

	}

}
