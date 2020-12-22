package com.example.bottomnavigationactivity.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bottomnavigationactivity.R;

import java.io.File;

public class PhotoFragment extends Fragment {

    private static String FILE_NAME = "file_name";

    public static PhotoFragment create(File image)
    {
        Bundle args = new Bundle();
        args.putString(FILE_NAME, image.getAbsolutePath());
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(args);
        return photoFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return new ImageView(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            String resource = args.getString(FILE_NAME);
            File resFile;
            if (resource != null) {
                resFile = new File(resource);
                Glide.with(view).load(resource).into((ImageView) view);
            }
        }
    }
}