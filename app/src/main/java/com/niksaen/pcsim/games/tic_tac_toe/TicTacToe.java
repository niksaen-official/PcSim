package com.niksaen.pcsim.games.tic_tac_toe;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.databinding.GameTictactoeBinding;
import com.niksaen.pcsim.program.Program;

import static android.graphics.Typeface.BOLD;

public class TicTacToe extends Program {
    String lastType = "PvE";
    GameTictactoeBinding binding;
    public TicTacToe(MainActivity activity) {
        super(activity);
        Title = "Tic Tac Toe";
        ValueRam = new int[]{1025,1224};
        ValueVideoMemory = new int[]{400,500};
        binding = GameTictactoeBinding.inflate(activity.getLayoutInflater());
    }

    @Override
    public void initProgram() {
        mainWindow = binding.getRoot();
        style();
        activity.toolbar.setVisibility(View.GONE);
        binding.pause.setOnClickListener(v->{
            binding.getRoot().openDrawer(Gravity.LEFT);
        });
        binding.sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 1){
                lastType = "PvP";
                restart();
            }
            if(position == 2){
                lastType = "PvE";
                restart();
            }
            if(position == 3){
                closeProgram(1);
            }
        });
        binding.reset.setOnClickListener(v->{
            restart();
        });

    }
    Field fieldAdapter;
    private void style(){
        binding.text.setTypeface(activity.font);
        binding.reset.setText(activity.words.get("New Game"));
        binding.reset.setVisibility(View.GONE);
        binding.reset.setTypeface(activity.font);
        CustomListViewAdapter sideMenuAdapter = new CustomListViewAdapter(activity,0,
                new String[]{
                        activity.words.get("Menu")+":",
                        activity.words.get("New Game")+" ("+activity.words.get("vs player")+")",
                        activity.words.get("New Game")+" ("+activity.words.get("vs bots")+")",
                        activity.words.get("Exit")});
        sideMenuAdapter.TextSize = 40;
        sideMenuAdapter.isFirstElementOtherStyle = true;
        binding.sideMenu.setAdapter(sideMenuAdapter);
        fieldAdapter = new Field(activity);
        fieldAdapter.textView = binding.text;
        fieldAdapter.reset = binding.reset;
        binding.field.setLayoutManager(new GridLayoutManager(activity,3));
        binding.field.setAdapter(fieldAdapter);
    }
    private void restart(){
        fieldAdapter = new Field(activity);
        fieldAdapter.Type = lastType;
        fieldAdapter.textView = binding.text;
        fieldAdapter.reset = binding.reset;
        binding.field.setAdapter(fieldAdapter);
        binding.text.setText("");
        binding.reset.setVisibility(View.GONE);
    }

    @Override
    public void openProgram() {
        if(Integer.parseInt(activity.pcParametersSave.CPU.get("Кол-во потоков"))>=4){
            if(activity.pcParametersSave.GPU1!=null || activity.pcParametersSave.GPU2!=null) {
                super.openProgram();
            }
        }
    }
    @Override
    public void closeProgram(int mode) {
        super.closeProgram(mode);
        activity.toolbar.setVisibility(View.VISIBLE);
    }
}
