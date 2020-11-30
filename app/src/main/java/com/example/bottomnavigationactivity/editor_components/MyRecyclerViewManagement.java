package com.example.bottomnavigationactivity.editor_components;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.editor_components.tools.MyTool;

import java.util.ArrayList;

public class MyRecyclerViewManagement
{
    private ArrayList<MyTool> initialTools;
    private MyToolAdapter myToolAdapter;
    private RecyclerView recyclerView;
    private MyTool selectedTool;
    private View selectedView;
    private View fragmentView;
    private Context mActivity;
    private ArrayList<MyTool> myToolList;
    private LinearLayoutManager layoutManager;
    private boolean userScrolled = false;
    private static String TAG = "RecyclerViewManagement";
    private MyPaintView myPaintView;
    public MyRecyclerViewManagement(ArrayList<MyTool> myToolList, View view, Context context)
    {
        //this.myToolList = cloneArrayList(myToolList);
        this.myToolList = (ArrayList<MyTool>)myToolList.clone();
        this.initialTools = myToolList;
        this.fragmentView = view;
        this.mActivity = context;
        recyclerView = fragmentView.findViewById(R.id.rv_number_list);
        myPaintView = fragmentView.findViewById(R.id.paintView);
    }

    private ArrayList<MyTool> cloneArrayList(ArrayList<MyTool> myToolList) {
        ArrayList<MyTool> result = new ArrayList<MyTool>();
        for(int i = 0; i < myToolList.size(); ++i)
            result.add(new MyTool(myToolList.get(i).getName(), myToolList.get(i).getToolID(), myToolList.get(i).getDescribtion()));
        return result;
    }

    public void initRecyclerView() {
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        myToolAdapter = new MyToolAdapter(mActivity, myToolList);
        recyclerView.setAdapter(myToolAdapter);
        MySnapHelper snapHelper = new MySnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int cPosition = layoutManager.findFirstVisibleItemPosition();
                Log.d(TAG, "onScrolled Position: " + String.valueOf(cPosition));

                if(userScrolled && cPosition == 0){
                    generateItemsOnLeft();
                    layoutManager.scrollToPositionWithOffset(initialTools.size() + 1, 0);
                    cPosition = layoutManager.findFirstVisibleItemPosition();
                }
                selectedTool = myToolList.get((cPosition + 2) % myToolList.size());

                updateUIForSelectedView(cPosition + 2);
                //myPaintView.setTool(selectedTool.getToolID());
                myPaintView.setTool(selectedTool);

                Log.d(TAG, "onScrolled: Selected Tool: " + selectedTool.getName());
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    userScrolled = true;
                }
            }

            public void generateItemsOnLeft(){
                for(int i = initialTools.size() - 1; i >= 0; i--){
                    MyTool newTool = new MyTool(initialTools.get(i).getName(), initialTools.get(i).getToolID(), initialTools.get(i).getDescribtion());
                    myToolList.add(0, newTool);
                }
                myToolAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPositionWithOffset(initialTools.size()/2, 0);
        int cPosition = layoutManager.findFirstVisibleItemPosition();
        Log.d(TAG, "onScrolled Position: " + String.valueOf(cPosition));
    }

    private void updateUIForSelectedView(int pos) {
        if(selectedView != null) {

            //TextView tv = selectedView.findViewById(R.id.tv_tool);
            //tv.setTextColor(Color.parseColor("#000000"));
            ImageView iv = selectedView.findViewById(R.id.iv_tool);
            //iv.setBackground(mActivity.getDrawable(R.drawable.selected_frame));

        }
        selectedView = layoutManager.findViewByPosition(pos);
//        TextView tv = selectedView.findViewById(R.id.tv_tool);
//        tv.setTextColor(Color.parseColor("#ffffff"));

        ImageView iv = selectedView.findViewById(R.id.iv_tool);
        //iv.setBackground(mActivity.getDrawable(R.drawable.selected_frame));
    }
}
