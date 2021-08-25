package com.niksaen.pcsim.program;

import android.view.LayoutInflater;
import android.widget.ListView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.adapters.CustomListViewAdapter;

import java.util.ArrayList;

public class ViewPowerSupplyLoad extends Program {

    public ViewPowerSupplyLoad(MainActivity activity){
        super(activity);
        this.Title = "View Power Supply Load";
        ValueRam = new int[]{50,60};
        ValueVideoMemory = new int[]{50,150};
    }
    private ListView temperatureView;
    private void initView(){
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        temperatureView = mainWindow.findViewById(R.id.main);
    }

    private void styleView(){
        temperatureView.setAdapter(adapter);
    }

    CustomListViewAdapter adapter;
    private void initAdapter(){
        /* получение нагрузки железа на блок питания*/
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(activity.words.get("CPU") +": "+activity.pcParametersSave.Cpu +"\n"+
                activity.words.get("Energy consumption") +": "+activity.pcParametersSave.cpuPower+"W");

        if(activity.pcParametersSave.RAM1 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 1) : "+activity.pcParametersSave.Ram1
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.ram1Power+"W");
        }
        if(activity.pcParametersSave.RAM2 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 2) : "+activity.pcParametersSave.Ram2
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.ram2Power+"W");
        }
        if(activity.pcParametersSave.RAM3 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 3) : "+activity.pcParametersSave.Ram3
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.ram3Power+"W");
        }
        if(activity.pcParametersSave.RAM4 != null){
            arrayList.add(activity.words.get("RAM") +"("+activity.words.get("Slot")+" 4) : "+activity.pcParametersSave.Ram4
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.ram4Power+"W");
        }

        if(activity.pcParametersSave.GPU1 != null){
            arrayList.add(activity.words.get("Graphics card") +" : "+activity.pcParametersSave.Gpu1
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.gpu1Power+"W");
        }
        if(activity.pcParametersSave.GPU2 != null){
            arrayList.add(activity.words.get("Graphics card") +"("+activity.words.get("Slot")+" 2) : "+activity.pcParametersSave.Gpu2
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.gpu2Power+"W");
        }

        if(activity.pcParametersSave.DATA1 != null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 1) : "+activity.pcParametersSave.Data1
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data1Power+"W");
        }
        if(activity.pcParametersSave.DATA2 != null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 2) : "+activity.pcParametersSave.Data2
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data2Power+"W");
        }
        if(activity.pcParametersSave.DATA3 != null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 3) : "+activity.pcParametersSave.Data3
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data3Power+"W");
        }
        if(activity.pcParametersSave.DATA4 != null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 4) : "+activity.pcParametersSave.Data4
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data4Power+"W");
        }
        if(activity.pcParametersSave.DATA5 != null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 5) : "+activity.pcParametersSave.Data5
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data5Power+"W");
        }
        if(activity.pcParametersSave.DATA6!= null){
            arrayList.add(activity.words.get("Storage device") +"("+activity.words.get("Slot")+" 6) : "+activity.pcParametersSave.Data6
                    +"\n"+ activity.words.get("Energy consumption") +": "+activity.pcParametersSave.data5Power+"W");
        }

        adapter = new CustomListViewAdapter(activity.getBaseContext(),0,arrayList);
        adapter.BackgroundColor1 = activity.styleSave.ThemeColor1;
        adapter.BackgroundColor2 =activity. styleSave.ThemeColor1;
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
