package com.example.bottomnavigationactivity.ui.process;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.process_components.SetPlainDialog;
import com.example.bottomnavigationactivity.ui.camera.CameraFragment;
import com.example.bottomnavigationactivity.ui.editor.EditorFragment;
import com.example.bottomnavigationactivity.utility.MyImageManager;

public class ProcessFragment extends Fragment {
    private View fragmentView = null;
    private Bitmap bitmap;
    private Uri imageUri;
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

                    long start = System.currentTimeMillis();
                    arguments.putString("ImageBitmap", MyImageManager.bitMapToString(bitmap));
                    long end = System.currentTimeMillis();
                    Log.d("Timing: ", String.valueOf(end-start));
                    fragment.setArguments(arguments);
                    replaceFragment(fragment);
                    break;
                case R.id.btn_discard:
                    fragment = new CameraFragment();
                    MyImageManager.deleteImage(imageUri, requireContext());
                    replaceFragment(fragment);
                    break;
                case R.id.btn_set_plain:
                    //showSetPlainDialog();
                    break;
            }
        }
    };

    private void showSetPlainDialog() {
        FragmentManager fm = getFragmentManager();
        SetPlainDialog editNameDialogFragment = SetPlainDialog.newInstance();
        // SETS the target fragment for use later when sending results
        editNameDialogFragment.setTargetFragment(this, 300);
        editNameDialogFragment.show(fm, "fragment_set_plain");
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
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
        imageUri = Uri.parse(args.getString("ImageUri"));
        bitmap = MyImageManager.loadImage(requireActivity(), imageUri);
        ImageView imageView = fragmentView.findViewById(R.id.imageViewCapturedImage);
        imageView.setImageBitmap(bitmap);
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
