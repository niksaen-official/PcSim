package com.niksaen.pcsim.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.niksaen.pcsim.classes.Others;

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

    private SharedPreferences preferences;
    public PlayerData(Context context){
        preferences = context.getSharedPreferences("PlayerData",Context.MODE_PRIVATE);
        getAllData();
    }
    private void getAllData(){
        PcCaseList = preferences.getString("PcCaseList","").split(",");
        MotherboardList = preferences.getString("MotherboardList","").split(",");
        CpuList = preferences.getString("CpuList","").split(",");
        CoolerList = preferences.getString("CoolerList","").split(",");
        RamList = preferences.getString("RamList","").split(",");
        GraphicsCardList = preferences.getString("GraphicsCardList","").split(",");
        StorageDeviceList = preferences.getString("StorageDeviceList","").split(",");
        PowerSupplyList = preferences.getString("PowerSupplyList","").split(",");
        Money = preferences.getInt("Money",16000);
        ListPurchasedPrograms = preferences.getString("ListPurchasedPrograms","").split(",");
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
    }
}
