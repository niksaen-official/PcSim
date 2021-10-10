package com.niksaen.pcsim.program.appDownloader;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.program.Program;

public class PrepareForInstall extends Program {
    private String programForSetup;

    public void setProgramForSetup(String programForSetup) {
        this.programForSetup = programForSetup;
    }

    private SetupWindow setupWindow;
    public PrepareForInstall(MainActivity activity) {
        super(activity);
        Title = "Installation Wizard";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
        setupWindow = new SetupWindow(activity);
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
        diskList = getDiskList();
        initView();
        viewStyle();

        text.setText("Выберите диск для установки и необходимые опции");
        next.setText("Далее >");
        cancel.setText(activity.words.get("Cancel"));
        addToDesktop.setText("Добавить иконку на рабочий стол");
        installAdditionalSoft.setText("Установить необходимое дополнительное ПО");

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
                setupWindow.setDisk(disk);
                setupWindow.setInstallAdditionalSoft(installAdditionalSoft.isChecked());
                setupWindow.setAddToDesktop(addToDesktop.isChecked());
                setupWindow.setProgramForSetup(programForSetup);
                setupWindow.openProgram();
                closeProgram(1);
            }
        });

        super.initProgram();
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

        text.setTextColor(activity.styleSave.TextColor);
        addToDesktop.setTextColor(activity.styleSave.TextColor);
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {activity.styleSave.ThemeColor3, activity.styleSave.ThemeColor3};
        addToDesktop.setButtonTintList(new ColorStateList(states, colors));
        installAdditionalSoft.setTextColor(activity.styleSave.TextColor);
        installAdditionalSoft.setButtonTintList(new ColorStateList(states, colors));

        next.setBackgroundColor(activity.styleSave.ThemeColor2);
        next.setTextColor(activity.styleSave.TextButtonColor);
        cancel.setBackgroundColor(activity.styleSave.ThemeColor2);
        cancel.setTextColor(activity.styleSave.TextButtonColor);

        main.setBackgroundColor(activity.styleSave.ThemeColor1);

        adapter = new CustomListViewAdapter(activity,0,diskList);
        adapter.BackgroundColor1=activity.styleSave.ThemeColor1;
        adapter.BackgroundColor2 = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
        changeDisk.setAdapter(adapter);
    }
    private String[] getDiskList(){
        String[] diskList = new String[]{"Выберите диск:"};
        if(activity.pcParametersSave.DATA1 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA1.get("name"));
        }
        if(activity.pcParametersSave.DATA2 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA2.get("name"));
        }
        if(activity.pcParametersSave.DATA3 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA3.get("name"));
        }
        if(activity.pcParametersSave.DATA4 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA4.get("name"));
        }
        if(activity.pcParametersSave.DATA5 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA5.get("name"));
        }
        if(activity.pcParametersSave.DATA6 != null){
            diskList = StringArrayWork.add(diskList,activity.pcParametersSave.DATA6.get("name"));
        }
        return diskList;
    }
}
