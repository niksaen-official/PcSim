package com.niksaen.pcsim.program.paint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static com.niksaen.pcsim.R.*;

public class PaintRecyclerView extends RecyclerView.Adapter<PaintRecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> dataList;
    private Context context;
    private TextView current_color;

    PaintCanvas paintCanvas;

    Activity activity;

    public PaintRecyclerView(Context context, List<String> dataList,TextView textView,PaintCanvas paintCanvas){
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.current_color = textView;
        this.paintCanvas = paintCanvas;
    }

    public PaintRecyclerView(Context context, String[] dataList) {
        this.dataList = Arrays.asList(dataList);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PaintRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(layout.item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintRecyclerView.ViewHolder viewHolder, int position) {
        final String s = dataList.get(position);
        viewHolder.button.setBackgroundColor(Color.parseColor(s));
        viewHolder.button.setOnClickListener(v -> {
            current_color.setBackgroundColor(Color.parseColor(s));
            paintCanvas.strokeColor = Color.parseColor(s);
            paintCanvas.setStyle();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final Button button;

        ViewHolder(View view) {
            super(view);
            button = view.findViewById(id.current_color);
        }
    }
}