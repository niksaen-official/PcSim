package com.niksaen.pcsim.program;

import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.PortableView;

public class Warning extends Program {

    private ConstraintLayout layout;
    private LayoutInflater layoutInflater;
    private Typeface font;
    private View mainWindow;
    private boolean result;

    public Warning(Context context, ConstraintLayout layout){
        this.layout = layout;
        layoutInflater =LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }
    public void warn(String text){
        this.status = 0;
        result = false;
        mainWindow = layoutInflater.inflate(R.layout.program_warning,null);

        mainWindow.setScaleY(0.5f);
        mainWindow.setScaleX(0.5f);
        PortableView portableView = new PortableView(mainWindow);

        TextView title = mainWindow.findViewById(R.id.title),
                message = mainWindow.findViewById(R.id.text);
        Button cancel = mainWindow.findViewById(R.id.button6),
                save = mainWindow.findViewById(R.id.button5);

        cancel.setTypeface(font,Typeface.BOLD);
        save.setTypeface(font,Typeface.BOLD);
        title.setTypeface(font,Typeface.BOLD);
        message.setTypeface(font,Typeface.BOLD);
        message.setText(text);

        cancel.setOnClickListener(v -> {
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        });
        save.setOnClickListener(v -> {
            result = true;
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        });
        mainWindow.findViewById(R.id.close).setOnClickListener(v -> {
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        });
        layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
    @Override
    public void closeProgram() {
        mainWindow.setVisibility(View.GONE);
        this.status = -1;
    }
}
