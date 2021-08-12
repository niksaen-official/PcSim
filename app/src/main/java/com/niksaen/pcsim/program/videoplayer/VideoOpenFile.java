package com.niksaen.pcsim.program.videoplayer;

import android.content.Context;
import android.graphics.Typeface;
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

import java.util.ArrayList;
import java.util.HashMap;

public class VideoOpenFile extends Program {

    View mainWindow;
    Context context;
    ConstraintLayout layout;
    VideoPlayer videoPlayer;
    StyleSave styleSave;
    Typeface font;
    MainActivity mainActivity;

    public VideoOpenFile(MainActivity activity){
        context = activity.getBaseContext();
        layout = activity.layout;
        this.mainActivity = activity;

        videoPlayer = new VideoPlayer(activity);
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_for_open_file,null);
        styleSave = new StyleSave(context);
        font = Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf");
    }

    Button close,fullscreen;
    TextView title;
    Button openButton,pageDown;
    ListView listView;

    private void initView(){
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        title = mainWindow.findViewById(R.id.title);
        openButton = mainWindow.findViewById(R.id.button4);
        listView = mainWindow.findViewById(R.id.listView);
        pageDown = mainWindow.findViewById(R.id.pageDown);
    }


    ArrayList<String> folders;
    FileManagerListViewAdapter listViewAdapter;
    HashMap<String,String> words;
    private void style(){
        title.setTypeface(font,Typeface.BOLD);
        openButton.setTypeface(font,Typeface.BOLD);

        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);

        mainWindow.setBackgroundColor(styleSave.ColorWindow);
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

    String buffPathOpen = "",buffPath2 = "";
    View buffView;
    public void openProgram(){
        this.status = 0;
        initView();style();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(FileUtil.isDirectory(folders.get(position))) {
                buffPathOpen = folders.get(position);
                FileUtil.listDir(folders.get(position), folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                if(buffView != null){
                    buffView.setBackgroundColor(styleSave.ThemeColor1);
                }
            }
            else if(folders.get(position).endsWith(".mp4")){
                view.setBackgroundColor(styleSave.ThemeColor2);
                buffView = view;
                buffPathOpen = folders.get(position);
            }
        });
        pageDown.setOnClickListener(v -> {
            if(buffPathOpen.contains("/storage/emulated/0/") && buffPath2 != "/storage/emulated/0/"){
                v.setVisibility(View.VISIBLE);
                buffPathOpen = buffPathOpen.substring(0, buffPathOpen.lastIndexOf("/"));
                FileUtil.listDir(buffPathOpen, folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
        });

        openButton.setOnClickListener(v -> {
            if(buffPathOpen.endsWith(".mp4")) {
                videoPlayer.openProgram(buffPathOpen);
                mainWindow.setVisibility(View.GONE);
                mainWindow = null;
            }
        });

        final int[] buttonClickCount = {0,0};
        fullscreen.setOnClickListener(v -> {
            if(buttonClickCount[0]==0){
                fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                mainWindow.setScaleX(0.7f);
                mainWindow.setScaleY(0.7f);
                PortableView portableView1 = new PortableView(mainWindow);
                buttonClickCount[0]++;
            }
            else{
                fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setX(0);
                mainWindow.setY(0);
                mainWindow.setOnTouchListener(null);
                buttonClickCount[0]=0;
            }
        });
        close.setOnClickListener(v -> closeProgram());
        if(mainWindow.getParent() == null) {
            layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }else{
            mainWindow.setVisibility(View.VISIBLE);
        }
        mainActivity.programArrayList.add(this);
    }

    @Override
    public void closeProgram() {
        mainWindow.setVisibility(View.GONE);
        this.status = -1;
    }
}
