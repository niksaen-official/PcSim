package com.niksaen.pcsim.classes;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class AssetFile {

    Context context;
    public AssetFile(Context context){
        this.context = context;
    }

    public String getText(String filePath){
        byte[] buffer = null;
        InputStream is;
        try {
            is = context.getAssets().open(filePath);
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str_data = new String(buffer);
        return str_data;
    }

    public Drawable getImage(String filePath){
        try {
            // получаем входной поток
            InputStream ims = context.getAssets().open(filePath);
            // загружаем как Drawable
            return Drawable.createFromStream(ims, null);
        }
        catch(IOException ex) {
            System.out.println("Not found image: "+filePath);
            return null;
        }
    }
}
