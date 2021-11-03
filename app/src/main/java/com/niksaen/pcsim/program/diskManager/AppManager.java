package com.niksaen.pcsim.program.diskManager;

import android.view.LayoutInflater;
import android.widget.ListView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

public class AppManager extends Program {
    private String[] appList;

    public void setAppList(String[] appList) {
        this.appList = appList;
    }

    public AppManager(MainActivity activity) {
        super(activity);
        Title = "App manager";
        ValueRam = new int[]{50,75};
        ValueVideoMemory = new int[]{10,20};
    }

    private ListView appListView;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer,null);
        appListView = mainWindow.findViewById(R.id.main);
        appListView.setBackgroundColor(activity.styleSave.ThemeColor1);

        AppManagerAdapter adapter = new AppManagerAdapter(activity,0,appList);
        adapter.BackgroundColor = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
        appListView.setAdapter(adapter);


        super.initProgram();
    }
}
