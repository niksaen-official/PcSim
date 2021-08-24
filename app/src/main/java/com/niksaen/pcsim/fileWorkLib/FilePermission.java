package com.niksaen.pcsim.fileWorkLib;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FilePermission {
    static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";
    static final String NOT_APPLICABLE = "N/A";
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST = 3;

    public static String getStoragePermissionName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return MANAGE_EXTERNAL_STORAGE_PERMISSION;
        } else {
            return Manifest.permission.READ_EXTERNAL_STORAGE;
        }
    }

    public static void openPermissionSettings(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestStoragePermissionApi30(activity);
        }
        else {
            activity.startActivity(
                    new Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", activity.getPackageName(), null)
                    )
            );
        }
    }

    public static String getPermissionStatus(Activity activity) {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           return  checkStoragePermissionApi30(activity).toString();
        } else {
           return  checkStoragePermissionApi19(activity).toString();
        }
    }

    public static Boolean checkStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return checkStoragePermissionApi30(activity);
        } else {
            return  checkStoragePermissionApi19(activity);
        }
    }

    public static void requestStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestStoragePermissionApi30(activity);
        }
        // If you want to see the default storage behavior on Android Q once the permission is granted
        // Set the "requestLegacyExternalStorage" flag in the AndroidManifest.xml file to false
        else {
            requestStoragePermissionApi19(activity);
        }
    }

    @RequiresApi(30)
    public static Boolean checkStoragePermissionApi30(Activity activity) {
        AppOpsManager appOps = activity.getSystemService(AppOpsManager.class);
        int mode = appOps.unsafeCheckOpNoThrow(
                MANAGE_EXTERNAL_STORAGE_PERMISSION,
                activity.getApplicationInfo().uid,
                activity.getPackageName()
        );

        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @RequiresApi(30)
    public static void requestStoragePermissionApi30(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);

        activity.startActivityForResult(intent, MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
    }

    @RequiresApi(19)
    public static Boolean checkStoragePermissionApi19(Activity activity) {
        int status =
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        return status == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(19)
    public static void  requestStoragePermissionApi19(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(
                activity,
                permissions,
                READ_EXTERNAL_STORAGE_PERMISSION_REQUEST
        );
    }
}
