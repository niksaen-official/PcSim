package com.niksaen.pcsim.games.mines;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.databinding.GameMinesweeperBinding;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends Program {

    private int bombCount = 12;
    GameMinesweeperBinding binding;
    Timer timer = new Timer();
    int[] cellsIcon = {R.drawable.empty_cell, R.drawable.c1_cell, R.drawable.c2_cell, R.drawable.c3_cell, R.drawable.c4_cell,};
    View[][] cells;
    TimerTask task;
    boolean isPlaying = true;
    Runnable runnable;

    private int timeSec = 0;
    private boolean isFirstClick = true;

    private int bombsFound = 0;
    int[][] mines; // минное поле если в ячейке есть мина то значение равно 1
    boolean[][] flags; // поле для флагов если флаг установлен то значение true
    boolean[][] revealed; // открытые клетки в значении true если открыта

    public Minesweeper(MainActivity activity) {
        super(activity);
        Title = "Minesweeper";
        ValueRam = new int[]{1980,2024};
        ValueVideoMemory = new int[]{940,960};
        binding = GameMinesweeperBinding.inflate(activity.getLayoutInflater());
        HidesTaskbar = true;
    }
    @Override
    public void initProgram() {
        mainWindow = binding.getRoot();
        activity.toolbar.setVisibility(View.GONE);
        style();
        fieldGenerator(bombCount);

        runnable = () -> activity.runOnUiThread(() -> {if(isPlaying) binding.clock.setText(MusicPlayer.convertTime(timeSec++ * 1000));});

        timer = new Timer();
        task = new TimerTask() {@Override public void run(){runnable.run();}};

        binding.reset.setOnClickListener(v -> restart());
        binding.pause.setOnClickListener(v -> {
            binding.getRoot().openDrawer(Gravity.LEFT);
            binding.getRoot().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN,Gravity.LEFT);
            isPlaying = !isPlaying;
            ((DrawerLayout)mainWindow).openDrawer(binding.sideMenu);
        });
        binding.sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            binding.getRoot().closeDrawer(Gravity.LEFT);
            switch (position){
                case 1:{ isPlaying = !isPlaying;break;}
                case 2:{ restart();break; }
                case 3:{ bombCount = 6;restart();break; }
                case 4:{ bombCount = 12;restart();break; }
                case 5:{ bombCount = 18;restart();break; }
                case 6:{ closeProgram(1);break; }
            }
        });
    }
    private void style(){
        binding.clock.setTypeface(activity.font);
        binding.reset.setTypeface(activity.font, Typeface.BOLD);
        binding.reset.setVisibility(View.GONE);
        binding.reset.setText(activity.words.get("New Game"));

        String[] strings = {
                activity.words.get("Menu")+":",
                activity.words.get("Resume"),
                activity.words.get("New Game"),
                activity.words.get("New Game")+" ("+activity.words.get("Easy")+")",
                activity.words.get("New Game")+" ("+activity.words.get("Middle")+")",
                activity.words.get("New Game")+" ("+activity.words.get("Hard")+")",
                activity.words.get("Exit")
        };
        CustomListViewAdapter adapter = new CustomListViewAdapter(activity,0,strings);
        adapter.BackgroundColor2 = Color.parseColor("#888888");
        adapter.isFirstElementOtherStyle = true;
        adapter.TextSize = 40;
        adapter.TextColor = Color.parseColor("#000000");
        binding.sideMenu.setAdapter(adapter);
        binding.sideMenu.setBackgroundColor(Color.parseColor("#888888"));
    }

    // основная логика игры
    //генерация поля
    private void fieldGenerator(int bombCount){
        final int width = 10;
        final int height = 10;
        final int cellSize = 85;

        cells = new View[width][height];
        mines = new int[width][height];
        revealed = new boolean[width][height];
        flags = new boolean[width][height];
        //расстановка бомб
        for(int i = 0;i<bombCount;i++){
            int x = Others.random(0,width-1);
            int y = Others.random(0,height-1);
            if(mines[x][y] == 0){
                mines[x][y] = 1;
            }
        }

        // генерация поля
        for(int x = 0;x<width;x++){
            LinearLayout lineBuff = new LinearLayout(activity);
            for(int y = 0;y<height;y++){
                cells[x][y] = new View(activity);
                cells[x][y].setBackgroundResource(R.drawable.close_cell);
                int finalX = x;
                int finalY = y;
                cells[x][y].setOnClickListener(v -> {
                    if(isFirstClick){
                        timer.scheduleAtFixedRate(task,0,1000);
                        isFirstClick = false;
                    }
                    if(mines[finalX][finalY] == 1){
                        endGame();
                        cells[finalX][finalY].setBackgroundResource(R.drawable.mine_activate_cell);
                        timer.cancel();
                        binding.reset.setVisibility(View.VISIBLE);
                        binding.clock.setText(binding.clock.getText().toString()+"\n"+activity.words.get("You lose"));
                    }else {
                        reveal(finalX, finalY);
                    }
                });
                cells[x][y].setOnLongClickListener(v -> {
                    if(!revealed[finalX][finalY]) {
                        flags[finalX][finalY] = true;
                        cells[finalX][finalY].setBackgroundResource(R.drawable.flag_cell);
                        if (mines[finalX][finalY] == 1) {
                            bombsFound++;
                            if (bombsFound == bombCount) {
                                timer.cancel();
                                binding.reset.setVisibility(View.VISIBLE);
                                binding.clock.setText(binding.clock.getText().toString() + "\n"+activity.words.get("You win"));
                            }
                        }
                    }
                    return true;
                });
                lineBuff.addView(cells[x][y],cellSize,cellSize);
            }
            binding.field.addView(lineBuff);
        }
    }
    boolean outBounds(int x,int y){ return x<0||y<0||x>=10||y>=10; }

    int calcNear(int x, int y) {
        if(outBounds(x,y))return 0;
        int i=0;
        for (int offsetX=-1; offsetX<=1; offsetX++) {
            for (int offsetY=-1; offsetY<=1; offsetY++) {
                if (outBounds(offsetX+x, offsetY+y))continue;
                i+=mines[offsetX+x][offsetY+y];
            }
        }

        cells[x][y].setBackgroundResource(cellsIcon[i]);
        return i;
    }
    // авто открытие клеток
    void reveal(int x, int y){
        if(outBounds(x,y))return;
        if(revealed[x][y])return;
        revealed[x][y]=true;
        cells[x][y].setBackgroundResource(R.drawable.empty_cell);
        if(calcNear(x,y)!=0)return;
        reveal(x-1,y-1);
        reveal(x-1,y+1);
        reveal(x+1,y-1);
        reveal(x+1,y+1);
        reveal(x-1,y);
        reveal(x+1,y);
        reveal(x,y-1);
        reveal(x,y+1);
    }
    // дейсвтия если игрок проиграл
    private void endGame(){
        for(int x = 0; x<cells.length; x++){
            for(int y = 0; y<cells[x].length; y++){
                if(mines[x][y] == 1 && !flags[x][y]){
                    cells[x][y].setBackgroundResource(R.drawable.mine_cell);
                }
                cells[x][y].setOnClickListener(null);
                cells[x][y].setOnLongClickListener(null);
            }
        }
    }
    // очистка поля
    private void fieldClear(){
        for(View[] views:cells){
            for (View view:views){
                view.setVisibility(View.GONE);
            }
        }
    }
    // рестарт игры
    private void restart(){
        isPlaying = true;
        timer.cancel();
        fieldClear();
        timeSec = 0;
        binding.clock.setText("");
        binding.reset.setVisibility(View.GONE);
        isFirstClick = true;
        bombsFound = 0;
        fieldGenerator(bombCount);
        timer = new Timer();
        task = new TimerTask() {@Override public void run(){runnable.run();}};
    }
}
