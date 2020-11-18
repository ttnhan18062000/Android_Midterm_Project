package com.example.bottomnavigationactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bottomnavigationactivity.editor_components.SetRatioDialog;
import com.example.bottomnavigationactivity.ui.editor.EditorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity{

    private static int POPUPWINDOW_SET_RATIO = 203;

    Activity selfActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

         // in Activity Context
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getDrawable(R.drawable.menu_icon));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        SubActionButton cameraButton = createSubActionButton(itemBuilder,R.drawable.camera_icon),
                rulerButton = createSubActionButton(itemBuilder,R.drawable.ruler_icon),
                editorButton = createSubActionButton(itemBuilder,R.drawable.editor_icon);

        setNavigationController(cameraButton,R.id.actionGlobal_toCamera);
        setNavigationController(editorButton,R.id.actionGlobal_toEditor);
        setNavigationController(rulerButton,R.id.actionGlobal_toRuler);



        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(cameraButton)
                .addSubActionView(editorButton)
                .addSubActionView(rulerButton)
                .attachTo(actionButton)
                .build();
    }

    private void setNavigationController(SubActionButton button, final int actionGlobalID) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(selfActivity,R.id.nav_host_fragment).navigate(actionGlobalID);
            }
        });
    }

    private SubActionButton createSubActionButton(SubActionButton.Builder itemBuilder, int DrawableID) {
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( getDrawable(DrawableID) );
        SubActionButton button = itemBuilder.setContentView(itemIcon).build();
        return button;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == POPUPWINDOW_SET_RATIO){
            if (data != null) {
                float ratioLength = data.getFloatExtra("ratio_length", 1f);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ratio_length", ratioLength);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }

}