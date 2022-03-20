package com.niksaen.pcsim.save;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CartAdapters;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.classes.ProgramListAndData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * В этом классе происходит сохранение комплектуюхищ пк
 * и их параметров, также здесь проверяется пк на работоспособность
 * и проверка блока питания на нехватку мощности
 **/

public class PcParametersSave {

    public boolean pcWork = false;
    public int ramCanals;
    public String Case,Mobo,Cpu,Cooler,Ram1,Ram2,Ram3,Ram4,Gpu1,Gpu2,Data1,Data2,Data3,Data4,Data5,Data6,Psu;
    public HashMap<String,String> CASE,MOBO,CPU,COOLER,RAM1,RAM2,RAM3,RAM4,GPU1,GPU2,DATA1,DATA2,DATA3,DATA4,DATA5,DATA6,PSU;
    public double moboPower = 0,cpuPower = 0, coolerPower = 0, ram1Power = 0, ram2Power = 0, ram3Power = 0, ram4Power = 0, gpu1Power = 0, gpu2Power = 0, data1Power = 0, data2Power = 0, data3Power = 0, data4Power = 0, data5Power = 0, data6Power = 0, psuPower = 0,AllPcPower=0;
    TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};


    SharedPreferences preferences;

    public PcParametersSave(Context context){
        preferences = context.getSharedPreferences("PcParametersSave",Context.MODE_PRIVATE);
        getAllPcParameters();
    }
    void getAllPcParameters(){
        pcWork = preferences.getBoolean("pcwork",false);
        Case = preferences.getString("Case",null);
        Mobo = preferences.getString("Mobo",null);
        Cpu = preferences.getString("Cpu",null);
        Cooler = preferences.getString("Cooler",null);
        Ram1  = preferences.getString("Ram1",null);
        Ram2  = preferences.getString("Ram2",null);
        Ram3  = preferences.getString("Ram3",null);
        Ram4  = preferences.getString("Ram4",null);
        Gpu1  = preferences.getString("Gpu1",null);
        Gpu2  = preferences.getString("Gpu2",null);
        Data1 = preferences.getString("Data1",null);
        Data2 = preferences.getString("Data2",null);
        Data3 = preferences.getString("Data3",null);
        Data4 = preferences.getString("Data4",null);
        Data5 = preferences.getString("Data5",null);
        Data6 = preferences.getString("Data6",null);
        Psu = preferences.getString("Psu",null);

        if(Case != null){
            CASE = new Gson().fromJson(preferences.getString("CASE",""),typeToken.getType());
        }
        if(Mobo != null){
            MOBO = new Gson().fromJson(preferences.getString("MOBO",""),typeToken.getType());
        }
        if(Cpu != null){
            CPU = new Gson().fromJson(preferences.getString("CPU",""),typeToken.getType());
        }
        if(Cooler != null){
            COOLER = new Gson().fromJson(preferences.getString("COOLER",""),typeToken.getType());
        }
        if(Ram1 != null){
            RAM1 = new Gson().fromJson(preferences.getString("RAM1",""),typeToken.getType());
        }
        if(Ram2 != null){
            RAM2 = new Gson().fromJson(preferences.getString("RAM2",""),typeToken.getType());
        }
        if(Ram3 != null){
            RAM3 = new Gson().fromJson(preferences.getString("RAM3",""),typeToken.getType());
        }
        if(Ram4 != null){
            RAM4 = new Gson().fromJson(preferences.getString("RAM4",""),typeToken.getType());
        }
        if(Gpu1 != null){
            GPU1 = new Gson().fromJson(preferences.getString("GPU1",""),typeToken.getType());
        }
        if(Gpu2 != null){
            GPU2 = new Gson().fromJson(preferences.getString("GPU2",""),typeToken.getType());
        }
        if(Data1 != null){
            DATA1 = new Gson().fromJson(preferences.getString("DATA1",""),typeToken.getType());
        }
        if(Data2 != null){
            DATA2 = new Gson().fromJson(preferences.getString("DATA2",""),typeToken.getType());
        }
        if(Data3 != null){
            DATA3 = new Gson().fromJson(preferences.getString("DATA3",""),typeToken.getType());
        }
        if(Data4 != null){
            DATA4 = new Gson().fromJson(preferences.getString("DATA4",""),typeToken.getType());
        }
        if(Data5 != null){
            DATA5 = new Gson().fromJson(preferences.getString("DATA5",""),typeToken.getType());
        }
        if(Data6 != null){
            DATA6 = new Gson().fromJson(preferences.getString("DATA6",""),typeToken.getType());
        }
        if(Psu != null){
            PSU = new Gson().fromJson(preferences.getString("PSU",""),typeToken.getType());
        }
    }

    public void setCase(String Case,HashMap<String,String> CASE){
        this.Case = Case;
        this.CASE = CASE;
        preferences.edit().putString("Case",Case).apply();
        preferences.edit().putString("CASE",new Gson().toJson(CASE)).apply();
    }
    public void setMobo(String Mobo,HashMap<String,String> MOBO){
        this.Mobo = Mobo;
        this.MOBO = MOBO;
        preferences.edit().putString("Mobo",Mobo).apply();
        preferences.edit().putString("MOBO",new Gson().toJson(MOBO)).apply();
    }
    public void setCpu(String Cpu,HashMap<String,String> CPU){
        this.Cpu = Cpu;
        this.CPU = CPU;
        preferences.edit().putString("Cpu",Cpu).apply();
        preferences.edit().putString("CPU",new Gson().toJson(CPU)).apply();
    }
    public void setCooler(String Cooler,HashMap<String,String> COOLER){
        this.Cooler = Cooler;
        this.COOLER = COOLER;
        preferences.edit().putString("Cooler",Cooler).apply();
        preferences.edit().putString("COOLER",new Gson().toJson(COOLER)).apply();
    }
    public void setRam1(String Ram1,HashMap<String,String> RAM1){
            this.Ram1 = Ram1;
            this.RAM1 = RAM1;
            preferences.edit().putString("Ram1", Ram1).apply();
            preferences.edit().putString("RAM1", new Gson().toJson(RAM1)).apply();
    }
    public void setRam2(String Ram2,HashMap<String,String> RAM2){
            this.Ram2 = Ram2;
            this.RAM2 = RAM2;
            preferences.edit().putString("Ram2", Ram2).apply();
            preferences.edit().putString("RAM2", new Gson().toJson(RAM2)).apply();

    }
    public void setRam3(String Ram3,HashMap<String,String> RAM3){
            this.Ram3 = Ram3;
            this.RAM3 = RAM3;
            preferences.edit().putString("Ram3", Ram3).apply();
            preferences.edit().putString("RAM3", new Gson().toJson(RAM3)).apply();

    }
    public void setRam4(String Ram4,HashMap<String,String> RAM4){
            this.Ram4 = Ram4;
            this.RAM4 = RAM4;
            preferences.edit().putString("Ram4", Ram4).apply();
            preferences.edit().putString("RAM4", new Gson().toJson(RAM4)).apply();
    }
    public void setGpu1(String Gpu1,HashMap<String,String> GPU1){
        this.Gpu1 = Gpu1;
        this.GPU1 = GPU1;
        preferences.edit().putString("Gpu1",Gpu1).apply();
        preferences.edit().putString("GPU1",new Gson().toJson(GPU1)).apply();
    }
    public void setGpu2(String Gpu2,HashMap<String,String> GPU2){
        this.Gpu2 = Gpu2;
        this.GPU2 = GPU2;
        preferences.edit().putString("Gpu2",Gpu2).apply();
        preferences.edit().putString("GPU2",new Gson().toJson(GPU2)).apply();
    }
    public void setData1(String Data1,HashMap<String,String> DATA1){
        this.Data1 = Data1;
        this.DATA1 = DATA1;
        if(this.DATA1 != null && !this.DATA1.containsKey("rename"))
            this.DATA1.put("name","A:");
        preferences.edit().putString("Data1",Data1).apply();
        preferences.edit().putString("DATA1",new Gson().toJson(DATA1)).apply();
    }
    public void setData2(String Data2,HashMap<String,String> DATA2){
        this.Data2 = Data2;
        this.DATA2 = DATA2;
        if(this.DATA2 != null && !this.DATA2.containsKey("rename"))
            this.DATA2.put("name","B:");
        preferences.edit().putString("Data2",Data2).apply();
        preferences.edit().putString("DATA2",new Gson().toJson(DATA2)).apply();
    }
    public void setData3(String Data3,HashMap<String,String> DATA3){
        this.Data3 = Data3;
        this.DATA3 = DATA3;
        if(this.DATA3 != null && !this.DATA3.containsKey("rename"))
            this.DATA3.put("name","C:");
        preferences.edit().putString("Data3",Data3).apply();
        preferences.edit().putString("DATA3",new Gson().toJson(DATA3)).apply();
    }
    public void setData4(String Data4,HashMap<String,String> DATA4){
        this.Data4 = Data4;
        this.DATA4 = DATA4;
        if(this.DATA4 != null && !this.DATA4.containsKey("rename"))
            this.DATA4.put("name","D:");
        preferences.edit().putString("Data4",Data4).apply();
        preferences.edit().putString("DATA4",new Gson().toJson(DATA4)).apply();
    }
    public void setData5(String Data5,HashMap<String,String> DATA5){
        this.Data5 = Data5;
        this.DATA5 = DATA5;
        if(this.DATA5 != null && !this.DATA5.containsKey("rename"))
            this.DATA5.put("name","E:");
        preferences.edit().putString("Data5",Data5).apply();
        preferences.edit().putString("DATA5",new Gson().toJson(DATA5)).apply();
    }
    public void setData6(String Data6,HashMap<String,String> DATA6){
        this.Data6 = Data6;
        this.DATA6 = DATA6;
        if(this.DATA6 != null && !this.DATA6.containsKey("rename"))
            this.DATA6.put("name","F:");
        preferences.edit().putString("Data6",Data6).apply();
        preferences.edit().putString("DATA6",new Gson().toJson(DATA6)).apply();
    }
    public void setPsu(String Psu,HashMap<String,String> PSU) {
        this.Psu = Psu;
        this.PSU = PSU;
        preferences.edit().putString("Psu", Psu).apply();
        preferences.edit().putString("PSU", new Gson().toJson(PSU)).apply();
    }
    public void setData(String diskId,HashMap<String,String> dataHashMap){
        if(DATA1 != null) {
            if(diskId.equals(DATA1.get("name"))){
                setData1(Data1,dataHashMap);
            }
        }
        if(DATA2 != null) {
            if (diskId.equals(DATA2.get("name"))) {
                setData2(Data2, dataHashMap);
            }
        }
        if(DATA3 != null) {
            if (diskId.equals(DATA3.get("name"))) {
                setData3(Data3, dataHashMap);
            }
        }
        if(DATA4 != null) {
            if(diskId.equals(DATA4.get("name"))){
                setData4(Data4,dataHashMap);
            }
        }
        if(DATA5 != null) {
            if(diskId.equals(DATA5.get("name"))){
                setData5(Data5,dataHashMap);
            }
        }

        if(DATA6 != null) {
            if(diskId.equals(DATA6.get("name"))){
                setData6(Data6,dataHashMap);
            }
        }
    }
    /**
     * Проверка пк на правильность сборки
     * */
    public int maxRamFrequency, minRamFrequency;
    public boolean getPcWork(){
        boolean work = false;
        if(CASE != null){
            if(Mobo != null){
                if(CPU != null && COOLER != null){
                    //Совпадают ли сокеты материнки и проца
                    if(Objects.equals(CPU.get("Сокет"), MOBO.get("Сокет"))) {

                        ramCanals = Math.min(Integer.parseInt(CPU.get("Кол-во каналов")),Integer.parseInt(MOBO.get("Кол-во каналов")));

                        //Установка минимальной частоты для оперативки
                        minRamFrequency = Math.min(Integer.parseInt(MOBO.get("Мин. частота")), Integer.parseInt(CPU.get("Мин. частота")));

                        //установка максимальной частоты для оперативки
                        maxRamFrequency = Math.min(Integer.parseInt(MOBO.get("Макс. частота")), Integer.parseInt(CPU.get("Макс. частота")));

                        if(MOBO.get("Кол-во слотов").equals("4")){
                            if (ramValid(RAM1,1) || ramValid(RAM2,2) || ramValid(RAM3,3) || ramValid(RAM4,4)) {
                                if (Objects.requireNonNull(CPU.get("Графическое ядро")).equals("+") || GPU1 != null || GPU2 != null) {
                                    if (DATA1 != null || DATA2 != null || DATA3 != null || DATA4 != null || DATA5 != null || DATA6 != null && PSU != null) {
                                        work = true;
                                    }
                                }
                            }
                        }else if(MOBO.get("Кол-во слотов").equals("2")){
                            if (ramValid(RAM1,1) || ramValid(RAM2,2)) {
                                if (Objects.requireNonNull(CPU.get("Графическое ядро")).equals("+") || GPU1 != null || GPU2 != null) {
                                    if (DATA1 != null || DATA2 != null || DATA3 != null || DATA4 != null || DATA5 != null || DATA6 != null && PSU != null) {
                                        work = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return work;
    }

    // валидация оперативки
    public boolean ramValid(HashMap<String,String> RAM) {
        if (RAM != null) {
            boolean res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency &&
                    Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) <= maxRamFrequency &&
                    Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"));
            return res;
        }else {
            return false;
        }
    }
    public boolean ramValid(HashMap<String,String> RAM,int slot){
        boolean res = false;
        if(RAM != null) {
            if(Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"))) {
                res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency &&
                        Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) <= maxRamFrequency;
                if (!res) {
                    switch (slot) {
                        case 1: {
                            if (Integer.parseInt(RAM.get("Частота")) > maxRamFrequency) {
                                float k = Float.parseFloat(Objects.requireNonNull(RAM.get("Частота"))) / Float.parseFloat(Objects.requireNonNull(RAM.get("Пропускная способность")));
                                RAM.put("Частота", String.valueOf(maxRamFrequency));
                                RAM.put("Пропускная способность", String.valueOf((int) Math.round(maxRamFrequency / k)));

                                res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency && Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"));
                                setRam1(Ram1, RAM);
                            } else {
                                setRam1(Ram1, null);
                            }
                            break;
                        }
                        case 2: {
                            if (Integer.parseInt(RAM.get("Частота")) > maxRamFrequency) {
                                float k = Float.parseFloat(Objects.requireNonNull(RAM.get("Частота"))) / Float.parseFloat(Objects.requireNonNull(RAM.get("Пропускная способность")));
                                RAM.put("Частота", String.valueOf(maxRamFrequency));
                                RAM.put("Пропускная способность", String.valueOf((int) Math.round(maxRamFrequency / k)));
                                res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency && Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"));
                                setRam2(Ram2, RAM);
                            } else {
                                setRam2(Ram2, null);
                            }
                            break;
                        }
                        case 3: {
                            if (Integer.parseInt(RAM.get("Частота")) > maxRamFrequency) {
                                float k = Float.parseFloat(Objects.requireNonNull(RAM.get("Частота"))) / Float.parseFloat(Objects.requireNonNull(RAM.get("Пропускная способность")));
                                RAM.put("Частота", String.valueOf(maxRamFrequency));
                                RAM.put("Пропускная способность", String.valueOf((int) Math.round(maxRamFrequency / k)));
                                res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency && Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"));
                                setRam3(Ram3, RAM);
                            } else {
                                setRam3(Ram3, null);
                            }
                            break;
                        }
                        case 4: {
                            if (Integer.parseInt(RAM.get("Частота")) > maxRamFrequency) {
                                float k = Float.parseFloat(Objects.requireNonNull(RAM.get("Частота"))) / Float.parseFloat(Objects.requireNonNull(RAM.get("Пропускная способность")));
                                RAM.put("Частота", String.valueOf(maxRamFrequency));
                                RAM.put("Пропускная способность", String.valueOf((int) Math.round(maxRamFrequency / k)));
                                res = Integer.parseInt(Objects.requireNonNull(RAM.get("Частота"))) >= minRamFrequency && Objects.equals(RAM.get("Тип памяти"), CPU.get("Тип памяти"));
                                setRam4(Ram4, RAM);
                            } else {
                                setRam4(Ram4, null);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }
    //получение свободной оперативки в MB
    public int getEmptyRam(ArrayList<Program> programs){
        int allRam = 0;
        HashMap<String,String>[] ramList = new HashMap[]{
                this.RAM1,
                this.RAM2,
                this.RAM3,
                this.RAM4
        };
        for(HashMap<String,String> ram:ramList) {
            if (ram != null) {
                allRam += Integer.parseInt(ram.get("Объём"));
            }
        }
        allRam = allRam*1024;
        if(CPU.get("Графическое ядро").equals("+")){
            allRam-= 1024;
        }

        for(Program program:programs){
            allRam -= program.CurrentRamUse;
        }

        return allRam;
    }
    //получение всей оперативки в MB
    public int getAllRam(){
        int allRam = 0;
        HashMap<String,String>[] ramList = new HashMap[]{
                this.RAM1,
                this.RAM2,
                this.RAM3,
                this.RAM4
        };
        for(HashMap<String,String> ram:ramList) {
            if (ram != null) {
                allRam += Integer.parseInt(ram.get("Объём"));
            }
        }
        return allRam*1024;
    }
    public void setAllRamFrequency(){
        int ram1=10000,ram2=10000,ram3=10000,ram4=10000;
        if(RAM1 != null){
            ram1 = Integer.parseInt(RAM1.get("Частота"));
        }
        if(RAM2 != null){
            ram2 = Integer.parseInt(RAM1.get("Частота"));
        }
        if(RAM3 != null){
            ram3 = Integer.parseInt(RAM1.get("Частота"));
        }
        if(RAM4 != null){
            ram4 = Integer.parseInt(RAM1.get("Частота"));
        }
        String fr = String.valueOf(Math.min(Math.min(ram1,ram2),Math.min(ram3,ram4)));
        if(RAM1 != null){
            RAM1.put("Частота",fr);
        }
        if(RAM2 != null){
            RAM2.put("Частота",fr);
        }
        if(RAM3 != null){
            RAM3.put("Частота",fr);
        }
        if(RAM4 != null){
            RAM4.put("Частота",fr);
        }
    }
    //проверка блока питания на мощность
    public boolean psuEnoughPower(){
        if(MOBO!=null) {
            moboPower = Double.parseDouble(MOBO.get("Мощность"));
            if(CPU!=null && COOLER!=null) {
                cpuPower = (Integer.parseInt(CPU.get("Частота"))*0.025);
                coolerPower = Double.parseDouble(COOLER.get("Мощность"));
            }
            if(RAM1!=null) {
                ram1Power = ramPower(RAM1);
            }
            if(RAM2!=null) {
                ram2Power = ramPower(RAM2);
            }
            if(RAM3!=null) {
                ram3Power = ramPower(RAM3);
            }
            if(RAM4!=null) {
                ram4Power = ramPower(RAM4);
            }
            if(GPU1!=null) {
                gpuPower(GPU1,1);
            }
            if(GPU2!=null) {
                gpuPower(GPU2,2);
            }
        }
        if(DATA1 != null) {
            data1Power = Double.parseDouble(DATA1.get("Мощность"));
        }
        if(DATA2 != null) {
            data2Power = Double.parseDouble(DATA2.get("Мощность"));
        }
        if(DATA3 != null) {
            data3Power = Double.parseDouble(DATA3.get("Мощность"));
        }
        if(DATA4 != null) {
            data4Power = Double.parseDouble(DATA4.get("Мощность"));
        }
        if(DATA5 != null) {
            data5Power = Double.parseDouble(DATA5.get("Мощность"));
        }
        if(DATA6 != null) {
            data6Power = Double.parseDouble(DATA6.get("Мощность"));
        }
        if(PSU != null && !Psu.contains("[Сломано]")) {
            psuPower = Double.parseDouble(PSU.get("Мощность"));
        }
        else{
            psuPower = 0;
        }
        AllPcPower = moboPower+cpuPower+coolerPower+ram1Power+ram2Power+ram3Power+ram4Power+gpu1Power+gpu2Power+data1Power+data2Power+data3Power+data4Power+data5Power+data6Power;
        return AllPcPower < psuPower;
    }

    //энерго потребление различного железа
    public double ramPower(HashMap<String,String> RAM){
        double k =  Integer.parseInt(RAM.get("Частота"))/Double.parseDouble(RAM.get("Пропускная способность"));
        return  Integer.parseInt(RAM.get("Частота"))*(k/100);
    }
    public static float RamPower(HashMap<String,String> RAM){
        double k =  Integer.parseInt(RAM.get("Частота"))/Double.parseDouble(RAM.get("Пропускная способность"));
        return (float) (Integer.parseInt(RAM.get("Частота"))*(k/100));
    }
    public void gpuPower(HashMap<String,String> GPU,int slot){
        double main = 0;
        int frequency = Integer.parseInt(GPU.get("Частота"));
        float power,power_fan = 0;
        float power_coefficient;
        switch (GPU.get("Тип памяти")){
            case "GDDR":{power_coefficient = 1.4f*1.2f;break;}
            case "GDDR2":{power_coefficient = 1.5f*1.2f;break;}
            case "GDDR3":{power_coefficient = 1.6f*1.2f;break;}
            case "GDDR4":{power_coefficient = 1.7f*1.5f;break;}
            case "GDDR5":{power_coefficient = 1.8f*1.5f;break;}
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип памяти"));
        }
        switch (GPU.get("Тип охлаждения")){
            case "Passive":{break; }
            case "Cooler":{ power_fan = 10;break; }
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип охлаждения"));
        }
        main = (frequency /power_coefficient)/5;
        power = (float)(main*power_coefficient)/1.6f+power_fan;
        switch (slot){
            case 1:{gpu1Power = power;break;}
            case 2:{gpu2Power = power;break;}
        }
    }
    public static float GpuPower(HashMap<String,String> GPU){
        float main = 0;
        int frequency = Integer.parseInt(GPU.get("Частота"));
        float power,power_fan = 0;
        float power_coefficient;
        switch (GPU.get("Тип памяти")){
            case "GDDR":{power_coefficient = 1.68f;break;}
            case "GDDR2":{power_coefficient = 1.8f;break;}
            case "GDDR3":{power_coefficient = 1.92f;break;}
            case "GDDR4":{power_coefficient = 2.55f;break;}
            case "GDDR5":{power_coefficient = 2.7f;break;}
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип памяти"));
        }
        switch (GPU.get("Тип охлаждения")){
            case "Passive":{break; }
            case "Cooler":{ power_fan = 10;break; }
            case "Cooler*2":{ power_fan = 20;break; }
            case "Cooler*3":{ power_fan = 30;break; }
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип охлаждения"));
        }
        main = (frequency /power_coefficient)/5;
        power = (float)(main*power_coefficient)/1.6f+power_fan;
        return power;
    }

    // температура железа
    public double currentCpuTemperature(){ return Integer.parseInt(CPU.get("Частота")) * 0.016f - (Double.parseDouble(COOLER.get("TDP")) - Integer.parseInt(CPU.get("TDP"))) / 8; }
    public double maxCpuTemperature(){ return Integer.parseInt(CPU.get("TDP"))*1.5-10; }
    public  float currentRamTemperature( HashMap<String,String> RAM){
        if(RAM != null) {
            float frequency = Integer.parseInt(RAM.get("Частота"));
            float throughput = Integer.parseInt(RAM.get("Пропускная способность"));
            float k = frequency / throughput;
            return (float) (frequency * (k * 0.25));
        }else {
            return 0;
        }
    }
    public double currentGpuTemperature(HashMap<String,String> GPU){
        if(GPU != null) {
            float main = 0;
            int frequency = Integer.parseInt(GPU.get("Частота"));
            float power_coefficient = 0,temperature_coefficient = 0;
            float temperature;

            switch (GPU.get("Тип памяти")) {
                case "GDDR":{power_coefficient = 1.68f;break;}
                case "GDDR2":{power_coefficient = 1.8f;break;}
                case "GDDR3":{power_coefficient = 1.92f;break;}
                case "GDDR4":{power_coefficient = 2.55f;break;}
                case "GDDR5":{power_coefficient = 2.7f;break;}
                default:
                    throw new IllegalStateException("Unexpected value: " + GPU.get("Тип памяти"));
            }

            switch (GPU.get("Тип охлаждения")) {
                case "Passive": {
                    temperature_coefficient = 1.4f;
                    break;
                }
                case "Cooler": {
                    temperature_coefficient = 1.15f;
                    break;
                }
                case "Cooler*2": {
                    temperature_coefficient = 0.9f;
                    break;
                }
                case "Cooler*3": {
                    temperature_coefficient = 0.65f;
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + GPU.get("Тип охлаждения"));
            }

            main = (frequency /power_coefficient)/5;
            temperature = (float) main*temperature_coefficient/1.75f;
            return  temperature;
        }
        else{
            return 0;
        }
    }

    //получение свободного места на диске в MB
    public float getEmptyStorageSpace(HashMap<String,String> disk){
        if(!disk.get("Содержимое").equals("")) {
            String[] program = disk.get("Содержимое").split(",");
            float allSpace = Float.parseFloat(disk.get("Свободно"))*1024;// in Mb
            for (String item : program) {
                if (item.startsWith(DriverInstaller.DriversPrefix) || item.startsWith(DriverInstaller.AdditionalSoftPrefix)) {
                    continue;
                }
                allSpace -= ProgramListAndData.programSize.get(item) * 1024;
            }
            return allSpace;
        }else {
            return Float.parseFloat(disk.get("Объём")) * 1024;
        }
    }
    // получение типа главного диска
    public String getMainDiskType(){
        String type = null;
        HashMap<String,String>[] mainDiskId = new HashMap[]{DATA1, DATA2, DATA3, DATA4, DATA5, DATA6};
        for(HashMap<String,String> hashMap:mainDiskId){
            if(hashMap.get("MainDisk").equals("true")){
                type = hashMap.get("Тип");
                break;
            }
        }
        return type;
    }
    public String[] getContentOfAllDrives(){
        String[][] allDiskAppList = new String[0][];
        if(this.DATA1 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA1.get("Содержимое").split(","));
        }
        if(this.DATA2 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA2.get("Содержимое").split(","));
        }
        if(this.DATA3 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA3.get("Содержимое").split(","));
        }
        if(this.DATA4 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA4.get("Содержимое").split(","));
        }
        if(this.DATA5 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA5.get("Содержимое").split(","));
        }
        if(this.DATA6 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,this.DATA6.get("Содержимое").split(","));
        }
        return StringArrayWork.concatAll(allDiskAppList);
    }
    // получение списка накопителей
    public String[] getDiskList(){
        String[] diskList = new String[0];
        if(DATA1 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data1)){
            diskList = StringArrayWork.add(diskList,DATA1.get("name"));
        }
        if(DATA2 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data2)){
            diskList = StringArrayWork.add(diskList,DATA2.get("name"));
        }
        if(DATA3 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data3)){
            diskList = StringArrayWork.add(diskList,DATA3.get("name"));
        }
        if(DATA4 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data4)){
            diskList = StringArrayWork.add(diskList,DATA4.get("name"));
        }
        if(DATA5 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data5)){
            diskList = StringArrayWork.add(diskList,DATA5.get("name"));
        }
        if(DATA6 != null
                && StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+Data6)){
            diskList = StringArrayWork.add(diskList,DATA6.get("name"));
        }
        return diskList;
    }
    //получение свободной видео памяти в MB
    public int getEmptyVideoMemory(ArrayList<Program> programs){
        int allVideoMemory = 0;
        if(CPU.get("Графическое ядро").equals("+")){
            allVideoMemory += 1024;
        }
        if(GPU1 != null){
            allVideoMemory += Integer.parseInt(GPU1.get("Объём видеопамяти"))*1024;
        }
        if(GPU2 != null){
            allVideoMemory += Integer.parseInt(GPU2.get("Объём видеопамяти"))*1024;
        }

        for(Program program:programs){
            allVideoMemory-= program.CurrentVideoMemoryUse;
        }
        return allVideoMemory;
    }
    public int getAllVideoMemory(){
        int allVideoMemory = 0;
        if(CPU.get("Графическое ядро").equals("+")){
            allVideoMemory += 1024;
        }
        if(GPU1 != null){
            allVideoMemory += Integer.parseInt(GPU1.get("Объём видеопамяти"))*1024;
        }
        if(GPU2 != null){
            allVideoMemory += Integer.parseInt(GPU2.get("Объём видеопамяти"))*1024;
        }
        return allVideoMemory;
    }

    //проверки на наличие необходимых драйверов
    public boolean ramDriverValid(){
        String[] ramList = {Ram1,Ram2,Ram3,Ram4};
        for(String ram:ramList){
            if(ram != null){
                if(StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForRAM+ram)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean driveDriverValid(){
        String[] driveList = {Data1,Data2,Data3,Data4,Data5,Data6};
        for(String drive:driveList){
            if(drive != null){
                if(StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForStorageDevices+drive)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean gpuDriverValid(){
        String[] gpuList = {Gpu1,Gpu2};
        for(String gpu:gpuList){
            if(gpu != null){
                if(StringArrayWork.ArrayListToString(getContentOfAllDrives()).contains(DriverInstaller.DriverForGPU+gpu)){
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<String, String> getRam(int position) {
        switch (position){
            case 0:{
                return RAM1;
            }
            case 1:{
                return RAM2;
            }
            case 2:{
                return RAM3;
            }
            case 3:{
                return RAM4;
            }
            default: return null;
        }
    }

    public void setRam(int position, HashMap<String, String> ram) {
        switch (position){
            case 0:{
                setRam1(ram.get("Model"),ram);
                break;
            }
            case 1:{
                setRam2(ram.get("Model"),ram);
                break;
            }
            case 2:{
                setRam3(ram.get("Model"),ram);
                break;
            }
            case 3:{
                setRam4(ram.get("Model"),ram);
                break;
            }
        }
    }

    public HashMap<String, String> getGpu(int position) {
        switch (position){
            case 0:{
                return GPU1;
            }
            case 1:{
                return GPU2;
            }
            default: return null;
        }
    }

    public void setGpu(int position, HashMap<String, String> gpu) {
        switch (position){
            case 0:{
                setGpu1(gpu.get("Model"),gpu);
                break;
            }
            case 1:{
                setGpu2(gpu.get("Model"),gpu);
                break;
            }
        }
    }
    public HashMap<String, String> getDrive(int position) {
        switch (position){
            case 0:{
                return DATA1;
            }
            case 1:{
                return DATA2;
            }
            case 2:{
                return DATA3;
            }
            case 3:{
                return DATA4;
            }
            case 4:{
                return DATA5;
            }
            case 5:{
                return DATA6;
            }
            default: return null;
        }
    }

    public void setDrive(int position, HashMap<String, String> drive) {
        switch (position){
            case 0:{
                setData1(drive.get("Model"),drive);
                break;
            }
            case 1:{
                setData2(drive.get("Model"),drive);
                break;
            }
            case 2:{
                setData3(drive.get("Model"),drive);
                break;
            }
            case 3:{
                setData4(drive.get("Model"),drive);
                break;
            }
            case 4:{
                setData5(drive.get("Model"),drive);
                break;
            }
            case 5:{
                setData6(drive.get("Model"),drive);
                break;
            }
        }
    }

}
