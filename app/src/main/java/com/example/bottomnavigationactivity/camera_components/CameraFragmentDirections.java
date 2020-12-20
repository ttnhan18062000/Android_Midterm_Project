package com.example.bottomnavigationactivity.camera_components;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

import com.example.bottomnavigationactivity.R;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class CameraFragmentDirections {
    private CameraFragmentDirections() {
    }

    @NonNull
    public static NavDirections actionCameraToPermissions() {
        return new ActionOnlyNavDirections(R.id.actionCamera_toPermissions);
    }

    @NonNull
    public static ActionCameraToGallery actionCameraToGallery(@NonNull String rootDirectory) {
        return new ActionCameraToGallery(rootDirectory);
    }

    public static class ActionCameraToGallery implements NavDirections {

        private static String TAG = "ActionCameraToGallery";
        private final HashMap<String, String> arguments = new HashMap<>();

        private ActionCameraToGallery(@NonNull String rootDirectory) {
            if (rootDirectory == null) {
                throw new IllegalArgumentException("Argument \"root_directory\" is marked as non-null but was passed a null value.");
            }
            this.arguments.put("root_directory", rootDirectory);
            Log.d(TAG, "ActionCameraToGallery: storing directory : " + arguments.get("root_directory"));
        }

        @NonNull
        public ActionCameraToGallery setRootDirectory(@NonNull String rootDirectory) {
            if (rootDirectory == null) {
                throw new IllegalArgumentException("Argument \"root_directory\" is marked as non-null but was passed a null value.");
            }
            this.arguments.put("root_directory", rootDirectory);
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        @NonNull
        public Bundle getArguments() {
            Bundle __result = new Bundle();
            if (arguments.containsKey("root_directory")) {
                String rootDirectory = arguments.get("root_directory");
                __result.putString("root_directory", rootDirectory);
            }
            return __result;
        }

        @Override
        public int getActionId() {
            return R.id.actionCamera_toGallery;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        public String getRootDirectory() {
            return arguments.get("root_directory");
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            ActionCameraToGallery that = (ActionCameraToGallery) object;
            if (arguments.containsKey("root_directory") != that.arguments.containsKey("root_directory")) {
                return false;
            }
            if (getRootDirectory() != null ? !getRootDirectory().equals(that.getRootDirectory()) : that.getRootDirectory() != null) {
                return false;
            }
            if (getActionId() != that.getActionId()) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + (getRootDirectory() != null ? getRootDirectory().hashCode() : 0);
            result = 31 * result + getActionId();
            return result;
        }

        @Override
        public String toString() {
            return "ActionCameraToGallery(actionId=" + getActionId() + "){"
                    + "rootDirectory=" + getRootDirectory()
                    + "}";
        }
    }
}