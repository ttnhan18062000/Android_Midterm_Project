package com.example.bottomnavigationactivity.ui.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bottomnavigationactivity.R;
import com.example.bottomnavigationactivity.camera_components.PermissionsFragmentDirections;

import java.util.ArrayList;


public class PermissionFragment extends Fragment {
    private int PERMISSIONS_REQUEST_CODE = 10;
    private static String[] permissionsRequired = new String[]{Manifest.permission.CAMERA};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasPermission(requireContext()))
            requestPermissions(permissionsRequired, PERMISSIONS_REQUEST_CODE);
        else
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                    PermissionsFragmentDirections.actionPermissionsToCamera());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSIONS_REQUEST_CODE)
        {
            for(int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), "Permission granted!!!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                            PermissionsFragmentDirections.actionPermissionsToCamera());
                } else
                    Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean hasPermission(Context context)
    {
        for(int i = 0; i < permissionsRequired.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissionsRequired[i]) == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }
}
