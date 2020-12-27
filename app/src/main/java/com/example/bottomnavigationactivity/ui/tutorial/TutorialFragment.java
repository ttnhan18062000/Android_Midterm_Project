package com.example.bottomnavigationactivity.ui.tutorial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.tutorial_components.MyTutorial;

import java.util.ArrayList;

public class TutorialFragment extends Fragment {
    private static String TAG = "TutorialFragment";
    private int currentPos = 0;
    private ArrayList<MyTutorial> tutorialList = new ArrayList<MyTutorial>();
    private ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    class TutorialPagerAdapter extends FragmentStatePagerAdapter {

        public TutorialPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int pos) {
            return TutorialPageFragment.create(tutorialList.get(pos));
        }

        @Override
        public int getCount() {
            return tutorialList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        initTutorialList();
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    private void initTutorialList() {
        for(int i = 0; i < 6; i++)
        {
            MyTutorial myTutorial = new MyTutorial(i);
            tutorialList.add(myTutorial);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getImageViewList(view);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_tutorial);
        viewPager.setOffscreenPageLimit(2);
        TutorialFragment.TutorialPagerAdapter mediaPagerAdapter = new TutorialFragment.TutorialPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mediaPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imageList.get(currentPos).setAlpha(0.4f);
                currentPos = position;
                imageList.get(currentPos).setAlpha(1f);
                Log.d(TAG, "onPageSelected: current pos: " + String.valueOf(currentPos));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getImageViewList(View view) {
        imageList.add(view.findViewById(R.id.image_view_tutorial_1));
        imageList.add(view.findViewById(R.id.image_view_tutorial_2));
        imageList.add(view.findViewById(R.id.image_view_tutorial_3));
        imageList.add(view.findViewById(R.id.image_view_tutorial_4));
        imageList.add(view.findViewById(R.id.image_view_tutorial_5));
        imageList.add(view.findViewById(R.id.image_view_tutorial_6));
    }
}
