package com.niksaen.pcsim.games.memory;

import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.databinding.MemoryGameBinding;
import com.niksaen.pcsim.program.Program;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryGame extends Program {

    int openCardPos = -1;
    int openCardCount = 0;
    boolean isFirstClick = true;
    Timer timer;
    TimerTask timerTask;
    int time = 0;
    int[] cardResource = {
            R.drawable.card_1,
            R.drawable.card_1,
            R.drawable.card_2,
            R.drawable.card_2,
            R.drawable.card_3,
            R.drawable.card_3,
            R.drawable.card_4,
            R.drawable.card_4,
    };
    ImageView[] cardsView;
    MemoryGameBinding binding;
    public MemoryGame(MainActivity activity) {
        super(activity);
        Title = "Memory";
        ValueRam = new int[]{980,1024};
        ValueVideoMemory = new int[]{440,460};
        binding = MemoryGameBinding.inflate(activity.getLayoutInflater());
        HidesTaskbar = true;
    }
    @Override
    public void initProgram() {
        mainWindow = binding.getRoot();
        initUI();
        shuffleArray(cardResource);
        cardsView = new ImageView[]{
                binding.card0,
                binding.card1,
                binding.card2,
                binding.card3,
                binding.card4,
                binding.card5,
                binding.card6,
                binding.card7
        };
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if(!binding.getRoot().isDrawerOpen(binding.sideMenu)) {
                        time += 100;
                        binding.time.setText(convertTime(time));
                    }
                });
            }
        };
        for(int i = 0;i<cardsView.length;i++){
            int finalI = i;
            cardsView[i].setOnClickListener(view -> {
                if(isFirstClick){
                    timer.scheduleAtFixedRate(timerTask,0,100);
                    isFirstClick = false;
                }
                cardsView[finalI].setImageResource(cardResource[finalI]);
                if(openCardPos<0) {
                    openCardPos = finalI;
                }else {
                    if (cardResource[openCardPos] != cardResource[finalI]) {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            cardsView[finalI].setImageResource(R.drawable.card_back);
                            cardsView[openCardPos].setImageResource(R.drawable.card_back);
                            openCardPos = -1;
                        }, 250);
                    }else {
                        openCardPos = -1;
                        openCardCount += 2;
                        if(openCardCount == 8){
                            timer.cancel();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void openProgram() {
        if(Integer.parseInt(activity.pcParametersSave.CPU.get("Кол-во потоков"))>2 && Integer.parseInt(activity.pcParametersSave.CPU.get("Частота"))>=3000) {
            super.openProgram();
        }
    }

    private void restartGame(){
        openCardCount = 0;
        openCardPos = -1;
        isFirstClick = true;
        time = 0;
        binding.time.setText("");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if(!binding.getRoot().isDrawerOpen(binding.sideMenu)) {
                        time += 100;
                        binding.time.setText(convertTime(time));
                    }
                });
            }
        };
        for(ImageView imageView:cardsView){
            imageView.setImageResource(R.drawable.card_back);
        }
        shuffleArray(cardResource);
    }
    private String convertTime(int milliSeconds){
        int second = milliSeconds/1000;
        int minute = second/60;
        String strSecond = String.valueOf(second%60);
        if(strSecond.length() == 1){
            strSecond = "0"+strSecond;
        }

        String strMinute = String.valueOf(minute%60);
        if(strMinute.length() == 1){
            strMinute = "0"+strMinute;
        }
        return strMinute + ":" + strSecond+":"+ milliSeconds/100;
    }
    private int[] shuffleArray(int[] array){
        int[] newArray = array;
        for(int i = 0;i< newArray.length;i++){
            int tmp = newArray[i];
            int r = Others.random(i,newArray.length-1);
            newArray[i] = newArray[r];
            newArray[r] = tmp;
        }
        return newArray;
    }
    private void initUI(){
        ArrayList<String> menuList = new ArrayList<>();
        menuList.add(activity.words.get("Menu")+":");
        menuList.add(activity.words.get("Resume"));
        menuList.add(activity.words.get("New Game"));
        menuList.add(activity.words.get("Exit"));

        CustomListViewAdapter adapter = new CustomListViewAdapter(activity,0,menuList);
        adapter.TextSize = 35;
        adapter.isFirstElementOtherStyle = true;
        adapter.TextColor = Color.parseColor("#FFFFFF");
        adapter.BackgroundColor2 = Color.parseColor("#1B5E20");
        binding.sideMenu.setAdapter(adapter);

        binding.time.setTypeface(activity.font);
        // listeners
        binding.restart.setOnClickListener(view -> restartGame());
        binding.pause.setOnClickListener(view -> binding.getRoot().openDrawer(binding.sideMenu));
        binding.sideMenu.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i){
                case 1:{
                    binding.getRoot().closeDrawer(binding.sideMenu);
                    break;
                }
                case 2:{
                    restartGame();
                    break;
                }
                case 3:{
                    closeProgram(1);
                    break;
                }
            }
        });
    }
}
