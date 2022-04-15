package com.niksaen.pcsim.save;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;

public class Language {

    public static String EN = "EN";
    public static String RU = "RU";

    String[] langList = new String[]{RU,EN};
    String[] langListUser = new String[]{"Русский","English"};
    String langCode = RU;

    public String getLangCode() { return langCode; }
    public View.OnClickListener saveClickListener;
    public AlertDialog dialog;
    Context context;

    public Language(Context context){this.context = context;}
    public void ChangeLanguage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.change_language,null);
        TextView textView = view.findViewById(R.id.text);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        Spinner lang = view.findViewById(R.id.lang);
        Button save = view.findViewById(R.id.save);
        save.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        save.setBackgroundColor(context.getColor(R.color.buttonColor));
        save.setTextColor(Color.parseColor("#ffffff"));
        NotepadSpinnerAdapter adapter = new NotepadSpinnerAdapter(context,0,langListUser);
        adapter.BackgroundColor = context.getColor(R.color.background2);
        adapter.TextColor = context.getColor(R.color.color17);
        lang.setAdapter(adapter);

        lang.setAdapter(adapter);
        lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langCode = langList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        save.setOnClickListener(saveClickListener);
        dialog.show();
    }
}
