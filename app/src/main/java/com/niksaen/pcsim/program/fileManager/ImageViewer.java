package com.niksaen.pcsim.program.fileManager;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.StyleSave;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ImageViewer extends Program {

    Context context;

    View mainWindow;
    ConstraintLayout layout;
    StyleSave styleSave;
    MainActivity mainActivity;

    Typeface font;

    public ImageViewer(MainActivity activity) {
        super(activity);
        this.Title = "Image Viewer";
        mainActivity = activity;
        this.context = activity.getBaseContext();
        this.layout = activity.layout;

        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_filemanager_imageviewer, null);
        styleSave = new StyleSave(context);
    }

    TextView title;
    Button close, rollUp, fullscreen;
    ImageView imageView;
    HashMap<String, String> words;

    private void initView() {
        title = mainWindow.findViewById(R.id.title);
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        imageView = mainWindow.findViewById(R.id.image);
        rollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style() {
        words = new Gson().fromJson(new AssetFile(context).getText("language/" + Language.getLanguage(context) + ".json"), new TypeToken<HashMap<String, String>>() {
        }.getType());

        title.setText(words.get("Image Viewer"));
        title.setTypeface(font, Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);

        imageView.setBackgroundColor(styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
    }

    public void openProgram(String imagePath) {
        if(status == -1) {
            super.openProgram();
            initView();
            style();

            imageView.setImageDrawable(getImage(imagePath));
            final int[] button2ClickCount = {0};
            fullscreen.setOnClickListener(v -> {
                if (button2ClickCount[0] == 0) {
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    mainWindow.setScaleX(0.7f);
                    mainWindow.setScaleY(0.7f);
                    mainWindow.setX(0f);
                    mainWindow.setY(0f);
                    PortableView portableView1 = new PortableView(mainWindow);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mainWindow.setZ(0f);
                    }
                    button2ClickCount[0]++;
                } else {
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    mainWindow.setOnTouchListener(null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mainWindow.setZ(10f);
                    }
                    button2ClickCount[0] = 0;
                }
            });
            close.setOnClickListener(v -> closeProgram(1));
            if (mainWindow.getParent() == null) {
                layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                mainWindow.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void closeProgram(int mode){
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
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
