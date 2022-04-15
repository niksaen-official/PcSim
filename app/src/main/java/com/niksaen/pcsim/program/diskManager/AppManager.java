package com.niksaen.pcsim.program.diskManager;

import android.view.LayoutInflater;
import android.widget.ListView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.window.WarningWindow;

import java.util.HashMap;

public class AppManager extends Program {

    WarningWindow warningWindow;

    private HashMap<String,String> diskData;

    public void setDiskData(HashMap<String, String> diskData) {
        this.diskData = diskData;
    }

    public AppManager(MainActivity activity) {
        super(activity);
        Title = "App manager";
        ValueRam = new int[]{100,125};
        ValueVideoMemory = new int[]{70,90};
        warningWindow = new WarningWindow(activity);
    }

    private ListView appListView;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer,null);
        appListView = mainWindow.findViewById(R.id.main);
        appListView.setBackgroundColor(activity.styleSave.ThemeColor1);

        AppManagerAdapter adapter = new AppManagerAdapter(activity,0,diskData.get("Содержимое").split(","));
        adapter.BackgroundColor = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
        adapter.activity = activity;
        appListView.setAdapter(adapter);

        appListView.setOnItemClickListener((parent, view, position, id) -> {
            warningWindow.setMessageText("Вы хотите удалить программу \""+activity.words.get(diskData.get("Содержимое").split(",")[position])+"\" ?\nПосле удаления ваш компьютер будет перезагржен!");
            warningWindow.setButtonOkClick(v->{
                diskData.put("Содержимое",diskData.get("Содержимое").replace(diskData.get("Содержимое").split(",")[position]+",",""));
                activity.pcParametersSave.setData(diskData.get("name"),diskData);
                activity.reloadPc();
            });
            warningWindow.setOkButtonText(activity.words.get("Yes"));
            warningWindow.setCancelButtonText(activity.words.get("Cancel"));
            warningWindow.openProgram();
        });
        super.initProgram();
    }
}
