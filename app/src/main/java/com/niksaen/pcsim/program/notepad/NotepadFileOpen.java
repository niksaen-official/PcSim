package com.niksaen.pcsim.program.notepad;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.fileWorkLib.FileUtil;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.fileManager.FileManagerListViewAdapter;

import java.io.File;
import java.util.ArrayList;

public class NotepadFileOpen extends Program {
    public NotepadFileOpen(MainActivity activity){
        super(activity);
        this.Title = "Opening file";
        ValueRam = new int[]{30,50};
        ValueVideoMemory = new int[]{10,15};
    }
    Button openButton,pageDown;
    ListView listView;

    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_for_open_file,null);
        openButton = mainWindow.findViewById(R.id.button4);
        listView = mainWindow.findViewById(R.id.listView);
        pageDown = mainWindow.findViewById(R.id.pageDown);
    }

    ArrayList<String> folders;
    FileManagerListViewAdapter listViewAdapter;
    private void style(){
        openButton.setTypeface(activity.font,Typeface.BOLD);
        openButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        openButton.setTextColor(activity.styleSave.TextButtonColor);
        pageDown.setBackgroundResource(activity.styleSave.ArrowButtonImage);
        folders = new ArrayList<>();
        for(File file: activity.getExternalFilesDirs( "")){
            folders.add(file.getPath());
        }
        listViewAdapter = new FileManagerListViewAdapter(activity.getBaseContext(),0,folders);
        listViewAdapter.ColorText = activity.styleSave.TextColor;
        listViewAdapter.ColorBackground = activity.styleSave.ThemeColor1;
        listView.setAdapter(listViewAdapter);
        listView.setBackgroundColor(activity.styleSave.ThemeColor1);
        openButton.setText(activity.words.get("Open"));
    }

    String buffPathOpen = "";
    View buff;
    public void initProgram(){
        initView();
        style();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(!folders.get(position).endsWith("/Android")) {
                if (FileUtil.isDirectory(folders.get(position))) {
                    buffPathOpen = folders.get(position);
                    FileUtil.listDir(folders.get(position), folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                } else if (folders.get(position).endsWith(".txt")) {
                    if (buff != null) {
                        buff.setBackgroundColor(activity.styleSave.ThemeColor1);
                    }
                    view.setBackgroundColor(activity.styleSave.ThemeColor2);
                    buff = view;
                    buffPathOpen = folders.get(position);
                }
            }
        });
        pageDown.setOnClickListener(v -> {
            v.setVisibility(View.VISIBLE);
            buffPathOpen = buffPathOpen.substring(0, buffPathOpen.lastIndexOf("/"));
            FileUtil.listDir(buffPathOpen, folders);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        });
        openButton.setOnClickListener(v -> {
            if (buffPathOpen.endsWith(".txt")) {
                new Notepad(activity).openProgram(FileUtil.readFile(buffPathOpen));
                closeProgram(1);
            }
        });
        super.initProgram();
    }
}
