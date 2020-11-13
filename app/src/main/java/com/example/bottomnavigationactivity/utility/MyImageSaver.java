package com.example.bottomnavigationactivity.utility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyImageSaver {
    private static String TAG = "SaveImageUtility";

    private static File getImagesDirectory(String folder) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + folder);//Environment.getExternalStorageDirectory()
        if (!file.mkdirs() && !file.isDirectory()) {
            Log.e("mkdir", "Directory not created");
        }
        return file;
    }

    public static File generateImagePath(String title, String imgType, String folder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        return new File(getImagesDirectory(folder), title + "_" + sdf.format(new Date()) + "." + imgType);
    }

    public static boolean compressAndSaveImage(File file, Bitmap bitmap) {
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                Log.w("image manager", "Compression success");
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Uri addImageToGallery(ContentResolver cr, String imgType, File filepath, String name) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name);
        values.put(MediaStore.Images.Media.DESCRIPTION, "");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + imgType);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, filepath.toString());

        return cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static void saveImage(Context context, Bitmap bitmap, String folder, String name){
        File storedImagePath = generateImagePath("player", "jpeg", folder);
        Log.d(TAG, "save: generated image path");
        if (!compressAndSaveImage(storedImagePath, bitmap)) {
            Log.d(TAG, "save: Failed to compress");
            return;
        }
        Log.d(TAG, "save: compressed");
        Uri url = addImageToGallery(context.getContentResolver(), "jpeg", storedImagePath, name);
        Log.d(TAG, "save: completed");
    }
}
