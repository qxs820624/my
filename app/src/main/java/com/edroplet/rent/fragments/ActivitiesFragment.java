package com.edroplet.rent.fragments;

import java.util.ArrayList;

import com.edroplet.rent.activities.ActivitiesDetailActivity;
import com.edroplet.rent.R;
import com.edroplet.rent.adapter.ActivitiesAdapter;
import com.edroplet.rent.beans.ActivitiesData;
import com.edroplet.rent.clients.ClientApi;
import com.edroplet.rent.utils.LoadingAinm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @name ActivitiesFragment
 * @Descripation 这是活动的是实体类<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class ActivitiesFragment extends Fragment {
	private ListView listView;
	private ActivitiesAdapter adapter;
	private RelativeLayout loadRelativeLayout;
	private LinearLayout dataLinearLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =inflater.inflate(R.layout.fragment_activity, container, false);
		listView=(ListView) view.findViewById(R.id.hongdong_listview);
		adapter=new ActivitiesAdapter(getActivity());
		new DownData().execute();
		loadRelativeLayout=(RelativeLayout) view.findViewById(R.id.lodingRelativeLayout);
		dataLinearLayout=(LinearLayout) view.findViewById(R.id.huodongLinearlayout);
		LoadingAinm.ininLodingView(view);
		return view;
	}
	
	class DownData extends AsyncTask<Void, Void, ArrayList<ActivitiesData>>{

		@Override
		protected ArrayList<ActivitiesData> doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return ClientApi.getHuoDongList();
		}
		
		@Override
		protected void onPostExecute(final ArrayList<ActivitiesData> result) {
		
			super.onPostExecute(result);
			if (result!=null) {
				adapter.BindData(result);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				loadRelativeLayout.setVisibility(View.GONE);
				dataLinearLayout.setVisibility(View.VISIBLE);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent =new Intent(getActivity(),ActivitiesDetailActivity.class);
						intent.putExtra("url",result.get(position).getUrlS());
						intent.putExtra("name", result.get(position).getName());
						startActivity(intent);
					}
				});
			}
			else {
				loadRelativeLayout.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "网络异常,请检查",1).show();
			}
		}
		
		
	}

}
