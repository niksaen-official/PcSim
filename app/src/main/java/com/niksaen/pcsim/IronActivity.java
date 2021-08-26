package com.niksaen.pcsim;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.Custom;
import com.niksaen.pcsim.classes.PcComponentLists;
import com.niksaen.pcsim.pcView.MotherBoardView;
import com.niksaen.pcsim.save.PcParametersSave;

import java.util.ArrayList;
import java.util.HashMap;

public class IronActivity extends AppCompatActivity {

    //iron list
    ArrayList<String>
            caseList = new ArrayList<>(),
            moboList = new ArrayList<>(),
            cpuList = new ArrayList<>(),
            coolerList = new ArrayList<>(),
            ramList = new ArrayList<>(),
            gpuList = new ArrayList<>(),
            dataList = new ArrayList<>(),
            psuList = new ArrayList<>();

    //Iron parameters
    PcParametersSave parametersSave;
    TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
    HashMap<String,String> caseParameters, moboParameters, cpuParameters, coolerParameters, ram1Parameters, ram2Parameters, ram3Parameters, ram4Parameters, gpu1Parameters,gpu2Parameters,data1Parameters,data2Parameters,data3Parameters,data4Parameters,data5Parameters,data6Parameters,psuParameters;

    boolean caseInstall = false,moboInstall = false, coolerInstall = false, cpuInstall = false, ramInstall = false, gpuInstall = false, dataInstall = false, psuInstall = false;
    int ramSlotCount,dataSlotCount;
    String typeForInstall,nameForInstall;

    //view
    Button button1,button2,button3,button4,button5,button6;
    Spinner caseSpinner,moboSpinner,cpuSpinner,coolerSpinner,ramSpinner,gpuSpinner,dataSpinner,psuSpinner;

    View caseView;
    ImageView moboView,cpuView,coolerView,ram1View,ram2View,ram3View,ram4View,gpu1View,gpu2View,data1View,data2View,data3View,data4View,data5View,data6View,psuView;

    //for style
    Typeface font;
    int style = Typeface.BOLD;
    Custom custom = new Custom(this);
    AssetFile assetFile = new AssetFile(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iron);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        parametersSave = new PcParametersSave(this);

        //debug
        caseList = PcComponentLists.CaseList;
        moboList = PcComponentLists.MotherboardList;
        cpuList = PcComponentLists.CpuList;
        coolerList  = PcComponentLists.CoolerList;
        ramList = PcComponentLists.RamList;
        gpuList = PcComponentLists.GraphicsCardList;
        dataList = PcComponentLists.DataStorageList;
        psuList = PcComponentLists.PowerSupplyList;


        initViewAndStyle();
        getSavedPc();
        logic();
    }

    //инициализация вью и стили для них
    void initViewAndStyle(){
        button1 = findViewById(R.id.slot1);
        button2 = findViewById(R.id.slot2);
        button3 = findViewById(R.id.slot3);
        button4 = findViewById(R.id.slot4);
        button5 = findViewById(R.id.slot5);
        button6 = findViewById(R.id.slot6);

        caseSpinner = findViewById(R.id.caseSpinner);
        moboSpinner = findViewById(R.id.moboSpinner);
        cpuSpinner = findViewById(R.id.cpuSpinner);
        coolerSpinner = findViewById(R.id.coolerSpinner);
        ramSpinner = findViewById(R.id.ramSpinner);
        gpuSpinner = findViewById(R.id.gpuSpinner);
        dataSpinner = findViewById(R.id.dataSpinner);
        psuSpinner = findViewById(R.id.psuSpinner);

        //style
        custom.CustomSpinnerPc(caseSpinner,caseList,"CASE");
        custom.CustomSpinnerPc(moboSpinner,moboList,"MOBO");
        custom.CustomSpinnerPc(cpuSpinner,cpuList,"CPU");
        custom.CustomSpinnerPc(coolerSpinner,coolerList,"COOLER");
        custom.CustomSpinnerPc(ramSpinner,ramList,"RAM");
        custom.CustomSpinnerPc(gpuSpinner,gpuList,"GPU");
        custom.CustomSpinnerPc(dataSpinner,dataList,"DATA");
        custom.CustomSpinnerPc(psuSpinner,psuList,"PSU");

        button1.setTypeface(font,style);
        button2.setTypeface(font,style);
        button3.setTypeface(font,style);
        button4.setTypeface(font,style);
        button5.setTypeface(font,style);
        button6.setTypeface(font,style);

        //iron view
        caseView = findViewById(R.id.caseImage);
        moboView = findViewById(R.id.moboImage);
        cpuView = findViewById(R.id.cpuImage);
        coolerView = findViewById(R.id.coolerImage);
        ram1View = findViewById(R.id.ramImage1);
        ram2View = findViewById(R.id.ramImage2);
        ram3View = findViewById(R.id.ramImage3);
        ram4View = findViewById(R.id.ramImage4);
        gpu1View = findViewById(R.id.gpu1View);
        gpu2View = findViewById(R.id.gpu2View);
        data1View = findViewById(R.id.data1View);
        data2View = findViewById(R.id.data2View);
        data3View = findViewById(R.id.data3View);
        data4View = findViewById(R.id.data4View);
        data5View = findViewById(R.id.data5View);
        data6View = findViewById(R.id.data6View);
        psuView = findViewById(R.id.psuView);
    }

    //вся логика сборки пк здесь ->
    void logic(){
        caseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    removeAll();
                    caseParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/CASE/" + caseList.get(position) + ".json"),typeToken.getType());
                    caseView.setBackground(assetFile.getImage("pc_component/images/CASE/" + caseList.get(position) + ".png"));
                    caseList.add(parametersSave.Case);
                    parametersSave.setCase(caseList.get(position),caseParameters);
                    caseList.remove(position);
                    caseInstall = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        moboSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (caseInstall && position>0) {
                    removeMobo();
                    moboView.setImageDrawable(assetFile.getImage("pc_component/images/MOBO/" + moboList.get(position) + ".png"));
                    MotherBoardView motherBoardView = new MotherBoardView(moboList.get(position), caseView, getBaseContext());
                    moboParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/MOBO/" + moboList.get(position) + ".json"), typeToken.getType());
                    moboList.add(parametersSave.Mobo);
                    parametersSave.setMobo(moboList.get(position),moboParameters);
                    moboList.remove(position);
                    ramSlotCount = Integer.parseInt(moboParameters.get("Кол-во слотов"));
                    dataSlotCount = Integer.parseInt(moboParameters.get("Портов SATA"));

                    moboInstall = true;
                }else if(caseInstall){
                    MotherBoardView motherBoardView = new MotherBoardView(parametersSave.Mobo, caseView, getBaseContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cpuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(moboInstall && position>0) {
                    cpuParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/CPU/" + cpuList.get(position) + ".json"), typeToken.getType());
                    if (cpuParameters.get("Сокет").equals(moboParameters.get("Сокет"))) {
                        cpuView.setImageDrawable(assetFile.getImage("pc_component/images/CPU/" + cpuList.get(position) + ".png"));
                        cpuList.add(parametersSave.Cpu);
                        cpuInstall = true;
                        if(cpuParameters.get("Графическое ядро").equals("+")){
                            gpuInstall = true;
                        }
                        parametersSave.setCpu(cpuList.get(position),cpuParameters);
                        cpuList.remove(position);
                        coolerList.add(parametersSave.Cooler);
                        coolerView.setImageDrawable(null);
                        parametersSave.setCooler(null,null);
                        coolerInstall = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        coolerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    coolerParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/COOLER/" +coolerList.get(position)+".json"),typeToken.getType());
                    coolerView.setImageDrawable(assetFile.getImage("pc_component/images/COOLER/" +coolerList.get(position)+".png"));
                    coolerList.add(parametersSave.Cooler);
                    parametersSave.setCooler(coolerList.get(position),coolerParameters);
                    coolerList.remove(position);
                    coolerInstall = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    typeForInstall = "RAM";
                    nameForInstall = ramList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gpuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(moboInstall && position>0){
                    int countPci = Integer.parseInt(moboParameters.get("Слотов PCI"));
                    if(countPci>1){
                        typeForInstall = "GPU";
                        nameForInstall = gpuList.get(position);
                    }
                    else{
                        gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/GPU/" +gpuList.get(position)+"_horizontal.png"));
                        gpu1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/GPU/" +gpuList.get(position)+".json"),typeToken.getType());
                        gpuList.add(parametersSave.Gpu1);
                        parametersSave.setGpu1(gpuList.get(position),gpu1Parameters);
                        gpuList.remove(position);
                        gpuInstall = true;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    typeForInstall = "DATA";
                    nameForInstall = dataList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        psuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    psuParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/PSU/" +psuList.get(position)+".json"),typeToken.getType());
                    psuView.setImageDrawable(assetFile.getImage("pc_component/images/PSU/" +psuList.get(position)+".png"));
                    psuList.add(parametersSave.Psu);
                    parametersSave.setPsu(psuList.get(position),psuParameters);
                    psuList.remove(position);
                    psuInstall = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (typeForInstall != null) {
                    if (typeForInstall.equals("RAM") && moboInstall) {
                        ram1View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + nameForInstall + "_top.png"));
                        ramList.add(parametersSave.Ram1);
                        ram1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/RAM/" + nameForInstall + ".json"), typeToken.getType());
                        if(moboParameters.get("Тип памяти").equals(ram1Parameters.get("Тип памяти"))) {
                            parametersSave.setRam1(nameForInstall, ram1Parameters);
                        }
                        else{
                            parametersSave.setRam1(nameForInstall, null);
                        }
                        ramList.remove(nameForInstall);
                    }
                    if (typeForInstall.equals("GPU") && moboInstall) {
                        gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/GPU/" + nameForInstall + "_horizontal.png"));
                        gpu1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/GPU/" + nameForInstall + ".json"), typeToken.getType());
                        gpuList.add(parametersSave.Gpu1);
                        parametersSave.setGpu1(nameForInstall, gpu1Parameters);
                        gpuList.remove(nameForInstall);
                        gpuInstall = true;
                    }
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data1View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data1);
                        parametersSave.setData1(nameForInstall, data1Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeForInstall != null) {
                    if (typeForInstall.equals("RAM") && moboInstall) {
                        ram2View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + nameForInstall + "_top.png"));
                        ramList.add(parametersSave.Ram2);
                        ram2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/RAM/" + nameForInstall + ".json"), typeToken.getType());
                        if(moboParameters.get("Тип памяти").equals(ram2Parameters.get("Тип памяти"))) {
                            parametersSave.setRam2(nameForInstall, ram2Parameters);
                        }
                        else{
                            parametersSave.setRam2(nameForInstall, null);
                        }
                        ramList.remove(nameForInstall);
                    }
                    if (typeForInstall.equals("GPU") && moboInstall) {
                        gpu2View.setImageDrawable(assetFile.getImage("pc_component/images/GPU/" + nameForInstall + "_horizontal.png"));
                        gpu2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/GPU/" + nameForInstall + ".json"), typeToken.getType());
                        gpuList.add(parametersSave.Gpu2);
                        parametersSave.setGpu2(nameForInstall, gpu2Parameters);
                        gpuList.remove(nameForInstall);
                    }
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data2View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data2);
                        parametersSave.setData2(nameForInstall, data2Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (typeForInstall != null) {
                    if (typeForInstall.equals("RAM") && moboInstall) {
                        if (ramSlotCount >= 3) {
                            ram3View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + nameForInstall + "_top.png"));
                            ramList.add(parametersSave.Ram3);
                            ram3Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/RAM/" + nameForInstall + ".json"), typeToken.getType());
                            if(moboParameters.get("Тип памяти").equals(ram3Parameters.get("Тип памяти"))) {
                                parametersSave.setRam3(nameForInstall, ram3Parameters);
                            }
                            else{
                                parametersSave.setRam3(nameForInstall, null);
                            }
                            ramList.remove(nameForInstall);
                        }
                    }
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data3View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data3Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data3);
                        parametersSave.setData3(nameForInstall, data3Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeForInstall != null) {
                    if (typeForInstall.equals("RAM") && moboInstall) {
                        if (ramSlotCount >= 4) {
                            ram4View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + nameForInstall + "_top.png"));
                            ramList.add(parametersSave.Ram4);
                            ram4Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/RAM/" + nameForInstall + ".json"), typeToken.getType());
                            if(moboParameters.get("Тип памяти").equals(ram4Parameters.get("Тип памяти"))) {
                                parametersSave.setRam4(nameForInstall, ram4Parameters);
                            }
                            else{
                                parametersSave.setRam4(nameForInstall, null);
                            }
                            ramList.remove(nameForInstall);
                        }
                    }
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data4View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data4Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data4);
                        parametersSave.setData4(nameForInstall, data4Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (typeForInstall != null) {
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data5View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data5Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data5);
                        parametersSave.setData5(nameForInstall, data5Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeForInstall != null) {
                    if (typeForInstall.equals("DATA") && caseInstall) {
                        data6View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" + nameForInstall + "_h.png"));
                        data6Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/DATA/" + nameForInstall + ".json"), typeToken.getType());
                        dataList.add(parametersSave.Data6);
                        parametersSave.setData6(nameForInstall, data6Parameters);
                        dataList.remove(nameForInstall);
                    }
                    typeForInstall = null;
                }
            }
        });
    }

    //сборка сохранённого пк
    void getSavedPc(){
        moboParameters = parametersSave.MOBO;
        if(moboParameters != null) {
            ramSlotCount = Integer.parseInt(moboParameters.get("Кол-во слотов"));
            caseView.setBackground(assetFile.getImage("pc_component/images/CASE/" + parametersSave.Case + ".png"));
            caseInstall = parametersSave.Case != null;
            moboInstall = parametersSave.Mobo != null;
            moboView.setImageDrawable(assetFile.getImage("pc_component/images/MOBO/" + parametersSave.Mobo + ".png"));
            cpuView.setImageDrawable(assetFile.getImage("pc_component/images/CPU/" + parametersSave.Cpu + ".png"));
            coolerView.setImageDrawable(assetFile.getImage("pc_component/images/COOLER/" + parametersSave.Cooler + ".png"));
            ram1View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + parametersSave.Ram1 + "_top.png"));
            ram2View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + parametersSave.Ram2 + "_top.png"));
            ram3View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + parametersSave.Ram3 + "_top.png"));
            ram4View.setImageDrawable(assetFile.getImage("pc_component/images/RAM/" + parametersSave.Ram4 + "_top.png"));
            gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/GPU/" + parametersSave.Gpu1 + "_horizontal.png"));
            gpu2View.setImageDrawable(assetFile.getImage("pc_component/images/GPU/" + parametersSave.Gpu2 + "_horizontal.png"));
        }
        data1View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data1+"_h.png"));
        data2View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data2+"_h.png"));
        data3View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data3+"_h.png"));
        data4View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data4+"_h.png"));
        data5View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data5+"_h.png"));
        data6View.setImageDrawable(assetFile.getImage("pc_component/images/DATA/" +parametersSave.Data6+"_h.png"));
        psuView.setImageDrawable(assetFile.getImage("pc_component/images/PSU/" +parametersSave.Psu+".png"));
    }

    //снятие комплектующих с материнки
    private void removeMobo(){
        //снятие проца
        cpuList.add(parametersSave.Cpu);
        parametersSave.setCpu(null,null);
        cpuInstall = false;

        //снятие кулера
        coolerList.add(parametersSave.Cooler);
        parametersSave.setCooler(null,null);
        coolerInstall = false;

        //снятие оперативки
        ramList.add(parametersSave.Ram1);
        ramList.add(parametersSave.Ram2);
        ramList.add(parametersSave.Ram3);
        ramList.add(parametersSave.Ram4);
        parametersSave.setRam1(null,null);
        parametersSave.setRam2(null,null);
        parametersSave.setRam3(null,null);
        parametersSave.setRam4(null,null);
        ramInstall = false;

        //снятие видеокарт
        gpuList.add(parametersSave.Gpu1);
        gpuList.add(parametersSave.Gpu2);
        parametersSave.setGpu1(null,null);
        parametersSave.setGpu2(null,null);
        gpuInstall = false;

        cpuView.setImageDrawable(null);
        coolerView.setImageDrawable(null);
        ram1View.setImageDrawable(null);
        ram2View.setImageDrawable(null);
        ram3View.setImageDrawable(null);
        ram4View.setImageDrawable(null);
        gpu1View.setImageDrawable(null);
        gpu2View.setImageDrawable(null);
    }

    //разборка всего пк
    private void removeAll(){
        //снятие материнки
        moboList.add(parametersSave.Mobo);
        parametersSave.setMobo(null,null);
        moboInstall = false;

        //снятие проца
        cpuList.add(parametersSave.Cpu);
        parametersSave.setCpu(null,null);
        cpuInstall = false;

        //снятие кулера
        coolerList.add(parametersSave.Cooler);
        parametersSave.setCooler(null,null);
        coolerInstall = false;

        //снятие оперативки
        ramList.add(parametersSave.Ram1);
        ramList.add(parametersSave.Ram2);
        ramList.add(parametersSave.Ram3);
        ramList.add(parametersSave.Ram4);
        parametersSave.setRam1(null,null);
        parametersSave.setRam2(null,null);
        parametersSave.setRam3(null,null);
        parametersSave.setRam4(null,null);
        ramInstall = false;

        //снятие видеокарт
        gpuList.add(parametersSave.Gpu1);
        gpuList.add(parametersSave.Gpu2);
        parametersSave.setGpu1(null,null);
        parametersSave.setGpu2(null,null);
        gpuInstall = false;

        //снятие накопителей
        dataList.add(parametersSave.Data1);
        parametersSave.setData1(null,null);
        dataList.add(parametersSave.Data2);
        parametersSave.setData2(null,null);
        dataList.add(parametersSave.Data3);
        parametersSave.setData3(null,null);
        dataList.add(parametersSave.Data4);
        parametersSave.setData4(null,null);
        dataList.add(parametersSave.Data5);
        parametersSave.setData5(null,null);
        dataList.add(parametersSave.Data6);
        parametersSave.setData6(null,null);
        dataInstall = false;

        //снятие бп
        psuList.add(parametersSave.Psu);
        parametersSave.setPsu(null,null);
        psuInstall = false;

        moboView.setImageDrawable(null);
        cpuView.setImageDrawable(null);
        coolerView.setImageDrawable(null);
        ram1View.setImageDrawable(null);
        ram2View.setImageDrawable(null);
        ram3View.setImageDrawable(null);
        ram4View.setImageDrawable(null);
        gpu1View.setImageDrawable(null);
        gpu2View.setImageDrawable(null);
        data1View.setImageDrawable(null);
        data2View.setImageDrawable(null);
        data3View.setImageDrawable(null);
        data4View.setImageDrawable(null);
        data5View.setImageDrawable(null);
        data6View.setImageDrawable(null);
        psuView.setImageDrawable(null);
    }
}