package com.niksaen.pcsim.classes.pcComponents;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.classes.AssetFile;

import java.util.HashMap;

public class PcComponent {
    public final static String CASE = "Pc Case";
    public final static String Motherboard = "Motherboard";
    public final static String CPU = "CPU";
    public final static String COOLER = "Cooler";
    public final static String RAM = "RAM";
    public final static String GPU = "Graphics card";
    public final static String StorageDevice = "Storage device";
    public final static String PowerSupply = "Power supply";
    public final static String Disk = "Disk";

    public String Type;
    public String Name;
    public int Price;
    public HashMap<String,String> Parameters;
    public Drawable Textures;

    public PcComponent(String name, String type, HashMap<String,String> parameters, Drawable textures){
        this.Name = name;
        this.Type = type;
        this.Parameters = parameters;
        this.Textures = textures;

        this.Price = Integer.parseInt(parameters.get("Цена"));
    }
    public PcComponent(Context context, String name, String type){
        this.Name = name;
        this.Type = type;
        this.Parameters = new Gson().fromJson(new AssetFile(context).getText("pc_component/parameters/"+type+"/" + name + ".json"),new TypeToken<HashMap<String,String>>(){}.getType());
        this.Textures = new AssetFile(context).getImage("pc_component/images/"+type+"/" + name + ".png");

        this.Price = Integer.parseInt(Parameters.get("Цена"));
    }
}
