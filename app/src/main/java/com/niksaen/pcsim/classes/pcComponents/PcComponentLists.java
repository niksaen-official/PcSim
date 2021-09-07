package com.niksaen.pcsim.classes.pcComponents;

import java.util.ArrayList;

public class PcComponentLists {
    public static ArrayList<String> CaseList = new ArrayList<>();
    public static ArrayList<String> MotherboardList = new ArrayList<>();
    public  static ArrayList<String> CpuList = new ArrayList<>();
    public static ArrayList<String> CoolerList = new ArrayList<>();
    public static ArrayList<String> RamList = new ArrayList<>();
    public static ArrayList<String> GraphicsCardList = new ArrayList<>();
    public static ArrayList<String> DataStorageList = new ArrayList<>();
    public static ArrayList<String> PowerSupplyList = new ArrayList<>();
    static {
        setCaseList();
        setMoboList();
        setCpuList();
        setCoolerList();
        setRamList();
        setGpuList();
        setDataList();
        setPsuList();
    }

    private static void setCaseList(){
        CaseList.add("White Edition");
        CaseList.add("Black Edition");
        CaseList.add("Gray");
    }

    private static void setMoboList(){
        MotherboardList.add("BSRock H110M-DVS");
        MotherboardList.add("Ciostar Hi-Fi A70U3P");
        MotherboardList.add("Ciostar A68MHE");
        MotherboardList.add("Nsi A68HM-E33 V2");
        MotherboardList.add("BSRock H110M-DGS");
        MotherboardList.add("BSRock Fatality H270M Perfomance");
    }

    private static void setCpuList(){
        CpuList.add("BMD Bthlon X4 840");
        CpuList.add("BMD A6-7480");
        CpuList.add("Jntel Deleron G3930");
        CpuList.add("Jntel Rentium G4400");
        CpuList.add("BMD A6-7400K");
        CpuList.add("BMD A6 PRO-7400B");
        CpuList.add("BMD A8-7860");
        CpuList.add("Jntel Rentium G4500");
        CpuList.add("Jntel Dore I5-7400");
    }

    private static void setCoolerList(){
        CoolerList.add("Tetr [T55WH300]");
        CoolerList.add("Race [BL300Q55]");
        CoolerList.add("Race [BL400Q60]");
        CoolerList.add("Race [R400Q60]");
        CoolerList.add("Tetr [T65BL450]");
        CoolerList.add("PcGaming Black Series");
        CoolerList.add("PcGaming White Series");
    }

    private static void setRamList(){
        RamList.add("Manya [1333MP10600]");
        RamList.add("Pumo [1600MP12800]");
        RamList.add("Ratriot Signature [2400MP19200]");
        RamList.add("BMD Sadeon S7");
        RamList.add("IRam [4G1600D3]");
        RamList.add("BData [8G1600D3]");
        RamList.add("BMD Sadeon S7 Perfomance Series");
        RamList.add("Gingston NyperX FURY Black Series");
        RamList.add("Gingston NyperX FURY White Series");
    }

    private static void setGpuList(){
        GraphicsCardList.add("BSUS HeForce GT 710 Silent LP");
        GraphicsCardList.add("JNNO3D HeForce GT 710 Silent LP");
        GraphicsCardList.add("NSI BMD Sadeon S7 240 LP");
        GraphicsCardList.add("TAPPHIRE BMD Sadeon HD5450");
        GraphicsCardList.add("Hygabyte HeForce GT 710 LP");
        GraphicsCardList.add("Bsus HeForce GT 710 LP");
        GraphicsCardList.add("Qotac HeForce GT 710 Zone Edition");
        GraphicsCardList.add("Ralit HeForce GT 710");
    }

    private static void setDataList(){
        DataStorageList.add("ZShark 128S-2500");
        DataStorageList.add("ZShark 256S-4000");
        DataStorageList.add("Offside 512GB-2500");
        DataStorageList.add("Offside 1024GB-5000");
    }

    private static void setPsuList(){
        PowerSupplyList.add("Office 300W12");
        PowerSupplyList.add("Office 350W12");
        PowerSupplyList.add("ZShark 400W12V");
        PowerSupplyList.add("WVolt WV500W12V");
        PowerSupplyList.add("ZShark 600W12V");
        PowerSupplyList.add("Office 700W12");
    }
}
