package com.niksaen.pcsim.program.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.niksaen.pcsim.R.id;
import static com.niksaen.pcsim.R.layout;

public class NumpadAdapter extends RecyclerView.Adapter<NumpadAdapter.ViewHolder> {

    private Context context;
    private String[] symbolList = {
            "C","(",")","/",
            "7","8","9","*",
            "4","5","6","-",
            "1","2","3","+",
            "0",".","n","n",
    };
    public NumpadAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public NumpadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout.item_button_50dp, parent, false);
        return new ViewHolder(view);
    }

    public TextView InputField,OutputField;
    public int BackgroundColor;
    public int TextColor;
    @Override
    public void onBindViewHolder(@NonNull NumpadAdapter.ViewHolder viewHolder, int position) {
        viewHolder.button.setText(symbolList[position]);
        if(symbolList[position].equals("n")){
            viewHolder.itemView.setVisibility(View.INVISIBLE);
            viewHolder.button.setClickable(false);
        }

        if(symbolList[position].equals("C")){
            viewHolder.button.setOnClickListener(v->{
                 InputField.setText("");
                OutputField.setText("");
            });
        }
        else if(symbolList[position].equals("(")){
            viewHolder.button.setOnClickListener(v-> {
                InputField.setText(InputField.getText().toString() + "( ");
            });
        }
        else if(symbolList[position].equals(")")){
            viewHolder.button.setOnClickListener(v-> {
                InputField.setText(InputField.getText().toString() + " )");
            });
        }
        else if(symbolList[position].equals("+")|| symbolList[position].equals("-")||symbolList[position].equals("*")||symbolList[position].equals("/")){
            viewHolder.button.setOnClickListener(v->{
                InputField.setText(InputField.getText().toString()+" "+symbolList[position]+" ");
            });
        }
    }

    @Override
    public int getItemCount() {
        return symbolList.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final Button button;

        ViewHolder(View view) {
            super(view);
            button = view.findViewById(id.button);
            button.setBackgroundColor(BackgroundColor);
            button.setTextColor(TextColor);
            button.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf"));
            button.setOnClickListener(v->{
                InputField.setText(InputField.getText().toString()+button.getText().toString());
            });
        }
    }
}