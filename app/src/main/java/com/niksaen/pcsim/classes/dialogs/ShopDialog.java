package com.niksaen.pcsim.classes.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.Settings;

import java.util.HashMap;

public class ShopDialog {
    private Context context;
    private String name;
    public ShopDialog(Context context){
        this.context = context;
    }

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+new Settings(context).Language+".json"),typeToken.getType());
    }

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button ButtonBuy,ButtonCancel;
    public View.OnClickListener buyClickAction;
    public void create(PcComponent pcComponent){
        getLanguage();
        name = pcComponent.Name;
        builder = new AlertDialog.Builder(context);
        View main = LayoutInflater.from(context).inflate(R.layout.dialog_shop,null);
        TextView Title = main.findViewById(R.id.title);
        TextView Text = main.findViewById(R.id.text);
        ImageView Image = main.findViewById(R.id.image);
        ButtonBuy = main.findViewById(R.id.buy);
        ButtonCancel = main.findViewById(R.id.cancel_button);

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf");
        Title.setTypeface(font,Typeface.BOLD);
        Text.setTypeface(font);
        ButtonBuy.setTypeface(font,Typeface.BOLD);
        ButtonCancel.setTypeface(font,Typeface.BOLD);

        Title.setText(pcComponent.Name);
        Text.setText(parser(pcComponent.Parameters,pcComponent.Type));
        Image.setImageDrawable(pcComponent.Textures);
        ButtonBuy.setText(words.get("Add to cart"));
        ButtonCancel.setText(words.get("Cancel"));

        Settings settings = new Settings(context);
        if(settings.Theme == "Dark"){
            main.setBackgroundColor(Color.parseColor("#111111"));
            Title.setTextColor(Color.WHITE);
            Text.setTextColor(Color.WHITE);
            ButtonBuy.setTextColor(Color.WHITE);
            ButtonBuy.setBackgroundColor(Color.parseColor("#1C1C1C"));
            ButtonCancel.setTextColor(Color.WHITE);
            ButtonCancel.setBackgroundColor(Color.parseColor("#1C1C1C"));
        }

        builder.setView(main);
        builder.setCancelable(false);
    }
    public void dismiss(){
        dialog.dismiss();
    }
    public void show(){
        dialog = builder.create();
        ButtonBuy.setOnClickListener(buyClickAction);
        ButtonCancel.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

    private String parser(HashMap<String,String> parameters,String type){
        String result = "";
        switch (type){
            case PcComponent.CASE:{
                result =
                        words.get("Ports") +" SATA: "+parameters.get("DATA")+"\n"+
                        words.get("Color")+": "+parameters.get("Цвет")+"\n";
                break;
            }
            case PcComponent.Motherboard:{
                result = words.get("Socket") + ": " +parameters.get("Сокет")+"\n"+
                        words.get("Memory type")+": "+parameters.get("Тип памяти")+"\n"+
                        words.get("Number of channels")+": "+parameters.get("Кол-во каналов")+"\n"+
                        words.get("Number of slots")+": "+parameters.get("Кол-во слотов")+"\n"+
                        words.get("Minimum frequency")+": "+parameters.get("Мин. частота")+"MHz\n"+
                        words.get("Maximum frequency")+": "+parameters.get("Макс. частота")+"MHz\n"+
                        words.get("Maximum volume")+": "+parameters.get("Макс. объём")+"Gb\n"+
                        words.get("Ports") +" SATA: "+ parameters.get("Портов SATA")+"\n"+
                        words.get("Slots") + " PCI: " + parameters.get("Слотов PCI") +"\n"+
                        words.get("Power") + ": " +parameters.get("Мощность")+"W\n";
                break;
            }
            case PcComponent.CPU:{
                result = words.get("Socket") + ": " +parameters.get("Сокет")+"\n"+
                        words.get("Technological process")+": " +parameters.get("Техпроцесс")+"Nm\n"+
                        words.get("Number of cores")+": "+parameters.get("Кол-во ядер")+"\n"+
                        words.get("Number of threads")+": "+parameters.get("Кол-во потоков")+"\n"+
                        words.get("Cache") + ": "+parameters.get( "Кэш")+"Mb\n"+
                        words.get("Frequency")+": "+parameters.get("Частота")+"MHz\n"+
                        words.get("Overclocking capability")+": "+parameters.get("Возможность разгона")+"\n"+
                        words.get("Power") + ": " +parameters.get("Мощность")+"W\n"+
                        words.get("Heat dissipation")+": "+parameters.get("TDP")+"W\n\n"+
                        words.get("RAM characteristics")+"\n"+
                        words.get("Memory type")+": "+parameters.get("Тип памяти")+"\n"+
                        words.get("Maximum volume")+": "+parameters.get("Макс. объём")+"Gb\n"+
                        words.get("Number of channels")+": "+parameters.get("Кол-во каналов")+"\n"+
                        words.get("Minimum frequency")+": "+parameters.get("Мин. частота")+"MHz\n"+
                        words.get("Maximum frequency")+": " + parameters.get("Макс. частота")+"MHz\n\n"+
                        words.get("Integrated graphics core")+": "+parameters.get( "Графическое ядро")+"\n";
                if(parameters.get( "Графическое ядро").equals("+")){
                    result += words.get("Model")+": " + parameters.get("Модель")+"\n"+
                            words.get("Frequency")+": " + parameters.get("Частота GPU")+"MHz\n";
                }
                break;
            }
            case PcComponent.COOLER:{
                result = words.get("Power dissipation")+": "+parameters.get("TDP")+"W\n"+
                        words.get("Power")+": "+parameters.get("Мощность")+"W\n";
                break;
            }
            case PcComponent.RAM:{
                result = words.get("Memory type")+": "+parameters.get("Тип памяти")+"\n"+
                        words.get( "Volume")+": "+parameters.get( "Объём")+"Gb\n"+
                        words.get("Frequency")+": "+parameters.get("Частота")+"MHz\n"+
                        words.get("Throughput")+": "+parameters.get("Пропускная способность")+"PC\n"+
                        words.get("Power") + ": " + PcParametersSave.RamPower(parameters) +"W\n";
                break;
            }
            case PcComponent.GPU:{
                result = words.get("GPU")+": "+parameters.get("Графический процессор")+"\n"+
                        words.get("Number of video chips")+": "+parameters.get("Кол-во видеочипов")+"\n"+
                        words.get("Frequency")+": "+parameters.get("Частота")+"MHz\n"+
                        words.get("Memory type")+":"+parameters.get("Тип памяти")+"\n"+
                        words.get("Video memory size")+": "+parameters.get("Объём видеопамяти")+"Gb\n"+
                        words.get("Throughput")+": "+parameters.get("Пропускная способность")+"Gb/s\n"+
                        words.get("Cooling type") + ": "+ words.get(parameters.get("Тип охлаждения"))+"\n"+
                        words.get("Power") + ": " + PcParametersSave.GpuPower(parameters) +"W\n";
                break;
            }
            case PcComponent.StorageDevice:{
                result = words.get("Volume")+": "+parameters.get("Объём")+"Gb\n"+
                        words.get("Type")+": "+parameters.get("Тип")+"\n"+
                        words.get("Power")+": "+parameters.get("Мощность")+"W\n";
                break;
            }
            case PcComponent.PowerSupply:{
                result = words.get("Power")+": "+parameters.get("Мощность")+"W\n"+
                        words.get("Protection")+": "+parameters.get("Защита")+"\n";
                break;
            }
            case PcComponent.Disk:{
                result += words.get(name+":Description")+"\n";
                break;
            }
        }

        result += "\n"+words.get("Price")+": "+parameters.get("Цена")+"R";
        return result;
    }
}
