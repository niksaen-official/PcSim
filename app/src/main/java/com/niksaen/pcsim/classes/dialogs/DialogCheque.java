package com.niksaen.pcsim.classes.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.adapters.RecyclerChequeAdapter;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.save.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogCheque {
    private Context context;
    private AlertDialog alertDialog;

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+new Settings(context).Language+".json"),typeToken.getType());
    }
    public DialogCheque(Context context){
        this.context = context;
    }
    public View.OnClickListener BuyClickListener;

    public void show(ArrayList<PcComponent> pcComponentArrayList){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        getLanguage();
        View main = LayoutInflater.from(context).inflate(R.layout.dialog_recyclerview,null);
        TextView title = main.findViewById(R.id.titleView);
        title.setText(words.get("Confirm Purchase"));
        title.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = main.findViewById(R.id.list);
        recyclerView.setAdapter(new RecyclerChequeAdapter(context,pcComponentArrayList));
        recyclerView.setLayoutManager(layoutManager);

        Button buy = main.findViewById(R.id.ok);
        buy.setOnClickListener(BuyClickListener);
        buy.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        buy.setText(words.get("Buy"));

        Button cancel = main.findViewById(R.id.cancel);
        cancel.setText(words.get("Cancel"));
        cancel.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        cancel.setOnClickListener(v -> alertDialog.dismiss());

        TextView allPriceView = main.findViewById(R.id.textView);
        allPriceView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        allPriceView.setText(words.get("Total cost")+": "+getAllPrice(pcComponentArrayList)+"R");

        builder.setView(main);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }
    public void dismiss(){alertDialog.dismiss();}
    public int getAllPrice(ArrayList<PcComponent> pcComponentArrayList){
        int allPrice = 0;
        for (PcComponent component:pcComponentArrayList){
            allPrice+= component.Price;
        }
        return allPrice;
    }
}
