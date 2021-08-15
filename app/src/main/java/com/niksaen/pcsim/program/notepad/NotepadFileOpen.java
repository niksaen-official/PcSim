package com.niksaen.pcsim.program.notepad;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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

public class NotepadFileOpen extends Program {

    Context context;
    View fileOpenWindow;
    Typeface font;
    StyleSave styleSave;
    Notepad notepad;
    MainActivity mainActivity;

    public NotepadFileOpen(MainActivity activity){
        super(activity);
        this.Title = "Opening file";
        this.context = activity.getBaseContext();
        mainActivity = activity;

        fileOpenWindow = LayoutInflater.from(context).inflate(R.layout.program_for_open_file,null);
        font = Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf");
        styleSave = new StyleSave(context);
        notepad = new Notepad(activity);
    }

    Button close,fullscreen;
    TextView title;
    Button openButton,pageDown;
    ListView listView;

    private void initView(){
        close = fileOpenWindow.findViewById(R.id.close);
        fullscreen = fileOpenWindow.findViewById(R.id.fullscreenMode);
        title = fileOpenWindow.findViewById(R.id.title);
        openButton = fileOpenWindow.findViewById(R.id.button4);
        listView = fileOpenWindow.findViewById(R.id.listView);
        pageDown = fileOpenWindow.findViewById(R.id.pageDown);
    }

    ArrayList<String> folders;
    FileManagerListViewAdapter listViewAdapter;
    HashMap<String,String> words;
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

    String buffPathOpen = "",buffPath2;
    View buff;
    public void openProgram(){
        if(status == -1) {
            super.openProgram();
            initView();
            style();
            listView.setOnItemClickListener((parent, view, position, id) -> {
                if (FileUtil.isDirectory(folders.get(position))) {
                    buffPathOpen = folders.get(position);
                    FileUtil.listDir(folders.get(position), folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                } else if (folders.get(position).endsWith(".txt")) {
                    if (buff != null) {
                        buff.setBackgroundColor(styleSave.ThemeColor1);
                    }
                    view.setBackgroundColor(styleSave.ThemeColor2);
                    buff = view;
                    buffPathOpen = folders.get(position);
                }
            });

            pageDown.setOnClickListener(v -> {
                if (buffPathOpen.contains("/storage/emulated/0/") && buffPath2 != "/storage/emulated/0/") {
                    v.setVisibility(View.VISIBLE);
                    buffPathOpen = buffPathOpen.substring(0, buffPathOpen.lastIndexOf("/"));
                    FileUtil.listDir(buffPathOpen, folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                }
            });

            openButton.setOnClickListener(v -> {
                if (buffPathOpen.endsWith(".txt")) {
                    notepad.openProgram(FileUtil.readFile(buffPathOpen));
                    closeProgram(1);
                }
            });

            final int[] button2ClickCount = {0};
            fullscreen.setOnClickListener(v -> {
                if (button2ClickCount[0] == 0) {
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
                } else {
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    fileOpenWindow.setScaleX(1);
                    fileOpenWindow.setScaleY(1);
                    fileOpenWindow.setX(0);
                    fileOpenWindow.setY(0);
                    fileOpenWindow.setOnTouchListener(null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fileOpenWindow.setZ(10f);
                    }
                    button2ClickCount[0] = 0;
                }
            });
            close.setOnClickListener(v -> closeProgram(1));
            if (fileOpenWindow.getParent() == null) {
                mainActivity.layout.addView(fileOpenWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                fileOpenWindow.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void closeProgram(int mode){
        super.closeProgram(mode);
        fileOpenWindow.setVisibility(View.GONE);
    }
}
