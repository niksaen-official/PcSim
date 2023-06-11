package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.save.Settings;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;

public class EditTextAdapter extends RecyclerView.Adapter<EditTextAdapter.ViewHolder>{

    String hint;
    HashMap<String,String> words;

    private final LayoutInflater layoutInflater;
    private final Typeface typeface;
    private final StyleSave styleSave;
    private ViewHolder viewHolder;

    public EditTextAdapter(Context context, StyleSave styleSave){
        layoutInflater = LayoutInflater.from(context);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        BackgroundColor = styleSave.ThemeColor1;
        getTranslate(context);
        hint = words.get("Enter greeting text");
        this.styleSave = styleSave;
    }

    int BackgroundColor;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_edittext,viewGroup,false);
        view.setBackgroundColor(BackgroundColor);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.editText.setTypeface(typeface);
        viewHolder.editText.setHint(hint);
        viewHolder.editText.setHintTextColor(styleSave.ThemeColor3);
        viewHolder.editText.setTextColor(styleSave.TextColor);
        viewHolder.editText.setText(styleSave.Greeting);
    }
    public String getCurrentText(){
        if(viewHolder != null) {
            return viewHolder.editText.getText().toString();
        }else{
            return styleSave.Greeting;
        }
    }
    private void getTranslate(Context context){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ new Settings(context).Language+".json"),typeToken.getType());
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        EditText editText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.editText);
        }
    }
}

