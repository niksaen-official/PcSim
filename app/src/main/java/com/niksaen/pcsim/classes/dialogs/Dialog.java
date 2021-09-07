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
    private Context context;
    public Dialog(Context context){
        this.context = context;
    }
    private View.OnClickListener ButtonOkOnClick,ButtonCancelOnClick;
    public void setButtonOkOnClick(View.OnClickListener buttonOkOnClick) { this.ButtonOkOnClick = buttonOkOnClick; }
    public void setButtonCancelOnClick(View.OnClickListener buttonCancelOnClick) { this.ButtonCancelOnClick = buttonCancelOnClick; }

    private boolean TitleVisible = false;
    private String TitleText = "Title";
    private TextView TitleView;
    public void setTitleVisible(boolean titleVisible) { TitleVisible = titleVisible; }
    public void setTitleText(String titleText) { TitleText = titleText; }

    private boolean ButtonOkVisible = false,ButtonCancelVisible = false;
    private String ButtonOkText = "Ok",ButtonCancelText = "Cancel";
    private Button ButtonOk,ButtonCancel;
    public void setButtonOkVisible(boolean ButtonOkVisible) { this.ButtonOkVisible = ButtonOkVisible; }
    public void setButtonCancelVisible(boolean ButtonCancelVisible) { this.ButtonCancelVisible = ButtonCancelVisible; }
    public void setButtonCancelText(String buttonCancelText) { ButtonCancelText = buttonCancelText; }
    public void setButtonOkText(String buttonOkText) { ButtonOkText = buttonOkText; }

    private String Text;
    private TextView TextView;
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
        TitleView = main.findViewById(R.id.titleView);
        TextView = main.findViewById(R.id.textView);
        ButtonOk = main.findViewById(R.id.ok);
        ButtonCancel = main.findViewById(R.id.cancel);

        TextView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
        TitleView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        TextView.setText(Text);
        ButtonOk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);
        ButtonCancel.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"),Typeface.BOLD);

        if(TitleVisible){
            TitleView.setVisibility(View.VISIBLE);
            TitleView.setText(TitleText);
        }
        if(ButtonCancelVisible){
            ButtonCancel.setVisibility(View.VISIBLE);
            ButtonCancel.setText(ButtonCancelText);
        }
        if(ButtonOkVisible){
            ButtonOk.setVisibility(View.VISIBLE);
            ButtonOk.setText(ButtonOkText);
        }
        if(ButtonCancelOnClick != null){
            ButtonCancel.setOnClickListener(ButtonCancelOnClick);
        } else{
            ButtonCancel.setOnClickListener(v -> dialog.dismiss());
        }
        if(ButtonOkOnClick != null){
            ButtonOk.setOnClickListener(ButtonOkOnClick);
        }else {
            ButtonOk.setOnClickListener(v -> dialog.dismiss());
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

        TitleView.setTextColor(TextColor);
        TextView.setTextColor(TextColor);
        ButtonOk.setTextColor(TextColor);
        ButtonCancel.setTextColor(TextColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(Cancelable);
        builder.setView(main);
        dialog = builder.create();
    }
    public void show(){ dialog.show(); }
    public void dismiss(){ dialog.dismiss(); }
}
