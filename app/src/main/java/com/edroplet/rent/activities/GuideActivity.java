package com.edroplet.rent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edroplet.rent.R;
import com.edroplet.rent.views.VerticalViewPager;

import java.util.Locale;


public class GuideActivity extends AppCompatActivity {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.75f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        verticalViewPager.setAdapter(new DummyAdapter(fm));
        verticalViewPager.setPageMargin(0);
        //verticalViewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

        verticalViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                View localView = view.findViewById(R.id.view_scroller);
                if ((position < -1.0F) || (position > 1.0F))
                    return;
                localView.setTranslationY(-(1.0F * (position * view.getHeight())));
            }
        });
    }

    public class DummyAdapter extends FragmentPagerAdapter {

        public DummyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "PAGE 1";
                case 1:
                    return "PAGE 2";
                case 2:
                    return "PAGE 3";
            }
            return null;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            int resId = 0;
            switch (sectionNumber) {
                case 1:
                    resId = R.layout.feature_page1_layout;
                    break;
                case 2:
                    resId = R.layout.feature_page2_layout;
                    break;
                case 3:
                    resId = R.layout.feature_page3_layout;
                    break;
            }
            args.putInt("resId", resId);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int resourceId = getArguments().getInt("resId");
            View rootView = inflater.inflate(resourceId, container, false);
            if (R.layout.feature_page3_layout == resourceId){
                rootView.findViewById(R.id.guide_to_login).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
            }
            return rootView;
        }


    }

}
