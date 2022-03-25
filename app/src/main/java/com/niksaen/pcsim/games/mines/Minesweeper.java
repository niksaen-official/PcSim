package com.niksaen.pcsim.games.mines;

import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Minesweeper extends Program {

    private int bombCount = 12;

    public Minesweeper(MainActivity activity) {
        super(activity);
        Title = "Minesweeper";
        ValueRam = new int[]{1980,2024};
        ValueVideoMemory = new int[]{940,960};
    }
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.game_minesweeper,null);
        activity.toolbar.setVisibility(View.GONE);
        initView();
        style();
        fieldGenerator(bombCount);

        reset.setOnClickListener(v -> {
            timer.cancel();
            fieldClear();
            timeSec = 0;
            clock.setText("");
            reset.setVisibility(View.VISIBLE);
            isFirstClick = true;
            bombsFound = 0;
            fieldGenerator(bombCount);
        });
        imageView.setOnClickListener(v -> {
            ((DrawerLayout)mainWindow).openDrawer(sideMenu);
        });
        sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1:{
                    timer.cancel();
                    fieldClear();
                    timeSec = 0;
                    clock.setText("");
                    reset.setVisibility(View.VISIBLE);
                    isFirstClick = true;
                    bombsFound = 0;
                    fieldGenerator(bombCount);
                    break;
                }
                case 2:{
                    bombCount = 6;
                    timer.cancel();
                    fieldClear();
                    timeSec = 0;
                    clock.setText("");
                    reset.setVisibility(View.VISIBLE);
                    isFirstClick = true;
                    bombsFound = 0;
                    fieldGenerator(bombCount);
                    break;
                }
                case 3:{
                    bombCount = 12;
                    timer.cancel();
                    fieldClear();
                    timeSec = 0;
                    clock.setText("");
                    reset.setVisibility(View.VISIBLE);
                    isFirstClick = true;
                    bombsFound = 0;
                    fieldGenerator(bombCount);
                    break;
                }
                case 4:{
                    bombCount = 18;
                    timer.cancel();
                    fieldClear();
                    timeSec = 0;
                    clock.setText("");
                    reset.setVisibility(View.VISIBLE);
                    isFirstClick = true;
                    bombsFound = 0;
                    fieldGenerator(bombCount);
                    break;
                }
                case 5:{
                    closeProgram(1);
                    break;
                }
            }
        });
    }
    LinearLayout field;
    ImageView imageView;
    TextView clock;
    Button reset;
    ListView sideMenu;

    private void initView(){
        field = mainWindow.findViewById(R.id.field);
        imageView = mainWindow.findViewById(R.id.pause);
        clock = mainWindow.findViewById(R.id.clock);
        reset = mainWindow.findViewById(R.id.reset);
        sideMenu = mainWindow.findViewById(R.id.side_menu);
    }
    private void style(){
        clock.setTypeface(activity.font);
        reset.setTypeface(activity.font, Typeface.BOLD);
        reset.setVisibility(View.GONE);
        reset.setText(activity.words.get("New Game"));

        String[] strings = {
                activity.words.get("Menu"),
                activity.words.get("New Game"),
                activity.words.get("New Game")+" (Легкий)",
                activity.words.get("New Game")+" (Средний)",
                activity.words.get("New Game")+" (Сложный)",
                activity.words.get("Exit")
        };
        CustomListViewAdapter adapter = new CustomListViewAdapter(activity,0,strings);
        adapter.BackgroundColor2 = R.color.color23;
        adapter.TextSize = 40;
        adapter.BackgroundColor1 = R.color.color23;
        adapter.TextColor = R.color.color31;
        sideMenu.setAdapter(adapter);
    }

    // основная логика игры
    Timer timer = new Timer();
    int[] cellsIcon = {
            R.drawable.empty_cell,
            R.drawable.c1_cell,
            R.drawable.c2_cell,
            R.drawable.c3_cell,
            R.drawable.c4_cell,
    };
    View[][] cells;

    private int timeSec = 0;
    private boolean isFirstClick = true;

    private int bombsFound = 0;
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
                        timer = new Timer();
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                activity.runOnUiThread(() -> clock.setText(MusicPlayer.convertTime(timeSec++*1000)));
                            }
                        };
                        timer.scheduleAtFixedRate(task,0,1000);
                        isFirstClick = false;
                    }
                    if(mines[finalX][finalY] == 1){
                        endGame();
                        cells[finalX][finalY].setBackgroundResource(R.drawable.mine_activate_cell);
                        timer.cancel();
                        reset.setVisibility(View.VISIBLE);
                        clock.setText(clock.getText().toString()+"\n"+activity.words.get("You lose"));
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
                                reset.setVisibility(View.VISIBLE);
                                clock.setText(clock.getText().toString() + "\n"+activity.words.get("You win"));
                            }
                        }
                    }
                    return true;
                });
                lineBuff.addView(cells[x][y],cellSize,cellSize);
            }
            field.addView(lineBuff);
        }
    }
    int[][] mines; // минное поле если в ячейке есть мина то значение равно 1
    boolean[][] flags; // поле для флагов если флаг установлен то значение true
    boolean[][] revealed; // открытые клетки в значении true если открыта
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
    @Override
    public void closeProgram(int mode) {
        super.closeProgram(mode);
        activity.toolbar.setVisibility(View.VISIBLE);
    }
}
