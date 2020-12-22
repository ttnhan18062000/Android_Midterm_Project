package com.example.bottomnavigationactivity.camera_components;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

import com.example.bottomnavigationactivity.R;

public class PermissionsFragmentDirections {
    private PermissionsFragmentDirections() {
    }

    @NonNull
    public static NavDirections actionPermissionsToCamera() {
        return new ActionOnlyNavDirections(R.id.actionPermissions_toCamera);
    }
}
