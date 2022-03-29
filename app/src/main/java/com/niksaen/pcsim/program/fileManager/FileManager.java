package com.niksaen.pcsim.program.fileManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.fileWorkLib.FileUtil;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.window.WarningWindow;

import java.io.File;
import java.util.ArrayList;

public class FileManager extends Program {

    public FileManager(MainActivity activity){
        super(activity);
        this.Title = "File manager";
        ValueRam = new int[]{30,50};
        ValueVideoMemory = new int[]{10,15};
    }

    private String path = "";
    private TextView folderName;
    private ListView listViewFiles;
    private FileManagerListViewAdapter fileManagerListViewAdapter;
    private Button pageDown;
    private LinearLayout container;

    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_file_manager_filepage,null);

        folderName = mainWindow.findViewById(R.id.folderName);
        listViewFiles = mainWindow.findViewById(R.id.main);
        container = mainWindow.findViewById(R.id.container);
        pageDown = mainWindow.findViewById(R.id.pageDown);
    }

    ArrayList<String> files;
    private void initAdapter(){
        files = new ArrayList<>();
        FileUtil.listDir("/storage/emulated/0/",files);
        fileManagerListViewAdapter = new FileManagerListViewAdapter(activity.getBaseContext(),R.layout.item_for_filemanager,files);
    }
    private void style(){
        fileManagerListViewAdapter.ColorBackground = activity.styleSave.ThemeColor1;
        fileManagerListViewAdapter.ColorText = activity.styleSave.TextColor;
        folderName.setTextColor(activity.styleSave.TextColor);
        folderName.setTypeface(activity.font);
        pageDown.setBackgroundResource(activity.styleSave.ArrowButtonImage);
        container.setBackgroundColor(activity.styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);

        listViewFiles.setBackgroundColor(activity.styleSave.ThemeColor1);
        listViewFiles.setAdapter(fileManagerListViewAdapter);
    }

    public void initProgram(){
        initView();
        initAdapter();
        style();
        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            if(!files.get(position).endsWith("/Android")) {
                if (FileUtil.isDirectory(files.get(position))) {
                    folderName.setText(files.get(position));
                    path = files.get(position);
                    FileUtil.listDir(files.get(position), files);
                    ((BaseAdapter) listViewFiles.getAdapter()).notifyDataSetChanged();
                } else if (files.get(position).endsWith(".txt")) {
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.AdditionalSoftPrefix + "File manager: Text Viewer")) {
                        new TextViewer(activity).openProgram(FileUtil.readFile(files.get(position)));
                    }
                } else if (files.get(position).endsWith(".png") || files.get(position).endsWith(".jpg")) {
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.AdditionalSoftPrefix + "File manager: Image Viewer")) {
                        new ImageViewer(activity).openProgram(files.get(position));
                    }
                }
            }
        });
        listViewFiles.setOnItemLongClickListener((parent, view, position, id) -> {
            WarningWindow window = new WarningWindow(activity);
            if (FileUtil.isDirectory(files.get(position))) {
                window.setMessageText("Вы хотите удалить эту папку?");
            } else {
                window.setMessageText("Вы хотите удалить этот файл?");
            }
            window.setCancelButtonText(activity.words.get("Cancel"));
            window.setOkButtonText(activity.words.get("Remove"));
            window.setButtonOkClick(v1 -> {
                FileUtil.deleteFile(files.get(position));
                ((BaseAdapter) listViewFiles.getAdapter()).notifyDataSetChanged();
                window.closeProgram(1);
            });
            window.openProgram();
            return true;
        });
        pageDown.setOnClickListener(v -> {
            if (path.contains("/storage/emulated/0/") && path != "/storage/emulated/0/") {
                v.setVisibility(View.VISIBLE);
                path = path.substring(0, path.lastIndexOf("/"));
                FileUtil.listDir(path, files);
                folderName.setText(path);
                ((BaseAdapter) listViewFiles.getAdapter()).notifyDataSetChanged();
            }
        });
        super.initProgram();
    }
}
