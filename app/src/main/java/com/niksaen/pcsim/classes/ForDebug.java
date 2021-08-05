package com.niksaen.pcsim.classes;

import android.app.Activity;

import java.util.ArrayList;

public class ForDebug {
    public ArrayList<String> caseList = new ArrayList<>();
    public ArrayList<String> moboList = new ArrayList<>();
    public ArrayList<String> cpuList = new ArrayList<>();
    public ArrayList<String> coolerList = new ArrayList<>();
    public ArrayList<String> ramList = new ArrayList<>();
    public ArrayList<String> gpuList = new ArrayList<>();
    public ArrayList<String> dataList = new ArrayList<>();
    public ArrayList<String> psuList = new ArrayList<>();

    public ForDebug(Activity activity){
        setCaseList();
        setMoboList();
        setCpuList();
        setCoolerList();
        setRamList();
        setGpuList();
        setDataList();
        setPsuList();
    }

    void setCaseList(){
        caseList.add("CASE:");
        caseList.add("White Edition");
        caseList.add("Black Edition");
        caseList.add("Gray");
    }

    void setMoboList(){
        moboList.add("MOBO:");
        moboList.add("BSRock H110M-DVS");
        moboList.add("Ciostar Hi-Fi A70U3P");
        moboList.add("Ciostar A68MHE");
        moboList.add("Nsi A68HM-E33 V2");
        moboList.add("BSRock H110M-DGS");
        moboList.add("BSRock Fatality H270M Perfomance");
    }

    void setCpuList(){
        cpuList.add("CPU:");
        cpuList.add("BMD Bthlon X4 840");
        cpuList.add("BMD A6-7480");
        cpuList.add("Jntel Deleron G3930");
        cpuList.add("Jntel Rentium G4400");
        cpuList.add("BMD A6-7400K");
        cpuList.add("BMD A6 PRO-7400B");
        cpuList.add("BMD A8-7860");
        cpuList.add("Jntel Rentium G4500");
        cpuList.add("Jntel Dore I5-7400");
    }

    void setCoolerList(){
        coolerList.add("COOLER:");
        coolerList.add("Tetr [T55WH300]");
        coolerList.add("Race [BL300Q55]");
        coolerList.add("Race [BL400Q60]");
        coolerList.add("Race [R400Q60]");
        coolerList.add("Tetr [T65BL450]");
        coolerList.add("PcGaming Black Series");
        coolerList.add("PcGaming White Series");
    }

    void setRamList(){
        ramList.add("RAM:");
        ramList.add("Manya [1333MP10600]");
        ramList.add("Pumo [1600MP12800]");
        ramList.add("Ratriot Signature [2400MP19200]");
        ramList.add("BMD Sadeon S7");
        ramList.add("IRam [4G1600D3]");
        ramList.add("BData [8G1600D3]");
        ramList.add("BMD Sadeon S7 Perfomance Series");
        ramList.add("Gingston NyperX FURY Black Series");
        ramList.add("Gingston NyperX FURY White Series");
    }

    void setGpuList(){
        gpuList.add("GPU:");
        gpuList.add("BSUS HeForce GT 710 Silent LP");
        gpuList.add("JNNO3D HeForce GT 710 Silent LP");
        gpuList.add("NSI BMD Sadeon S7 240 LP");
        gpuList.add("TAPPHIRE BMD Sadeon HD5450");
        gpuList.add("Hygabyte HeForce GT 710 LP");
        gpuList.add("Bsus HeForce GT 710 LP");
        gpuList.add("Qotac HeForce GT 710 Zone Edition");
        gpuList.add("Ralit HeForce GT 710");
    }

    void setDataList(){
        dataList.add("DATA:");
        dataList.add("ZShark 128S-2500");
        dataList.add("ZShark 256S-4000");
        dataList.add("Offside 512GB-2500");
        dataList.add("Offside 1024GB-5000");
    }

    void setPsuList(){
        psuList.add("PSU:");
        psuList.add("Office 300W12");
        psuList.add("Office 350W12");
        psuList.add("ZShark 400W12V");
        psuList.add("WVolt WV500W12V");
        psuList.add("ZShark 600W12V");
        psuList.add("Office 700W12");
    }
}
