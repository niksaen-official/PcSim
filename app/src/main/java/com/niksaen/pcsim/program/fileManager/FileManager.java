package com.niksaen.pcsim.program.fileManager;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;

import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.FileUtil;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class FileManager {
    private View mainWindow;
    private ConstraintLayout monitor;

    private LayoutInflater layoutInflater;
    private Typeface font;
    private Context context;

    private PcParametersSave pcParametersSave;

    public FileManager(Context context, PcParametersSave pcParametersSave, ConstraintLayout monitor){
        layoutInflater=LayoutInflater.from(context);
        this.context = context;
        this.monitor = monitor;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        this.pcParametersSave = pcParametersSave;
    }

    private String path = "",path2 = "";
    private TextView title, folderName;
    private ListView listViewFiles;
    private FileManagerListViewAdapter fileManagerListViewAdapter;
    private Button buttonClose,buttonFullscreen,rollUp,pageDown;
    private LinearLayout container;

    private void initView(){
        mainWindow = layoutInflater.inflate(R.layout.program_file_manager_filepage,null);

        title = mainWindow.findViewById(R.id.title);
        folderName = mainWindow.findViewById(R.id.folderName);
        listViewFiles = mainWindow.findViewById(R.id.main);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        container = mainWindow.findViewById(R.id.container);
        pageDown = mainWindow.findViewById(R.id.pageDown);
    }

    ArrayList<String> files;
    private void initAdapter(){
        files = new ArrayList<>();
        FileUtil.listDir("//storage/emulated/0/",files);
        fileManagerListViewAdapter = new FileManagerListViewAdapter(context,R.layout.item_for_filemanager,files);
    }

    HashMap<String,String> words;

    private StyleSave styleSave;
    private void style(){
        styleSave = new StyleSave(context);
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());

        fileManagerListViewAdapter.ColorBackground = styleSave.ThemeColor1;
        fileManagerListViewAdapter.ColorText = styleSave.TextColor;

        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        buttonClose.setBackgroundResource(styleSave.CloseButtonImageRes);
        buttonFullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);
        title.setText(words.get("File manager"));
        folderName.setTextColor(styleSave.TextColor);
        folderName.setTypeface(font);
        folderName.setText("/storage/emulated/0");
        pageDown.setBackgroundResource(styleSave.ArrowButtonImage);
        container.setBackgroundColor(styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(styleSave.ColorWindow);

        listViewFiles.setBackgroundColor(styleSave.ThemeColor1);
        listViewFiles.setAdapter(fileManagerListViewAdapter);
    }

    public void openProgram(){

        initView();initAdapter();style();

        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            if(FileUtil.isDirectory(files.get(position))){
                folderName.setText(files.get(position));
                path = files.get(position);
                FileUtil.listDir(files.get(position), files);
                ((BaseAdapter) listViewFiles.getAdapter()).notifyDataSetChanged();
            }
            else if(files.get(position).endsWith(".txt")){
                new TextViewer(context,monitor).openProgram(FileUtil.readFile(files.get(position)));
            }
            else if(files.get(position).endsWith(".png") || files.get(position).endsWith(".jpg")){
                new ImageViewer(context,monitor).openProgram(files.get(position));
            }
        });

        pageDown.setOnClickListener(v -> {
            if(path.contains("/storage/emulated/0/") && path != "/storage/emulated/0/"){
                v.setVisibility(View.VISIBLE);
                path = path.substring(0, path.lastIndexOf("/"));
                FileUtil.listDir(path, files);
                folderName.setText(path);
                ((BaseAdapter) listViewFiles.getAdapter()).notifyDataSetChanged();
            }
        });

        //нажатие кнопок
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeProgram();
            }
        });
        final int[] button2ClickCount = {0};
        buttonFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button2ClickCount[0]==0){
                    buttonFullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    mainWindow.setScaleX(0.7f);
                    mainWindow.setScaleY(0.7f);
                    mainWindow.setX(0f);
                    mainWindow.setY(0f);
                    PortableView portableView = new PortableView(mainWindow);
                    button2ClickCount[0]++;
                }
                else{
                    buttonFullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    mainWindow.setOnTouchListener(null);
                    button2ClickCount[0]=0;
                }
            }
        });

        monitor.addView(mainWindow,ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
    }

    public void closeProgram(){
        if(mainWindow!=null) {
            mainWindow.setVisibility(View.GONE);
        }
    }
}
