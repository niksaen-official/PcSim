package com.niksaen.pcsim.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.ItemClickSupport;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CartAdapters;
import com.niksaen.pcsim.classes.adapters.ShopAdapter;
import com.niksaen.pcsim.classes.dialogs.Dialog;
import com.niksaen.pcsim.classes.dialogs.DialogCheque;
import com.niksaen.pcsim.classes.dialogs.ShopDialog;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.classes.pcComponents.PcComponentLists;
import com.niksaen.pcsim.save.PlayerData;
import com.niksaen.pcsim.save.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class MainShopActivity extends AppCompatActivity {

    private Typeface font;
    private TextView moneyView,cartTitle;
    private Button buy,back;
    private RecyclerView main;
    private RecyclerView cartView;
    PlayerData playerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");
        playerData = new PlayerData(this);

        getLanguage();
        initAdapters();
        initView();
        style();
        logic();
        updateMoneyView();
    }
    public HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(this).getText("language/"+new Settings(this).Language+".json"),typeToken.getType());
    }

    private void initView(){
        moneyView = findViewById(R.id.moneyView);
        main = findViewById(R.id.main);
        cartView = findViewById(R.id.cart);
        cartTitle = findViewById(R.id.cartTitle);
        buy = findViewById(R.id.buttonBuy);
        back = findViewById(R.id.back);
    }
    private void style(){
        moneyView.setTypeface(font,Typeface.BOLD);
        cartTitle.setTypeface(font,Typeface.BOLD);
        buy.setTypeface(font,Typeface.BOLD);;
        back.setTypeface(font,Typeface.BOLD);;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        main.setLayoutManager(layoutManager);
        main.setAdapter(baseAdapter);

        cartTitle.setText(words.get("Cart"));
        buy.setText(words.get("Buy"));
        back.setText(words.get("Back"));
    }

    ShopAdapter baseAdapter;
    CartAdapters cartAdapters;
    private final ArrayList<ShopAdapter> adapters = new ArrayList<>();
    private void initAdapters(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add(PcComponent.CASE);
        strings.add(PcComponent.Motherboard);
        strings.add(PcComponent.CPU);
        strings.add(PcComponent.COOLER);
        strings.add(PcComponent.RAM);
        strings.add(PcComponent.GPU);
        strings.add(PcComponent.StorageDevice);
        strings.add(PcComponent.PowerSupply);
        strings.add(PcComponent.Disk);

        baseAdapter = new ShopAdapter(this,strings,"icon");
        adapters.add(new ShopAdapter(this, PcComponentLists.CaseList,PcComponent.CASE));
        adapters.add(new ShopAdapter(this, PcComponentLists.MotherboardList,PcComponent.Motherboard));
        adapters.add(new ShopAdapter(this, PcComponentLists.CpuList,PcComponent.CPU));
        adapters.add(new ShopAdapter(this, PcComponentLists.CoolerList,PcComponent.COOLER));
        adapters.add(new ShopAdapter(this, PcComponentLists.RamList,PcComponent.RAM));
        adapters.add(new ShopAdapter(this, PcComponentLists.GraphicsCardList,PcComponent.GPU));
        adapters.add(new ShopAdapter(this, PcComponentLists.DataStorageList,PcComponent.StorageDevice));
        adapters.add(new ShopAdapter(this, PcComponentLists.PowerSupplyList,PcComponent.PowerSupply));
        adapters.add(new ShopAdapter(this, PcComponentLists.DiskList,PcComponent.Disk));
    }

    private void updateCart(){
        cartAdapters = new CartAdapters(MainShopActivity.this,componentArrayList);
        cartView.setAdapter(cartAdapters);
    }
    private void updateMoneyView(){
        moneyView.setText(playerData.Money+"R");
    }

    private final ArrayList<PcComponent> componentArrayList = new ArrayList<>();

    private void logic(){
        //add item to cart
        ItemClickSupport.addTo(main).setOnItemClickListener((recyclerView, position, v) -> {
            if(main.getAdapter() == baseAdapter){
                main.setAdapter(adapters.get(position));
            }else {
                ShopDialog dialog = new ShopDialog(MainShopActivity.this);
                String item = ((ShopAdapter)main.getAdapter()).getItem(position);
                PcComponent buff = new PcComponent(MainShopActivity.this, item, ((ShopAdapter)main.getAdapter()).getType());

                dialog.buyClickAction = v1 -> {
                    componentArrayList.add(buff);
                    updateCart();
                    dialog.dismiss();
                };
                dialog.create(buff);
                dialog.show();
            }
        });

        //remove item from cart
        ItemClickSupport.addTo(cartView).setOnItemClickListener((recyclerView, position, v) -> {
           Dialog dialog = new Dialog(MainShopActivity.this);
           dialog.setTitleText(words.get("Removing an item"));
           dialog.setText(words.get("Do you want to remove this item from your cart?"));
           dialog.setButtonOkText(words.get("Yes"));
           dialog.setButtonCancelText(words.get("No"));

           dialog.setTitleVisible(true);
           dialog.setButtonOkVisible(true);
           dialog.setButtonCancelVisible(true);
           dialog.setCancelable(false);

           dialog.setButtonOkOnClick(v1 -> {
               componentArrayList.remove(position);
               updateCart();
               dialog.dismiss();
           });
           dialog.create();
           dialog.show();
        });

        //buying
        buy.setOnClickListener(v -> {
            DialogCheque cheque = new DialogCheque(MainShopActivity.this);
            cheque.BuyClickListener = v1 -> {
                if(playerData.Money - cheque.getAllPrice(componentArrayList) >= 0){
                    // buying all components
                    for(PcComponent pcComponent: componentArrayList){
                        switch (pcComponent.Type){
                            case PcComponent.CASE:{
                                playerData.PcCaseList = StringArrayWork.add(playerData.PcCaseList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.Motherboard:{
                                playerData.MotherboardList = StringArrayWork.add(playerData.MotherboardList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.CPU:{
                                playerData.CpuList = StringArrayWork.add(playerData.CpuList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.COOLER:{
                                playerData.CoolerList = StringArrayWork.add(playerData.CoolerList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.RAM:{
                                playerData.RamList = StringArrayWork.add(playerData.RamList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.GPU:{
                                playerData.GraphicsCardList = StringArrayWork.add(playerData.GraphicsCardList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.StorageDevice:{
                                playerData.StorageDeviceList = StringArrayWork.add(playerData.StorageDeviceList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.PowerSupply:{
                                playerData.PowerSupplyList = StringArrayWork.add(playerData.PowerSupplyList,pcComponent.Name);
                                break;
                            }
                            case PcComponent.Disk:{
                                playerData.DiskSoftList = StringArrayWork.add(playerData.DiskSoftList,pcComponent.Name);
                                break;
                            }
                        }
                    }

                    playerData.Money -= cheque.getAllPrice(componentArrayList);
                    playerData.setAllData();
                    componentArrayList.clear();
                    cheque.dismiss();
                    updateCart();
                    updateMoneyView();
                }
            };
            cheque.show(componentArrayList);
        });
    }

    public void BackButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        this.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        if(main.getAdapter() != baseAdapter){
            main.setAdapter(baseAdapter);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }
    }
}