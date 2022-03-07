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

    private static void setLanguage(Context context,String langCode){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("lang",langCode).apply();
    }
    public static void ChangeLanguage(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.change_language,null);
        TextView
                textView = view.findViewById(R.id.text),
                textView1 = view.findViewById(R.id.warn);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        textView1.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        Spinner lang = view.findViewById(R.id.lang);
        Button save = view.findViewById(R.id.save);
        save.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        save.setBackgroundColor(context.getColor(R.color.buttonColor));
        save.setTextColor(Color.parseColor("#ffffff"));

        String[] langList = new String[]{RU,EN};
        String[] langListUser = new String[]{"Русский","English"};

        NotepadSpinnerAdapter adapter = new NotepadSpinnerAdapter(context,0,langListUser);
        adapter.BackgroundColor = context.getColor(R.color.background2);
        adapter.TextColor = context.getColor(R.color.color17);
        lang.setAdapter(adapter);

        final String[] buff = {RU};
        lang.setAdapter(adapter);
        lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buff[0] = langList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        save.setOnClickListener(v -> {
            setLanguage(context,buff[0]);
            dialog.dismiss();
        });
        dialog.show();
    }

}
