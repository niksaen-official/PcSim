package com.niksaen.pcsim.program.gStore;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;

public class WindowForBuy extends Program {
    private String messageText;
    private View.OnClickListener buttonOkClick = v -> { };

    public void setButtonOkClick(View.OnClickListener buttonOkClick) {
        this.buttonOkClick = buttonOkClick;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public WindowForBuy(MainActivity activity) {
        super(activity);
        Title = "Confirm Purchase";
        ValueRam = new int[]{10,25};
        ValueVideoMemory = new int[]{10,20};
    }

    TextView message;
    Button cancel,ok;
    LinearLayout background;
    int buttonClicks;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.warning_window,null);
        initView();
        style();

        message.setText(messageText);
        ok.setOnClickListener(buttonOkClick);

        ok.setText(activity.words.get("Yes"));
        cancel.setText(activity.words.get("No"));
        cancel.setOnClickListener(v->{closeProgram(1);});

        buttonClose.setOnClickListener(v->closeProgram(1));
        buttonFullscreenMode.setOnClickListener(v -> {
            if (buttonClicks== 0) {
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(R.drawable.button_2_1_color17);
                buttonClicks = 1;
            } else {
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener((v1, event) -> false);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(R.drawable.button_2_2_color17);
                buttonClicks = 0;
            }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram());
    }
    private void initView(){
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        message = mainWindow.findViewById(R.id.message);
        cancel = mainWindow.findViewById(R.id.cancel);
        ok = mainWindow.findViewById(R.id.ok);
        background = mainWindow.findViewById(R.id.background);
    }
    private void style(){
        background.setBackgroundColor(Color.parseColor("#0042A5"));
        mainWindow.setBackgroundColor(Color.parseColor("#002C6E"));
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        buttonClose.setBackgroundResource(R.drawable.button_1_color17);
        buttonFullscreenMode.setBackgroundResource(R.drawable.button_2_2_color17);
        buttonRollUp.setBackgroundResource(R.drawable.button_3_color17);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(activity.font, Typeface.BOLD);
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));

        cancel.setBackgroundColor(Color.parseColor("#002C6E"));
        ok.setBackgroundColor(Color.parseColor("#002C6E"));
        cancel.setTextColor(Color.parseColor("#FFFFFF"));
        ok.setTextColor(Color.parseColor("#FFFFFF"));
        cancel.setTypeface(activity.font, Typeface.BOLD);
        ok.setTypeface(activity.font, Typeface.BOLD);
        message.setTypeface(activity.font, Typeface.BOLD);
        message.setTextColor(Color.parseColor("#FFFFFF"));
        message.setGravity(Gravity.CENTER_VERTICAL);
    }
}
