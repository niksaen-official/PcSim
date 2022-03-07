package com.niksaen.pcsim.program.gStore;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.Program;

public class GStorePrepareForInstall extends Program {
    private String programForSetup;

    public void setProgramForSetup(String programForSetup) {
        this.programForSetup = programForSetup;
    }

    private GStoreSetupWindow setupWindow;
    public GStorePrepareForInstall(MainActivity activity) {
        super(activity);
        Title = "Installation Wizard";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
        setupWindow = new GStoreSetupWindow(activity);
    }

    private TextView text;
    private ConstraintLayout main;
    private androidx.appcompat.widget.AppCompatCheckBox addToDesktop,installAdditionalSoft;
    private Spinner changeDisk;
    private Button next,cancel;
    private CustomListViewAdapter adapter;

    private String disk;
    private String[] diskList;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_prepare_for_install,null);
        super.initProgram();
        diskList = getDiskList();
        initView();
        viewStyle();

        text.setText(activity.words.get("Select the drive to install and the required options"));
        next.setText(activity.words.get("Next"));
        cancel.setText(activity.words.get("Cancel"));
        addToDesktop.setText(activity.words.get("Add an icon to the desktop"));
        installAdditionalSoft.setText(activity.words.get("Install the required additional software"));

        changeDisk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >0){
                    disk = diskList[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(v -> {
            if(disk != null) {
                setupWindow.setDiskID(disk);
                setupWindow.setInstallAdditionalSoft(installAdditionalSoft.isChecked());
                setupWindow.setAddToDesktop(addToDesktop.isChecked());
                setupWindow.setProgramForSetup(programForSetup);
                setupWindow.openProgram();
                closeProgram(1);
            }
        });
    }

    private void initView(){
        text = mainWindow.findViewById(R.id.text);
        main = mainWindow.findViewById(R.id.main);
        addToDesktop = mainWindow.findViewById(R.id.addToDesktop);
        installAdditionalSoft = mainWindow.findViewById(R.id.installAdditionalSoft);
        changeDisk = mainWindow.findViewById(R.id.changedDisk);
        next = mainWindow.findViewById(R.id.button2);
        cancel = mainWindow.findViewById(R.id.button3);
    }

    private void viewStyle(){
        text.setTypeface(activity.font);
        addToDesktop.setTypeface(activity.font);
        installAdditionalSoft.setTypeface(activity.font);
        next.setTypeface(activity.font, Typeface.BOLD);
        cancel.setTypeface(activity.font, Typeface.BOLD);

        text.setTextColor(Color.parseColor("#FFFFFF"));
        addToDesktop.setTextColor(Color.parseColor("#FFFFFF"));
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {Color.parseColor("#002C6E"), Color.parseColor("#002C6E")};
        addToDesktop.setButtonTintList(new ColorStateList(states, colors));
        installAdditionalSoft.setTextColor(activity.styleSave.TextColor);
        installAdditionalSoft.setButtonTintList(new ColorStateList(states, colors));

        next.setBackgroundColor(Color.parseColor("#002C6E"));
        next.setTextColor(Color.parseColor("#FFFFFF"));
        cancel.setBackgroundColor(Color.parseColor("#002C6E"));
        cancel.setTextColor(Color.parseColor("#FFFFFF"));

        mainWindow.setBackgroundColor(Color.parseColor("#002C6E"));
        main.setBackgroundColor(Color.parseColor("#0042A5"));

        adapter = new CustomListViewAdapter(activity,0,diskList);
        adapter.BackgroundColor1=Color.parseColor("#0042A5");
        adapter.BackgroundColor2 = Color.parseColor("#0042A5");
        adapter.TextColor = Color.parseColor("#FFFFFF");
        changeDisk.setAdapter(adapter);
    }
    private String[] getDiskList(){
        String[] diskList = new String[]{activity.words.get("Select drive:")};
        if(activity.pcParametersSave.DATA1 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data1)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA1.get("name"));
        }
        if(activity.pcParametersSave.DATA2 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data2)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA2.get("name"));
        }
        if(activity.pcParametersSave.DATA3 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data3)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA3.get("name"));
        }
        if(activity.pcParametersSave.DATA4 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data4)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA4.get("name"));
        }
        if(activity.pcParametersSave.DATA5 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data5)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA5.get("name"));
        }
        if(activity.pcParametersSave.DATA6 != null
                && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForStorageDevices+activity.pcParametersSave.Data6)){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA6.get("name"));
        }
        return diskList;
    }
}
