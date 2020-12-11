package com.example.bottomnavigationactivity.ui.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
<<<<<<< Updated upstream
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
=======
import android.webkit.MimeTypeMap;
import android.widget.Filter;
import android.widget.ImageButton;
>>>>>>> Stashed changes

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;

import com.example.bottomnavigationactivity.MainActivity;
import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.camera_components.CameraFragmentDirections;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.ImageFormat;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraMetadata;
//import android.hardware.camera2.CaptureRequest;
//import android.hardware.camera2.TotalCaptureResult;
//import android.hardware.camera2.params.StreamConfigurationMap;
//import android.media.Image;
//import android.media.ImageReader;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.Log;
//import android.util.Size;
//import android.util.SparseIntArray;
//import android.view.Surface;
//import android.view.TextureView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Toast;
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.bottomnavigationactivity.MainActivity;
//import com.example.bottomnavigationactivity.R;
//import com.example.bottomnavigationactivity.ui.process.ProcessFragment;
//import com.example.bottomnavigationactivity.utility.MyImageManager;
//
//import org.jetbrains.annotations.NotNull;

public class CameraFragment extends Fragment {

<<<<<<< Updated upstream
    View fragmentView;
    private static final String TAG = "CameraApi";
    private Button takePictureButton;
    private TextureView textureView;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest captureRequest;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_STORAGE_PERMISSION = 201;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private String IMAGE_DIR = "myPic";
=======
    private static double RATIO_4_3_VALUE = 4.0/3.0;
    private static double RATIO_16_9_VALUE = 16.0/9.0;
    private static long ANIMATION_SLOW_MILLIS = 100L;
    private static long ANIMATION_FAST_MILLIS = 50L;
    private static String FILENAME = "picture";
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
>>>>>>> Stashed changes

    private DisplayManager displayManagerInstance = null;
    private synchronized DisplayManager getDisplayManager()
    {
        if(displayManagerInstance == null)
            displayManagerInstance = (DisplayManager)requireContext().getSystemService(Context.DISPLAY_SERVICE);
        return displayManagerInstance;
    }

<<<<<<< Updated upstream
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestStorage();
        fragmentView = inflater.inflate(R.layout.fragment_camera, container, false);
        textureView = (TextureView)fragmentView. findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        takePictureButton = (Button) fragmentView.findViewById(R.id.btn_takepicture);
        assert takePictureButton != null;
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        return fragmentView;
=======
    private ExecutorService cameraExecutorInstance = null;
    @NotNull
    private ExecutorService getCameraExecutor() {
        ExecutorService cameraExecutor = this.cameraExecutorInstance;
        if (cameraExecutor == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String) "cameraServiceInstance");
        }
        return cameraExecutor;
>>>>>>> Stashed changes
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

        ImageButton switchButton = (ImageButton)controls.findViewById(R.id.camera_switch_button);
        if(switchButton != null)
        {
            switchButton.setEnabled(false);
            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CameraSelector.LENS_FACING_FRONT == lensFacing)
                        lensFacing = CameraSelector.LENS_FACING_BACK;
                    else
                        lensFacing = CameraSelector.LENS_FACING_FRONT;
                    bindCameraUseCase();
                }
            });
        }
<<<<<<< Updated upstream
    }
    private void openCamera() {
        CameraManager manager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
=======

        ImageButton photoButton = (ImageButton)controls.findViewById(R.id.photo_view_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.requireNonNull(outputDirectory.listFiles()).length > 0)
                {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(CameraFragmentDirections
                                    .actionCameraToGallery(outputDirectory.getAbsolutePath()));
                }
>>>>>>> Stashed changes
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

    //
//    View fragmentView;
//    private static final String TAG = "CameraApi";
//    private ImageButton takePictureButton;
//    private TextureView textureView;
//    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
//    static {
//        ORIENTATIONS.append(Surface.ROTATION_0, 90);
//        ORIENTATIONS.append(Surface.ROTATION_90, 0);
//        ORIENTATIONS.append(Surface.ROTATION_180, 270);
//        ORIENTATIONS.append(Surface.ROTATION_270, 180);
//    }
//    private String cameraId;
//    protected CameraDevice cameraDevice;
//    protected CameraCaptureSession cameraCaptureSessions;
//    protected CaptureRequest captureRequest;
//    protected CaptureRequest.Builder captureRequestBuilder;
//    private Size imageDimension;
//    private ImageReader imageReader;
//    private File file;
//    private static final int REQUEST_CAMERA_PERMISSION = 200;
//    private static final int REQUEST_STORAGE_PERMISSION = 201;
//    private boolean mFlashSupported;
//    private Handler mBackgroundHandler;
//    private HandlerThread mBackgroundThread;
//    private String IMAGE_DIR = "myPic";
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        requestStorage();
//        fragmentView = inflater.inflate(R.layout.fragment_camera, container, false);
//        textureView = (TextureView)fragmentView. findViewById(R.id.texture);
//        assert textureView != null;
//        textureView.setSurfaceTextureListener(textureListener);
//        takePictureButton = (ImageButton) fragmentView.findViewById(R.id.btn_takepicture);
//        assert takePictureButton != null;
//        takePictureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePicture();
//            }
//        });
//        return fragmentView;
//    }
//
//    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
//        @Override
//        public void onSurfaceTextureAvailable(@NotNull SurfaceTexture surface, int width, int height) {
//            //open your camera here
//            openCamera();
//        }
//        @Override
//        public void onSurfaceTextureSizeChanged(@NotNull SurfaceTexture surface, int width, int height) {
//            // Transform you image captured size according to the surface width and height
//        }
//        @Override
//        public boolean onSurfaceTextureDestroyed(@NotNull SurfaceTexture surface) {
//            return false;
//        }
//        @Override
//        public void onSurfaceTextureUpdated(@NotNull SurfaceTexture surface) {
//        }
//    };
//    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(@NotNull CameraDevice camera) {
//            //This is called when the camera is open
//            Log.e(TAG, "onOpened");
//            cameraDevice = camera;
//            createCameraPreview();
//        }
//        @Override
//        public void onDisconnected(CameraDevice camera) {
//            cameraDevice.close();
//        }
//        @Override
//        public void onError(CameraDevice camera, int error) {
//            cameraDevice.close();
//            cameraDevice = null;
//        }
//    };
//    final CameraCaptureSession.CaptureCallback captureCallbackListener = new CameraCaptureSession.CaptureCallback() {
//        @Override
//        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
//            super.onCaptureCompleted(session, request, result);
//            Toast.makeText(getActivity(), "Saved:" + file, Toast.LENGTH_SHORT).show();
//            createCameraPreview();
//        }
//    };
//    protected void startBackgroundThread() {
//        mBackgroundThread = new HandlerThread("Camera Background");
//        mBackgroundThread.start();
//        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
//    }
//    protected void stopBackgroundThread() {
//        mBackgroundThread.quitSafely();
//        try {
//            mBackgroundThread.join();
//            mBackgroundThread = null;
//            mBackgroundHandler = null;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    protected void takePicture() {
//
//        Long tsLong = System.currentTimeMillis();
//        String ts = tsLong.toString();
//        Log.d(TAG, "Call take Picture: " + ts);
//        //takePictureButton.setText("Waiting...");
//        if(null == cameraDevice) {
//            Log.e(TAG, "cameraDevice is null");
//            return;
//        }
//        try {
//            int[] sizes = getSizes();
//            ImageReader reader = ImageReader.newInstance(sizes[0], sizes[1], ImageFormat.JPEG, 1);
//            List<Surface> outputSurfaces = getSurfaces(reader);
//            final CaptureRequest.Builder captureBuilder = getCaptureBuilder(reader);
//            //final File file = new File(Environment.getExternalStorageDirectory()+"/pic.jpg");
//            //final File file = new File(getExternalFilesDir(null)+"/pic.jpg");
//            ImageReader.OnImageAvailableListener readerListener = new MyReaderListener().invoke();
//            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
//            createCaptureSession(outputSurfaces, captureBuilder);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private List<Surface> getSurfaces(ImageReader reader) {
//        List<Surface> outputSurfaces = new ArrayList<Surface>(2);
//        outputSurfaces.add(reader.getSurface());
//        outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
//        return outputSurfaces;
//    }
//
//    private CaptureRequest.Builder getCaptureBuilder(ImageReader reader) throws CameraAccessException {
//        final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
//        captureBuilder.addTarget(reader.getSurface());
//        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//        // Orientation
//        int rotation = requireActivity().getWindowManager().getDefaultDisplay().getRotation();
//        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
//        return captureBuilder;
//    }
//
//    private void createCaptureSession(List<Surface> outputSurfaces, final CaptureRequest.Builder captureBuilder) throws CameraAccessException {
//        final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
//            @Override
//            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
//                super.onCaptureCompleted(session, request, result);
//                //takePictureButton.setText("Take_Picture");
//                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
//                //createCameraPreview();
//            }
//        };
//        cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
//            @Override
//            public void onConfigured(CameraCaptureSession session) {
//                try {
//                    session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onConfigureFailed(CameraCaptureSession session) {
//            }
//        }, mBackgroundHandler);
//    }
//
//    private int[] getSizes() {
//        CameraManager manager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
//        CameraCharacteristics characteristics = null;
//        try {
//            characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//        Size[] jpegSizes = null;
//        if (characteristics != null) {
//            jpegSizes = Objects.requireNonNull(characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)).getOutputSizes(ImageFormat.JPEG);
//        }
//        int width = 640;
//        int height = 480;
//        if (jpegSizes != null && 0 < jpegSizes.length) {
//            width = jpegSizes[0].getWidth();
//            height = jpegSizes[0].getHeight();
//        }
//        return new int[]{width, height};
//    }
//
//    protected void createCameraPreview() {
//        try {
//            SurfaceTexture texture = textureView.getSurfaceTexture();
//            assert texture != null;
//            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
//            Surface surface = new Surface(texture);
//            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            captureRequestBuilder.addTarget(surface);
//            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback(){
//                @Override
//                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
//                    //The camera is already closed
//                    if (null == cameraDevice) {
//                        return;
//                    }
//                    // When the session is ready, we start displaying the preview.
//                    cameraCaptureSessions = cameraCaptureSession;
//                    updatePreview();
//                }
//                @Override
//                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
//                    Toast.makeText(getActivity(), "Configuration change", Toast.LENGTH_SHORT).show();
//                }
//            }, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//    private void openCamera() {
//        CameraManager manager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);
//        Log.e(TAG, "is camera open");
//        try {
//            cameraId = manager.getCameraIdList()[0];
//            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
//            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//            assert map != null;
//            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
//            // Add permission for camera and let user grant the permission
//            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
//                return;
//            }
//            manager.openCamera(cameraId, stateCallback, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//        Log.e(TAG, "openCamera X");
//    }
//
//    private void requestStorage(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_STORAGE_PERMISSION);
//        }
//    }
//
//    protected void updatePreview() {
////        int[] sizes = getSizes();
////        Log.d("DEBUG!", "Size: " + String.valueOf(sizes[0]) + " " +  String.valueOf(sizes[1]));
//        if(null == cameraDevice) {
//            Log.e(TAG, "updatePreview error, return");
//        }
//        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//        try {
//            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//    private void closeCamera() {
//        if (null != cameraDevice) {
//            cameraDevice.close();
//            cameraDevice = null;
//        }
//        if (null != imageReader) {
//            imageReader.close();
//            imageReader = null;
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CAMERA_PERMISSION) {
//            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                // close the app
//                Toast.makeText(getActivity(), "Sorry, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
//                requireActivity().finish();
//            }
//            else
//                Log.d(TAG, "onRequestPermissionsResult: REQUEST_CAMERA granted");
//        }
//        if (requestCode == REQUEST_STORAGE_PERMISSION) {
//            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                // close the app
//                Toast.makeText(getActivity(), "Sorry, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
//                requireActivity().finish();
//            } else
//                Log.d(TAG, "onRequestPermissionsResult: WRITE_EXTERNAL_STORAGE granted");
//        }
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "onResume");
//        startBackgroundThread();
//        if (textureView.isAvailable()) {
//            openCamera();
//        } else {
//            textureView.setSurfaceTextureListener(textureListener);
//        }
//    }
//    @Override
//    public void onPause() {
//        Log.e(TAG, "onPause");
//        //closeCamera();
//        stopBackgroundThread();
//        super.onPause();
//    }
//
//    private class MyReaderListener {
//        public ImageReader.OnImageAvailableListener invoke() {
//            return new ImageReader.OnImageAvailableListener() {
//                @Override
//                public void onImageAvailable(ImageReader reader) {
//                    Image image = null;
//                    try {
//                        image = reader.acquireLatestImage();
//                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
//                        byte[] bytes = new byte[buffer.capacity()];
//                        buffer.get(bytes);
//                        save(bytes);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (image != null) {
//                            image.close();
//                        }
//                    }
//                }
//                private void save(byte[] bytes) throws IOException {
//                    Log.d(TAG, "save: ");
//                    OutputStream output = null;
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                    Uri uri = MyImageManager.saveImage(requireActivity(), MyImageManager.rotateBitmap(bitmap, 90), "rulerPic", "capture");
//                    if(uri != null)
//                        openProcessFragment(uri);
//                    else
//                        Log.d(TAG, "save: failed");
//                }
//            };
//        }
//    }
//
//    private void openProcessFragment(Uri uri) {
//        ProcessFragment fragment = new ProcessFragment();
//        assert getFragmentManager() != null;
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//        Bundle arguments = new Bundle();
//        arguments.putString("ImageUri", uri.toString());
//        fragment.setArguments(arguments);
//        ft.replace(R.id.nav_host_fragment, fragment); // f1_container is your FrameLayout container
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.addToBackStack(null);
//        ft.commit();
//    }
}