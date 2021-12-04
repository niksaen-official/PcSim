package com.niksaen.pcsim.program.gStore;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.ProgramListAndData;
import com.niksaen.pcsim.program.appDownloader.PrepareForInstall;
import com.niksaen.pcsim.save.PlayerData;

public class AppDescriptionPage extends Program {
    public String ProgramForInstall;
    private PlayerData data;
    public AppDescriptionPage(MainActivity activity) {
        super(activity);
        Title = "Application description page";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
        data = new PlayerData(activity);
    }

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_disk_change,null);
        initView();
        style();

        prev.setOnClickListener(v->{ closeProgram(1); });
        if(!StringArrayWork.ArrayListToString(data.ListPurchasedPrograms).contains(ProgramForInstall)) {
            next.setText(activity.words.get("Buy for")+" "+ProgramListAndData.programPrice.get(ProgramForInstall)+"R");
            next.setOnClickListener(v -> {
                WindowForBuy forBuy = new WindowForBuy(activity);
                forBuy.setMessageText(
                        "Вы подтверждаете покупку товара?\n"
                                + activity.words.get(ProgramListAndData.programType.get(ProgramForInstall)) + ": \"" + activity.words.get(ProgramForInstall) + "\"\n"
                                + "Стоимость: " + ProgramListAndData.programPrice.get(ProgramForInstall) + "R\n"
                                + "У вас на счету: " + data.Money + "R\n\n\n\n"
                                + "Внимание: Это единоразовая покупка при следующей установке вам не надо покупать её снова!!!");
                forBuy.setButtonOkClick(v1 -> {
                    data.Money = data.Money - ProgramListAndData.programPrice.get(ProgramForInstall);
                    data.ListPurchasedPrograms = StringArrayWork.add(data.ListPurchasedPrograms, ProgramForInstall);
                    data.setAllData();
                    forBuy.closeProgram(1);
                    next.setText(activity.words.get("Install"));
                });
                forBuy.openProgram();
            });
        }else{
            next.setText(activity.words.get("Install"));
            next.setOnClickListener(v->{
                PrepareForInstall prepareForInstall = new PrepareForInstall(activity);
                prepareForInstall.setProgramForSetup(ProgramForInstall);
                prepareForInstall.openProgram();
            });
        }
    }

    ImageView appIcon;
    TextView appName;
    TextView appDescription;
    Button next,prev;
    ConstraintLayout layout;
    private void initView() {
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        appIcon = mainWindow.findViewById(R.id.app_icon);
        appName = mainWindow.findViewById(R.id.program_name);
        appDescription = mainWindow.findViewById(R.id.text);
        next = mainWindow.findViewById(R.id.button);
        prev = mainWindow.findViewById(R.id.button1);
        layout = mainWindow.findViewById(R.id.main);
    }

    int buttonClicks = 0;
    private void style(){
        layout.setBackgroundColor(Color.parseColor("#0042A5"));
        mainWindow.setBackgroundColor(Color.parseColor("#002C6E"));
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        buttonClose.setBackgroundResource(R.drawable.button_1_color17);
        buttonFullscreenMode.setBackgroundResource(R.drawable.button_2_2_color17);
        buttonRollUp.setBackgroundResource(R.drawable.button_3_color17);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(activity.font, Typeface.BOLD);
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));

        // настройка кнопок
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
        buttonRollUp.setOnClickListener(v->rollUpProgram(1));

        appName.setTypeface(activity.font, Typeface.BOLD);
        appDescription.setTypeface(activity.font);
        next.setTypeface(activity.font, Typeface.BOLD);
        prev.setTypeface(activity.font, Typeface.BOLD);
        appName.setTextColor(Color.parseColor("#FFFFFF"));
        appDescription.setTextColor(Color.parseColor("#FFFFFF"));
        next.setTextColor(Color.parseColor("#FFFFFF"));
        prev.setTextColor(Color.parseColor("#FFFFFF"));
        next.setBackgroundColor(Color.parseColor("#002C6E"));
        prev.setBackgroundColor(Color.parseColor("#002C6E"));

        //set data
        appIcon.setImageResource(ProgramListAndData.programIcon.get(ProgramForInstall));
        appName.setText(activity.words.get(ProgramForInstall));
        appDescription.setText(activity.words.get(ProgramForInstall+":Description"));
        prev.setText(activity.words.get("Cancel"));
    };
}
