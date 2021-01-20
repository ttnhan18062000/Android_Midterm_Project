package com.example.simp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.simp.FloatingMenu.FloatingActionButton;
import com.example.simp.FloatingMenu.FloatingActionMenu;
import com.example.simp.FloatingMenu.SubActionButton;
import com.example.simp.ui.MenuLayout;
import com.example.simp.utility.AccountInfoSingleton;
import com.example.simp.utility.NetworkSingleton;
import com.squareup.picasso.Picasso;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    private static int POPUPWINDOW_SET_RATIO = 203;

    Activity selfActivity = this;
    private FloatingActionButton actionButton = null;
    public FloatingActionButton getActionButton()
    {
        return actionButton;
    }
    private boolean loginStatus = false;
    public boolean getLoginStatus()
    {
        return loginStatus;
    }
    public void setLoginStatus(boolean status)
    {
        this.loginStatus = status;
    }
    MenuLayout menuLayout;
    View mainLayout;
    public ImageView menuIcon = null;

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
                editorButton = createSubActionButton(itemBuilder,R.drawable.editor_icon),
                menuButton = createSubActionButton(itemBuilder, R.drawable.ic_baseline_menu_24),
                informationButton = createSubActionButton(itemBuilder,R.drawable.information_icon);

        setNavigationController(informationButton,R.id.actionGlobal_toInformation,true);
        setNavigationController(cameraButton,R.id.actionGlobal_toCamera, true);
        setNavigationController(editorButton,R.id.actionGlobal_toEditor, true);
        setNavigationController(rulerButton,R.id.actionGlobal_toRuler, true);
        setNavigationController(menuButton, R.id.actionGlobal_toMain, false);
        //MyFloatingActionButton actionButton = new MyFloatingActionButton.Builder(this).build();


        //  in Activity Context

        menuIcon = new ImageView(menuLayout.getContext()); // Create an icon
        menuIcon.setImageDrawable(getDrawable(R.drawable.menu_icon));
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(menuIcon)
                .setPosition(9).build();

        //MyMenuButton actionButton = (MyMenuButton)LayoutInflater.from(this).inflate(R.layout.my_menu_button_layout,null);

//        ImageButton actionButton = findViewById(R.id.actionButton);

        actionButton.detach();
        menuLayout.addView(actionButton,actionButton.getLayoutParams());
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(cameraButton)
                .addSubActionView(editorButton)
                .addSubActionView(rulerButton)
                .addSubActionView(menuButton)
                .addSubActionView(informationButton)
                .attachTo(actionButton)
                .setStartAngle(0)
                .setEndAngle(90)
                .build();
        menuLayout.actionMenu = actionMenu;
        menuLayout.mHeaderView = actionButton;
        turnActionButton(false);
    }

    public void turnActionButton(boolean turn)
    {
        if(turn)
            actionButton.setVisibility(View.VISIBLE);
        else
            actionButton.setVisibility(View.INVISIBLE);
    }

    private void setNavigationController(SubActionButton button, final int actionGlobalID, final boolean turnActionButton) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionGlobalID == R.id.actionGlobal_toInformation)
                    if(AccountInfoSingleton.getAccountInfoHolder().getUserID() == "")
                        return;
                Navigation.findNavController(selfActivity,R.id.nav_host_fragment).navigate(actionGlobalID);
                MainActivity.this.turnActionButton(turnActionButton);
                MainActivity.this.actionButton.callOnClick();
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