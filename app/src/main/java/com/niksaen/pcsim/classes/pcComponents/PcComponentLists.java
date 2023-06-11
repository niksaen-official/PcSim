package com.niksaen.pcsim.classes.pcComponents;

import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.NapiOS;

import java.util.ArrayList;
import java.util.HashMap;

public class PcComponentLists {
    public static ArrayList<String> CaseList = new ArrayList<>();
    public static ArrayList<String> MotherboardList = new ArrayList<>();
    public  static ArrayList<String> CpuList = new ArrayList<>();
    public static ArrayList<String> CoolerList = new ArrayList<>();
    public static ArrayList<String> RamList = new ArrayList<>();
    public static ArrayList<String> GraphicsCardList = new ArrayList<>();
    public static ArrayList<String> DataStorageList = new ArrayList<>();
    public static ArrayList<String> PowerSupplyList = new ArrayList<>();
    public static ArrayList<String> DiskList = new ArrayList<>();

    static {
        setCaseList();
        setMoboList();
        setCpuList();
        setCoolerList();
        setRamList();
        setGpuList();
        setDataList();
        setPsuList();
        setDiskList();
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
        MotherboardList.add("BSRock B460M Pro4");
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
        CpuList.add("Jntel Deleron G5900");
        CpuList.add("Jntel Dore I3-10100F");
        CpuList.add("Jntel Dore I5-10400F");
        CpuList.add("Jntel Dore I7-11700");
        CpuList.add("Jntel Dore I7-11700F");
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
        RamList.add("CSkill TRIDENT Z RGB [F4-3866C18D-16GTZR]");
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
        GraphicsCardList.add("Bsus BMD Sadeon RX 560 AREZ");
        GraphicsCardList.add("Bsus BMD Sadeon S7 240 OC LP");
        GraphicsCardList.add("Bsus HeForce GT 1030 LP");
        GraphicsCardList.add("NSI HeForce GTX 1050 OC");
        GraphicsCardList.add("Ralit HeForce GTX 1050 Ti MONSTER");
        GraphicsCardList.add("CFA2 HeForce RTX 2080 Ti HOF");
    }

    private static void setDataList(){
        DataStorageList.add("ZShark 128S-2500");
        DataStorageList.add("ZShark 512S-8000");
        DataStorageList.add("ZShark 256S-4000");
        DataStorageList.add("ZShark 1024S-12000");
        DataStorageList.add("Offside 512GB-2500");
        DataStorageList.add("Offside 1024GB-5000");
        DataStorageList.add("Offside 2048GB-10000");
        DataStorageList.add("ZShark 128S-2500 White");
        DataStorageList.add("ZShark 512S-8000 White");
        DataStorageList.add("ZShark 256S-4000 White");
        DataStorageList.add("ZShark 1024S-12000 White");
    }

    private static void setPsuList(){
        PowerSupplyList.add("Office 300W12");
        PowerSupplyList.add("Office 350W12");
        PowerSupplyList.add("ZShark 400W12V");
        PowerSupplyList.add("WVolt WV500W12V");
        PowerSupplyList.add("ZShark 600W12V");
        PowerSupplyList.add("Office 700W12");
    }
    private static void setDiskList(){
        DiskList.add(NapiOS.TITLE+" Installer");
        DiskList.add(NapiOS.TITLE+" Installer Simplified");
        DiskList.add(LiriOS.TITLE+" Installer");
        DiskList.add(LiriOS.TITLE+" Installer Simplified");
        DiskList.add(MakOS.TITLE+" Installer");
        DiskList.add("Antivirus Installer");
    }
}
