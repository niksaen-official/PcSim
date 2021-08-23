package com.niksaen.pcsim.program.fileManager;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageViewer extends Program {
    Typeface font;

    public ImageViewer(MainActivity activity) {
        super(activity);
        this.Title = "Image Viewer";

        font = Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf");
        ValueRam = new int[]{10,20};
        ValueVideoMemory = new int[]{10,30};
    }
    ImageView imageView;

    private void initView() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_filemanager_imageviewer, null);
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        imageView = mainWindow.findViewById(R.id.image);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style() {
        imageView.setBackgroundColor(activity.styleSave.ThemeColor1);
    }

    public void openProgram(String imagePath) {
        initView();
        style();
        imageView.setImageDrawable(getImage(imagePath));
        initProgram();
        super.openProgram();
    }

    private Drawable getImage(String path) {
        try {
            // получаем входной поток
            InputStream bufferedInputStream = new FileInputStream(path);
            // загружаем как Drawable
            return Drawable.createFromStream(bufferedInputStream, null);
        } catch (IOException ex) {
            return null;
        }
    }
}
