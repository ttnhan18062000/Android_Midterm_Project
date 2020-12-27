package com.example.bottomnavigationactivity.ui.start;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bottomnavigationactivity.FloatingMenu.FloatingActionButton;
import com.example.bottomnavigationactivity.R;

import java.util.Objects;


public class StartFragment extends Fragment {

    View mFragment;
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragment = inflater.inflate(R.layout.fragment_start, container, false);
        CardView imageButtonStart = mFragment.findViewById(R.id.card_view_login);
        imageButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionGlobal_toCamera);
            }
        });
        CardView imageButtonTutorial = mFragment.findViewById(R.id.card_view_Tutorial);
        imageButtonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionMain_toTutorial);
            }
        });
        return mFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }
}
