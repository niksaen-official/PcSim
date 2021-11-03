package com.niksaen.pcsim.program.diskManager;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

import java.util.ArrayList;
import java.util.HashMap;

public class DiskManager extends Program {
    public DiskManager(MainActivity activity) {
        super(activity);
        Title = "Disk manager";
        ValueRam = new int[]{50,75};
        ValueVideoMemory = new int[]{10,20};
    }

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_disk_manager,null);
        initView();
        style();
        super.initProgram();
    }

    private RecyclerView diskListView;
    private TextView diskName,emptySpace,emptySpaceToPercent,allSpace;
    private LinearLayout main;
    private void initView(){
        diskListView = mainWindow.findViewById(R.id.diskList);
        diskName = mainWindow.findViewById(R.id.diskName);
        emptySpace = mainWindow.findViewById(R.id.emptySpace);
        emptySpaceToPercent = mainWindow.findViewById(R.id.emptySpaceToPercent);
        allSpace = mainWindow.findViewById(R.id.allSpace);
        main = mainWindow.findViewById(R.id.main);
    }
    private void style(){
        diskName.setTypeface(activity.font);
        emptySpaceToPercent.setTypeface(activity.font);
        emptySpace.setTypeface(activity.font);
        allSpace.setTypeface(activity.font);

        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        diskName.setTextColor(activity.styleSave.TextColor);
        emptySpaceToPercent.setTextColor(activity.styleSave.TextColor);
        emptySpace.setTextColor(activity.styleSave.TextColor);
        allSpace.setTextColor(activity.styleSave.TextColor);
        
        //edit future
        diskName.setText(activity.words.get("Disk")+":");
        emptySpace.setText("Свободно:");
        emptySpaceToPercent.setText("Свободно в %:");
        allSpace.setText("Всего:");

        DiskManagerAdapter adapter = new DiskManagerAdapter(activity,getAllDisk());
        adapter.TextColor = activity.styleSave.TextColor;
        adapter.BackgroundColor = activity.styleSave.ThemeColor1;
        diskListView.setAdapter(adapter);
    }
    private ArrayList<HashMap<String,String>> getAllDisk(){
        ArrayList<HashMap<String,String>> diskList = new ArrayList<>();
        if(activity.pcParametersSave.DATA1!=null){
            diskList.add(activity.pcParametersSave.DATA1);
        }
        if(activity.pcParametersSave.DATA2!=null){
            diskList.add(activity.pcParametersSave.DATA2);
        }
        if(activity.pcParametersSave.DATA3!=null){
            diskList.add(activity.pcParametersSave.DATA3);
        }
        if(activity.pcParametersSave.DATA4!=null){
            diskList.add(activity.pcParametersSave.DATA4);
        }
        if(activity.pcParametersSave.DATA5!=null){
            diskList.add(activity.pcParametersSave.DATA5);
        }
        if(activity.pcParametersSave.DATA6!=null){
            diskList.add(activity.pcParametersSave.DATA6);
        }
        return diskList;
    }
}
