package com.example.bottomnavigationactivity.ui.editor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.editor_components.MyPaintView;
import com.example.bottomnavigationactivity.editor_components.MyRecyclerViewManagement;
import com.example.bottomnavigationactivity.editor_components.MyTool;
import com.example.bottomnavigationactivity.editor_components.MyToolAdapter;
import com.example.bottomnavigationactivity.utility.MyMath;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EditorFragment extends Fragment {

    Activity mActivity = null;
    View fragmentView = null;
    private int initialListToolSize = 5;
    private int currentToolIndex = 0;
    private int currentPosition = 0;
    private static String TAG = "EditorFragment";
    private MyToolAdapter myToolAdapter;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<MyTool> myToolList;
    private boolean userScrolled = false;
    MyRecyclerViewManagement myRecyclerViewManagement;

//    View.OnClickListener onScrollButtonClicked = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            RecyclerView toolrRecyclerView = fragmentView.findViewById(R.id.rv_number_list);
//            switch (v.getId())
//            {
//                case R.id.btnScrollLeft:
//                    myRecyclerViewManagement.onScrollLeft();
//                    break;
//                case R.id.btnScrollRight:
//                    myRecyclerViewManagement.onScrollRight();
//                    break;
//            }
//        }
//    };

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
        return fragmentView;
    }

    private ArrayList<MyTool> createToolList() {
        ArrayList<MyTool> tools = new ArrayList<MyTool>();
        tools.add(new MyTool("L", MyTool.ToolType.LINE));
        tools.add(new MyTool("E", MyTool.ToolType.ERASER));
        tools.add(new MyTool("T", MyTool.ToolType.TEXT));
        tools.add(new MyTool("Z", MyTool.ToolType.ZOOM));
        tools.add(new MyTool("R", MyTool.ToolType.RATIO));
        return tools;
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


    public void setImageBitmap(Bitmap bitmap) {
        final ImageView imageView = fragmentView.findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK){
            Uri targetUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}