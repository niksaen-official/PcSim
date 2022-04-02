package com.niksaen.pcsim.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.cmd.libs.Pc;

public class PlayerData {
    public String[] PcCaseList;
    public String[] MotherboardList;
    public String[] CpuList;
    public String[] CoolerList;
    public String[] RamList;
    public String[] GraphicsCardList;
    public String[] StorageDeviceList;
    public String[] PowerSupplyList;
    public int Money;
    public String[] ListPurchasedPrograms;
    public String[] DiskSoftList;

    private SharedPreferences preferences;
    public PlayerData(Context context){
        preferences = context.getSharedPreferences("PlayerData",Context.MODE_PRIVATE);
        getAllData();
    }
    private void getAllData(){
        PcCaseList = StringArrayWork.clearEmpty(preferences.getString("PcCaseList","").split(","));
        MotherboardList = StringArrayWork.clearEmpty(preferences.getString("MotherboardList","").split(","));
        CpuList = StringArrayWork.clearEmpty(preferences.getString("CpuList","").split(","));
        CoolerList = StringArrayWork.clearEmpty(preferences.getString("CoolerList","").split(","));
        RamList = StringArrayWork.clearEmpty(preferences.getString("RamList","").split(","));
        GraphicsCardList = StringArrayWork.clearEmpty(preferences.getString("GraphicsCardList","").split(","));
        StorageDeviceList = StringArrayWork.clearEmpty(preferences.getString("StorageDeviceList","").split(","));
        PowerSupplyList = StringArrayWork.clearEmpty(preferences.getString("PowerSupplyList","").split(","));
        Money = preferences.getInt("Money",17500);
        ListPurchasedPrograms = StringArrayWork.clearEmpty(preferences.getString("ListPurchasedPrograms","").split(","));
        DiskSoftList = StringArrayWork.clearEmpty(preferences.getString("DiskSoftList","").split(","));
    }
    public void setAllData(){
        preferences.edit().putString("PcCaseList", Others.ArrayToString(PcCaseList)).apply();
        preferences.edit().putString("MotherboardList", Others.ArrayToString(MotherboardList)).apply();
        preferences.edit().putString("CpuList", Others.ArrayToString(CpuList)).apply();
        preferences.edit().putString("CoolerList", Others.ArrayToString(CoolerList)).apply();
        preferences.edit().putString("RamList", Others.ArrayToString(RamList)).apply();
        preferences.edit().putString("GraphicsCardList", Others.ArrayToString(GraphicsCardList)).apply();
        preferences.edit().putString("StorageDeviceList", Others.ArrayToString(StorageDeviceList)).apply();
        preferences.edit().putString("PowerSupplyList", Others.ArrayToString(PowerSupplyList)).apply();
        preferences.edit().putInt("Money",Money).apply();
        preferences.edit().putString("ListPurchasedPrograms", Others.ArrayToString(ListPurchasedPrograms)).apply();
        preferences.edit().putString("DiskSoftList",Others.ArrayToString(DiskSoftList)).apply();
    }
}
