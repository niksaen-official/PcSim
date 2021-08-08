package com.niksaen.pcsim.program.checkIron;

import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CheckIron extends Program {
    Context context;
    PcParametersSave pcParametersSave;
    ConstraintLayout layout;
    StyleSave styleSave;

    Typeface font;
    LayoutInflater inflater;

    View mainWindow;
    TextView title;
    Button close,rollUp,fullscreenMode;
    Spinner mobo,cpu,ram,gpu,data,psu;
    LinearLayout content;
    int fullscreenMode1,fullscreenMode2;
    String titleText;

    String[] motherboardParameters,cpuParameters,psuParameters;
    ArrayList<String> ramParameters,dataParameters,gpuParameters;
    CheckIronSpinnerAdapter motherboardAdapter,cpuAdapter,psuAdapter,ramAdapter,dataAdapter,gpuAdapter;

    HashMap<String,String> language;
    public CheckIron(Context context, PcParametersSave pcParametersSave, ConstraintLayout monitor){
        this.context = context;
        this.pcParametersSave = pcParametersSave;
        this.layout = monitor;

        styleSave = new StyleSave(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        inflater = LayoutInflater.from(context);
    }
    private void initView(){
        content = mainWindow.findViewById(R.id.content);
        title = mainWindow.findViewById(R.id.title);
        close = mainWindow.findViewById(R.id.close);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        fullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        mobo = mainWindow.findViewById(R.id.motherboard);
        cpu = mainWindow.findViewById(R.id.cpu);
        ram = mainWindow.findViewById(R.id.ram);
        gpu = mainWindow.findViewById(R.id.gpu);
        data = mainWindow.findViewById(R.id.data);
        psu = mainWindow.findViewById(R.id.psu);
    }

    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        language = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
        titleText = language.get("Device Manager");
    }

    private void getParameters(){
        //характеристики материнки
        motherboardParameters = new String[]{
                language.get("Motherboard specifications")+": ",
                " "+language.get("Model")+": " + pcParametersSave.Mobo,
                " "+language.get("Ports")+" SATA: " + pcParametersSave.MOBO.get("Портов SATA"),
                " "+language.get("Slots")+" PCI: " + pcParametersSave.MOBO.get("Слотов PCI"),
                language.get("RAM characteristics")+": ",
                "    "+language.get("Memory type")+": " + pcParametersSave.MOBO.get("Тип памяти"),
                "    "+language.get("Number of channels")+": " + pcParametersSave.MOBO.get("Кол-во каналов"),
                "    "+language.get("Minimum frequency")+": " + pcParametersSave.MOBO.get("Мин. частота") + "MHz",
                "    "+language.get("Maximum frequency")+": " + pcParametersSave.MOBO.get("Макс. частота") + "MHz",
                "    "+language.get("Maximum volume")+": " + pcParametersSave.MOBO.get("Макс. объём") + "Gb",
        };

        //характеристики проца
        if(pcParametersSave.CPU != null) {
            if(pcParametersSave.CPU.get("Графическое ядро").equals("+")) {
                cpuParameters = new String[]{
                        language.get("Processor specifications")+": ",
                        " " + language.get("Model") + ": " + pcParametersSave.Cpu,
                        " " + language.get("Number of cores") + ": " + pcParametersSave.CPU.get("Кол-во ядер"),
                        " " + language.get("Number of threads") + ": " + pcParametersSave.CPU.get("Кол-во потоков"),
                        " " + language.get("Cache") + ": " + pcParametersSave.CPU.get("Кэш") + "Mb",
                        " " + language.get("Frequency") + ": " + pcParametersSave.CPU.get("Частота") + "MHz",
                        " " + language.get("Overclocking capability") + ": " + pcParametersSave.CPU.get("Возможность разгона"),
                        language.get("RAM characteristics")+": ",
                        "    " + language.get("Memory type") + ": " + pcParametersSave.CPU.get("Тип памяти"),
                        "    " + language.get("Maximum volume") + ": " + pcParametersSave.CPU.get("Макс. объём") + "Gb",
                        "    " + language.get("Number of channels") + ": " + pcParametersSave.CPU.get("Кол-во каналов"),
                        "    " + language.get("Minimum frequency") + ": " + pcParametersSave.CPU.get("Мин. частота") + "MHz",
                        "    " + language.get("Maximum frequency") + ": " + pcParametersSave.CPU.get("Макс. частота") + "MHz",
                        language.get("Integrated graphics core")+": " + pcParametersSave.CPU.get("Графическое ядро"),
                        "    " + language.get("Model") + ": " + pcParametersSave.CPU.get("Модель"),
                        "    " + language.get("Frequency") + ": " + pcParametersSave.CPU.get("Частота GPU") + "MHz",
                };
            }else{
                cpuParameters = new String[]{
                        language.get("Processor specifications")+": ",
                        " " + language.get("Model") + ": " + pcParametersSave.Cpu,
                        " " + language.get("Number of cores") + ": " + pcParametersSave.CPU.get("Кол-во ядер"),
                        " " + language.get("Number of threads") + ": " + pcParametersSave.CPU.get("Кол-во потоков"),
                        " " + language.get("Cache") + ": " + pcParametersSave.CPU.get("Кэш") + "Mb",
                        " " + language.get("Frequency") + ": " + pcParametersSave.CPU.get("Частота") + "MHz",
                        " " + language.get("Overclocking capability") + ": " + pcParametersSave.CPU.get("Возможность разгона"),
                        language.get("RAM characteristics")+": ",
                        "    " + language.get("Memory type") + ": " + pcParametersSave.CPU.get("Тип памяти"),
                        "    " + language.get("Maximum volume") + ": " + pcParametersSave.CPU.get("Макс. объём") + "Gb",
                        "    " + language.get("Number of channels") + ": " + pcParametersSave.CPU.get("Кол-во каналов"),
                        "    " + language.get("Minimum frequency") + ": " + pcParametersSave.CPU.get("Мин. частота") + "MHz",
                        "    " + language.get("Maximum frequency") + ": " + pcParametersSave.CPU.get("Макс. частота") + "MHz",
                        language.get("Integrated graphics core")+": " + pcParametersSave.CPU.get("Графическое ядро")
                };
            }
        }
        //характеристики оперативки
        String[] ram4 = new String[0],ram3 = new String[0];
        if((Integer.parseInt(pcParametersSave.MOBO.get("Кол-во слотов"))>=4)) {
             ram4 = new String[]{language.get("RAM") + " (" + language.get("Slot") + " 4): " + language.get("Out")};
             ram3 = new String[]{language.get("RAM") + " (" + language.get("Slot") + " 3): " + language.get("Out")};
        }
        String[] ram2 = new String[]{language.get("RAM")+" ("+language.get("Slot")+" 2): "+language.get("Out")};
        String[] ram1 = new String[]{language.get("RAM")+" ("+language.get("Slot")+" 1): "+language.get("Out")};
        if(pcParametersSave.RAM1 != null && pcParametersSave.ramValid(pcParametersSave.RAM1,1)) {
            ram1 = new String[]{
                    language.get("RAM characteristics")+": ("+language.get("Slot")+" 1):",
                    " "+language.get("Model")+": "+pcParametersSave.Ram1,
                    " "+language.get("Memory type")+": "+pcParametersSave.RAM1.get("Тип памяти"),
                    " " + language.get("Volume") + ": "+pcParametersSave.RAM1.get("Объём")+"Gb",
                    " " + language.get("Frequency") + ": "+pcParametersSave.RAM1.get("Частота")+"MHz",
                    " " + language.get("Throughput") + ": "+pcParametersSave.RAM1.get("Пропускная способность")+"PC"
            };
        }
        if(pcParametersSave.RAM2 != null && pcParametersSave.ramValid(pcParametersSave.RAM2,2)) {
            ram2 = new String[]{
                    language.get("RAM characteristics")+": ("+language.get("Slot")+" 2):",
                    " "+language.get("Model")+": "+pcParametersSave.Ram2,
                    " "+language.get("Memory type")+": "+pcParametersSave.RAM2.get("Тип памяти"),
                    " " + language.get("Volume") + ": "+pcParametersSave.RAM2.get("Объём")+"Gb",
                    " " + language.get("Frequency") + ": "+pcParametersSave.RAM2.get("Частота")+"MHz",
                    " " + language.get("Throughput") + ": "+pcParametersSave.RAM2.get("Пропускная способность")+"PC"
            };
        }
        if(pcParametersSave.RAM3 != null && pcParametersSave.ramValid(pcParametersSave.RAM3,3)) {
            ram3 = new String[]{
                    language.get("RAM characteristics")+": ("+language.get("Slot")+" 3):",
                    " "+language.get("Model")+": "+pcParametersSave.Ram3,
                    " "+language.get("Memory type")+": "+pcParametersSave.RAM3.get("Тип памяти"),
                    " " + language.get("Volume") + ": "+pcParametersSave.RAM3.get("Объём")+"Gb",
                    " " + language.get("Frequency") + ": "+pcParametersSave.RAM3.get("Частота")+"MHz",
                    " " + language.get("Throughput") + ": "+pcParametersSave.RAM3.get("Пропускная способность")+"PC"
            };
        }
        if(pcParametersSave.RAM4 != null && pcParametersSave.ramValid(pcParametersSave.RAM4,4)) {
            ram4 = new String[]{
                    language.get("RAM characteristics")+": ("+language.get("Slot")+" 4):",
                    " "+language.get("Model")+": "+pcParametersSave.Ram4,
                    " "+language.get("Memory type")+": "+pcParametersSave.RAM4.get("Тип памяти"),
                    " " + language.get("Volume") + ": "+pcParametersSave.RAM4.get("Объём")+"Gb",
                    " " + language.get("Frequency") + ": "+pcParametersSave.RAM4.get("Частота")+"MHz",
                    " " + language.get("Throughput") + ": "+pcParametersSave.RAM4.get("Пропускная способность")+"PC"
            };
        }
        ramParameters = concat(new String[][]{ram1,ram2,ram3,ram4});

        //характеристики видюхи
        String[] gpu1 = new String[]{ language.get("Graphics card")+" ("+language.get("Slot")+" 1): "+language.get("Out")},gpu2=new String[0];

        if (Integer.parseInt(pcParametersSave.MOBO.get("Слотов PCI")) >= 2) {
            gpu2 = new String[]{language.get("Graphics card") + " (" + language.get("Slot") + " 2): " + language.get("Out")};
        }
        if(pcParametersSave.GPU1 != null){
            gpu1 = new String[]{
                    language.get("Graphics card specifications")+" ("+language.get("Slot")+" 1): ",
                    " "+language.get("Model")+": "+pcParametersSave.Gpu1,
                    " "+language.get("GPU")+": "+pcParametersSave.GPU1.get("Графический процессор"),
                    " "+language.get("Number of video chips")+": "+pcParametersSave.GPU1.get("Кол-во видеочипов"),
                    " "+language.get("Frequency")+": "+pcParametersSave.GPU1.get("Частота")+"Mhz",
                    " "+language.get("Memory type")+": "+pcParametersSave.GPU1.get("Тип памяти"),
                    " "+language.get("Video memory size")+": "+pcParametersSave.GPU1.get("Объём видеопамяти")+"Gb",
                    " "+language.get("Throughput")+": "+pcParametersSave.GPU1.get("Пропускная способность")+"Gb/s",

            };
        }
        if(pcParametersSave.GPU2 != null){
            gpu2 = new String[]{
                    language.get("Graphics card specifications")+" ("+language.get("Slot")+" 2): ",
                    " "+language.get("Model")+": "+pcParametersSave.Gpu2,
                    " "+language.get("GPU")+": "+pcParametersSave.GPU2.get("Графический процессор"),
                    " "+language.get("Number of video chips")+": "+pcParametersSave.GPU2.get("Кол-во видеочипов"),
                    " "+language.get("Frequency")+": "+pcParametersSave.GPU2.get("Частота")+"Mhz",
                    " "+language.get("Memory type")+": "+pcParametersSave.GPU2.get("Тип памяти"),
                    " "+language.get("Video memory size")+": "+pcParametersSave.GPU2.get("Объём видеопамяти")+"Gb",
                    " "+language.get("Throughput")+": "+pcParametersSave.GPU2.get("Пропускная способность")+"Gb/s",

            };
        }
        gpuParameters = concat(new String[][]{gpu1,gpu2});

        //характеристики накопителей
        String[]
                data1 = new String[]{language.get("Storage device")+" ("+language.get("Slot")+" 1): "+language.get("Out")},
                data2 = new String[]{language.get("Storage device")+" ("+language.get("Slot")+" 2): "+language.get("Out")},
                data3 = new String[]{language.get("Storage device")+" ("+language.get("Slot")+" 3): "+language.get("Out")},
                data4 = new String[]{language.get("Storage device")+" ("+language.get("Slot")+" 4): "+language.get("Out")},
                data5=new String[0],
                data6=new String[0];
        if (Integer.parseInt(pcParametersSave.MOBO.get("Портов SATA")) >= 6) {
            data5 = new String[]{language.get("Storage device") + " (" + language.get("Slot") + " 5): " + language.get("Out")};
            data6 = new String[]{language.get("Storage device") + " (" + language.get("Slot") + " 6): " + language.get("Out")};
        }
        if(pcParametersSave.DATA1 != null){
            data1 = new String[]{
                    language.get("Drive characteristics")+" ("+language.get("Slot")+" 1):",
                    " "+language.get("Model")+": " + pcParametersSave.Data1,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA1.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA1.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA1.get("MainDisk")
            };
        }
        if(pcParametersSave.DATA2 != null){
            data2 = new String[]{
                    language.get("Drive characteristics")+" ("+language.get("Slot")+" 2):",
                    " "+language.get("Model")+": " + pcParametersSave.Data2,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA2.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA2.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA2.get("MainDisk")
            };
        }
        if(pcParametersSave.DATA3 != null){
            data3 = new String[]{
                    language.get("Drive characteristics") +" ("+language.get("Slot")+" 3):",
                    " "+language.get("Model")+": " + pcParametersSave.Data3,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA3.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA3.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA3.get("MainDisk")
            };
        }
        if(pcParametersSave.DATA4 != null){
            data4 = new String[]{
                    language.get("Drive characteristics")+" ("+language.get("Slot")+" 4):",
                    " "+language.get("Model")+": " + pcParametersSave.Data4,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA4.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA4.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA4.get("MainDisk")
            };
        }
        if(pcParametersSave.DATA5 != null){
            data5 = new String[]{
                    language.get("Drive characteristics")+" ("+language.get("Slot")+" 5):",
                    " "+language.get("Model")+": " + pcParametersSave.Data5,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA5.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA5.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA5.get("MainDisk")
            };
        }
        if(pcParametersSave.DATA6 != null){
            data6 = new String[]{
                    language.get("Drive characteristics")+" ("+language.get("Slot")+" 6):",
                    " "+language.get("Model")+": " + pcParametersSave.Data6,
                    " "+language.get("Volume")+": " + pcParametersSave.DATA6.get("Объём") +"Gb",
                    " "+language.get("Type")+": " + pcParametersSave.DATA6.get("Тип"),
                    " "+language.get("Main")+": " + pcParametersSave.DATA6.get("MainDisk")
            };
        }
        dataParameters = concat(new String[][]{data1,data2,data3,data4,data5,data6});

        //характеристики блока питания
        psuParameters = new String[]{
                language.get("Power supply characteristics")+":",
                " "+language.get("Model")+": "+pcParametersSave.Psu,
                " "+language.get("Power")+": "+ pcParametersSave.PSU.get("Мощность")+"W",
                " "+language.get("Protection")+": " + pcParametersSave.PSU.get("Защита")
        };

    }

    private void style(){
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        title.setTypeface(font,Typeface.BOLD);
        title.setText(titleText);
        content.setBackgroundColor(styleSave.ThemeColor1);

        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullscreenMode1 = styleSave.FullScreenMode1ImageRes;
        fullscreenMode2 = styleSave.FullScreenMode2ImageRes;
        fullscreenMode.setBackgroundResource(fullscreenMode2);

        motherboardAdapter = new CheckIronSpinnerAdapter(context,0,motherboardParameters,language.get("Motherboard"),"MOBO");
        cpuAdapter = new CheckIronSpinnerAdapter(context,0,cpuParameters,language.get("CPU"),"CPU");
        ramAdapter = new CheckIronSpinnerAdapter(context,0,ramParameters,language.get("RAM"),"RAM");
        psuAdapter = new CheckIronSpinnerAdapter(context,0,psuParameters,language.get("Power supply"),"PSU");
        dataAdapter = new CheckIronSpinnerAdapter(context,0,dataParameters,language.get("Storage device"),"DATA");
        gpuAdapter = new CheckIronSpinnerAdapter(context,0,gpuParameters,language.get("Graphics card"),"GPU");

        motherboardAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        motherboardAdapter.TextColor_DropDownView = styleSave.TextColor;
        motherboardAdapter.TextColor_View = styleSave.TextColor;

        cpuAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        cpuAdapter.TextColor_DropDownView = styleSave.TextColor;
        cpuAdapter.TextColor_View = styleSave.TextColor;

        ramAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        ramAdapter.TextColor_DropDownView = styleSave.TextColor;
        ramAdapter.TextColor_View = styleSave.TextColor;

        psuAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        psuAdapter.TextColor_DropDownView = styleSave.TextColor;
        psuAdapter.TextColor_View = styleSave.TextColor;

        dataAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        dataAdapter.TextColor_DropDownView = styleSave.TextColor;
        dataAdapter.TextColor_View = styleSave.TextColor;

        gpuAdapter.BackgroundColor_DropDownView = styleSave.ThemeColor2;
        gpuAdapter.TextColor_DropDownView = styleSave.TextColor;
        gpuAdapter.TextColor_View = styleSave.TextColor;

        mobo.setAdapter(motherboardAdapter);
        cpu.setAdapter(cpuAdapter);
        ram.setAdapter(ramAdapter);
        gpu.setAdapter(gpuAdapter);
        data.setAdapter(dataAdapter);
        psu.setAdapter(psuAdapter);
    }

    public void openProgram(){
        this.status = 0;
        mainWindow = inflater.inflate(R.layout.program_checkiron,null);
        getLanguage();
        initView();
        getParameters();
        style();

        int[] buttonClicks = {0};
        close.setOnClickListener(v -> {
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        });
        fullscreenMode.setOnClickListener(v->{
            if(buttonClicks[0] == 0){
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(fullscreenMode1);
                buttonClicks[0]=1;
            }else{
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener(null);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(fullscreenMode2);
                buttonClicks[0]=0;
            }
        });

        layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private ArrayList<String> concat(String[][] arrays){
        ArrayList<String> result = new ArrayList<>();
        for (String[] array : arrays){
            result.addAll(Arrays.asList(array));
        }
        return result;
    }
    @Override
    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
        this.status = -1;
    }
}
