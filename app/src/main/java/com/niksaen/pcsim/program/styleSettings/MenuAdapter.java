package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.save.Settings;

import java.util.HashMap;

public class MenuAdapter extends BaseExpandableListAdapter {

    private String[] listGroup;
    private HashMap<String,String[]> listChild = new HashMap<>();
    private LayoutInflater layoutInflater;
    private Typeface font;
    Context context;

    public MenuAdapter(Context context){
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        layoutInflater = LayoutInflater.from(context);
        getLanguage();
    }
    private void getLanguage(){
        HashMap<String,String> words;
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ new Settings(context).Language+".json"),typeToken.getType());
        listGroup = new String[]{
                words.get("Background"),
                words.get("Window"),
                words.get("Colors"),
                words.get("Start"),
                words.get("Task bar"),
                words.get("ProgressBar"),
                words.get("SeekBar"),
                words.get("Greeting"),
                words.get("Desktop")
        };
        listChild.put(listGroup[0],new String[]{words.get("Color"), words.get("Gradient"), words.get("Wallpaper")});
        listChild.put(listGroup[1],new String[]{words.get("Window Color"),words.get("Button Color"),words.get("Title Color")});
        listChild.put(listGroup[2],new String[]{words.get("Theme"),words.get("Text Color"),words.get("Button text color")});
        listChild.put(listGroup[3],new String[]{words.get("Color"),words.get("Text Color"),words.get("Other")});
        listChild.put(listGroup[4],new String[]{words.get("Color"),words.get("Text Color"),words.get("Other")});
        listChild.put(listGroup[5],new String[]{words.get("Color"),words.get("Background")});
        listChild.put(listGroup[6],new String[]{words.get("Color of progress"),words.get("Slider color"),words.get("Background")});
        listChild.put(listGroup[7],new String[]{words.get("Greeting color"),words.get("Greeting text")});
        listChild.put(listGroup[8],new String[]{words.get("Text Color"),words.get("Other")});
    }

    public int getGroupCount() {
        return listGroup.length;
    }

    public int getChildrenCount(int groupPosition) {
        return listChild.get(listGroup[groupPosition]).length;
    }

    public String getGroup(int groupPosition) {
        return listGroup[groupPosition];
    }

    public String getChild(int groupPosition, int childPosition) { return listChild.get(listGroup[groupPosition])[childPosition]; }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    public int textColor = Color.parseColor("#000000");
    public int backgroundGroupColor = Color.parseColor("#FFFFFF");
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView = (TextView)layoutInflater.inflate(R.layout.group_list_item,null);
        textView.setBackgroundColor(backgroundGroupColor);
        textView.setTextColor(textColor);
        textView.setTypeface(font,Typeface.BOLD);
        textView.setText(getGroup(groupPosition));
        return textView;
    }

    public int backgroundChildColor = Color.parseColor("#dddddd");
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = (TextView)layoutInflater.inflate(R.layout.child_list_item,null);
        textView.setBackgroundColor(backgroundChildColor);
        textView.setTextColor(textColor);
        textView.setTypeface(font);
        textView.setText(getChild(groupPosition,childPosition));
        return textView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
