package com.niksaen.pcsim.program.fileManager;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;

public class TextViewer  {

    Context context;

    View mainWindow;
    ConstraintLayout layout;
    StyleSave styleSave;

    Typeface font;

    public TextViewer(Context context, ConstraintLayout layout){
        this.context=context;
        this.layout = layout;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_filemanager_textviewer,null);
        styleSave = new StyleSave(context);
    }

    TextView title,textView;
    Button close,rollUp,fullscreen;

    HashMap<String,String> words;
    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        textView = mainWindow.findViewById(R.id.textView);
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style(){
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());

        title.setText(words.get("Text Viewer"));
        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);

        textView.setTextColor(styleSave.TextColor);
        textView.setBackgroundColor(styleSave.ThemeColor1);
        textView.setTypeface(font);

        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
    }

    public void openProgram(String text){
        initView();style();
        textView.setText(text);
        final int[] button2ClickCount = {0};
        fullscreen.setOnClickListener(v -> {
            if(button2ClickCount[0]==0){
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
            }
            else{
                fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setX(0);
                mainWindow.setY(0);
                mainWindow.setOnTouchListener(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mainWindow.setZ(10f);
                }
                button2ClickCount[0]=0;
            }
        });
        close.setOnClickListener(v -> closeProgram());
        layout.addView(mainWindow,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
    }
}
