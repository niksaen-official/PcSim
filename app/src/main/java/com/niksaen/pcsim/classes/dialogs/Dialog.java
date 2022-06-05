package com.niksaen.pcsim.classes.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.niksaen.pcsim.R;

public class Dialog {
    private final Context context;
    public Dialog(Context context){
        this.context = context;
    }
    private View.OnClickListener ButtonOkOnClick,ButtonCancelOnClick;
    public void setButtonOkOnClick(View.OnClickListener buttonOkOnClick) { this.ButtonOkOnClick = buttonOkOnClick; }
    public void setButtonCancelOnClick(View.OnClickListener buttonCancelOnClick) { this.ButtonCancelOnClick = buttonCancelOnClick; }

    private boolean TitleVisible = false;
    private String TitleText = "Title";

    public void setTitleVisible(boolean titleVisible) { TitleVisible = titleVisible; }
    public void setTitleText(String titleText) { TitleText = titleText; }

    private boolean ButtonOkVisible = false,ButtonCancelVisible = false;
    private String ButtonOkText = "Ok",ButtonCancelText = "Cancel";

    public void setButtonOkVisible(boolean ButtonOkVisible) { this.ButtonOkVisible = ButtonOkVisible; }
    public void setButtonCancelVisible(boolean ButtonCancelVisible) { this.ButtonCancelVisible = ButtonCancelVisible; }
    public void setButtonCancelText(String buttonCancelText) { ButtonCancelText = buttonCancelText; }
    public void setButtonOkText(String buttonOkText) { ButtonOkText = buttonOkText; }

    private String Text;

    public void setText(String text){ this.Text = text; }

    private boolean Cancelable = true;

    public final static int TYPE_BASE = 0;
    public final static int TYPE_WARN = 1;
    public final static int TYPE_ERROR = 2;
    public final static int TYPE_SUCCESSFULLY = 3;
    private int Type = TYPE_BASE;
    private int TextColor = Color.parseColor("#FFFFFF");
    public void setType(int type) { Type = type; }

    public void setCancelable(boolean cancelable) { Cancelable = cancelable; }

    private AlertDialog dialog;
    public void create(){
        View main = LayoutInflater.from(context).inflate(R.layout.dialog_base,null);
        android.widget.TextView titleView = main.findViewById(R.id.titleView);
        android.widget.TextView textView = main.findViewById(R.id.textView);
        Button buttonOk = main.findViewById(R.id.ok);
        Button buttonCancel = main.findViewById(R.id.cancel);

        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        titleView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        textView.setText(Text);
        buttonOk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        buttonCancel.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);

        if(TitleVisible){
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(TitleText);
        }
        if(ButtonCancelVisible){
            buttonCancel.setVisibility(View.VISIBLE);
            buttonCancel.setText(ButtonCancelText);
        }
        if(ButtonOkVisible){
            buttonOk.setVisibility(View.VISIBLE);
            buttonOk.setText(ButtonOkText);
        }
        if(ButtonCancelOnClick != null){
            buttonCancel.setOnClickListener(ButtonCancelOnClick);
        } else{
            buttonCancel.setOnClickListener(v -> dialog.dismiss());
        }
        if(ButtonOkOnClick != null){
            buttonOk.setOnClickListener(ButtonOkOnClick);
        }else {
            buttonOk.setOnClickListener(v -> dialog.dismiss());
        }

        switch (Type){
            case Dialog.TYPE_WARN:{
                TextColor = Color.parseColor("#F57F17");
                break;
            }
            case Dialog.TYPE_ERROR:{
                TextColor = Color.parseColor("#B71C1C");
                break;
            }
            case Dialog.TYPE_SUCCESSFULLY:{
                TextColor = Color.parseColor("#004D40");
                break;
            }
            default:{break;}
        }

        titleView.setTextColor(TextColor);
        textView.setTextColor(TextColor);
        buttonOk.setTextColor(TextColor);
        buttonCancel.setTextColor(TextColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(Cancelable);
        builder.setView(main);
        dialog = builder.create();
    }
    public void show(){ dialog.show(); }
    public void dismiss(){ dialog.dismiss(); }
}
