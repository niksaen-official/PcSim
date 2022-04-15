package com.niksaen.pcsim.program.driverInstaller;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.pcComponents.PcComponentLists;
import com.niksaen.pcsim.program.Program;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeComponent extends Program {
    String TypeComponent;
    String DriveID;
    boolean Extended = false;
    public ChangeComponent(MainActivity activity) {
        super(activity);
        Title = "Driver installer";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{50,75};
    }

    private String selectedItems="";
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_driver_installer_change_component,null);
        initView();
        style();
        super.initProgram();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItems = "";
            SparseBooleanArray selected=listView.getCheckedItemPositions();
            for(int i=0;i < getDriversList().size();i++)
            {
                if(selected.get(i) && !selectedItems.contains(getDriversList().get(i))) selectedItems+=getDriversList().get(i)+",";
            }
        });
        install.setOnClickListener(v -> {
            Install install = new Install(activity);
            install.DriversForSetup = selectedItems.split(",");
            install.DriveID = DriveID;
            install.openProgram();
        });
    }

    View main;
    ListView listView;
    TextView description;
    Button install;
    private void initView(){
        main = mainWindow.findViewById(R.id.main);
        listView = mainWindow.findViewById(R.id.componentList);
        description = mainWindow.findViewById(R.id.description);
        install = mainWindow.findViewById(R.id.next);
    }
    private void style(){
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {activity.styleSave.ThemeColor3, activity.styleSave.ThemeColor3};
        ColorStateList stateList = new ColorStateList(states, colors);

        ArrayList<String> strings = getDriversList();
        listView.setAdapter(new ArrayAdapter<String>(activity, R.layout.item_for_driver_installer_listview, strings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                CheckedTextView view = (CheckedTextView) super.getView(position, convertView, parent);
                view.setText(
                        strings.get(position)
                                .replace(DriverInstaller.DriverForMotherboard,activity.words.get(DriverInstaller.DriverForMotherboard))
                                .replace(DriverInstaller.DriverForCPU,activity.words.get(DriverInstaller.DriverForCPU))
                                .replace(DriverInstaller.DriverForRAM,activity.words.get(DriverInstaller.DriverForRAM))
                                .replace(DriverInstaller.DriverForStorageDevices,activity.words.get(DriverInstaller.DriverForStorageDevices))
                                .replace(DriverInstaller.DriverForGPU,activity.words.get(DriverInstaller.DriverForGPU))
                                .replace(DriverInstaller.EXTENDED_TYPE,activity.words.get(DriverInstaller.EXTENDED_TYPE))
                                .replace(DriverInstaller.BASE_TYPE,activity.words.get(DriverInstaller.BASE_TYPE))
                );
                view.setTypeface(activity.font);
                view.setTextColor(activity.styleSave.TextColor);
                view.setBackgroundColor(activity.styleSave.ThemeColor1);
                view.setCheckMarkTintList(stateList);
                return view;
            }
        });

        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        description.setTextColor(activity.styleSave.TextColor);
        install.setTextColor(activity.styleSave.TextButtonColor);
        install.setBackgroundColor(activity.styleSave.ThemeColor2);

        description.setText(activity.words.get("Choose which drivers you want to install"));
        install.setText(activity.words.get("Install"));

        description.setTypeface(activity.font);
        install.setTypeface(activity.font, Typeface.BOLD);
    }
    private ArrayList<String> getDriversList(){
        HashMap<String,ArrayList<String>> componentMap = new HashMap<>();
        componentMap.put(DriverInstaller.DriverForMotherboard,PcComponentLists.MotherboardList);
        componentMap.put(DriverInstaller.DriverForCPU,PcComponentLists.CpuList);
        componentMap.put(DriverInstaller.DriverForRAM,PcComponentLists.RamList);
        componentMap.put(DriverInstaller.DriverForStorageDevices,PcComponentLists.DataStorageList);
        componentMap.put(DriverInstaller.DriverForGPU,PcComponentLists.GraphicsCardList);

        ArrayList<String> buff = new ArrayList<>();
        String[] strings = TypeComponent.split(",");
        for(int i = 0;i<strings.length;i++){
            for(String component:componentMap.get(strings[i])){
                if(Extended){
                    buff.add(strings[i]+component+"\n"+DriverInstaller.EXTENDED_TYPE);
                }else {
                    buff.add(strings[i]+component+"\n"+DriverInstaller.BASE_TYPE);
                }
            }
        }
        return buff;
    }
}
