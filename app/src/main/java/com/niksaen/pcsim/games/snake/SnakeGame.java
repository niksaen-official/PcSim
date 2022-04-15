package com.niksaen.pcsim.games.snake;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.databinding.SnakeGameBinding;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.save.PcParametersSave;

import java.util.ArrayList;

public class SnakeGame extends Program {

    private final SnakeGameBinding binding;
    ArrayList<String> menuList = new ArrayList<>();
    SnakeCoreKT core;
    public SnakeGame(MainActivity activity) {
        super(activity);
        Title = "Snake";
        ValueRam = new int[]{2400,2524};
        ValueVideoMemory = new int[]{940,960};
        binding = SnakeGameBinding.inflate(activity.getLayoutInflater());
        HidesTaskbar = true;
    }

    @Override
    public void initProgram() {
        mainWindow = binding.getRoot();
        if(core != null){
            core.restart();
        }else {
            core = new SnakeCoreKT(activity, binding.field);
        }
        style();
        core.start();

        binding.up.setOnClickListener(v ->core.move(SnakeCoreKT.Direction.UP));
        binding.right.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.RIGHT));
        binding.left.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.LEFT));
        binding.bottom.setOnClickListener(v -> core.move(SnakeCoreKT.Direction.BOTTOM));

        binding.pause.setOnClickListener(v -> {
            binding.getRoot().openDrawer(Gravity.LEFT);
            binding.getRoot().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN,Gravity.LEFT);
            if(!core.isLose()) {
                core.setPlaying(!core.isPlaying());
            }
        });
        binding.reset.setOnClickListener(v->{core.restart();binding.reset.setVisibility(View.GONE);});

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!core.isLose()) {
                    binding.score.setText(String.valueOf(core.getPartOfTails().size()));
                } else {
                    binding.reset.setVisibility(View.VISIBLE);
                    binding.score.setText(activity.words.get("You lose")+ ":\n"+ core.getPartOfTails().size());
                }
                handler.postDelayed(this, core.getDelay());
            }
        };
        handler.postDelayed(runnable,core.getDelay());
        binding.sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1:{
                    binding.getRoot().closeDrawer(Gravity.LEFT);
                    core.setPlaying(true);
                    break;
                }
                case 2:{
                    core.restart();
                    binding.reset.setVisibility(View.GONE);
                    binding.getRoot().closeDrawer(Gravity.LEFT);
                    break;
                }
                case 3:{
                    closeProgram(1);
                    break;
                }
            }
        });
    }

    @Override
    public void openProgram() {
        if(
                PcParametersSave.gpuPerformance(activity.pcParametersSave.GPU1.get("Графический процессор"),activity) >= PcParametersSave.gpuPerformance("HeForce GT 710",activity) ||
                PcParametersSave.gpuPerformance(activity.pcParametersSave.GPU2.get("Графический процессор"),activity) >= PcParametersSave.gpuPerformance("HeForce GT 710",activity) ||
                PcParametersSave.gpuPerformance(activity.pcParametersSave.CPU.get("Модель"),activity) >= PcParametersSave.gpuPerformance("HeForce GT 710",activity))
        {
            super.openProgram();
        }
    }

    private void style(){
        core.setHeadTexture(activity.getDrawable(R.drawable.snake_head));
        core.setEatTexture(activity.getDrawable(R.drawable.snake_eat));
        core.setTailTexture(activity.getDrawable(R.drawable.snake_tail));
        binding.field.setBackground(activity.getDrawable(R.color.bg_snake));
        menuList.add(activity.words.get("Menu")+":");
        menuList.add(activity.words.get("Resume"));
        menuList.add(activity.words.get("New Game"));
        menuList.add(activity.words.get("Exit"));

        CustomListViewAdapter adapter = new CustomListViewAdapter(activity,0,menuList);
        adapter.TextSize = 35;
        adapter.isFirstElementOtherStyle = true;
        binding.sideMenu.setAdapter(adapter);

        binding.score.setTypeface(activity.font);
    }
    @Override
    public void closeProgram(int mode) {
        menuList.clear();
    }
}