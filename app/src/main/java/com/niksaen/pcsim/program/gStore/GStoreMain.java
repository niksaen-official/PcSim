package com.niksaen.pcsim.program.gStore;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;

public class GStoreMain extends Program {

    AppDescriptionPage appDescriptionPage;
    public GStoreMain(MainActivity activity) {
        super(activity);
        Title = "GStore";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
    }

    private ListView appList;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer,null);
        initAdapter();
        initViewAndStyle();

        appList.setOnItemClickListener((parent, view, position, id) -> {
            appDescriptionPage = new AppDescriptionPage(activity);
            appDescriptionPage.ProgramForInstall = adapter.getItem(position);
            appDescriptionPage.openProgram();
        });
    }
    GStoreAdapter adapter;
    private void initAdapter(){
        adapter = new GStoreAdapter(activity.getBaseContext(),0);
    }

    int buttonClicks = 0;
    private void initViewAndStyle(){
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        appList = mainWindow.findViewById(R.id.main);

        appList.setBackgroundColor(Color.parseColor("#0042A5"));
        appList.setAdapter(adapter);

        mainWindow.setBackgroundColor(Color.parseColor("#002C6E"));
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        buttonClose.setBackgroundResource(R.drawable.button_1_color17);
        buttonFullscreenMode.setBackgroundResource(R.drawable.button_2_2_color17);
        buttonRollUp.setBackgroundResource(R.drawable.button_3_color17);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));

        // настройка кнопок
        buttonClose.setOnClickListener(v->closeProgram(1));
        buttonFullscreenMode.setOnClickListener(v -> {
            if (buttonClicks== 0) {
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(R.drawable.button_2_1_color17);
                buttonClicks = 1;
            } else {
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener((v1, event) -> false);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(R.drawable.button_2_2_color17);
                buttonClicks = 0;
            }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram(1));
    }
}
