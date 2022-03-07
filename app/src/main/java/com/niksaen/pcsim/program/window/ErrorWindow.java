package com.niksaen.pcsim.program.window;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.program.Program;

public class ErrorWindow extends Program {
    private String Text;
    private View.OnClickListener buttonOkClick = v -> { };

    public void setButtonOkClick(View.OnClickListener buttonOkClick) {
        this.buttonOkClick = buttonOkClick;
    }

    private String okButtonText;
    public void setOkButtonText(String okButtonText) {
        this.okButtonText = okButtonText;
    }
    public void setMessage(String message) {
        this.Text = message;
    }

    public ErrorWindow(MainActivity activity) {
        super(activity);
        Title = "Error";
        ValueRam = new int[]{10,25};
        ValueVideoMemory = new int[]{10,20};
    }

    TextView message;
    Button cancel,ok;
    LinearLayout background;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.warning_window,null);
        initView();
        style();
        super.initProgram();

        titleTextView.setText("Error");
        ok.setOnClickListener(buttonOkClick);
        buttonClose.setClickable(false);
        buttonRollUp.setClickable(false);
        buttonFullscreenMode.setClickable(false);
        mainWindow.setScaleX(0.6f);
        mainWindow.setScaleY(0.6f);
        mainWindow.setZ(100f);
        buttonFullscreenMode.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);

        message.setText(Text);
        ok.setText(okButtonText);
        cancel.setVisibility(View.GONE);
    }
    private void initView(){
        message = mainWindow.findViewById(R.id.message);
        cancel = mainWindow.findViewById(R.id.cancel);
        ok = mainWindow.findViewById(R.id.ok);
        background = mainWindow.findViewById(R.id.background);
    }
    private void style(){
        background.setBackgroundColor(activity.styleSave.ThemeColor1);
        cancel.setBackgroundColor(activity.styleSave.ThemeColor2);
        ok.setBackgroundColor(activity.styleSave.ThemeColor2);
        cancel.setTypeface(activity.font, Typeface.BOLD);
        ok.setTypeface(activity.font, Typeface.BOLD);
        message.setTypeface(activity.font, Typeface.BOLD);
        ok.setTextColor(activity.styleSave.TextButtonColor);
        cancel.setTextColor(activity.styleSave.TextButtonColor);
    }
}
