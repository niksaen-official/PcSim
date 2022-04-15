package com.niksaen.pcsim.program.paint;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.fileWorkLib.FileUtil;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.fileManager.FileManagerListViewAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PaintOpenFile extends Program {

    public PaintOpenFile(MainActivity activity){
        super(activity);
        this.Title = "Opening file";
        ValueRam = new int[]{30,50};
        ValueVideoMemory = new int[]{10,15};
    }
    private Button openButton,pageDown;
    private  ListView listView;

    private void initView(){
        titleTextView = mainWindow.findViewById(R.id.title);
        openButton = mainWindow.findViewById(R.id.button4);
        listView = mainWindow.findViewById(R.id.listView);
        pageDown = mainWindow.findViewById(R.id.pageDown);
    }

    private ArrayList<String> folders;

    private void style(){
        openButton.setTypeface(activity.font,Typeface.BOLD);
        openButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        openButton.setTextColor(activity.styleSave.TextButtonColor);
        pageDown.setBackgroundResource(activity.styleSave.ArrowButtonImage);

        folders = new ArrayList<>();
        FileUtil.listDir("//storage/emulated/0/",folders);
        FileManagerListViewAdapter listViewAdapter = new FileManagerListViewAdapter(activity.getBaseContext(), 0, folders);
        listViewAdapter.ColorText = activity.styleSave.TextColor;
        listViewAdapter.ColorBackground = activity.styleSave.ThemeColor1;
        listView.setAdapter(listViewAdapter);
        listView.setBackgroundColor(activity.styleSave.ThemeColor1);

        //set text
        openButton.setText(activity.words.get("Open"));
    }

    private String buffPathOpen = "";
    private View buff;
    public void initProgram(){
        initView();
        style();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (FileUtil.isDirectory(folders.get(position))) {
                buffPathOpen = folders.get(position);
                FileUtil.listDir(folders.get(position), folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            } else if (folders.get(position).endsWith(".png") || folders.get(position).endsWith(".jpg")) {
                if (buff != null) {
                    buff.setBackgroundColor(activity.styleSave.ThemeColor1);
                }
                view.setBackgroundColor(activity.styleSave.ThemeColor2);
                buff = view;
                buffPathOpen = folders.get(position);
            }
        });
        pageDown.setOnClickListener(v -> {
            if (buffPathOpen.contains("/storage/emulated/0/")) {
                v.setVisibility(View.VISIBLE);
                buffPathOpen = buffPathOpen.substring(0, buffPathOpen.lastIndexOf("/"));
                FileUtil.listDir(buffPathOpen, folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
           });
        openButton.setOnClickListener(v -> {
            if (buffPathOpen.endsWith(".png") || buffPathOpen.endsWith(".jpg")) {
                new Paint(activity).openProgram(getImage(buffPathOpen));
                closeProgram(1);
            }
        });
        super.initProgram();
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
