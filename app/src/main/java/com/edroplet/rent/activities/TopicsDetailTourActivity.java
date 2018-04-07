package com.edroplet.rent.activities;

import java.util.ArrayList;

import com.edroplet.rent.BaseActivity;
import com.edroplet.rent.R;
import com.edroplet.rent.adapter.HandPickedAdapter;
import com.edroplet.rent.beans.HandPickedData;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TopicsDetailTourActivity extends BaseActivity {
	private ListView listView;
	private HandPickedAdapter adapter;
	private String searchId;
	private TextView titletTextView;
	private RelativeLayout loadRelativeLayout;
	private LinearLayout dataLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topics_detail);
		LoadingAinm.ininLoding(TopicsDetailTourActivity.this);
		listView = (ListView) findViewById(R.id.tour_zhuanti_listview);
		adapter = new HandPickedAdapter(getApplicationContext());
		searchId = getIntent().getStringExtra("SearchId");
		String title = getIntent().getStringExtra("name");
		titletTextView = (TextView) findViewById(R.id.zhuanti_main_title);
		titletTextView.setText(title);
		// AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		loadRelativeLayout = (RelativeLayout) findViewById(R.id.lodingRelativeLayout);
		dataLinearLayout = (LinearLayout) findViewById(R.id.data);
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
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(TopicsDetailTourActivity.this,
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
