package com.niksaen.pcsim.program.miner;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.save.PlayerData;

import java.util.Timer;
import java.util.TimerTask;

public class Miner extends Program {
    public Miner(MainActivity activity) {
        super(activity);
        Title = "Miner";
        ValueRam = new int[]{200,250};
        ValueVideoMemory = new int[]{10,20};
    }

    private LinearLayout main;
    private TextView consoleView;
    private Button startMining,stopMining,getMoney;

    private void initView(){
        main = mainWindow.findViewById(R.id.main);
        consoleView = mainWindow.findViewById(R.id.console);
        startMining = mainWindow.findViewById(R.id.start);
        stopMining = mainWindow.findViewById(R.id.stop);
        getMoney = mainWindow.findViewById(R.id.get);
    }
    private void viewStyle(){
        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        consoleView.setBackgroundColor(activity.styleSave.ThemeColor2);
        startMining.setBackgroundColor(activity.styleSave.ThemeColor2);
        stopMining.setBackgroundColor(activity.styleSave.ThemeColor2);
        getMoney.setBackgroundColor(activity.styleSave.ThemeColor2);

        consoleView.setTextColor(activity.styleSave.TextColor);
        startMining.setTextColor(activity.styleSave.TextButtonColor);
        stopMining.setTextColor(activity.styleSave.TextButtonColor);
        getMoney.setTextColor(activity.styleSave.TextButtonColor);

        startMining.setTypeface(activity.font, Typeface.BOLD);
        stopMining.setTypeface(activity.font, Typeface.BOLD);
        getMoney.setTypeface(activity.font, Typeface.BOLD);
        consoleView.setTypeface(activity.font);

        startMining.setText("Start");
        stopMining.setText("Stop");
        getMoney.setText("GET");
    }

    private Timer timer;
    private TimerTask task;
    private int buffMoney = 0;
    private int delay = 0;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_miner,null);
        initView();
        viewStyle();

        task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if(activity.pcParametersSave.CPU.get("Графическое ядро").equals("-")) {
                        buffMoney += activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList) / 64;
                    }
                    else{
                        buffMoney += (activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)-1024) / 64;
                    }
                    consoleView.setText("Накоплено: "+buffMoney+"R");
                });
            }
        };
        if(activity.pcParametersSave.GPU1 != null){
            delay  = (int) (1/Float.parseFloat(activity.pcParametersSave.GPU1.get("Пропускная способность"))*20000);
        }
        if(activity.pcParametersSave.GPU2 != null){
            delay  = (int) (1/Float.parseFloat(activity.pcParametersSave.GPU2.get("Пропускная способность"))*20000);
        }

        startMining.setOnClickListener(v -> {
            timer = new Timer();
            timer.scheduleAtFixedRate(task,0,delay);
            startMining.setClickable(false);
        });
        stopMining.setOnClickListener(v->{
            timer.cancel();
            startMining.setClickable(true);
        });
        getMoney.setOnClickListener(v->{
            timer.cancel();
            PlayerData playerData = new PlayerData(activity);
            playerData.Money+=buffMoney;
            playerData.setAllData();
            consoleView.setText("Заработано: "+buffMoney+"R\n"+"Всего: "+playerData.Money+"R\n");
            buffMoney = 0;
            startMining.setClickable(true);
        });

        super.initProgram();
    }

    @Override
    public void openProgram() {
        if(activity.pcParametersSave.GPU1 != null || activity.pcParametersSave.GPU2 != null) {
            super.openProgram();
        }
    }
}
