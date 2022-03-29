package com.niksaen.pcsim.activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.fileWorkLib.FileUtil;
import com.niksaen.pcsim.pcView.MotherBoardView;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.PlayerData;
import com.niksaen.pcsim.save.Settings;
import com.niksaen.pcsim.save.StyleSave;

import java.io.File;
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
    String typeForInstall,nameForInstall;

    //view
    Button caseButton,moboButton,cpuButton,coolerButton,ramButton,gpuButton,dataButton,psuButton;

    RelativeLayout caseView;
    ImageView moboView,cpuView,coolerView,psuView;
    ArrayList<ImageView> ramView,gpuView,driveView;

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
        ramView = new ArrayList<>();
        gpuView = new ArrayList<>();
        driveView = new ArrayList<>();

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
        ramView.add(findViewById(R.id.ramImage1));
        ramView.add(findViewById(R.id.ramImage2));
        ramView.add(findViewById(R.id.ramImage3));
        ramView.add(findViewById(R.id.ramImage4));
        gpuView.add(findViewById(R.id.gpu1View));
        gpuView.add(findViewById(R.id.gpu2View));
        driveView.add(findViewById(R.id.data1View));
        driveView.add(findViewById(R.id.data2View));
        driveView.add(findViewById(R.id.data3View));
        driveView.add(findViewById(R.id.data4View));
        driveView.add(findViewById(R.id.data5View));
        driveView.add(findViewById(R.id.data6View));
        psuView = findViewById(R.id.psuView);
    }
    //стили для view
    ArrayAdapter<String> caseAdapter, moboAdapter, cpuAdapter, coolerAdapter, ramAdapter, gpuAdapter, dataAdapter, psuAdapter;
    private void style(){
        caseAdapter = CustomSpinnerPc(caseList,PcComponent.CASE);
        moboAdapter = CustomSpinnerPc(moboList,PcComponent.Motherboard);
        cpuAdapter = CustomSpinnerPc(cpuList,PcComponent.CPU);
        coolerAdapter = CustomSpinnerPc(coolerList,PcComponent.COOLER);
        ramAdapter = CustomSpinnerPc(ramList,PcComponent.RAM);
        gpuAdapter = CustomSpinnerPc(gpuList,PcComponent.GPU);
        dataAdapter = CustomSpinnerPc(dataList,PcComponent.StorageDevice);
        psuAdapter = CustomSpinnerPc(psuList,PcComponent.PowerSupply);

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
            selectSlot();
        };
        gpuOnItemClickListener = (parent, view, position, id) -> {
                if(moboInstall){
                    if(Integer.parseInt(moboParameters.get("Слотов PCI"))>1){
                        typeForInstall = "GPU";
                        nameForInstall = gpuList.get(position);
                        selectSlot();
                    }
                    else{
                        gpuView.get(0).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" +gpuList.get(position)+"_horizontal.png"));
                        gpu1Parameters = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.GPU+"/" +gpuList.get(position)+".json"),typeToken.getType());
                        gpuList.add(parametersSave.Gpu1);
                        parametersSave.setGpu1(gpuList.get(position),gpu1Parameters);
                        gpuList.remove(position);
                        gpuInstall = true;
                        gpuList = Others.clearEmpty(gpuList);
                        gpuAdapter.notifyDataSetChanged();
                    }
                }
            };
        dataOnItemClickListener = (parent, view, position, id) -> {
            typeForInstall = "DATA";
            nameForInstall = dataList.get(position);
            selectSlot();
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

        caseButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.PcCaseList));
            PopupWindow popupWindow = popupWindow(caseAdapter, caseOnItemClickListener);
            popupWindow.showAsDropDown(v, -5, 0);
        });
        moboButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.MotherboardList));
            PopupWindow popupWindow = popupWindow(moboAdapter, moboOnItemClickListener);
            popupWindow.showAsDropDown(v, -5, 0);
        });
        cpuButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.CpuList));
            PopupWindow popupWindow = popupWindow(cpuAdapter,cpuOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        coolerButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.CoolerList));
            PopupWindow popupWindow = popupWindow(coolerAdapter,coolerOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        ramButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.RamList));
            PopupWindow popupWindow = popupWindow(ramAdapter,ramOnItemClickListener);
            popupWindow.showAsDropDown(v,-5,0);
        });
        gpuButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.GraphicsCardList));
            PopupWindow popupWindow = popupWindow(gpuAdapter,gpuOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
        dataButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.StorageDeviceList));
            PopupWindow popupWindow = popupWindow(dataAdapter,dataOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
        psuButton.setOnClickListener(v->{
            System.out.println(Arrays.toString(playerData.PowerSupplyList));
            PopupWindow popupWindow = popupWindow(psuAdapter,psuOnItemClickListener);
            popupWindow.showAsDropDown(ramButton,-5,0);
        });
    }
    //получение данных
    void getSaveData(){
        caseList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.PcCaseList)));
        moboList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.MotherboardList)));
        cpuList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.CpuList)));
        coolerList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.CoolerList)));
        ramList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.RamList)));
        gpuList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.GraphicsCardList)));
        dataList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.StorageDeviceList)));
        psuList = new ArrayList<>(Arrays.asList(StringArrayWork.clearEmpty(playerData.PowerSupplyList)));

        caseInstall = parametersSave.Case != null;
        moboParameters = parametersSave.MOBO;

        caseView.setBackground(assetFile.getImage("pc_component/images/"+PcComponent.CASE+"/" + parametersSave.Case + ".png"));
        psuView.setBackground(assetFile.getImage("pc_component/images/"+PcComponent.PowerSupply+"/" +parametersSave.Psu+".png"));

        if(moboParameters != null) {
            moboInstall = parametersSave.Mobo != null;
            MotherBoardView motherBoardView = new MotherBoardView(parametersSave.Mobo,caseView,this);
            moboView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.Motherboard+"/" + parametersSave.Mobo + ".png"));
            if(parametersSave.CPU != null) cpuView.setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.CPU + "/" + parametersSave.Cpu + ".png"));
            coolerView.setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.COOLER+"/" + parametersSave.Cooler + ".png"));
            ramView.get(0).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram1 + "_top.png"));
            ramView.get(1).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + parametersSave.Ram2 + "_top.png"));
            gpuView.get(0).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + parametersSave.Gpu1 + "_horizontal.png"));

            if(ramView.size()==4) {
                ramView.get(2).setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.RAM + "/" + parametersSave.Ram3 + "_top.png"));
                ramView.get(3).setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.RAM + "/" + parametersSave.Ram4 + "_top.png"));
            }
            if(gpuView.size()==2) {
                gpuView.get(1).setImageDrawable(assetFile.getImage("pc_component/images/" + PcComponent.GPU + "/" + parametersSave.Gpu2 + "_horizontal.png"));
            }
        }
        driveView.get(0).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data1+"_h.png"));
        driveView.get(1).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data2+"_h.png"));
        driveView.get(2).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data3+"_h.png"));
        driveView.get(3).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data4+"_h.png"));
        driveView.get(4).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data5+"_h.png"));
        driveView.get(5).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" +parametersSave.Data6+"_h.png"));

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
        ramView.get(0).setImageDrawable(null);
        ramView.get(1).setImageDrawable(null);
        gpuView.get(0).setImageDrawable(null);

        if(ramView.size()==4) {
            ramView.get(2).setImageDrawable(null);
            ramView.get(3).setImageDrawable(null);
        }
        if(gpuView.size()==2) {
            gpuView.get(1).setImageDrawable(null);
        }

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

        removeMobo();

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
        ramView.get(0).setImageDrawable(null);
        ramView.get(1).setImageDrawable(null);
        gpuView.get(0).setImageDrawable(null);

        if(ramView.size()==4) {
            ramView.get(2).setImageDrawable(null);
            ramView.get(3).setImageDrawable(null);
        }
        if(gpuView.size()==2) {
            gpuView.get(1).setImageDrawable(null);
        }
        driveView.get(0).setImageDrawable(null);
        driveView.get(1).setImageDrawable(null);
        driveView.get(2).setImageDrawable(null);
        driveView.get(3).setImageDrawable(null);
        driveView.get(4).setImageDrawable(null);
        driveView.get(5).setImageDrawable(null);
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

    public void selectSlot(){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // view
        LinearLayout main = new LinearLayout(this);
        ListView slots = new ListView(this);
        TextView title = new TextView(this);
        ArrayList<String> slotList = new ArrayList<>();


        //set slot count
        if(typeForInstall.equals("RAM")){
            for(int i = 1;i<=Integer.parseInt(parametersSave.MOBO.get("Кол-во слотов"));i++){
                slotList.add(words.get("Slot")+" "+i);
            }
        }
        if(typeForInstall.equals("GPU")){
            for(int i = 1;i<=Integer.parseInt(parametersSave.MOBO.get("Слотов PCI"));i++){
                slotList.add(words.get("Slot")+" "+i);
            }
        }
        if(typeForInstall.equals("DATA")) {
            for (int i = 1; i <= Integer.parseInt(parametersSave.MOBO.get("Портов SATA")); i++) {
                slotList.add(words.get("Slot") + " " + i);
            }
        }

        //style
        title.setTypeface(font);
        title.setTextSize(45);
        title.setTextColor(Color.parseColor("#FFFFFF"));
        title.setText(words.get("Choose a slot:"));
        title.setPadding(8,8,8,24);
        title.setGravity(Gravity.CENTER_HORIZONTAL);

        CustomListViewAdapter adapter = new CustomListViewAdapter(this,0,slotList);
        adapter.TextColor = Color.parseColor("#FFFFFF");
        adapter.BackgroundColor2 = Color.parseColor("#121212");
        adapter.TextStyle = Typeface.BOLD;
        adapter.TextSize = 40;
        slots.setAdapter(adapter);

        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(Color.parseColor("#121212"));
        main.setPadding(16,16,16,16);

        main.addView(title,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        main.addView(slots,LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        builder.setView(main);
        dialog = builder.create();

        //logic
        slots.setOnItemClickListener((parent, view, position, id) -> {
            if (typeForInstall != null) {
                if (typeForInstall.equals("RAM") && moboInstall) {
                    if (parametersSave.getRam(position) != null) ramList.add(parametersSave.getRam(position).get("Model"));
                    ramView.get(position).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.RAM+"/" + nameForInstall + "_top.png"));
                    HashMap<String,String >ram = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.RAM+"/" + nameForInstall + ".json"), typeToken.getType());
                    parametersSave.setRam(position, ram);
                    ramList.remove(nameForInstall);
                }
                if (typeForInstall.equals("GPU") && moboInstall) {
                    if (parametersSave.getGpu(position) != null) gpuList.add(parametersSave.getGpu(position).get("Model"));
                    gpuView.get(position).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.GPU+"/" + nameForInstall + "_horizontal.png"));
                    HashMap<String,String> gpu = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.GPU+"/" + nameForInstall + ".json"), typeToken.getType());
                    parametersSave.setGpu(position, gpu);
                    gpuList.remove(nameForInstall);
                    gpuInstall = true;
                }
                if (typeForInstall.equals("DATA") && caseInstall) {
                    if (parametersSave.getDrive(position) != null) dataList.add(parametersSave.getDrive(position).get("Model"));
                    driveView.get(position).setImageDrawable(assetFile.getImage("pc_component/images/"+PcComponent.StorageDevice+"/" + nameForInstall + "_h.png"));
                    HashMap<String,String> drive = new Gson().fromJson(assetFile.getText("pc_component/parameters/"+PcComponent.StorageDevice+"/" + nameForInstall + ".json"), typeToken.getType());
                    if(parametersSave.getDrive(position)!= null){
                        if(parametersSave.getDrive(position).get("Содержимое").contains("OS")){
                            StyleSave styleSave = new StyleSave(IronActivity.this);
                            styleSave.resetAllStyle();
                        }
                    }
                    drive.put("MainDisk","true");
                    drive.put("name",(char)(65+position)+":");
                    parametersSave.setDrive(position, drive);
                    dataList.remove(nameForInstall);
                }
                typeForInstall = "";
                nameForInstall = "";
            }
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}