package com.niksaen.pcsim.program.notepad;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.FileUtil;
import com.niksaen.pcsim.fileWorkLib.FilePermission;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.fileManager.FileManagerListViewAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NotepadFileSave extends Program {
    public NotepadFileSave(MainActivity activity){
        super(activity);
        this.Title = "Saving a file";
        ValueRam = new int[]{30,50};
        ValueVideoMemory = new int[]{10,15};
    }

    String buffPath = "",buffPath2;

    Button saveButton,pageDown;
    EditText fileName;
    ListView listView;

    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_for_save_file,null);
        titleTextView = mainWindow.findViewById(R.id.title);
        saveButton = mainWindow.findViewById(R.id.button4);
        fileName = mainWindow.findViewById(R.id.fileName);
        listView = mainWindow.findViewById(R.id.listView);
        pageDown = mainWindow.findViewById(R.id.pageDown);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonClose = mainWindow.findViewById(R.id.close);
    }

    FileManagerListViewAdapter listViewAdapter;
    ArrayList<String> folders;
    private void style(){
        fileName.setTypeface(activity.font,Typeface.BOLD);
        saveButton.setTypeface(activity.font,Typeface.BOLD);
        saveButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        saveButton.setTextColor(activity.styleSave.TextButtonColor);
        fileName.setTextColor(activity.styleSave.TextColor);
        fileName.setBackgroundColor(activity.styleSave.ThemeColor2);
        fileName.setHintTextColor(activity.styleSave.TextColor);
        pageDown.setBackgroundResource(activity.styleSave.ArrowButtonImage);

        //adapter settings
        // "//storage/emulated/0/"
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
    public void openProgram(final String textFile){
        initView();
        style();
        boolean hasPermission = FilePermission.checkStoragePermission(activity);
        if(hasPermission){
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }else {
            FilePermission.requestStoragePermission(activity);
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (FileUtil.isDirectory(folders.get(position))) {
                buffPath = folders.get(position);
                buffPath2 = folders.get(position) + "/";
                FileUtil.listDir(folders.get(position), folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            } else if (folders.get(position).endsWith(".txt")) {
                if (buff != null) {
                    buff.setBackgroundColor(activity.styleSave.ThemeColor1);
                }
                view.setBackgroundColor(activity.styleSave.ThemeColor2);
                buff = view;
                buffPath = folders.get(position);
                fileName.setText(buffPath.substring(buffPath.lastIndexOf("/") + 1).replace(".txt", ""));
            }
        });

        saveButton.setOnClickListener(v -> {
            if (FileUtil.isDirectory(buffPath)) {
                if (fileName.getText() != null) {
                    writeTextInFile(buffPath+"/" + fileName.getText().toString() + ".txt", textFile);
                    FileUtil.listDir(buffPath, folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                    closeProgram(1);
                }
            } else if (buffPath.endsWith(".txt")) {
                if (fileName.getText() != null) {
                    writeTextInFile(buffPath, textFile);
                    FileUtil.listDir(buffPath, folders);
                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                    closeProgram(1);
                }
            }
        });
        pageDown.setOnClickListener(v -> {
            if (buffPath2.contains( "//storage/emulated/0/") && buffPath2 !=  "//storage/emulated/0/") {
                v.setVisibility(View.VISIBLE);
                buffPath2 = buffPath2.substring(0, buffPath2.lastIndexOf("/"));
                FileUtil.listDir(buffPath2, folders);
                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
        });
        initProgram();
        super.openProgram();
    }

    private void writeTextInFile(String path,String text){
        try(FileOutputStream fos=new FileOutputStream(path))
        {
            // перевод строки в байты
            byte[] buffer = text.getBytes();

            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.out.println("The file has been written");
    }
}
