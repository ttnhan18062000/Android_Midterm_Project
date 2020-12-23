package com.example.bottomnavigationactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bottomnavigationactivity.FloatingMenu.FloatingActionButton;
import com.example.bottomnavigationactivity.FloatingMenu.FloatingActionMenu;
import com.example.bottomnavigationactivity.FloatingMenu.SubActionButton;
import com.example.bottomnavigationactivity.ui.MenuLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    private static int POPUPWINDOW_SET_RATIO = 203;

    Activity selfActivity = this;

    MenuLayout menuLayout;
    View mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        menuLayout = findViewById(R.id.menuLayout);
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        SubActionButton cameraButton = createSubActionButton(itemBuilder,R.drawable.camera_icon),
                rulerButton = createSubActionButton(itemBuilder,R.drawable.ruler_icon),
                editorButton = createSubActionButton(itemBuilder,R.drawable.editor_icon);


        setNavigationController(cameraButton,R.id.actionGlobal_toCamera);
        setNavigationController(editorButton,R.id.actionGlobal_toEditor);
        setNavigationController(rulerButton,R.id.actionGlobal_toRuler);
        //MyFloatingActionButton actionButton = new MyFloatingActionButton.Builder(this).build();


        //  in Activity Context
        ImageView icon = new ImageView(menuLayout.getContext()); // Create an icon
        icon.setImageDrawable(getDrawable(R.drawable.menu_icon));
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(9).build();

        //MyMenuButton actionButton = (MyMenuButton)LayoutInflater.from(this).inflate(R.layout.my_menu_button_layout,null);

//        ImageButton actionButton = findViewById(R.id.actionButton);

        actionButton.detach();
        menuLayout.addView(actionButton,actionButton.getLayoutParams());
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(cameraButton)
                .addSubActionView(editorButton)
                .addSubActionView(rulerButton)
                .attachTo(actionButton)
                .setStartAngle(0)
                .setEndAngle(180)
                .build();
        menuLayout.actionMenu = actionMenu;
        menuLayout.mHeaderView = actionButton;
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

    public static File getOutputDirectory(Context context)
    {
        Context appContext = context.getApplicationContext();
        File[] mediaDirs = context.getExternalMediaDirs();
        for (int i = 0; i < mediaDirs.length; i++) {
            if (mediaDirs[i] != null) {
                File file = new File(mediaDirs[i], "SIMP");
                if(file.mkdirs())
                    return mediaDirs[i];
            }
        }
        return appContext.getFilesDir();
    }
}