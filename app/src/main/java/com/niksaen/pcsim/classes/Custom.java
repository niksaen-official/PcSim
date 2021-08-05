package com.niksaen.pcsim.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.niksaen.pcsim.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Custom {
    static Activity activity;
    AssetFile assetFile;

    public Custom(Activity activity) {
        Custom.activity = activity;
        assetFile = new AssetFile(activity);
    }

    public void CustomSpinnerPc(Spinner spinner,
                                final ArrayList<String> array, final String text) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.for_spinner, array) {
            final Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf");

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(Custom.activity);
                View v = layoutInflater.inflate(R.layout.for_spinner_pc,null);
                ImageView imageView = v.findViewById(R.id.image);
                imageView.setVisibility(View.GONE);
                TextView textView = v.findViewById(R.id.text);
                textView.setTypeface(font, Typeface.BOLD);
                textView.setPadding(4,4,4,4);
                textView.setTextSize(28);
                textView.setText(text.toUpperCase());
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(Custom.activity);
                View v = layoutInflater.inflate(R.layout.for_spinner_pc,null);
                ImageView imageView = v.findViewById(R.id.image);
                if(position>0) {
                    imageView.setImageDrawable(assetFile.getImage("pc_component/parameters/images/" + text + "/" + array.get(position) + ".png"));
                }
                else{
                    imageView.setVisibility(View.GONE);
                }
                TextView textView = v.findViewById(R.id.text);
                textView.setTypeface(font, Typeface.BOLD);
                textView.setText(array.get(position));
                return v;
            }
        };
        spinner.setAdapter(adapter);
    }

    public void CustomSpinnerPc(Spinner spinner,
                                final String[] array, final String text) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.for_spinner, array) {
            final Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf");

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setText(text);
                ((TextView) v).setTypeface(font, Typeface.BOLD);
                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(font, Typeface.BOLD);
                return v;
            }
        };
        spinner.setAdapter(adapter);
    }

    /**
     * используется для вывода об ошибках игрока спустя время сам закрывается
     */
    public static void ErrorDialog(String text,final Activity activity) {
        final Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf");

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_error, null);

        TextView textView = layout.findViewById(R.id.textView);
        textView.setText(text);
        textView.setTypeface(font, Typeface.BOLD);

        builder.setView(layout);
        final AlertDialog dialog=builder.create();
        dialog.show();
        final Timer timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        timer.cancel();
                    }
                });
            }
        };
        timer.schedule(timerTask,2000);
    }
}
