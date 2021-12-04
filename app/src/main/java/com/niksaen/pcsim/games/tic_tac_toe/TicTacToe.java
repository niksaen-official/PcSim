package com.niksaen.pcsim.games.tic_tac_toe;

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
import com.niksaen.pcsim.program.Program;

import static android.graphics.Typeface.BOLD;

public class TicTacToe extends Program {
    DrawerLayout layout;
    String lastType;
    public TicTacToe(MainActivity activity) {
        super(activity);
        Title = "Tic Tac Toe";
        ValueRam = new int[]{1022,1024};
        ValueVideoMemory = new int[]{400,500};
    }

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.game_tictactoe,null);
        initView();
        style();
        pause.setOnClickListener(v->{
            layout.openDrawer(sideMenu);
        });
        sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 1){
                adapter = new Adapter(activity);
                adapter.Type = "PvP";
                lastType = "PvP";
                adapter.textView = textView;
                field.setAdapter(adapter);
                adapter.reset = reset;
                textView.setText("");
                reset.setVisibility(View.GONE);
            }
            if(position == 2){
                adapter = new Adapter(activity);
                adapter.textView = textView;
                field.setAdapter(adapter);
                lastType = "PvE";
                adapter.reset = reset;
                textView.setText("");
                reset.setVisibility(View.GONE);
            }
            if(position == 3){
                closeProgram(1);
            }
        });
        reset.setOnClickListener(v->{
            adapter = new Adapter(activity);
            adapter.Type = lastType;
            adapter.textView = textView;
            adapter.reset = reset;
            field.setAdapter(adapter);
            textView.setText("");
            reset.setVisibility(View.GONE);
        });

    }
    TextView textView;
    ListView sideMenu;
    RecyclerView field;
    Adapter adapter;
    Button pause;
    Button reset;
    private void initView(){
        layout = (DrawerLayout) mainWindow;
        sideMenu = mainWindow.findViewById(R.id.side_menu);
        field = mainWindow.findViewById(R.id.field);
        textView = mainWindow.findViewById(R.id.text);
        pause = mainWindow.findViewById(R.id.button5);
        reset = mainWindow.findViewById(R.id.reset);
    }
    private void style(){
        textView.setTypeface(activity.font,BOLD);
        reset.setText(activity.words.get("New Game"));
        reset.setVisibility(View.GONE);
        reset.setTypeface(activity.font,BOLD);
        CustomListViewAdapter sideMenuAdapter = new CustomListViewAdapter(activity,0,
                new String[]{
                        activity.words.get("Menu"),
                        activity.words.get("New Game")+activity.words.get(" vs Player"),
                        activity.words.get("New Game")+activity.words.get(" vs Bots"),
                        activity.words.get("Exit")});
        sideMenuAdapter.TextSize = 40;
        sideMenuAdapter.TextStyle = BOLD;
        sideMenu.setAdapter(sideMenuAdapter);
        adapter = new Adapter(activity);
        adapter.textView = textView;
        lastType = "PvE";
        adapter.reset = reset;
        field.setLayoutManager(new GridLayoutManager(activity,3));
        field.setAdapter(adapter);
    }
}
