package com.niksaen.pcsim.program.paint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.FileUtil;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.fileManager.FileManagerListViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PaintSaveFile extends Program {

    public PaintSaveFile(MainActivity activity){
        super(activity);
        this.Title ="Saving a file";
    }


    String buffPath = "",buffPath2 = "";

    Button saveButton,pageDown;
    EditText fileName;
    ListView listView;

    private void initView(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_for_save_file,null);
        titleTextView = mainWindow.findViewById(R.id.title);
        saveButton = mainWindow.findViewById(R.id.button4);
        fileName = mainWindow.findViewById(R.id.fileName);
        listView = mainWindow.findViewById(R.id.listView);
        pageDown = mainWindow.findViewById(R.id.pageDown);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
    }

    FileManagerListViewAdapter listViewAdapter;
    ArrayList<String> folders;
    private void style(){
        fileName.setTypeface(activity.font,Typeface.BOLD);
        saveButton.setTypeface(activity.font,Typeface.BOLD);
        saveButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        saveButton.setTextColor(activity.styleSave.TextButtonColor);
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        fileName.setTextColor(activity.styleSave.TextColor);
        fileName.setBackgroundColor(activity.styleSave.ThemeColor2);
        fileName.setHintTextColor(activity.styleSave.TextColor);
        pageDown.setBackgroundResource(activity.styleSave.ArrowButtonImage);

        //adapter settings
        folders = new ArrayList<>();
        FileUtil.listDir("//storage/emulated/0/",folders);
        listViewAdapter = new FileManagerListViewAdapter(activity.getBaseContext(),R.layout.item_textview,folders);
        listViewAdapter.ColorBackground = activity.styleSave.ThemeColor1;
        listViewAdapter.ColorText = activity.styleSave.TextColor;
        listView.setAdapter(listViewAdapter);
        listView.setBackgroundColor(activity.styleSave.ThemeColor1);

        //set text
        saveButton.setText(activity.words.get("Save"));
        fileName.setHint(activity.words.get("Enter file name")+":");
    }

    View buff;
    public void openProgram(final View view){
        initView();
        style();
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (FileUtil.isDirectory(folders.get(position))) {
                buffPath = folders.get(position);
                buffPath2 = folders.get(position) + "/";
                FileUtil.listDir(folders.get(position), folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            } else if (folders.get(position).endsWith(".png") || folders.get(position).endsWith(".jpg")) {
                if (buff != null) {
                    buff.setBackgroundColor(activity.styleSave.ThemeColor1);
                }
                view1.setBackgroundColor(activity.styleSave.ThemeColor2);
                buff = view1;
                buffPath = folders.get(position);
                fileName.setText(buffPath.substring(buffPath.lastIndexOf("/") + 1).replace(".png", "").replace(".jpg", ""));
            }
        });
        saveButton.setOnClickListener(v -> {
            if (FileUtil.isDirectory(buffPath)) {
                if (fileName.getText() != null) {
                    saveImage(buffPath2 + fileName.getText().toString() + ".png", view);
                    fileName.setText("");
                    FileUtil.listDir(buffPath, folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                    closeProgram(1);
                }
            } else if (buffPath.endsWith(".png") || buffPath.endsWith(".jpg")) {
                saveImage(buffPath, view);
                FileUtil.listDir(buffPath, folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                closeProgram(1);
            }
        });
        pageDown.setOnClickListener(v -> {
            if (buffPath2.contains("/storage/emulated/0/") && buffPath2 != "/storage/emulated/0/") {
                v.setVisibility(View.VISIBLE);
                buffPath2 = buffPath2.substring(0, buffPath2.lastIndexOf("/"));
                FileUtil.listDir(buffPath2, folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
        });
        initProgram();
        super.openProgram();
    }
    void saveImage(String path, View view){
        FileUtil.writeFile(path,"");
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if(background != null){
            background.draw(canvas);
        }
        else{
            canvas.drawColor(-1);
        }

        view.draw(canvas);
        File file = new File(path);
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            createBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
