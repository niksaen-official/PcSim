package com.niksaen.pcsim.program.appDownloader;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.classes.ProgramListAndData;

public class AppDownloader extends Program {
    AcceptPolitic acceptPolitic;
    public AppDownloader(MainActivity activity) {
        super(activity);
        Title = "App Downloader";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
        acceptPolitic = new AcceptPolitic(activity);
    }

    private ListView appList;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer,null);
        initAdapter();
        initViewAndStyle();

        appList.setOnItemClickListener((parent, view, position, id) -> {
            acceptPolitic.setProgramForSetup(ProgramListAndData.FreeAppList[position]);
            acceptPolitic.openProgram();
            closeProgram(1);
        });

        super.initProgram();
    }
    AppDownloaderListAdapter adapter;
    private void initAdapter(){
        adapter = new AppDownloaderListAdapter(activity.getBaseContext(),0);
        adapter.BackgroundColor = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
    }

    private void initViewAndStyle(){
        appList = mainWindow.findViewById(R.id.main);
        appList.setBackgroundColor(activity.styleSave.ThemeColor1);
        appList.setAdapter(adapter);
    }
}
