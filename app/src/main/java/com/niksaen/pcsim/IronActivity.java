package com.niksaen.pcsim;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.pcView.MotherBoardView;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.PlayerData;
import com.niksaen.pcsim.save.Settings;

import java.util.ArrayList;
import java.util.Arrays;
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
    Button caseButton,moboButton,cpuButton,coolerButton,ramButton,gpuButton,dataButton,psuButton;

    RelativeLayout caseView;
    ImageView moboView,cpuView,coolerView,ram1View,ram2View,ram3View,ram4View,gpu1View,gpu2View,data1View,data2View,data3View,data4View,data5View,data6View,psuView;

    //for style
    Typeface font;
    int style = Typeface.BOLD;
    AssetFile assetFile = new AssetFile(this);
    PlayerData playerData;

    public HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(this).getText("language/"+ new Settings(this).Language+".json"),typeToken.getType());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iron);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        parametersSave = new PcParametersSave(this);
        playerData = new PlayerData(this);
        getLanguage();
        initView();
        getSaveData();
        logic();
        style();
    }

    //инициализация view
    void initView() {
        button1 = findViewById(R.id.slot1);
        button2 = findViewById(R.id.slot2);
        button3 = findViewById(R.id.slot3);
        button4 = findViewById(R.id.slot4);
        button5 = findViewById(R.id.slot5);
        button6 = findViewById(R.id.slot6);

        caseButton = findViewById(R.id.caseButton);
        moboButton = findViewById(R.id.moboButton);
        cpuButton = findViewById(R.id.cpuButton);
        coolerButton = findViewById(R.id.coolerButton);
        ramButton = findViewById(R.id.ramButton);
        gpuButton = findViewById(R.id.gpuButton);
        dataButton = findViewById(R.id.dataButton);
        psuButton = findViewById(R.id.psuButton);

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
    //стили для view
    ArrayAdapter<String>
            caseAdapter,
            moboAdapter,
            cpuAdapter,
            coolerAdapter,
            ramAdapter,
            gpuAdapter,
            dataAdapter,
            psuAdapter;
    private void style(){
        caseAdapter = CustomSpinnerPc(caseList,PcComponent.CASE);
        moboAdapter = CustomSpinnerPc(moboList,PcComponent.Motherboard);
        cpuAdapter = CustomSpinnerPc(cpuList,PcComponent.CPU);
        coolerAdapter = CustomSpinnerPc(coolerList,PcComponent.COOLER);
        ramAdapter = CustomSpinnerPc(ramList,PcComponent.RAM);
        gpuAdapter = CustomSpinnerPc(gpuList,PcComponent.GPU);
        dataAdapter = CustomSpinnerPc(dataList,PcComponent.StorageDevice);
        psuAdapter = CustomSpinnerPc(psuList,PcComponent.PowerSupply);

        button1.setTypeface(font,style);
        button2.setTypeface(font,style);
        button3.setTypeface(font,style);
        button4.setTypeface(font,style);
        button5.setTypeface(font,style);
        button6.setTypeface(font,style);

        caseButton.setText(words.get(PcComponent.CASE));
        caseButton.setTypeface(font,style);
        moboButton.setText(words.get(PcComponent.Motherboard));
        moboButton.setTypeface(font,style);
        cpuButton.setText(words.get(PcComponent.CPU));
        cpuButton.setTypeface(font,style);
        coolerButton.setText(words.get(PcComponent.COOLER));
        coolerButton.setTypeface(font,style);
        ramButton.setText(words.get(PcComponent.RAM));
        ramButton.setTypeface(font,style);
        gpuButton.setText(words.get(PcComponent.GPU));
        gpuButton.setTypeface(font,style);
        dataButton.setText(words.get(PcComponent.StorageDevice));
        dataButton.setTypeface(font,style);
        psuButton.setText(words.get(PcComponent.PowerSupply));
        psuButton.setTypeface(font,style);
    }

    //вся логика сборки пк здесь ->
    AdapterView.OnItemClickListener
            caseOnItemClickListener,
            moboOnItemClickListener,
            cpuOnItemClickListener,
            coolerOnItemClickListener,
            ramOnItemClickListener,
            gpuOnItemClickListener,
            dataOnItemClickListener,
            psuOnItemClickListener;

    void logic(){
        caseOnItemClickListener = (parent, view, position, id) -> {
            removeAll();
            caseParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+ PcComponent.CASE +"/" + caseList.get(position) + ".json"),typeToken.getType());
            caseView.setBackground(assetFile.getImage("pc_component/images/"+ PcComponent.CASE +"/" + caseList.get(position) + ".png"));
            caseList.add(parametersSave.Case);
            parametersSave.setCase(caseList.get(position),caseParameters);
            caseList.remove(position);
            caseInstall = true;
            caseAdapter.notifyDataSetChanged();
        };
        moboOnItemClickListener = (parent, view, position, id) -> {
            if (caseInstall) {
                removeMobo();
                moboView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.Motherboard+"/" + moboList.get(position) + ".png"));
                MotherBoardView motherBoardView = new MotherBoardView(moboList.get(position), caseView, getBaseContext());
                moboParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.Motherboard+"/" + moboList.get(position) + ".json"), typeToken.getType());
                moboList.add(parametersSave.Mobo);
                parametersSave.setMobo(moboList.get(position),moboParameters);
                moboList.remove(position);
                ramSlotCount = Integer.parseInt(moboParameters.get("Кол-во слотов"));
                dataSlotCount = Integer.parseInt(moboParameters.get("Портов SATA"));
                moboInstall = true;
                Others.clearEmpty(moboList);
                moboAdapter.notifyDataSetChanged();
            }
        };
        cpuOnItemClickListener = (parent, view, position, id) -> {
                if(moboInstall) {
                    cpuParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.CPU+"/" + cpuList.get(position) + ".json"), typeToken.getType());
                    if (cpuParameters.get("Сокет").equals(moboParameters.get("Сокет"))) {
                        cpuView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.CPU+"/" + cpuList.get(position) + ".png"));
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
                    Others.clearEmpty(coolerList);
                    Others.clearEmpty(cpuList);
                    coolerAdapter.notifyDataSetChanged();
                    cpuAdapter.notifyDataSetChanged();
                }
            };
        coolerOnItemClickListener = (parent, view, position, id) -> {
            if (moboInstall) {
                coolerParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/" + PcComponent.COOLER + "/" + coolerList.get(position) + ".json"), typeToken.getType());
                coolerView.setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.COOLER + "/" + coolerList.get(position) + ".png"));
                coolerList.add(parametersSave.Cooler);
                parametersSave.setCooler(coolerList.get(position), coolerParameters);
                coolerList.remove(position);
                coolerInstall = true;
                Others.clearEmpty(coolerList);
                coolerAdapter.notifyDataSetChanged();
            }
        };
        ramOnItemClickListener = (parent, view, position, id) -> {
            typeForInstall = "RAM";
            nameForInstall = ramList.get(position);
        };
        gpuOnItemClickListener = (parent, view, position, id) -> {
                if(moboInstall){
                    int countPci = Integer.parseInt(moboParameters.get("Слотов PCI"));
                    if(countPci>1){
                        typeForInstall = "GPU";
                        nameForInstall = gpuList.get(position);
                    }
                    else{
                        gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" +gpuList.get(position)+"_horizontal.png"));
                        gpu1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.GPU+"/" +gpuList.get(position)+".json"),typeToken.getType());
                        gpuList.add(parametersSave.Gpu1);
                        parametersSave.setGpu1(gpuList.get(position),gpu1Parameters);
                        gpuList.remove(position);
                        gpuInstall = true;
                        Others.clearEmpty(gpuList);
                        gpuAdapter.notifyDataSetChanged();
                    }
                }
            };
        dataOnItemClickListener = (parent, view, position, id) -> {
            typeForInstall = "DATA";
            nameForInstall = dataList.get(position);
        };
        psuOnItemClickListener = (parent, view, position, id) -> {
            psuParameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.PowerSupply+"/" +psuList.get(position)+".json"),typeToken.getType());
            psuView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.PowerSupply+"/" +psuList.get(position)+".png"));
            psuList.add(parametersSave.Psu);
            parametersSave.setPsu(psuList.get(position),psuParameters);
            psuList.remove(position);
            psuInstall = true;
            Others.clearEmpty(psuList);
            psuAdapter.notifyDataSetChanged();
        };
        button1.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("RAM") && moboInstall) {
                    ram1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + nameForInstall + "_top.png"));
                    ramList.add(parametersSave.Ram1);
                    ram1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.RAM+"/" + nameForInstall + ".json"), typeToken.getType());
                    if(moboParameters.get("Тип памяти").equals(ram1Parameters.get("Тип памяти"))) {
                        parametersSave.setRam1(nameForInstall, ram1Parameters);
                    }
                    else{
                        parametersSave.setRam1(nameForInstall, null);
                    }
                    ramList.remove(nameForInstall);
                }
                if (typeForInstall.equals("GPU") && moboInstall) {
                    gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + nameForInstall + "_horizontal.png"));
                    gpu1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.GPU+"/" + nameForInstall + ".json"), typeToken.getType());
                    gpuList.add(parametersSave.Gpu1);
                    parametersSave.setGpu1(nameForInstall, gpu1Parameters);
                    gpuList.remove(nameForInstall);
                    gpuInstall = true;
                }
                if (typeForInstall.equals("DATA") && caseInstall) {
                    data1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data1);
                    parametersSave.setData1(nameForInstall, data1Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });
        button2.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("RAM") && moboInstall) {
                    ram2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + nameForInstall + "_top.png"));
                    ramList.add(parametersSave.Ram2);
                    ram2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.RAM+"/" + nameForInstall + ".json"), typeToken.getType());
                    if(moboParameters.get("Тип памяти").equals(ram2Parameters.get("Тип памяти"))) {
                        parametersSave.setRam2(nameForInstall, ram2Parameters);
                    }
                    else{
                        parametersSave.setRam2(nameForInstall, null);
                    }
                    ramList.remove(nameForInstall);
                }
                if (typeForInstall.equals("GPU") && moboInstall) {
                    gpu2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + nameForInstall + "_horizontal.png"));
                    gpu2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.GPU+"/" + nameForInstall + ".json"), typeToken.getType());
                    gpuList.add(parametersSave.Gpu2);
                    parametersSave.setGpu2(nameForInstall, gpu2Parameters);
                    gpuList.remove(nameForInstall);
                }
                if (typeForInstall.equals("DATA") && caseInstall) {
                    data2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data2Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data2);
                    parametersSave.setData2(nameForInstall, data2Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });
        button3.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("RAM") && moboInstall) {
                    if (ramSlotCount >= 3) {
                        ram3View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + nameForInstall + "_top.png"));
                        ramList.add(parametersSave.Ram3);
                        ram3Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.RAM+"/" + nameForInstall + ".json"), typeToken.getType());
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
                    data3View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data3Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data3);
                    parametersSave.setData3(nameForInstall, data3Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });
        button4.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("RAM") && moboInstall) {
                    if (ramSlotCount >= 4) {
                        ram4View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + nameForInstall + "_top.png"));
                        ramList.add(parametersSave.Ram4);
                        ram4Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.RAM+"/" + nameForInstall + ".json"), typeToken.getType());
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
                    data4View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data4Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data4);
                    parametersSave.setData4(nameForInstall, data4Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });
        button5.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("DATA") && caseInstall) {
                    data5View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data5Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data5);
                    parametersSave.setData5(nameForInstall, data5Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });
        button6.setOnClickListener(v -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("DATA") && caseInstall) {
                    data6View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    data6Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    dataList.add(parametersSave.Data6);
                    parametersSave.setData6(nameForInstall, data6Parameters);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = null;
            }
        });

        caseButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(caseAdapter, caseOnItemClickListener);
            popupWindow.showAsDropDown(v, -5, 0);
        });
        moboButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(moboAdapter, moboOnItemClickListener);
            popupWindow.showAsDropDown(v, -5, 0);
        });
        cpuButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(cpuAdapter,cpuOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        coolerButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(coolerAdapter,coolerOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        ramButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(ramAdapter,ramOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        gpuButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(gpuAdapter,gpuOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
        dataButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(dataAdapter,dataOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
        psuButton.setOnClickListener(v->{
            PopupWindow popupWindow = popupWindow(psuAdapter,psuOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
    }
    //сборка сохранённого пк и получение данных
    void getSaveData(){
        caseList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.PcCaseList))));
        moboList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.MotherboardList))));
        cpuList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.CpuList))));
        coolerList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.CoolerList))));
        ramList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.RamList))));
        gpuList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.GraphicsCardList))));
        dataList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.StorageDeviceList))));
        psuList = Others.filter(Others.clearEmpty(new ArrayList<>(Arrays.asList(playerData.PowerSupplyList))));

        caseView.setBackground(assetFile.getImage("pc_component/images/"+PcComponent.CASE+"/" + parametersSave.Case + ".png"));
        caseInstall = parametersSave.Case != null;
        moboParameters = parametersSave.MOBO;
        if(moboParameters != null) {
            ramSlotCount = Integer.parseInt(moboParameters.get("Кол-во слотов"));
            moboInstall = parametersSave.Mobo != null;
            MotherBoardView motherBoardView = new MotherBoardView(parametersSave.Mobo,caseView,this);
            moboView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.Motherboard+"/" + parametersSave.Mobo + ".png"));
            if(parametersSave.CPU != null) {
                cpuView.setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.CPU + "/" + parametersSave.Cpu + ".png"));
            }
            coolerView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.COOLER+"/" + parametersSave.Cooler + ".png"));
            ram1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram1 + "_top.png"));
            ram2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram2 + "_top.png"));
            ram3View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram3 + "_top.png"));
            ram4View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram4 + "_top.png"));
            gpu1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + parametersSave.Gpu1 + "_horizontal.png"));
            gpu2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + parametersSave.Gpu2 + "_horizontal.png"));
        }
        data1View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data1+"_h.png"));
        data2View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data2+"_h.png"));
        data3View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data3+"_h.png"));
        data4View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data4+"_h.png"));
        data5View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data5+"_h.png"));
        data6View.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data6+"_h.png"));
        psuView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.PowerSupply+"/" +parametersSave.Psu+".png"));
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

        Others.clearEmpty(cpuList);
        Others.clearEmpty(coolerList);
        Others.clearEmpty(ramList);
        Others.clearEmpty(gpuList);
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

        Others.clearEmpty(moboList);
        Others.clearEmpty(cpuList);
        Others.clearEmpty(coolerList);
        Others.clearEmpty(ramList);
        Others.clearEmpty(gpuList);
        Others.clearEmpty(dataList);
        Others.clearEmpty(psuList);
    }

    public ArrayAdapter<String> CustomSpinnerPc(final ArrayList<String> array, final String text) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_pc_component, array) {
            final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(IronActivity.this);
                View v = layoutInflater.inflate(R.layout.item_pc_component,null);
                v.setBackgroundResource(R.color.background2);
                ImageView imageView = v.findViewById(R.id.image);
                imageView.setImageDrawable(assetFile.getImage("pc_component/images/" + text + "/" + array.get(position) + ".png"));
                TextView textView = v.findViewById(R.id.text);
                textView.setTypeface(font, Typeface.BOLD);
                textView.setText(array.get(position));
                return v;
            }
        };
        return adapter;
    }
    public PopupWindow popupWindow(ArrayAdapter<String> adapter, AdapterView.OnItemClickListener onItemClickListener) {
        PopupWindow popupWindow = new PopupWindow(this);
        ListView listViewDogs = new ListView(this);
        listViewDogs.setBackgroundResource(R.color.background2);
        listViewDogs.setAdapter(adapter);
        listViewDogs.setOnItemClickListener(onItemClickListener);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setContentView(listViewDogs);
        return popupWindow;
    }

    @Override
    public void onBackPressed() {
        playerData.PcCaseList = StringArrayWork.ArrayListToArray(caseList);
        playerData.MotherboardList = StringArrayWork.ArrayListToArray(moboList);
        playerData.CpuList = StringArrayWork.ArrayListToArray(cpuList);
        playerData.CoolerList =StringArrayWork.ArrayListToArray(coolerList);
        playerData.RamList = StringArrayWork.ArrayListToArray(ramList);
        playerData.GraphicsCardList = StringArrayWork.ArrayListToArray(gpuList);
        playerData.StorageDeviceList = StringArrayWork.ArrayListToArray(dataList);
        playerData.PowerSupplyList = StringArrayWork.ArrayListToArray(psuList);
        playerData.setAllData();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}