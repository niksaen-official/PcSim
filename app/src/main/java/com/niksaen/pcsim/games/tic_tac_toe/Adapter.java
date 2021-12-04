package com.niksaen.pcsim.games.tic_tac_toe;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.Others;

import org.jetbrains.annotations.NotNull;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private final String TAG_X = "a";
    private final String TAG_O = "b";
    private final String TAG_EMPTY = "0";

    public final String MODE_PVE = "PvE";
    public final String MODE_PVP = "PvP";

    public String[] field = {
            TAG_EMPTY,TAG_EMPTY,TAG_EMPTY,
            TAG_EMPTY,TAG_EMPTY,TAG_EMPTY,
            TAG_EMPTY,TAG_EMPTY,TAG_EMPTY
    };
    public Activity activity;
    public TextView textView;
    public Button reset;
    public Adapter(Activity activity){
        this.activity = activity;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tictacteo_cell, parent, false);
        return new ViewHolder(view);
    }

    int count = 0;
    public int isPlayer = 1;
    public String Type = MODE_PVE;
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {
        if(field[position] == TAG_O){
            holder.cell.setBackgroundResource(R.drawable.circle);
        }
        else if(field[position] == TAG_X){
            holder.cell.setBackgroundResource(R.drawable.cross);
        }
        holder.cell.setOnClickListener(v->{
            if (isPlayer == 1 && count<8){
                if(field[position] == TAG_EMPTY){
                    field[position] = TAG_X;
                    isPlayer = 0;
                    count++;
                    holder.cell.setBackgroundResource(R.drawable.cross);
                    if(validateForWin() == TAG_X){
                        textView.setText(validateForWin());
                        isPlayer = -1;
                        reset.setVisibility(View.VISIBLE);
                    }
                    if (Type == MODE_PVE && count<8) {
                        bot();
                    }
                }
            }
            if(isPlayer == 0 && Type == MODE_PVP && count<8){
                if(field[position] == TAG_EMPTY){
                    field[position] = TAG_O;
                    isPlayer = 1;
                    count++;
                    holder.cell.setBackgroundResource(R.drawable.circle);
                    if(validateForWin() == TAG_O){
                        textView.setText(validateForWin());
                        isPlayer = -1;
                        reset.setVisibility(View.VISIBLE);
                    }
                }
            }
            if(count == 8){
                reset.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public int getItemCount() {
        return field.length;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button cell;

        ViewHolder(View view) {
            super(view);
            cell = view.findViewById(R.id.cell);
        }
    }
    public String validateForWin(){
        if((field[0] == TAG_X && field[1] == TAG_X && field[2] == TAG_X)||//p,p,p,*,*,*,*,*,*
                (field[3] == TAG_X && field[4] == TAG_X && field[5] == TAG_X)||//*,*,*,p,p,p,*,*,*
                (field[6] == TAG_X && field[7] == TAG_X && field[8] == TAG_X)||// *,*,*,*,*,*,p,p,p,
                (field[0] == TAG_X && field[3] == TAG_X && field[6] == TAG_X)||//p,*,*,p,*,*,p,*,*
                (field[1] == TAG_X && field[4] == TAG_X && field[7] == TAG_X)||//*,p,*,*,p,*,*,p,*
                (field[2] == TAG_X && field[5] == TAG_X && field[8] == TAG_X)||// *,*,p,*,*,p,*,*,p,
                (field[0] == TAG_X && field[4] == TAG_X && field[8] == TAG_X)||//p,*,*,*,p,*,*,*,p
                (field[2] == TAG_X && field[4] == TAG_X && field[6] == TAG_X)//*,*,p,*,p,*,p,*,*
        ){
            return TAG_X;
        }
        else if((field[0] == TAG_O && field[1] == TAG_O && field[2] == TAG_O)||
                (field[3] == TAG_O && field[4] == TAG_O && field[5] == TAG_O)||
                (field[6] == TAG_O && field[7] == TAG_O && field[8] == TAG_O)||
                (field[0] == TAG_O && field[3] == TAG_O && field[6] == TAG_O)||
                (field[1] == TAG_O && field[4] == TAG_O && field[7] == TAG_O)||
                (field[2] == TAG_O && field[5] == TAG_O && field[8] == TAG_O)||
                (field[0] == TAG_O && field[4] == TAG_O && field[8] == TAG_O)||
                (field[2] == TAG_O && field[4] == TAG_O && field[6] == TAG_O)
        ){
            return TAG_O;
        }
        else{
            return "Draw";
        }
    }
    public String validateForWin(String[] field2){
        if((field2[0] == TAG_X && field2[1] == TAG_X && field2[2] == TAG_X)||//p,p,p,*,*,*,*,*,*
                (field2[3] == TAG_X && field2[4] == TAG_X && field2[5] == TAG_X)||//*,*,*,p,p,p,*,*,*
                (field2[6] == TAG_X && field2[7] == TAG_X && field2[8] == TAG_X)||// *,*,*,*,*,*,p,p,p,
                (field2[0] == TAG_X && field2[3] == TAG_X && field2[6] == TAG_X)||//p,*,*,p,*,*,p,*,*
                (field2[1] == TAG_X && field2[4] == TAG_X && field2[7] == TAG_X)||//*,p,*,*,p,*,*,p,*
                (field2[2] == TAG_X && field2[5] == TAG_X && field2[8] == TAG_X)||// *,*,p,*,*,p,*,*,p,
                (field2[0] == TAG_X && field2[4] == TAG_X && field2[8] == TAG_X)||//p,*,*,*,p,*,*,*,p
                (field2[2] == TAG_X && field2[4] == TAG_X && field2[6] == TAG_X)//*,*,p,*,p,*,p,*,*
        ){
            return TAG_X;
        }
        else if((field2[0] == TAG_O && field2[1] == TAG_O && field2[2] == TAG_O)||
                (field2[3] == TAG_O && field2[4] == TAG_O && field2[5] == TAG_O)||
                (field2[6] == TAG_O && field2[7] == TAG_O && field2[8] == TAG_O)||
                (field2[0] == TAG_O && field2[3] == TAG_O && field2[6] == TAG_O)||
                (field2[1] == TAG_O && field2[4] == TAG_O && field2[7] == TAG_O)||
                (field2[2] == TAG_O && field2[5] == TAG_O && field2[8] == TAG_O)||
                (field2[0] == TAG_O && field2[4] == TAG_O && field2[8] == TAG_O)||
                (field2[2] == TAG_O && field2[4] == TAG_O && field2[6] == TAG_O)
        ){
            return TAG_O;
        }
        else{
            return "Draw";
        }
    }
    private void bot(){
        while (true) {
            int cellId = Others.random(0, 8);
            if (field[cellId] == TAG_EMPTY) {
                field[cellId] = TAG_O;
                isPlayer = 1;
                count++;
                notifyItemChanged(cellId);
                if (validateForWin() == TAG_O) {
                    textView.setText(validateForWin());
                    isPlayer = -1;
                    reset.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
}
