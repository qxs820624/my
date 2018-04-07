package com.edroplet.rent.activities;

import java.util.ArrayList;

import com.edroplet.rent.BaseActivity;
import com.edroplet.rent.beans.HandPickedData;
import com.edroplet.rent.views.StaggeredGridView;
import com.edroplet.rent.R;
import com.edroplet.rent.adapter.TopicsImageDetailsAdapter;
import com.edroplet.rent.clients.ClientApi;
import com.edroplet.rent.utils.LoadingAinm;
//import com.zdp.aseo.content.AseoZdpAseo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TopicsDetailImageActivity extends BaseActivity {
	private StaggeredGridView  gridView;
	private TopicsImageDetailsAdapter adapter;
	private String searchId;
	private TextView titletTextView;
	private RelativeLayout loadRelativeLayout;
	private LinearLayout dataLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topics_detail_image_activity);
		LoadingAinm.ininLoding(TopicsDetailImageActivity.this);
		gridView = (StaggeredGridView ) findViewById(R.id.gridview);
		adapter = new TopicsImageDetailsAdapter(getApplicationContext());
		searchId = getIntent().getStringExtra("SearchId");
		String title = getIntent().getStringExtra("name");
		titletTextView = (TextView) findViewById(R.id.zhuanti_main_title);
		titletTextView.setText(title);
		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		loadRelativeLayout = (RelativeLayout) findViewById(R.id.lodingRelativeLayout);
		dataLinearLayout = (LinearLayout) findViewById(R.id.dataLinearlayout);
		new DownLoad().execute();
	}

	class DownLoad extends AsyncTask<Void, Void, ArrayList<HandPickedData>> {

		protected ArrayList<HandPickedData> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getTourZhuanti(searchId);
		}

		@Override
		protected void onPostExecute(final ArrayList<HandPickedData> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				adapter.BindData(result);
				gridView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(TopicsDetailImageActivity.this,
								HandPickedDetailsActivity.class);
						intent.putExtra("HandPickedData", result.get(position));
						intent.putExtra("id", result.get(position).getTourId());
						startActivity(intent);
					}
				});
			} else {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "网络异常，请检查", 0).show();
			}
		}

	}

}
