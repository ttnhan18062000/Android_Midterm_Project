package com.example.simp.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.simp.BuildConfig;
import com.example.simp.R;
import com.example.simp.gallery_components.GalleryFragmentArgs;
import com.example.simp.ui.editor.EditorFragment;
import com.example.simp.utility.MyFileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GalleryFragment extends Fragment {
    private static String TAG = "GalleryFragment";
    private GalleryFragmentArgs args = new GalleryFragmentArgs();
    private int currentPos = 0;
    private ArrayList<File> mediaList = new ArrayList<>();
    private static String[] EXTENSIONS = {"JPG"};
    private static int FLAGS_FS = View.SYSTEM_UI_FLAG_FULLSCREEN;

    class MediaPagerAdapter extends FragmentStatePagerAdapter{

        public MediaPagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int pos) {
            return PhotoFragment.create(mediaList.get(pos));
        }

        @Override
        public int getCount() {
            return mediaList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Bundle bundle = getArguments();
        if(bundle != null) {
            args = GalleryFragmentArgs.fromBundle(bundle);
        }

        File rootDir = new File(args.getRootDirectory());

        File[] files = rootDir.listFiles();
        if(files == null)
            return;
        Log.d(TAG, "onCreate: NumOfFiles: " + String.valueOf(files.length));
        for (int i = 0; i < files.length; ++i) {
            Log.d(TAG, "onCreate: files[" + String.valueOf(i) + "] with path: " + files[i].getAbsolutePath());
            //Log.d(TAG, "onCreate: files[" + String.valueOf(i) + "] with extension: " + MyFileManager.getExtension(files[i].getAbsolutePath()));
            boolean isMatched = false;
            for(int j = 0; j < EXTENSIONS.length; ++j) {
                if (EXTENSIONS[j].contains(MyFileManager.getExtension(files[i].getAbsolutePath()).toUpperCase()))
                    isMatched = true;
            }
            if(isMatched)
                mediaList.add(0, files[i]);
        }
        Log.d(TAG, "onCreate: NumOfFilesFiltered: " + String.valueOf(mediaList.size()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mediaList.isEmpty())
        {
            view.findViewById(R.id.delete_button).setEnabled(false);
            view.findViewById(R.id.share_button).setEnabled(false);
        }

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.photo_view_pager);
        viewPager.setOffscreenPageLimit(2);
        MediaPagerAdapter mediaPagerAdapter = new MediaPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mediaPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                Log.d(TAG, "onPageSelected: current pos: " + String.valueOf(currentPos));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        {
            View cutoutView = view.findViewById(R.id.cutout_safe_area);
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            if(rootWindowInsets != null)
            {
                DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
                if(displayCutout != null) {
                    padding(displayCutout, cutoutView);
                }
            }

            cutoutView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    DisplayCutout displayCutout = Objects.requireNonNull(rootWindowInsets).getDisplayCutout();
                    if(displayCutout != null) {
                        padding(displayCutout, v);
                    }
                    return insets;
                }
            });
        }

        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            }
        });

        view.findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaList == null)
                    return;

                Intent intent = new Intent();

                String mediaType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MyFileManager
                        .getExtension(mediaList.get(currentPos).getAbsolutePath()));

                Uri uri = FileProvider.getUriForFile(view.getContext(),
                        BuildConfig.APPLICATION_ID + ".provider", mediaList.get(currentPos));

                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType(mediaType);
                intent.setAction(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(intent, "Sharing"));
            }
        });

        view.findViewById(R.id.to_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaList == null)
                    return;

                Bundle bundle = new Bundle();
                bundle.putString("path", mediaList.get(currentPos).getAbsolutePath());
                EditorFragment editorFragment = new EditorFragment();
                editorFragment.setArguments(bundle);
                replaceFragment(editorFragment);
            }
        });

        view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaList == null)
                    return;
                final int j = currentPos;
                Log.d(TAG, "onClick: deleted image at pos: " + String.valueOf(j));
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog)
                        .setTitle("Confirm")
                        .setMessage("Delete current photo")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mediaList.get(j).delete();

                                MediaScannerConnection.scanFile(view.getContext(),
                                        new String[]{mediaList.get(j).getAbsolutePath()},
                                        null, null);

                                mediaList.remove(mediaList.get(j));
                                Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();

                                if (mediaList.isEmpty())
                                    Navigation.findNavController(requireActivity(),
                                            R.id.nav_host_fragment).navigateUp();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .create();

                showImmersive(alertDialog);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void padding(DisplayCutout displayCutout, View view)
    {
        view.setPadding(displayCutout.getSafeInsetLeft(),
                displayCutout.getSafeInsetTop(),
                displayCutout.getSafeInsetRight(),
                displayCutout.getSafeInsetBottom());
    }

    private void showImmersive(AlertDialog alertDialog)
    {
        Window window = alertDialog.getWindow();
        if(window != null){
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

            window.getDecorView().setSystemUiVisibility(FLAGS_FS);

            alertDialog.show();

            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction ft = requireFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.nav_host_fragment, fragment); // f1_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
