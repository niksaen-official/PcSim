package com.niksaen.pcsim.program.fileManager;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.fileWorkLib.FileUtil;

import java.util.ArrayList;

public class FileManagerListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> objects;
    private Context context;

    private Typeface typeface;

    public FileManagerListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public ArrayList<String>  getObjects() {
        return objects;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }

    public int ColorBackground,ColorText;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.item_for_filemanager,null);
        layout.setBackgroundColor(ColorBackground);
        TextView text = layout.findViewById(R.id.text);
        ImageView imageView = layout.findViewById(R.id.image);
        String fileName = objects.get(position);
        imageView.setImageResource(setFileIcons(fileName));
        text.setTypeface(typeface,Typeface.BOLD);
        text.setText(fileName.substring(fileName.lastIndexOf("/")+1));
        text.setTextColor(ColorText);
        if(fileName.equals("Папка пуста")){
            imageView.setVisibility(View.GONE);
            text.setText("Папка пуста");
            layout.setClickable(false);
        }
        return layout;
    }
    int setFileIcons(String fileName){
        if(fileName.endsWith(".txt")){
            return R.drawable.text_file;
        }
        else if(FileUtil.isDirectory(fileName)){
            return R.drawable.folder_icon;
        }
        else if(fileName.endsWith(".mp3")){
            return R.drawable.music_files;
        }
        else if(fileName.endsWith(".mp4")){
            return R.drawable.video_file;
        }
        else if(fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".jpe")){
            return R.drawable.image_file;
        }
        else{
            return R.drawable.other_file;
        }
    }
}
