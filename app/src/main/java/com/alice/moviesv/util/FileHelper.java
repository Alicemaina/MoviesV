package com.alice.moviesv.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by alice on 3/29/17.
 */

public class FileHelper {
    private static final String TAG = "FileHelper";

    //get image (drawable) from cache...
    public static Drawable getDrawableFromCache(String fileName, Context context) {
        MyDebug.LOGD(TAG, "getDrawableFromCache(" + fileName + ")");
        Drawable d = null;
        try {
            FileInputStream fin = context.openFileInput(fileName);
            if (fin != null) {
                d = Drawable.createFromStream(fin, null);
                fin.close();
            } else {

            }
        } catch (Exception c) {

        }
        return d;

    }

    //save image to cache
    public static void saveImageToCache(Bitmap theImage, String saveAsFileName, Context context) {
        MyDebug.LOGD(TAG, "saveImageToCache(" + saveAsFileName + ")");
        try {
            if (saveAsFileName.length() > 3 && theImage != null) {
                FileOutputStream fos = context.openFileOutput(saveAsFileName, Context.MODE_PRIVATE);
                theImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {

        }
    }

}