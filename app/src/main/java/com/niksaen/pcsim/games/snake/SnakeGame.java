package com.niksaen.pcsim.games.snake;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.program.Program;

public class SnakeGame extends Program {

    public SnakeGame(MainActivity activity) {
        super(activity);
        Title = "Snake";
        ValueRam = new int[]{2400,2524};
        ValueVideoMemory = new int[]{940,960};
    }

    //ui
    FrameLayout field;
    Button up,right,left,bottom;
    ImageView pause;

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.snake_game,null);
        initView();

        SnakeCoreKT core = new SnakeCoreKT(activity,field);

        up.setOnClickListener(v ->core.move(SnakeCoreKT.Direction.UP));
        right.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.RIGHT));
        left.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.LEFT));
        bottom.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.BOTTOM));
        pause.setOnClickListener(v -> {
            if(core.isPlaying()){
                pause.setImageResource(R.drawable.play_color18);
            }else{
                pause.setImageResource(R.drawable.pause_color18);
            }
            //core.isPlaying = !core.isPlaying;
            core.setPlaying(!core.isPlaying());
        });
        //core.start();
    }
    private void initView(){
        field = mainWindow.findViewById(R.id.field);
        up = mainWindow.findViewById(R.id.up);
        right = mainWindow.findViewById(R.id.right);
        left = mainWindow.findViewById(R.id.left);
        bottom = mainWindow.findViewById(R.id.bottom);
        pause = mainWindow.findViewById(R.id.pause);
    }
}
