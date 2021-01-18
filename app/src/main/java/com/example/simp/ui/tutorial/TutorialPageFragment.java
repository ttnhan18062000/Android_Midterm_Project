package com.example.simp.ui.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simp.R;
import com.example.simp.tutorial_components.MyTutorial;

public class TutorialPageFragment extends Fragment {
    private MyTutorial myTutorial = null;
    public static TutorialPageFragment create(MyTutorial tutorial)
    {
        TutorialPageFragment tutorialPageFragment = new TutorialPageFragment();
        tutorialPageFragment.myTutorial = tutorial;
        return tutorialPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tutorial_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
