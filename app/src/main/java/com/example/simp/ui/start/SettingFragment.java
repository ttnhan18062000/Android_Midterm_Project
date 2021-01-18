package com.example.simp.ui.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.simp.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SettingFragment extends Fragment {
    private ArrayList<String> languages = new ArrayList<String>();
    private ArrayList<String> themes = new ArrayList<String>();
    private Spinner spinnerLanguage;
    private Spinner spinnerTheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadLocal();
        initListItem(view);
        updateLocal();
        ImageButton back = view.findViewById(R.id.image_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionSetting_toMain);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initListItem(View parentView) {
        spinnerLanguage = parentView.findViewById(R.id.spinner_setting_language);
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(requireContext(),
                R.array.language_list, R.layout.setting_spinner_item);

        adapterLanguage.setDropDownViewResource(R.layout.setting_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterLanguage);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((TextView) view.findViewById(R.id.text_view_spinner_item)).getText().toString().equals("English"))
                    setLocal("en");
                else
                    setLocal("vi");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences pref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = pref.getString("Language", "");
        if(language != null && !language.equals("") && language.equals("en"))
            spinnerLanguage.setSelection(0);
        else if (language != null && !language.equals("") && language.equals("vi"))
            spinnerLanguage.setSelection(1);
    }


    public void setLocal(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("Language", language);
        editor.apply();
        updateLocal();
    }

    public void loadLocal(){
        SharedPreferences pref = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = pref.getString("Language", "");
        setLocal(language);
    }

    public void updateLocal() {
        ((TextView)requireView().findViewById(R.id.setting_text_view_language)).setText(requireContext().getResources().getString(R.string.setting_language));
        ((TextView)requireView().findViewById(R.id.text_view_title)).setText(requireContext().getResources().getString(R.string.title_setting));
    }
}
