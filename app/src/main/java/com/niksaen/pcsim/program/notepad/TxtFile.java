package com.niksaen.pcsim.program.notepad;

import android.app.Activity;
import android.database.Cursor;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

public class TxtFile{
    Activity activity;
    public TxtFile(Activity activity){
        this.activity = activity;
    }
    public String open(){
        return null;
    }
}
