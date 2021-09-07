package com.niksaen.pcsim.classes.PopuListView;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PopupListView {
    TextView TextView;
    Context context;

    public PopupListView(TextView TextView, Context context){
        this.TextView = TextView;
        this.context = context;
    }
    private ArrayAdapter<String> arrayAdapter;
    public void setAdapter(ArrayAdapter<String> adapter){arrayAdapter = adapter; }
    private AdapterView.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) { this.onItemClickListener = onItemClickListener; }

    AlertDialog dialog;
    ListView listView;
    public void show(){
        TextView textView = new TextView(context);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        textView.setTextSize(30);
        textView.setBackgroundColor(Color.parseColor("#111111"));
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setPadding(8,8,8,8);
        textView.setText(TextView.getText().toString());

        listView = new ListView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        listView.setBackgroundColor(Color.parseColor("#111111"));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onItemClickListener);

        LinearLayout main = new LinearLayout(context);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(12,12,12,12);
        main.setBackgroundColor(Color.parseColor("#111111"));

        main.addView(textView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        main.addView(listView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(main);
        dialog = builder.create();
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
