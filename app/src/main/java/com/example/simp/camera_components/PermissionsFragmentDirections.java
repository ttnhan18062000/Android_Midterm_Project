package com.example.simp.camera_components;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

import com.example.simp.R;

public class PermissionsFragmentDirections {
    private PermissionsFragmentDirections() {
    }

    @NonNull
    public static NavDirections actionPermissionsToCamera() {
        return new ActionOnlyNavDirections(R.id.actionPermissions_toCamera);
    }
}
