package com.example.simp.ui.start;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.simp.MainActivity;
import com.example.simp.R;


public class StartFragment extends Fragment {

    View mFragment;
    Activity mActivity;
    private static String TAG = "StartFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragment = inflater.inflate(R.layout.fragment_start, container, false);
        CardView start = mFragment.findViewById(R.id.card_view_login);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)mActivity).getLoginStatus())
                {
                    v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.actionGlobal_toCamera);
                    ((MainActivity)mActivity).turnActionButton(true);
                }
                else {
                    v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.actionMain_toLogin);
                }
            }
        });

        CardView tutorial = mFragment.findViewById(R.id.card_view_Tutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionMain_toTutorial);
            }
        });

        CardView setting = mFragment.findViewById(R.id.card_view_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionMain_toSetting);
            }
        });

        CardView credit = mFragment.findViewById(R.id.card_view_credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionMain_toCredit);
            }
        });

        CardView logout = mFragment.findViewById(R.id.card_view_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mActivity).setLoginStatus(false);
                refreshFragmentState();
            }
        });

        TextView textView = mFragment.findViewById(R.id.text_view_login);
        if(((MainActivity)mActivity).getLoginStatus()) {
            textView.setText(getString(R.string.main_start));
            logout.setVisibility(View.VISIBLE);
        }
        else {
            textView.setText(getString(R.string.main_login));
            logout.setVisibility(View.INVISIBLE);
        }
        return mFragment;
    }

    private void refreshFragmentState()
    {
        TextView textView = mFragment.findViewById(R.id.text_view_login);
        CardView logout = mFragment.findViewById(R.id.card_view_logout);
        textView.setText(getString(R.string.main_login));
        logout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }
}
