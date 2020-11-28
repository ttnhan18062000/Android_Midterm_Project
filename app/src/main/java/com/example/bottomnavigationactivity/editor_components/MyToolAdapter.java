package com.example.bottomnavigationactivity.editor_components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.editor_components.tools.MyTool;

import java.util.List;

public class MyToolAdapter extends RecyclerView.Adapter<MyToolAdapter.ViewHolder>{
    private static final String TAG = MyToolAdapter.class.getSimpleName();
    private final Context mContext;
    public int firstItemPushedDistance = 0;
    List<MyTool> myToolList;
    private int initialListItemSize;

    public MyToolAdapter(Context mContext, List<MyTool> myNumberList) {
        this.mContext = mContext;
        this.myToolList = myNumberList;
        this.initialListItemSize = myNumberList.size();
    }


    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Inflate an item view.
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.tool_editor, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Retrieve the data for that position.
        MyTool tool = getItem(position);
//        Add the data to the view holder
        holder.tvNumber.setText(tool.getName());
    }

    private MyTool getItem(int position) {
        return myToolList.get(position % initialListItemSize);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumber;
        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         */

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_tool);
        }
    }

}
