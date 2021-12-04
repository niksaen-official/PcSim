package com.niksaen.pcsim.games.tic_tac_toe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.Others;

import org.jetbrains.annotations.NotNull;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    public String[] field = {
            "0","0","0",
            "0","0","0",
            "0","0","0"
    };
    public MainActivity activity;
    public TextView textView;
    public Button reset;
    public Adapter(MainActivity activity){
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
    public String Type = "PvE";
    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {
        if(field[position] == "b"){
            holder.cell.setBackgroundResource(R.drawable.circle);
        }
        else if(field[position] == "a"){
            holder.cell.setBackgroundResource(R.drawable.cross);
        }
        holder.cell.setOnClickListener(v->{
            if (isPlayer == 1 && count<8){
                if(field[position] == "0"){
                    field[position] = "a";
                    isPlayer = 0;
                    count++;
                    holder.cell.setBackgroundResource(R.drawable.cross);
                    if(validateForWin() == "Win cross"){
                        textView.setText(activity.words.get("Cross win"));
                        isPlayer = -1;
                        reset.setVisibility(View.VISIBLE);
                    }
                    if (Type == "PvE" && count<8) {
                        bot();
                    }
                }
            }
            if(isPlayer == 0 && Type == "PvP" && count<8){
                if(field[position] == "0"){
                    field[position] = "b";
                    isPlayer = 1;
                    count++;
                    holder.cell.setBackgroundResource(R.drawable.circle);
                    if(validateForWin() == "Win circle"){
                        textView.setText(activity.words.get("Circle win"));
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
        if((field[0] == "a" && field[1] == "a" && field[2] == "a")||//p,p,p,*,*,*,*,*,*
                (field[3] == "a" && field[4] == "a" && field[5] == "a")||//*,*,*,p,p,p,*,*,*
                (field[6] == "a" && field[7] == "a" && field[8] == "a")||// *,*,*,*,*,*,p,p,p,
                (field[0] == "a" && field[3] == "a" && field[6] == "a")||//p,*,*,p,*,*,p,*,*
                (field[1] == "a" && field[4] == "a" && field[7] == "a")||//*,p,*,*,p,*,*,p,*
                (field[2] == "a" && field[5] == "a" && field[8] == "a")||// *,*,p,*,*,p,*,*,p,
                (field[0] == "a" && field[4] == "a" && field[8] == "a")||//p,*,*,*,p,*,*,*,p
                (field[2] == "a" && field[4] == "a" && field[6] == "a")//*,*,p,*,p,*,p,*,*
        ){
            return "Win cross";
        }
        else if((field[0] == "b" && field[1] == "b" && field[2] == "b")||
                (field[3] == "b" && field[4] == "b" && field[5] == "b")||
                (field[6] == "b" && field[7] == "b" && field[8] == "b")||
                (field[0] == "b" && field[3] == "b" && field[6] == "b")||
                (field[1] == "b" && field[4] == "b" && field[7] == "b")||
                (field[2] == "b" && field[5] == "b" && field[8] == "b")||
                (field[0] == "b" && field[4] == "b" && field[8] == "b")||
                (field[2] == "b" && field[4] == "b" && field[6] == "b")
        ){
            return "Win circle";
        }
        else{
            return "Draw";
        }
    }
    private void bot(){
        while(true){
            int cellId = Others.random(0,8);
            if(field[cellId]=="0"){
                field[cellId] = "b";
                isPlayer = 1;
                count++;
                notifyItemChanged(cellId);
                if(validateForWin() == "Win circle"){
                    textView.setText(activity.words.get("Circle win"));
                    isPlayer = -1;
                    reset.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
    private void botV2(){
        if(field[0] == "a" && field[2]=="0") {field[2] = "b"; }
        else if(field[0] == "a" && field[1]=="0") {field[1] = "b"; }
        else if(field[0] == "a" && field[3]=="0") {field[3] = "b"; }
        else if(field[0] == "a" && field[6]=="0") {field[6] = "b"; }

        else if(field[1] == "a" && field[0]=="0") {field[0] = "b"; }
        else if(field[1] == "a" && field[2]=="0") {field[2] = "b"; }
        else if(field[1] == "a" && field[7]=="0") {field[7] = "b"; }

        else if(field[2] == "a" && field[5]=="0") {field[5] = "b"; }
        else if(field[2] == "a" && field[7]=="0") {field[7] = "b"; }
        else if(field[2] == "a" && field[0]=="0") {field[0] = "b"; }

        else if(field[3] == "a" && field[0]=="0") {field[0] = "b"; }
        else if(field[3] == "a" && field[6]=="0") {field[6] = "b"; }
        else if(field[3] == "a" && field[5]=="0") {field[5] = "b"; }
    }
}
