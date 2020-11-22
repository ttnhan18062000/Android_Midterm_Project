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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.editor_components.MyPaintView;

import java.io.FileNotFoundException;

public class EditorFragment extends Fragment {

    Activity mActivity = null;
    View fragmentView = null;

    public static final int LINE = 0;
    public static final int ERASER = 4;
    public static final int TEXT = 6;

    View.OnClickListener onDrawShapeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyPaintView paintView = (MyPaintView)fragmentView.findViewById(R.id.paintView);
            paintView.setMoveMode(false);
            switch (v.getId())
            {

                case R.id.buttonLine:
                    paintView.selectShape(LINE);
                    break;
//            case R.id.buttonRectangle:
//                paintView.selectShape(1);
//                break;
//            case R.id.buttonEllipse:
//                paintView.selectShape(2);
//                break;
//            case R.id.buttonPath:
//                paintView.selectShape(3);
//                break;
                case R.id.buttonEraser:
                    paintView.selectShape(ERASER);
                    break;
                case R.id.buttonMove:
                    paintView.setMoveMode(true);
                    break;
                case R.id.buttonText:
                    paintView.selectShape(TEXT);
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_editor, container, false);
        setOnClickListenerForTool(fragmentView, R.id.buttonLine,onDrawShapeClicked);
        setOnClickListenerForTool(fragmentView, R.id.buttonEraser,onDrawShapeClicked);
        setOnClickListenerForTool(fragmentView,R.id.buttonMove,onDrawShapeClicked);
        setOnClickListenerForTool(fragmentView,R.id.buttonText,onDrawShapeClicked);
        setOnClickListenerForClearButton(fragmentView);
        setOnClickListenerForChooseImageButton(fragmentView);

        return fragmentView;
    }

    private void setOnClickListenerForChooseImageButton(final View fragmentView) {
        Button chooseImageButton = fragmentView.findViewById(R.id.buttonChooseImage);
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
        Button button = v.findViewById(R.id.buttonClear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MyPaintView paintView = (MyPaintView)fragmentView.findViewById(R.id.paintView);
                    paintView.clear();
                }
        });
    }

    private void setOnClickListenerForTool(View v,int buttonID, View.OnClickListener onClickListener) {
        Button button = v.findViewById(buttonID);
        button.setOnClickListener(onClickListener);
    }


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
}