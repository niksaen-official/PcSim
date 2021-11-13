package com.niksaen.pcsim.program.deviceManager;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

import java.util.ArrayList;
import java.util.Arrays;

public class DeviceManager extends Program {

    Spinner mobo,cpu,ram,gpu,data,psu;
    LinearLayout content;

    String[] motherboardParameters,cpuParameters,psuParameters;
    ArrayList<String> ramParameters,dataParameters,gpuParameters;
    SpinnerAdapter motherboardAdapter,cpuAdapter,psuAdapter,ramAdapter,dataAdapter,gpuAdapter;

    public DeviceManager(MainActivity mainActivity){
        super(mainActivity);
        this.Title = "Device manager";
        ValueRam = new int[]{15,40};
        ValueVideoMemory = new int[]{10,20};
    }
    private void initView(){
        content = mainWindow.findViewById(R.id.content);
        mobo = mainWindow.findViewById(R.id.motherboard);
        cpu = mainWindow.findViewById(R.id.cpu);
        ram = mainWindow.findViewById(R.id.ram);
        gpu = mainWindow.findViewById(R.id.gpu);
        data = mainWindow.findViewById(R.id.data);
        psu = mainWindow.findViewById(R.id.psu);
    }

    private void getParameters(){
        //характеристики материнки
        motherboardParameters = new String[]{
                activity.words.get("Motherboard specifications")+": ",
                " "+activity.words.get("Model")+": " + activity.pcParametersSave.Mobo,
                " "+activity.words.get("Ports")+" SATA: " + activity.pcParametersSave.MOBO.get("Портов SATA"),
                " "+activity.words.get("Slots")+" PCI: " + activity.pcParametersSave.MOBO.get("Слотов PCI"),
                activity.words.get("RAM characteristics")+": ",
                "    "+activity.words.get("Memory type")+": " + activity.pcParametersSave.MOBO.get("Тип памяти"),
                "    "+activity.words.get("Number of channels")+": " + activity.pcParametersSave.MOBO.get("Кол-во каналов"),
                "    "+activity.words.get("Minimum frequency")+": " + activity.pcParametersSave.MOBO.get("Мин. частота") + "MHz",
                "    "+activity.words.get("Maximum frequency")+": " + activity.pcParametersSave.MOBO.get("Макс. частота") + "MHz",
                "    "+activity.words.get("Maximum volume")+": " + activity.pcParametersSave.MOBO.get("Макс. объём") + "Gb",
        };

        //характеристики проца
        if(activity.pcParametersSave.CPU != null) {
            if(activity.pcParametersSave.CPU.get("Графическое ядро").equals("+")) {
                cpuParameters = new String[]{
                        activity.words.get("Processor specifications")+": ",
                        " " + activity.words.get("Model") + ": " + activity.pcParametersSave.Cpu,
                        " " + activity.words.get("Number of cores") + ": " + activity.pcParametersSave.CPU.get("Кол-во ядер"),
                        " " + activity.words.get("Number of threads") + ": " + activity.pcParametersSave.CPU.get("Кол-во потоков"),
                        " " + activity.words.get("Cache") + ": " + activity.pcParametersSave.CPU.get("Кэш") + "Mb",
                        " " + activity.words.get("Frequency") + ": " + activity.pcParametersSave.CPU.get("Частота") + "MHz",
                        " " + activity.words.get("Overclocking capability") + ": " + activity.pcParametersSave.CPU.get("Возможность разгона"),
                        activity.words.get("RAM characteristics")+": ",
                        "    " + activity.words.get("Memory type") + ": " + activity.pcParametersSave.CPU.get("Тип памяти"),
                        "    " + activity.words.get("Maximum volume") + ": " + activity.pcParametersSave.CPU.get("Макс. объём") + "Gb",
                        "    " + activity.words.get("Number of channels") + ": " + activity.pcParametersSave.CPU.get("Кол-во каналов"),
                        "    " + activity.words.get("Minimum frequency") + ": " + activity.pcParametersSave.CPU.get("Мин. частота") + "MHz",
                        "    " + activity.words.get("Maximum frequency") + ": " + activity.pcParametersSave.CPU.get("Макс. частота") + "MHz",
                        activity.words.get("Integrated graphics core")+": " + activity.pcParametersSave.CPU.get("Графическое ядро"),
                        "    " + activity.words.get("Model") + ": " + activity.pcParametersSave.CPU.get("Модель"),
                        "    " + activity.words.get("Frequency") + ": " + activity.pcParametersSave.CPU.get("Частота Graphics card") + "MHz",
                };
            }else{
                cpuParameters = new String[]{
                        activity.words.get("Processor specifications")+": ",
                        " " + activity.words.get("Model") + ": " + activity.pcParametersSave.Cpu,
                        " " + activity.words.get("Number of cores") + ": " + activity.pcParametersSave.CPU.get("Кол-во ядер"),
                        " " + activity.words.get("Number of threads") + ": " + activity.pcParametersSave.CPU.get("Кол-во потоков"),
                        " " + activity.words.get("Cache") + ": " + activity.pcParametersSave.CPU.get("Кэш") + "Mb",
                        " " + activity.words.get("Frequency") + ": " + activity.pcParametersSave.CPU.get("Частота") + "MHz",
                        " " + activity.words.get("Overclocking capability") + ": " + activity.pcParametersSave.CPU.get("Возможность разгона"),
                        activity.words.get("RAM characteristics")+": ",
                        "    " + activity.words.get("Memory type") + ": " + activity.pcParametersSave.CPU.get("Тип памяти"),
                        "    " + activity.words.get("Maximum volume") + ": " + activity.pcParametersSave.CPU.get("Макс. объём") + "Gb",
                        "    " + activity.words.get("Number of channels") + ": " + activity.pcParametersSave.CPU.get("Кол-во каналов"),
                        "    " + activity.words.get("Minimum frequency") + ": " + activity.pcParametersSave.CPU.get("Мин. частота") + "MHz",
                        "    " + activity.words.get("Maximum frequency") + ": " + activity.pcParametersSave.CPU.get("Макс. частота") + "MHz",
                        activity.words.get("Integrated graphics core")+": " + activity.pcParametersSave.CPU.get("Графическое ядро")
                };
            }
        }
        //характеристики оперативки
        String[] ram4 = new String[0],ram3 = new String[0];
        if((Integer.parseInt(activity.pcParametersSave.MOBO.get("Кол-во слотов"))>=4)) {
             ram4 = new String[]{activity.words.get("RAM") + " (" + activity.words.get("Slot") + " 4): " + activity.words.get("Out")};
             ram3 = new String[]{activity.words.get("RAM") + " (" + activity.words.get("Slot") + " 3): " + activity.words.get("Out")};
        }
        String[] ram2 = new String[]{activity.words.get("RAM")+" ("+activity.words.get("Slot")+" 2): "+activity.words.get("Out")};
        String[] ram1 = new String[]{activity.words.get("RAM")+" ("+activity.words.get("Slot")+" 1): "+activity.words.get("Out")};
        if(activity.pcParametersSave.RAM1 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM1,1)) {
            ram1 = new String[]{
                    activity.words.get("RAM characteristics")+": ("+activity.words.get("Slot")+" 1):",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Ram1,
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.RAM1.get("Тип памяти"),
                    " " + activity.words.get("Volume") + ": "+activity.pcParametersSave.RAM1.get("Объём")+"Gb",
                    " " + activity.words.get("Frequency") + ": "+activity.pcParametersSave.RAM1.get("Частота")+"MHz",
                    " " + activity.words.get("Throughput") + ": "+activity.pcParametersSave.RAM1.get("Пропускная способность")+"PC"
            };
        }
        if(activity.pcParametersSave.RAM2 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM2,2)) {
            ram2 = new String[]{
                    activity.words.get("RAM characteristics")+": ("+activity.words.get("Slot")+" 2):",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Ram2,
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.RAM2.get("Тип памяти"),
                    " " + activity.words.get("Volume") + ": "+activity.pcParametersSave.RAM2.get("Объём")+"Gb",
                    " " + activity.words.get("Frequency") + ": "+activity.pcParametersSave.RAM2.get("Частота")+"MHz",
                    " " + activity.words.get("Throughput") + ": "+activity.pcParametersSave.RAM2.get("Пропускная способность")+"PC"
            };
        }
        if(activity.pcParametersSave.RAM3 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM3,3)) {
            ram3 = new String[]{
                    activity.words.get("RAM characteristics")+": ("+activity.words.get("Slot")+" 3):",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Ram3,
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.RAM3.get("Тип памяти"),
                    " " + activity.words.get("Volume") + ": "+activity.pcParametersSave.RAM3.get("Объём")+"Gb",
                    " " + activity.words.get("Frequency") + ": "+activity.pcParametersSave.RAM3.get("Частота")+"MHz",
                    " " + activity.words.get("Throughput") + ": "+activity.pcParametersSave.RAM3.get("Пропускная способность")+"PC"
            };
        }
        if(activity.pcParametersSave.RAM4 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM4,4)) {
            ram4 = new String[]{
                    activity.words.get("RAM characteristics")+": ("+activity.words.get("Slot")+" 4):",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Ram4,
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.RAM4.get("Тип памяти"),
                    " " + activity.words.get("Volume") + ": "+activity.pcParametersSave.RAM4.get("Объём")+"Gb",
                    " " + activity.words.get("Frequency") + ": "+activity.pcParametersSave.RAM4.get("Частота")+"MHz",
                    " " + activity.words.get("Throughput") + ": "+activity.pcParametersSave.RAM4.get("Пропускная способность")+"PC"
            };
        }
        ramParameters = concat(new String[][]{ram1,ram2,ram3,ram4});

        //характеристики видюхи
        String[] gpu1 = new String[]{ activity.words.get("Graphics card")+" ("+activity.words.get("Slot")+" 1): "+activity.words.get("Out")},gpu2=new String[0];

        if (Integer.parseInt(activity.pcParametersSave.MOBO.get("Слотов PCI")) >= 2) {
            gpu2 = new String[]{activity.words.get("Graphics card") + " (" + activity.words.get("Slot") + " 2): " + activity.words.get("Out")};
        }
        if(activity.pcParametersSave.GPU1 != null){
            gpu1 = new String[]{
                    activity.words.get("Graphics card specifications")+" ("+activity.words.get("Slot")+" 1): ",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Gpu1,
                    " "+activity.words.get("GPU")+": "+activity.pcParametersSave.GPU1.get("Графический процессор"),
                    " "+activity.words.get("Number of video chips")+": "+activity.pcParametersSave.GPU1.get("Кол-во видеочипов"),
                    " "+activity.words.get("Frequency")+": "+activity.pcParametersSave.GPU1.get("Частота")+"Mhz",
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.GPU1.get("Тип памяти"),
                    " "+activity.words.get("Video memory size")+": "+activity.pcParametersSave.GPU1.get("Объём видеопамяти")+"Gb",
                    " "+activity.words.get("Throughput")+": "+activity.pcParametersSave.GPU1.get("Пропускная способность")+"Gb/s",

            };
        }
        if(activity.pcParametersSave.GPU2 != null){
            gpu2 = new String[]{
                    activity.words.get("Graphics card specifications")+" ("+activity.words.get("Slot")+" 2): ",
                    " "+activity.words.get("Model")+": "+activity.pcParametersSave.Gpu2,
                    " "+activity.words.get("GPU")+": "+activity.pcParametersSave.GPU2.get("Графический процессор"),
                    " "+activity.words.get("Number of video chips")+": "+activity.pcParametersSave.GPU2.get("Кол-во видеочипов"),
                    " "+activity.words.get("Frequency")+": "+activity.pcParametersSave.GPU2.get("Частота")+"Mhz",
                    " "+activity.words.get("Memory type")+": "+activity.pcParametersSave.GPU2.get("Тип памяти"),
                    " "+activity.words.get("Video memory size")+": "+activity.pcParametersSave.GPU2.get("Объём видеопамяти")+"Gb",
                    " "+activity.words.get("Throughput")+": "+activity.pcParametersSave.GPU2.get("Пропускная способность")+"Gb/s",

            };
        }
        gpuParameters = concat(new String[][]{gpu1,gpu2});

        //характеристики накопителей
        String[]
                data1 = new String[]{activity.words.get("Storage device")+" ("+activity.words.get("Slot")+" 1): "+activity.words.get("Out")},
                data2 = new String[]{activity.words.get("Storage device")+" ("+activity.words.get("Slot")+" 2): "+activity.words.get("Out")},
                data3 = new String[]{activity.words.get("Storage device")+" ("+activity.words.get("Slot")+" 3): "+activity.words.get("Out")},
                data4 = new String[]{activity.words.get("Storage device")+" ("+activity.words.get("Slot")+" 4): "+activity.words.get("Out")},
                data5=new String[0],
                data6=new String[0];
        if (Integer.parseInt(activity.pcParametersSave.MOBO.get("Портов SATA")) >= 6) {
            data5 = new String[]{activity.words.get("Storage device") + " (" + activity.words.get("Slot") + " 5): " + activity.words.get("Out")};
            data6 = new String[]{activity.words.get("Storage device") + " (" + activity.words.get("Slot") + " 6): " + activity.words.get("Out")};
        }
        if(activity.pcParametersSave.DATA1 != null){
            data1 = new String[]{
                    activity.words.get("Drive characteristics")+" ("+activity.words.get("Slot")+" 1):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data1,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA1.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA1.get("Тип")
            };
        }
        if(activity.pcParametersSave.DATA2 != null){
            data2 = new String[]{
                    activity.words.get("Drive characteristics")+" ("+activity.words.get("Slot")+" 2):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data2,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA2.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA2.get("Тип"),
            };
        }
        if(activity.pcParametersSave.DATA3 != null){
            data3 = new String[]{
                    activity.words.get("Drive characteristics") +" ("+activity.words.get("Slot")+" 3):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data3,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA3.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA3.get("Тип")
            };
        }
        if(activity.pcParametersSave.DATA4 != null){
            data4 = new String[]{
                    activity.words.get("Drive characteristics")+" ("+activity.words.get("Slot")+" 4):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data4,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA4.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA4.get("Тип")
            };
        }
        if(activity.pcParametersSave.DATA5 != null){
            data5 = new String[]{
                    activity.words.get("Drive characteristics")+" ("+activity.words.get("Slot")+" 5):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data5,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA5.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA5.get("Тип")
            };
        }
        if(activity.pcParametersSave.DATA6 != null){
            data6 = new String[]{
                    activity.words.get("Drive characteristics")+" ("+activity.words.get("Slot")+" 6):",
                    " "+activity.words.get("Model")+": " + activity.pcParametersSave.Data6,
                    " "+activity.words.get("Volume")+": " + activity.pcParametersSave.DATA6.get("Объём") +"Gb",
                    " "+activity.words.get("Type")+": " + activity.pcParametersSave.DATA6.get("Тип")
            };
        }
        dataParameters = concat(new String[][]{data1,data2,data3,data4,data5,data6});

        //характеристики блока питания
        psuParameters = new String[]{
                activity.words.get("Power supply characteristics")+":",
                " "+activity.words.get("Model")+": "+activity.pcParametersSave.Psu,
                " "+activity.words.get("Power")+": "+ activity.pcParametersSave.PSU.get("Мощность")+"W",
                " "+activity.words.get("Protection")+": " + activity.pcParametersSave.PSU.get("Защита")
        };

    }

    private void style(){
        content.setBackgroundColor(activity.styleSave.ThemeColor1);

        motherboardAdapter = new SpinnerAdapter(activity.getBaseContext(),0,motherboardParameters,activity.words.get("Motherboard"),"Motherboard");
        cpuAdapter = new SpinnerAdapter(activity.getBaseContext(),0,cpuParameters,activity.words.get("CPU"),"CPU");
        ramAdapter = new SpinnerAdapter(activity.getBaseContext(),0,ramParameters,activity.words.get("RAM"),"RAM");
        psuAdapter = new SpinnerAdapter(activity.getBaseContext(),0,psuParameters,activity.words.get("Power supply"),"PSU");
        dataAdapter = new SpinnerAdapter(activity.getBaseContext(),0,dataParameters,activity.words.get("Storage device"),"DATA");
        gpuAdapter = new SpinnerAdapter(activity.getBaseContext(),0,gpuParameters,activity.words.get("Graphics card"),"GPU");

        motherboardAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        motherboardAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        motherboardAdapter.TextColor_View = activity.styleSave.TextColor;

        cpuAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        cpuAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        cpuAdapter.TextColor_View = activity.styleSave.TextColor;

        ramAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        ramAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        ramAdapter.TextColor_View = activity.styleSave.TextColor;

        psuAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        psuAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        psuAdapter.TextColor_View = activity.styleSave.TextColor;

        dataAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        dataAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        dataAdapter.TextColor_View = activity.styleSave.TextColor;

        gpuAdapter.BackgroundColor_DropDownView = activity.styleSave.ThemeColor2;
        gpuAdapter.TextColor_DropDownView = activity.styleSave.TextColor;
        gpuAdapter.TextColor_View = activity.styleSave.TextColor;

        mobo.setAdapter(motherboardAdapter);
        cpu.setAdapter(cpuAdapter);
        ram.setAdapter(ramAdapter);
        gpu.setAdapter(gpuAdapter);
        data.setAdapter(dataAdapter);
        psu.setAdapter(psuAdapter);
    }

    public void initProgram(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_checkiron, null);
        initView();
        getParameters();
        style();
        super.initProgram();
    }

    private ArrayList<String> concat(String[][] arrays){
        ArrayList<String> result = new ArrayList<>();
        for (String[] array : arrays){
            result.addAll(Arrays.asList(array));
        }
        return result;
    }
}
