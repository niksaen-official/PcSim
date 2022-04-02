package com.niksaen.pcsim.program.miner;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.window.ErrorWindow;
import com.niksaen.pcsim.program.window.WarningWindow;
import com.niksaen.pcsim.save.PlayerData;

import java.util.Timer;
import java.util.TimerTask;

public class Miner extends Program {
    public Miner(MainActivity activity) {
        super(activity);
        Title = "Miner";
        ValueRam = new int[]{200, 250};
        ValueVideoMemory = new int[]{
                activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList) - 100,
                activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList) - 50
        };
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

        startMining.setText(activity.words.get("To begin"));
        stopMining.setText(activity.words.get("Stop"));
        getMoney.setText(activity.words.get("Withdraw"));
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
        if(activity.pcParametersSave.GPU1 != null){
            delay  = (int) (1/Float.parseFloat(activity.pcParametersSave.GPU1.get("Пропускная способность"))*20000);
        }
        if(activity.pcParametersSave.GPU2 != null){
            delay  = (int) (1/Float.parseFloat(activity.pcParametersSave.GPU2.get("Пропускная способность"))*20000);
        }

        startMining.setOnClickListener(v -> {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(() -> {
                        if(activity.pcParametersSave.CPU.get("Графическое ядро").equals("-")) {
                            buffMoney += CurrentVideoMemoryUse / 64;
                        }
                        else{
                            buffMoney += (CurrentVideoMemoryUse-1024) / 64;
                        }
                        consoleView.setText(activity.words.get("Accumulated:")+buffMoney+"R");
                    });
                }
            };
            timer.scheduleAtFixedRate(task,0,delay);
            startMining.setVisibility(View.GONE);
            if(activity.player.isPlaying()) activity.player.setVolume(0.25f,0.25f);
        });
        stopMining.setOnClickListener(v->{
            timer.cancel();
            startMining.setVisibility(View.VISIBLE);
            if(activity.player.isPlaying()) activity.player.setVolume(0.1f,0.1f);
        });
        getMoney.setOnClickListener(v->{
            if(activity.player.isPlaying()) activity.player.setVolume(0.1f,0.1f);
            timer.cancel();
            PlayerData playerData = new PlayerData(activity);
            playerData.Money+=buffMoney;
            playerData.setAllData();
            consoleView.setText(activity.words.get("Earned:")+buffMoney+"R\n"+activity.words.get("Total")+":"+playerData.Money+"R\n");
            buffMoney = 0;
        });

        super.initProgram();
    }

    @Override
    public void openProgram() {
        if(activity.pcParametersSave.GPU1 != null || activity.pcParametersSave.GPU2 != null) {
            if(!programIsOpen()) super.openProgram();
            else {
                ErrorWindow window = new ErrorWindow(activity);
                window.setMessageText("Данная программа уже запущена!");
                window.setButtonOkClick(v -> window.closeProgram(1));
                window.setButtonOkText("Ok");
                window.openProgram();
            }
        }
    }

    @Override
    public void closeProgram(int mode) {
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
        if(activity.player!=null && activity.player.isPlaying()) activity.player.setVolume(0.1f,0.1f);
    }
}
