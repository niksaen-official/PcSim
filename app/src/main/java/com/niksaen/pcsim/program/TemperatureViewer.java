package com.niksaen.pcsim.program;

import android.view.LayoutInflater;
import android.widget.ListView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;

import java.util.ArrayList;

public class TemperatureViewer extends Program {

    public TemperatureViewer(MainActivity activity){
        super(activity);
        this.Title = "Temperature Viewer";
        ValueRam = new int[]{30,45};
        ValueVideoMemory = new int[]{10,20};
    }

    private ListView temperatureView;
    private void initView(){
        temperatureView = mainWindow.findViewById(R.id.main);
    }

    private void styleView(){
        temperatureView.setBackgroundColor(activity.styleSave.ThemeColor1);
        temperatureView.setAdapter(adapter);
    }

    CustomListViewAdapter adapter;
    private void initAdapter(){
        /* получение температуры железа*/
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(activity.words.get("CPU") +": "+activity.pcParametersSave.Cpu +"\n"+
                activity.words.get("Temperature") +": "+activity.pcParametersSave.currentCpuTemperature()+"C");
        if(activity.pcParametersSave.RAM1 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 1) : "+activity.pcParametersSave.Ram1
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentRamTemperature(activity.pcParametersSave.RAM1)+"C");
        }
        if(activity.pcParametersSave.RAM2 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 2) : "+activity.pcParametersSave.Ram2
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentRamTemperature(activity.pcParametersSave.RAM2)+"C");
        }
        if(activity.pcParametersSave.RAM3 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 3) : "+activity.pcParametersSave.Ram3
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentRamTemperature(activity.pcParametersSave.RAM3)+"C");
        }
        if(activity.pcParametersSave.RAM4 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 4) : "+activity.pcParametersSave.Ram4
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentRamTemperature(activity.pcParametersSave.RAM4)+"C");
        }
        if(activity.pcParametersSave.GPU1 != null){
            arrayList.add(activity.words.get("Graphics card") +" : "+activity.pcParametersSave.Gpu1
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentGpuTemperature(activity.pcParametersSave.GPU1)+"C");
        }
        if(activity.pcParametersSave.GPU2 != null){
            arrayList.add(activity.words.get("Graphics card") +"("+activity.words.get("Slot")+" 2) : "+activity.pcParametersSave.Gpu2
                    +"\n"+ activity.words.get("Temperature") +": "+activity.pcParametersSave.currentGpuTemperature(activity.pcParametersSave.GPU2)+"C");
        }

        adapter = new CustomListViewAdapter(activity.getBaseContext(),0,arrayList);
        adapter.BackgroundColor1 = activity.styleSave.ThemeColor1;
        adapter.BackgroundColor2 = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
    }

    public void initProgram(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer,null);
        initAdapter();
        initView();
        styleView();

        super.initProgram();
    }
}

