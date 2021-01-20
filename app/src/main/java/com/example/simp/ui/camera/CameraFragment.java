package com.example.simp.ui.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import com.example.simp.MainActivity;
import com.example.simp.R;
import com.example.simp.camera_components.CameraFragmentDirections;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.jvm.internal.Intrinsics;

public class CameraFragment extends Fragment {
    private String IMAGE_DIR = "myPic";
    private static double RATIO_4_3_VALUE = 4.0/3.0;
    private static double RATIO_16_9_VALUE = 16.0/9.0;
    private static long ANIMATION_SLOW_MILLIS = 100L;
    private static long ANIMATION_FAST_MILLIS = 50L;
    private static String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static String PHOTO_EXTENSION = ".jpg";
    private static String TAG = "CameraFragment";
    private ConstraintLayout container;
    private PreviewView viewFinder;
    private File outputDirectory;
    private LocalBroadcastManager broadcastManager;
    private int displayId = -1;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private Preview preview = null;
    private ImageCapture imageCapture = null;
    private ImageAnalysis imageAnalysis = null;
    private Camera camera = null;
    private ProcessCameraProvider cameraProvider = null;

    private DisplayManager displayManagerInstance = null;
    private synchronized DisplayManager getDisplayManager()
    {
        if(displayManagerInstance == null)
            displayManagerInstance = (DisplayManager)requireContext().getSystemService(Context.DISPLAY_SERVICE);
        return displayManagerInstance;
    }

    private ExecutorService cameraExecutorInstance = null;
    @NotNull
    private synchronized ExecutorService getCameraExecutor() {
        ExecutorService cameraExecutor = this.cameraExecutorInstance;
        if (cameraExecutor == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String) "cameraServiceInstance");
        }
        return cameraExecutor;
    }

    private void setSubject(@NotNull ExecutorService cameraExecutor) {
        Intrinsics.checkParameterIsNotNull((Object) cameraExecutor, (String) "<set-?>");
        this.cameraExecutorInstance = cameraExecutor;
    }
    private static String KEY_EVENT_EXTRA =  "key_event_extra";
    private static String KEY_EVENT_ACTION = "key_event_action";
    private BroadcastReceiver volumnDownReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN))
            {
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    ImageButton shutter = (ImageButton)container.findViewById(R.id.camera_capture_button);
                    shutter.performClick();
            }
        }
    };

    private DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {
        }

        @Override
        public void onDisplayRemoved(int displayId) {
        }

        @Override
        public void onDisplayChanged(int displayId) {
            if(displayId == CameraFragment.this.displayId)
            {
                if(imageCapture != null)
                    imageCapture.setTargetRotation(requireView().getDisplay().getRotation());
                if(imageAnalysis != null)
                    imageAnalysis.setTargetRotation(requireView().getDisplay().getRotation());
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(!PermissionFragment.hasPermission(requireContext()))
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                    CameraFragmentDirections.actionCameraToPermissions());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getCameraExecutor().shutdown();
        broadcastManager.unregisterReceiver(volumnDownReceiver);
        getDisplayManager().unregisterDisplayListener(displayListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    //SeGalleryThumbnail

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (ConstraintLayout) view;
        viewFinder = container.findViewById(R.id.view_finder);
        cameraExecutorInstance = Executors.newSingleThreadExecutor();
        broadcastManager = LocalBroadcastManager.getInstance(view.getContext());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KEY_EVENT_ACTION);
        broadcastManager.registerReceiver(volumnDownReceiver, intentFilter);

        getDisplayManager().registerDisplayListener(displayListener, null);

        outputDirectory = MainActivity.getOutputDirectory(requireContext());
        Log.d(TAG, "onViewCreated: output Directory: " + outputDirectory);

        viewFinder.post(new Runnable() {
            @Override
            public void run() {
                displayId = viewFinder.getDisplay().getDisplayId();

                updateCameraUI();

                setUpCamera();
            }
        });
    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }

    private void setUpCamera()
    {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    cameraProvider = cameraProviderFuture.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if(hasBackCamera())
                    lensFacing = CameraSelector.LENS_FACING_BACK;
                else if(hasFrontCamera())
                    lensFacing = CameraSelector.LENS_FACING_FRONT;
                else
                    throw new IllegalStateException("Device hasn't back and front camera!");

                //updateCameraSwitchButton()

                bindCameraUseCase();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindCameraUseCase()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        viewFinder.getDisplay().getRealMetrics(metrics);
        Log.d(TAG, "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}");

        int screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels);
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio");

        int rotation = viewFinder.getDisplay().getRotation();

        ProcessCameraProvider camProvider = cameraProvider;
        if(camProvider == null)
            throw new IllegalStateException("Camera initialization failed!");


        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();

        preview = new Preview.Builder().setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(rotation).build();

        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .setTargetAspectRatio(screenAspectRatio).setTargetRotation(rotation).build();

        imageAnalysis = new ImageAnalysis.Builder().setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(rotation).build();
        //imageAnalysis.setAnalyzer(getCameraExecutor(), LuminosityAnalyzer{});

        cameraProvider.unbindAll();

        try
        {
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);
            preview.setSurfaceProvider(viewFinder.getSurfaceProvider());
        }
        catch (Exception e) {
            Log.e(TAG, "bindCameraUseCase: ", e);
        }
    }

    private int aspectRatio(int width, int height)
    {
        double previewRatio = (double) Math.max(width, height) / (double) Math.min(width, height);
        if(Math.abs(previewRatio - RATIO_4_3_VALUE) <= Math.abs(previewRatio - RATIO_16_9_VALUE))
            return AspectRatio.RATIO_4_3;
        return AspectRatio.RATIO_16_9;
    }

    private void updateCameraUI()
    {
        ConstraintLayout prevCL = (ConstraintLayout) container.findViewById(R.id.camera_ui_container);
        if (prevCL != null)
            container.removeView(prevCL);

        View controls = View.inflate(requireContext(), R.layout.camera_ui_container, container);

        controls.findViewById(R.id.camera_capture_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageCapture != null)
                {
                    File photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION);

                    ImageCapture.Metadata metadata = new ImageCapture.Metadata();
                    metadata.setReversedHorizontal(lensFacing == CameraSelector.LENS_FACING_FRONT);

                    ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile)
                            .setMetadata(metadata).build();


                    imageCapture.takePicture(outputOptions, getCameraExecutor(), new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            Uri savedUri = outputFileResults.getSavedUri();
                            if (savedUri == null)
                                savedUri = Uri.fromFile(photoFile);
                            Log.d(TAG, "photo capture onImageSaved: " + savedUri.toString());

//                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                                setGalleryThumbnail(savedUri);

                            String savedFilePath = new File(savedUri.getPath()).getAbsolutePath();
                            Log.d(TAG, "photo capture onImageSaved: saved file path: " + savedFilePath);
                            String mimeType = MimeTypeMap.getSingleton()
                                    .getMimeTypeFromExtension(savedFilePath.substring(savedFilePath.lastIndexOf(".")));
                            MediaScannerConnection.scanFile(requireContext(), new String[]{savedFilePath}, new String[]{mimeType}, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.d(TAG, "photo capture onScanCompleted: Completed " + uri.toString() + " path " + path);
                                }
                            });
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            Log.e(TAG, "Photo capture failed: " + exception.getMessage());
                        }
                    });

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        container.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                container.setForeground(new ColorDrawable(Color.WHITE));
                                container.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        container.setForeground(null);
                                    }
                                }, ANIMATION_FAST_MILLIS);
                            }
                        }, ANIMATION_SLOW_MILLIS);
                    }
                }
            }
        });


        ImageButton photoButton = (ImageButton)controls.findViewById(R.id.photo_view_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(outputDirectory.listFiles()).length > 0)
                {
                    Log.d(TAG, "onClick: photo view button: directory: " + outputDirectory.getAbsolutePath());
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(CameraFragmentDirections
                                    .actionCameraToGallery(outputDirectory.getAbsolutePath()));
                }
            }
        });
    }

    //private void updateCameraSwitchButton(){}

    private boolean hasBackCamera()
    {
        if(cameraProvider != null) {
            try {
                return cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA);
            } catch (CameraInfoUnavailableException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean hasFrontCamera()
    {
        if(cameraProvider != null) {
            try {
                return cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA);
            } catch (CameraInfoUnavailableException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private File createFile(File baseFolder, String format, String extension)
    {
        return new File(baseFolder, new SimpleDateFormat(format, Locale.CHINA)
                .format(System.currentTimeMillis()) + extension);
    }
}