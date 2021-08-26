package com.niksaen.pcsim.shop;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.adapters.ShopAdapter;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.ItemClickSupport;
import com.niksaen.pcsim.classes.PcComponentLists;
import com.niksaen.pcsim.save.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class MainShopActivity extends AppCompatActivity {

    private Typeface font;
    private TextView moneyView;
    private RecyclerView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        getLanguage();
        initAdapters();
        initView();
        style();
        logic();
    }
    public HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(this).getText("language/"+new Settings(this).Language+".json"),typeToken.getType());
    }

    private void initView(){
        moneyView = findViewById(R.id.moneyView);
        main = findViewById(R.id.main);
    }
    private void style(){
        moneyView.setTypeface(font,Typeface.BOLD);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        main.setLayoutManager(layoutManager);
        main.setAdapter(baseAdapter);
    }

    ShopAdapter baseAdapter;
    private final ArrayList<ShopAdapter> adapters = new ArrayList<>();
    private void initAdapters(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("PC case");
        strings.add("Motherboard");
        strings.add("CPU");
        strings.add("Cooler");
        strings.add("RAM");
        strings.add("Graphics card");
        strings.add("Storage device");
        strings.add("Power supply");

        baseAdapter = new ShopAdapter(this,strings,"icon");
        adapters.add(new ShopAdapter(this, PcComponentLists.CaseList,"CASE"));
        adapters.add(new ShopAdapter(this, PcComponentLists.MotherboardList,"MOBO"));
        adapters.add(new ShopAdapter(this, PcComponentLists.CpuList,"CPU"));
        adapters.add(new ShopAdapter(this, PcComponentLists.CoolerList,"COOLER"));
        adapters.add(new ShopAdapter(this, PcComponentLists.RamList,"RAM"));
        adapters.add(new ShopAdapter(this, PcComponentLists.GraphicsCardList,"GPU"));
        adapters.add(new ShopAdapter(this, PcComponentLists.DataStorageList,"DATA"));
        adapters.add(new ShopAdapter(this, PcComponentLists.PowerSupplyList,"PSU"));
    }
    private void logic(){
        ItemClickSupport.addTo(main).setOnItemClickListener((recyclerView, position, v) -> {
            if(main.getAdapter() == baseAdapter){
                main.setAdapter(adapters.get(position));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(main.getAdapter() != baseAdapter){
            main.setAdapter(baseAdapter);
        }else {
            super.onBackPressed();
        }
    }
}