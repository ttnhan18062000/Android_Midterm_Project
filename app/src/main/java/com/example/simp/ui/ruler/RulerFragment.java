package com.example.simp.ui.ruler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.simp.R;

import org.jetbrains.annotations.NotNull;

public class RulerFragment extends Fragment {
    View mFragment;
    private DisplayMetrics dm;
    private ImageView rulerImageView;
    private Bitmap bm;

    enum Unit {INCH,CM}
    Unit mUnit = Unit.CM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragment = inflater.inflate(R.layout.fragment_ruler, container, false);

        rulerImageView = mFragment.findViewById(R.id.ruler_image_view);
        Switch unitSwitch = mFragment.findViewById(R.id.ruler_unit_switch);
        bm = BitmapFactory.decodeResource(getResources(),R.drawable.ruler);

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mUnit = Unit.INCH;
                else
                    mUnit = Unit.CM;
                rulerImageView.setImageMatrix(getCropMatrix(rulerImageView,mUnit));
            }
        });

        Matrix drawMatrix = getCropMatrix(rulerImageView,mUnit);
        rulerImageView.setImageMatrix(drawMatrix);
        return mFragment;
    }

    @NotNull
    private Matrix getCropMatrix(ImageView rulerImageView,Unit unit) {

        rulerImageView.setImageBitmap(bm);
        Matrix drawMatrix = new Matrix();
        float scaleY = 2.4f/dm.density*(unit==Unit.INCH?2.54f:1f);
        //float scaleX = rulerImageView.getMeasuredWidth()/bm.getWidth();
        drawMatrix.postScale(1.0f,scaleY);

//        final int dwidth = bm.getWidth();
//        final int dheight = bm.getHeight();
//
//        final int vwidth = rulerImageView.getWidth() - rulerImageView.getPaddingLeft() - rulerImageView.getPaddingRight();
//        final int vheight = rulerImageView.getHeight() - rulerImageView.getPaddingTop() - rulerImageView.getPaddingBottom();
//
//        float scale;
//        float dx = 0, dy = 0;
//
//        if (dwidth * vheight > vwidth * dheight) {
//            scale = (float) vheight / (float) dheight;
//
//        } else {
//            scale = (float) vwidth / (float) dwidth;
//
//        }
        return drawMatrix;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dm = context.getResources().getDisplayMetrics();

    }
}