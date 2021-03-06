package com.edroplet.rent.activities;

import java.util.ArrayList;

import com.edroplet.rent.R;
import com.edroplet.rent.fragments.ActivitiesFragment;
import com.edroplet.rent.fragments.HandPickedFragment;
import com.edroplet.rent.fragments.LoginFragment;
import com.edroplet.rent.fragments.TopicsFragment;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @name MainActivity
 * @Descripation 这是在路上主界面的设计类<br>
 *               1、精选<br>
 *               2、专题<br>
 *               3、活动<br>
 *               4、记录<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	private TextView title;
	private Animation loadAnimation;
	private HandPickedFragment handPickedFragment;
	private ActivitiesFragment activitiesFragment;
	private TopicsFragment topicsFragment;
	private LoginFragment loginFragment;
	private ArrayList<Fragment> fragments;
	private RadioGroup group;
	private RadioButton imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
//		AseoZdpAseo.initTimer(this);
		group = (RadioGroup) findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		fragments = new ArrayList<Fragment>();
		fragments.add(handPickedFragment);
		fragments.add(topicsFragment);
		fragments.add(activitiesFragment);
		fragments.add(loginFragment);
		if (getIntent().getIntExtra("FragmentType",0)==3) {
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.main_framelayout,fragments.get(3));
			transaction.commit();
			title.setText("登录");
		}else {
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.main_framelayout, fragments.get(0));
			transaction.commit();
			title.setText("精选");
		}
		
	
	}

	private void initViews() {
		handPickedFragment = new HandPickedFragment();
		topicsFragment = new TopicsFragment();
		activitiesFragment = new ActivitiesFragment();
		loginFragment=new LoginFragment();
		title = (TextView) findViewById(R.id.main_title);
		imageView = (RadioButton) findViewById(R.id.main_add);
		// AseoZdpAseo.init(this, AseoZdpAseo.INSERT_TYPE);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loadAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.btn_add);
				imageView.startAnimation(loadAnimation);
				startActivity(new Intent(MainActivity.this, AddActivity.class));
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	@Override
	public void onCheckedChanged(RadioGroup view, int checkId) {
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btnButton = null;
		for (int i = 0; i < childCount; i++) {
			btnButton = (RadioButton) group.getChildAt(i);
			if (btnButton.isChecked()) {
				checkedIndex = i;
				break;
			}
		}

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = null;
		switch (checkedIndex) {
		case 0:
			fragment = fragments.get(0);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			title.setText("精选");
			break;
		case 1:
			fragment = fragments.get(1);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			title.setText("专题");
			break;
		case 2:
			
			break;
		case 3:
			fragment = fragments.get(2);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			title.setText("活动");
			break;
		case 4:
			fragment = fragments.get(3);
			transaction.replace(R.id.main_framelayout, fragment);
			transaction.commit();
			title.setText("登录");
			break;

		default:
			break;
		}

	}

}
