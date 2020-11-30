package com.example.bottomnavigationactivity.ui.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bottomnavigationactivity.R;

import com.example.bottomnavigationactivity.editor_components.ColorPickerView;
import com.example.bottomnavigationactivity.editor_components.dialog.SetTextDialog;
import com.example.bottomnavigationactivity.editor_components.MyPaintView;

import com.example.bottomnavigationactivity.editor_components.MyRecyclerViewManagement;
import com.example.bottomnavigationactivity.editor_components.shape.MyBitmap;
import com.example.bottomnavigationactivity.editor_components.tools.MyEraserTool;
import com.example.bottomnavigationactivity.editor_components.tools.MyLineTool;
import com.example.bottomnavigationactivity.editor_components.tools.MyMoveTool;
import com.example.bottomnavigationactivity.editor_components.tools.MyRatioTool;
import com.example.bottomnavigationactivity.editor_components.tools.MyTextTool;
import com.example.bottomnavigationactivity.editor_components.tools.MyTool;
import com.example.bottomnavigationactivity.editor_components.dialog.SetRatioDialog;
import com.example.bottomnavigationactivity.editor_components.tools.MyZoomTool;
import com.example.bottomnavigationactivity.utility.MyImageManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditorFragment extends Fragment implements SetRatioDialog.SetRatioDialogListener,SetTextDialog.SetTextDialogListener {

    Activity mActivity = null;
    View fragmentView = null;
    private MyPaintView myPaintView;
    ImageView ivColorPreview;
    Bitmap bitmapFromCamera = null;
    ColorPickerView mColorPickerView;
    private static String TAG = "EditorFragment";
    MyRecyclerViewManagement myRecyclerViewManagement;
    ColorPickerView.OnColorSelectListener onColorSelectListener;
  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_editor, container, false);
      
//        setOnClickListenerForTool(fragmentView, R.id.btnScrollLeft, onScrollButtonClicked);
//        setOnClickListenerForTool(fragmentView, R.id.btnScrollRight, onScrollButtonClicked);
        setOnClickListenerForClearButton(fragmentView);
        setOnClickListenerForChooseImageButton(fragmentView);
        myRecyclerViewManagement = new MyRecyclerViewManagement(createToolList(), fragmentView, mActivity);
        myRecyclerViewManagement.initRecyclerView();
        myPaintView = fragmentView.findViewById(R.id.paintView);
        ivColorPreview = fragmentView.findViewById(R.id.colorPickerPreview);
        mColorPickerView = fragmentView.findViewById(R.id.colorPicker);
        mColorPickerView.setOnColorSelectListener(new ColorPickerView.OnColorSelectListener() {
            @Override
            public void onColorSelect(int Color) {
                ivColorPreview.setBackgroundColor(Color);
            }

        });
        myPaintView.setOnEndDrawListener(new MyPaintView.OnEndDrawListener() {
            @Override
            public void onEndDraw(MyTool.ToolType iTool) {
                switch (iTool)
                {
                    case RATIO:
                        showSetRatioDialog();
                        break;
                    case TEXT:
                        showSetTextDialog();
                        break;
                }
            }
        });
        Bundle args = getArguments();
        if(args != null)
        {
            Uri imageUri = Uri.parse(args.getString("ImageUri"));
            bitmapFromCamera = MyImageManager.loadImage(requireActivity(), imageUri);
        }
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(bitmapFromCamera != null) {
            MyPaintView paintView = fragmentView.findViewById(R.id.paintView);
            paintView.addBackgroundWithShapes(bitmapFromCamera);
            bitmapFromCamera = null;
        }
    }

    private ArrayList<MyTool> createToolList() {
        ArrayList<MyTool> tools = new ArrayList<MyTool>();
        tools.add(new MyLineTool("L", MyTool.ToolType.LINE, "this is a line drawing tool"));
        tools.add(new MyEraserTool("E", MyTool.ToolType.ERASER, "this is a eraser tool"));
        tools.add(new MyTextTool("T", MyTool.ToolType.TEXT, "this is a text noting tool"));
        tools.add(new MyRatioTool("R", MyTool.ToolType.RATIO, "this is a setting ratio tool"));
        tools.add(new MyMoveTool("M", MyTool.ToolType.MOVE, "move move move move"));

//        tools.add(new MyLineTool("L", MyTool.ToolType.LINE));
//        tools.add(new MyEraserTool("E", MyTool.ToolType.ERASER));
//        tools.add(new MyTextTool("T", MyTool.ToolType.TEXT));
//        tools.add(new MyZoomTool("Z", MyTool.ToolType.ZOOM));
//        tools.add(new MyRatioTool("R", MyTool.ToolType.RATIO));
//        tools.add(new MyMoveTool("M", MyTool.ToolType.MOVE));

        return tools;
    }

    private void setOnClickListenerForChooseImageButton(final View fragmentView) {
        ImageButton chooseImageButton = fragmentView.findViewById(R.id.buttonChooseImage);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setOnClickListenerForTool(R.id.buttonLine,onDrawShapeClicked);
//        setOnClickListenerForTool(R.id.buttonEraser,onDrawShapeClicked);
//        setOnClickListenerForClearButton();
    }

    private void setOnClickListenerForClearButton(View v) {
        ImageButton button = v.findViewById(R.id.buttonClear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MyPaintView paintView = (MyPaintView)fragmentView.findViewById(R.id.paintView);
                    paintView.clear();
                }
        });
    }

//    private void setOnClickListenerForTool(View v,int buttonID, View.OnClickListener onClickListener) {
//        Button button = v.findViewById(buttonID);
//        button.setOnClickListener(onClickListener);
//    }


//    public void setImageBitmap(Bitmap bitmap) {
//        final ImageView imageView = fragmentView.findViewById(R.id.image);
//        imageView.setImageBitmap(bitmap);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK){
            Uri targetUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                MyPaintView paintView = (MyPaintView)fragmentView.findViewById(R.id.paintView);
                paintView.addBackgroundWithShapes(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void showSetRatioDialog() {
        FragmentManager fm = getFragmentManager();
        SetRatioDialog editNameDialogFragment = SetRatioDialog.newInstance();
        // SETS the target fragment for use later when sending results
        editNameDialogFragment.setTargetFragment(this, 300);
        editNameDialogFragment.show(fm, "fragment_set_ratio");
    }

    private void showSetTextDialog() {
        FragmentManager fm = getFragmentManager();
        SetTextDialog editNameDialogFragment = SetTextDialog.newInstance();
        // SETS the target fragment for use later when sending results
        editNameDialogFragment.setTargetFragment(this, 301);
        editNameDialogFragment.show(fm, "fragment_set_text");
    }


    @Override
    public void applyLength(float length) {
        myPaintView.setRatio(length);
    }

    @Override
    public void applyText(String text) {
        myPaintView.setText(text);
    }
}