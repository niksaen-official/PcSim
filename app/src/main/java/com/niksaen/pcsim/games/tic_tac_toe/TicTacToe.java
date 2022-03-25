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
        ValueRam = new int[]{1025,1224};
        ValueVideoMemory = new int[]{400,500};
    }

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.game_tictactoe,null);
        initView();
        style();
        activity.toolbar.setVisibility(View.GONE);
        pause.setOnClickListener(v->{
            layout.openDrawer(sideMenu);
        });
        sideMenu.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 1){
                fieldAdapter = new Field(activity);
                fieldAdapter.Type = "PvP";
                lastType = "PvP";
                fieldAdapter.textView = textView;
                field.setAdapter(fieldAdapter);
                fieldAdapter.reset = reset;
                textView.setText("");
                reset.setVisibility(View.GONE);
            }
            if(position == 2){
                fieldAdapter = new Field(activity);
                fieldAdapter.textView = textView;
                field.setAdapter(fieldAdapter);
                lastType = "PvE";
                fieldAdapter.reset = reset;
                textView.setText("");
                reset.setVisibility(View.GONE);
            }
            if(position == 3){
                closeProgram(1);
            }
        });
        reset.setOnClickListener(v->{
            fieldAdapter = new Field(activity);
            fieldAdapter.Type = lastType;
            fieldAdapter.textView = textView;
            fieldAdapter.reset = reset;
            field.setAdapter(fieldAdapter);
            textView.setText("");
            reset.setVisibility(View.GONE);
        });

    }
    TextView textView;
    ListView sideMenu;
    RecyclerView field;
    Field fieldAdapter;
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
        fieldAdapter = new Field(activity);
        fieldAdapter.textView = textView;
        lastType = "PvE";
        fieldAdapter.reset = reset;
        field.setLayoutManager(new GridLayoutManager(activity,3));
        field.setAdapter(fieldAdapter);
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
