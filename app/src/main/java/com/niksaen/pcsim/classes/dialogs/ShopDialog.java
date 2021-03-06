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

        main.setBackgroundColor(Color.parseColor("#111111"));
        Title.setTextColor(Color.WHITE);
        Text.setTextColor(Color.WHITE);
        ButtonBuy.setTextColor(Color.WHITE);
        ButtonBuy.setBackgroundColor(Color.parseColor("#1C1C1C"));
        ButtonCancel.setTextColor(Color.WHITE);
        ButtonCancel.setBackgroundColor(Color.parseColor("#1C1C1C"));
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
                        words.get("Color")+": "+parameters.get("????????")+"\n";
                break;
            }
            case PcComponent.Motherboard:{
                result = words.get("Socket") + ": " +parameters.get("??????????")+"\n"+
                        words.get("Memory type")+": "+parameters.get("?????? ????????????")+"\n"+
                        words.get("Number of channels")+": "+parameters.get("??????-???? ??????????????")+"\n"+
                        words.get("Number of slots")+": "+parameters.get("??????-???? ????????????")+"\n"+
                        words.get("Minimum frequency")+": "+parameters.get("??????. ??????????????")+"MHz\n"+
                        words.get("Maximum frequency")+": "+parameters.get("????????. ??????????????")+"MHz\n"+
                        words.get("Maximum volume")+": "+parameters.get("????????. ??????????")+"Gb\n"+
                        words.get("Ports") +" SATA: "+ parameters.get("???????????? SATA")+"\n"+
                        words.get("Slots") + " PCI: " + parameters.get("???????????? PCI") +"\n"+
                        words.get("Power") + ": " +parameters.get("????????????????")+"W\n";
                break;
            }
            case PcComponent.CPU:{
                result = words.get("Socket") + ": " +parameters.get("??????????")+"\n"+
                        words.get("Technological process")+": " +parameters.get("????????????????????")+"Nm\n"+
                        words.get("Number of cores")+": "+parameters.get("??????-???? ????????")+"\n"+
                        words.get("Number of threads")+": "+parameters.get("??????-???? ??????????????")+"\n"+
                        words.get("Cache") + ": "+parameters.get( "??????")+"Mb\n"+
                        words.get("Frequency")+": "+parameters.get("??????????????")+"MHz\n"+
                        words.get("Overclocking capability")+": "+parameters.get("?????????????????????? ??????????????")+"\n"+
                        words.get("Power") + ": " +parameters.get("????????????????")+"W\n"+
                        words.get("Heat dissipation")+": "+parameters.get("TDP")+"W\n\n"+
                        words.get("RAM characteristics")+"\n"+
                        words.get("Memory type")+": "+parameters.get("?????? ????????????")+"\n"+
                        words.get("Maximum volume")+": "+parameters.get("????????. ??????????")+"Gb\n"+
                        words.get("Number of channels")+": "+parameters.get("??????-???? ??????????????")+"\n"+
                        words.get("Minimum frequency")+": "+parameters.get("??????. ??????????????")+"MHz\n"+
                        words.get("Maximum frequency")+": " + parameters.get("????????. ??????????????")+"MHz\n\n"+
                        words.get("Integrated graphics core")+": "+parameters.get( "?????????????????????? ????????")+"\n";
                if(parameters.get( "?????????????????????? ????????").equals("+")){
                    result += words.get("Model")+": " + parameters.get("????????????")+"\n"+
                            words.get("Frequency")+": " + parameters.get("?????????????? GPU")+"MHz\n";
                }
                break;
            }
            case PcComponent.COOLER:{
                result = words.get("Power dissipation")+": "+parameters.get("TDP")+"W\n"+
                        words.get("Power")+": "+parameters.get("????????????????")+"W\n";
                break;
            }
            case PcComponent.RAM:{
                result = words.get("Memory type")+": "+parameters.get("?????? ????????????")+"\n"+
                        words.get( "Volume")+": "+parameters.get( "??????????")+"Gb\n"+
                        words.get("Frequency")+": "+parameters.get("??????????????")+"MHz\n"+
                        words.get("Throughput")+": "+parameters.get("???????????????????? ??????????????????????")+"PC\n"+
                        words.get("Power") + ": " + PcParametersSave.RamPower(parameters) +"W\n";
                break;
            }
            case PcComponent.GPU:{
                result = words.get("GPU")+": "+parameters.get("?????????????????????? ??????????????????")+"\n"+
                        words.get("Number of video chips")+": "+parameters.get("??????-???? ????????????????????")+"\n"+
                        words.get("Frequency")+": "+parameters.get("??????????????")+"MHz\n"+
                        words.get("Memory type")+":"+parameters.get("?????? ????????????")+"\n"+
                        words.get("Video memory size")+": "+parameters.get("?????????? ??????????????????????")+"Gb\n"+
                        words.get("Throughput")+": "+parameters.get("???????????????????? ??????????????????????")+"Gb/s\n"+
                        words.get("Cooling type") + ": "+ words.get(parameters.get("?????? ????????????????????"))+"\n"+
                        words.get("Power") + ": " + PcParametersSave.GpuPower(parameters) +"W\n";
                break;
            }
            case PcComponent.StorageDevice:{
                result = words.get("Volume")+": "+parameters.get("??????????")+"Gb\n"+
                        words.get("Type")+": "+parameters.get("??????")+"\n"+
                        words.get("Power")+": "+parameters.get("????????????????")+"W\n";
                break;
            }
            case PcComponent.PowerSupply:{
                result = words.get("Power")+": "+parameters.get("????????????????")+"W\n"+
                        words.get("Protection")+": "+parameters.get("????????????")+"\n";
                break;
            }
            case PcComponent.Disk:{
                result += words.get(name+":Description")+"\n";
                break;
            }
        }

        result += "\n"+words.get("Price")+": "+parameters.get("????????")+"R";
        return result;
    }
}
