package com.edroplet.rent.fragments;

import com.edroplet.rent.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @name LoginFragment
 * @Descripation 登陆的Fragment<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class LoginFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.login, container,false);
	}

}
