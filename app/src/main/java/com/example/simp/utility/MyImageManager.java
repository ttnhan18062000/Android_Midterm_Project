package com.example.simp.utility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyImageManager {
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

    public static Uri saveImage(Context context, Bitmap bitmap, String folder, String name){
        File storedImagePath = generateImagePath("player", "jpeg", folder);
        Log.d(TAG, "save: generated image path");
        if (!compressAndSaveImage(storedImagePath, bitmap)) {
            Log.d(TAG, "save: Failed to compress");
            return null;
        }
        Log.d(TAG, "save: compressed");
        Uri uri = addImageToGallery(context.getContentResolver(), "jpeg", storedImagePath, name);
        Log.d(TAG, "save: completed");
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(storedImagePath)));
        return uri;
    }

    public static Bitmap loadImage(Context context, Uri uri)
    {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), pictureUri);
//        Bitmap bitmap = ImageDecoder.decodeBitmap(source);
        return null;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static String getFilePath(Uri uri, ContentResolver contentResolver) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();
            return picturePath;
        }
        return null;
    }

    public static void deleteImage(Uri uri, Context context)
    {
        String filePath = getFilePath(uri, context.getContentResolver());
        Log.d(TAG, "deleteImage: " + filePath);
        File fdelete = new File(filePath);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.d(TAG, "deleteImage: completed");
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fdelete)));
            } else {
                Log.d(TAG, "deleteImage: failed");
            }
        }
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
