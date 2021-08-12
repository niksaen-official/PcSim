package com.niksaen.pcsim.program.paint;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.FileUtil;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.fileManager.FileManagerListViewAdapter;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.StyleSave;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PaintOpenFile extends Program {

    private Context context;
    private Typeface font;
    private View fileOpenWindow;
    private ConstraintLayout layout;
    private Paint paint;
    private StyleSave styleSave;
    private MainActivity mainActivity;

    public PaintOpenFile(MainActivity activity){
        context = activity.getBaseContext();
        layout = activity.layout;
        mainActivity = activity;

        fileOpenWindow = LayoutInflater.from(context).inflate(R.layout.program_for_open_file,null);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        styleSave = new StyleSave(context);
        paint = new Paint(activity);
    }

    private Button close,fullscreen;
    private TextView title;
    private Button openButton,pageDown;
    private  ListView listView;

    private void initView(){
        close = fileOpenWindow.findViewById(R.id.close);
        fullscreen = fileOpenWindow.findViewById(R.id.fullscreenMode);
        title = fileOpenWindow.findViewById(R.id.title);
        openButton = fileOpenWindow.findViewById(R.id.button4);
        listView = fileOpenWindow.findViewById(R.id.listView);
        pageDown = fileOpenWindow.findViewById(R.id.pageDown);
    }

    private ArrayList<String> folders;
    private FileManagerListViewAdapter listViewAdapter;
    private HashMap<String,String> words;
    private void style(){
        title.setTypeface(font,Typeface.BOLD);
        openButton.setTypeface(font,Typeface.BOLD);

        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);

        fileOpenWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        openButton.setBackgroundColor(styleSave.ThemeColor2);
        openButton.setTextColor(styleSave.TextButtonColor);
        pageDown.setBackgroundResource(styleSave.ArrowButtonImage);

        folders = new ArrayList<>();
        FileUtil.listDir("//storage/emulated/0/",folders);
        listViewAdapter = new FileManagerListViewAdapter(context,0,folders);
        listViewAdapter.ColorText = styleSave.TextColor;
        listViewAdapter.ColorBackground = styleSave.ThemeColor1;
        listView.setAdapter(listViewAdapter);
        listView.setBackgroundColor(styleSave.ThemeColor1);

        //set text
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());
        openButton.setText(words.get("Open"));
        title.setText(words.get("Opening file"));
    }

    private String buffPathOpen = "",buffPath2;
    private View buff;
    public void openProgram(){
        this.status = 0;
        initView();style();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(FileUtil.isDirectory(folders.get(position))) {
                    buffPathOpen = folders.get(position);
                    FileUtil.listDir(folders.get(position), folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                }
                else if(folders.get(position).endsWith(".png") || folders.get(position).endsWith(".jpg")){
                    if(buff != null){
                        buff.setBackgroundColor(styleSave.ThemeColor1);
                    }
                    view.setBackgroundColor(styleSave.ThemeColor2);
                    buff = view;
                    buffPathOpen = folders.get(position);
                }
            }
        });

        pageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buffPathOpen.contains("/storage/emulated/0/") && buffPath2 != "/storage/emulated/0/"){
                    v.setVisibility(View.VISIBLE);
                    buffPathOpen = buffPathOpen.substring(0, buffPathOpen.lastIndexOf("/"));
                    FileUtil.listDir(buffPathOpen, folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                }
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buffPathOpen.endsWith(".png") || buffPathOpen.endsWith(".jpg")) {
                    paint.openProgram(getImage(buffPathOpen));
                    fileOpenWindow.setVisibility(View.GONE);
                    fileOpenWindow = null;
                }
            }
        });

        final int[] button2ClickCount = {0};
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button2ClickCount[0]==0){
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    fileOpenWindow.setScaleX(0.7f);
                    fileOpenWindow.setScaleY(0.7f);
                    fileOpenWindow.setX(0f);
                    fileOpenWindow.setY(0f);
                    PortableView portableView = new PortableView(fileOpenWindow);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fileOpenWindow.setZ(0f);
                    }
                    button2ClickCount[0]++;
                }
                else{
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    fileOpenWindow.setScaleX(1);
                    fileOpenWindow.setScaleY(1);
                    fileOpenWindow.setX(0);
                    fileOpenWindow.setY(0);
                    fileOpenWindow.setOnTouchListener(null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fileOpenWindow.setZ(10f);
                    }
                    button2ClickCount[0]=0;
                }
            }
        });
        close.setOnClickListener(v -> closeProgram());
        if(fileOpenWindow.getParent() == null) {
            mainActivity.layout.addView(fileOpenWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }else {
            fileOpenWindow.setVisibility(View.VISIBLE);
        }
        mainActivity.programArrayList.add(this);
    }

    @Override
    public void closeProgram() {
        fileOpenWindow.setVisibility(View.GONE);
        status = -1;
    }

    Drawable getImage(String path){
        try {
            // получаем входной поток
            InputStream bufferedInputStream = new FileInputStream(path);
            // загружаем как Drawable
            return Drawable.createFromStream(bufferedInputStream, null);
        }
        catch(IOException ex) { return null;}
    }
}
