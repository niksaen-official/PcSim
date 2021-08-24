package com.niksaen.pcsim.fileWorkLib;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import com.niksaen.pcsim.BuildConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWorkLib {

    public static List<File> getFilesList(File selectedItem) {
        List<File> rawFilesList = Arrays.asList(selectedItem.listFiles());
        if(rawFilesList != null){
            return rawFilesList;
        }
        else{
            rawFilesList.add(new File("//storage/emulated/0/"));
            return rawFilesList;
        }
    }

    public static ArrayList<String> getFilesPathList(String pathDirectory){
        List<File> fileList = getFilesList(new File(pathDirectory));
        ArrayList<String> pathList = new ArrayList<>();
        for(File file:fileList){
            pathList.add(file.getAbsolutePath());
        }
        return pathList;
    }

    public static String getMimeType(String url) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(url);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }

    public  static void openFile(Activity activity, File selectedItem){
        // Get URI and MIME type of file
        Uri uri = FileProvider.getUriForFile(activity.getBaseContext(), BuildConfig.APPLICATION_ID+".provider", selectedItem);
        String  mime = getMimeType(uri.toString());

        // Open file with user selected app
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, mime);
        activity.startActivity(intent);
    }
}
