package com.example.bottomnavigationactivity.ui.process;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.ui.camera.CameraFragment;
import com.example.bottomnavigationactivity.ui.editor.EditorFragment;
import com.example.bottomnavigationactivity.utility.MyImageManager;

public class ProcessFragment extends Fragment {
    private View fragmentView = null;
    private Bitmap bitmap;
    private View.OnClickListener onClickButtonProcessFragment = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment = null;
            switch(view.getId())
            {
                case R.id.btn_back:
                    fragment = new CameraFragment();
                    replaceFragment(fragment);
                    break;
                case R.id.btn_process:
                    fragment = new EditorFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("ImageBitmap", MyImageManager.BitMapToString(bitmap));
                    fragment.setArguments(arguments);
                    break;
                case R.id.btn_discard:
                    break;
                case R.id.btn_set_plain:
                    break;
            }
            replaceFragment(fragment);
        }
    };

    private void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_process, container, false);
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        assert args != null;
        Uri imageUri = Uri.parse(args.getString("ImageUri"));
        bitmap = MyImageManager.loadImage(requireActivity(), imageUri);
        ImageView imageView = fragmentView.findViewById(R.id.imageViewCapturedImage);
        imageView.setImageBitmap(MyImageManager.rotateBitmap(bitmap, 90));
        initOnClickListener();
        return fragmentView;
    }

    private void initOnClickListener() {
        setOnClickListenerForButton(R.id.btn_back, onClickButtonProcessFragment);
        setOnClickListenerForButton(R.id.btn_process, onClickButtonProcessFragment);
        setOnClickListenerForButton(R.id.btn_discard, onClickButtonProcessFragment);
        setOnClickListenerForButton(R.id.btn_set_plain, onClickButtonProcessFragment);
    }

    private void setOnClickListenerForButton(int buttonID, View.OnClickListener onClickListener) {
        Button button = fragmentView.findViewById(buttonID);
        button.setOnClickListener(onClickListener);
    }
}
