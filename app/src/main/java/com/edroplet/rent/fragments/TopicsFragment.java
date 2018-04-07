package com.edroplet.rent.fragments;

import java.util.ArrayList;

import com.edroplet.rent.R;
import com.edroplet.rent.activities.TopicsDetailImageActivity;
import com.edroplet.rent.activities.TopicsDetailTourActivity;
import com.edroplet.rent.adapter.TopicsAdapter;
import com.edroplet.rent.beans.TopicsData;
import com.edroplet.rent.clients.ClientApi;
import com.edroplet.rent.utils.LoadingAinm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @name TopicsFragment
 * @Descripation 这是关于<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class TopicsFragment extends Fragment {
	private ListView image_listview;
	private ListView tour_listview;
	private TextView image_more;
	private TextView tour_more;
	private TopicsAdapter tour_adapter;
	private TopicsAdapter iamge_adaAdapter;
	private RelativeLayout loadRelativeLayout;
	private ScrollView scrollView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_topic, container,
				false);
		LoadingAinm.ininLodingView(view);
		initViews(view);
		new Downdata().execute();
		image_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		tour_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}

	private void initViews(View view) {
		image_listview = (ListView) view
				.findViewById(R.id.image_zhuanti_listView);
		tour_listview = (ListView) view
				.findViewById(R.id.lvxing_zhuanti_listView);
		image_more = (TextView) view.findViewById(R.id.zhuanti_image_more);
		tour_more = (TextView) view.findViewById(R.id.zhuanti_tour_more);
		iamge_adaAdapter = new TopicsAdapter(getActivity());
		tour_adapter = new TopicsAdapter(getActivity());
		loadRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.lodingRelativeLayout);
		scrollView = (ScrollView) view.findViewById(R.id.data);

	}

	class Downdata extends
			AsyncTask<Void, Void, ArrayList<ArrayList<TopicsData>>> {

		@Override
		protected ArrayList<ArrayList<TopicsData>> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getzhuantiDatas();
		}

		@Override
		protected void onPostExecute(
				final ArrayList<ArrayList<TopicsData>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				iamge_adaAdapter.BindData(result.get(1));
				image_listview.setAdapter(iamge_adaAdapter);
				iamge_adaAdapter.notifyDataSetChanged();
				image_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								TopicsDetailImageActivity.class);
						intent.putExtra("SearchId", result.get(0).get(position)
								.getId());
						intent.putExtra("name", result.get(0).get(position)
								.getName());
						startActivity(intent);
					}
				});
				tour_adapter.BindData(result.get(0));
				tour_listview.setAdapter(tour_adapter);
				tour_listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent(getActivity(),
								TopicsDetailTourActivity.class);
						intent.putExtra("SearchId", result.get(0).get(position)
								.getId());
						intent.putExtra("name", result.get(0).get(position)
								.getName());
						startActivity(intent);
					}
				});
				tour_adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				scrollView.setVisibility(View.VISIBLE);
			} else {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "网络异常", 0).show();
			}
		}

	}

}
