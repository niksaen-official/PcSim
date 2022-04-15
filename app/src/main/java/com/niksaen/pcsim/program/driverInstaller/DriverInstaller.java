package com.niksaen.pcsim.program.driverInstaller;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.program.Program;

public class DriverInstaller extends Program {
    public static String AdditionalSoftPrefix = "SOFT: ";
    public static String DriversPrefix = "Driver for";

    public static String DriverForCPU = "Driver for CPU:";
    public static String DriverForGPU = "Driver for graphics card:";
    public static String DriverForRAM = "Driver for RAM:";
    public static String DriverForStorageDevices = "Driver for drive:";
    public static String DriverForMotherboard = "Driver for motherboard:";

    public static String BASE_TYPE = "Type: Base";
    public static String EXTENDED_TYPE = "Type: Extended";

    public DriverInstaller(MainActivity activity){
        super(activity);
        Title = "Driver installer";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{50,75};
    }
    private String selectedItems="";
    private String driveID;
    private final String[] strings = new String[]{
            DriverForCPU,
            DriverForGPU,
            DriverForRAM,
            DriverForStorageDevices,
            DriverForMotherboard
    };

    private ListView selectedComponent;
    private TextView description;
    private Button next,cancel;
    private Spinner drive;
    private CheckBox checkBox;
    private View main;

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_driver_installer,null);
        initView();
        style();
        drive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    driveID = activity.pcParametersSave.getDiskList()[position-1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectedComponent.setOnItemClickListener((parent, view, position, id) -> {
            selectedItems = "";
            SparseBooleanArray selected=selectedComponent.getCheckedItemPositions();
            for(int i=0;i < strings.length;i++)
            {
                if(selected.get(i) && !selectedItems.contains(strings[i])) selectedItems+=strings[i]+",";
            }
        });
        next.setOnClickListener(v -> {
            ChangeComponent changeComponent = new ChangeComponent(activity);
            changeComponent.TypeComponent = selectedItems;
            changeComponent.Extended = checkBox.isChecked();
            changeComponent.DriveID = driveID;
            changeComponent.openProgram();
        });

        super.initProgram();
    }
    private void initView(){
        selectedComponent = mainWindow.findViewById(R.id.selectFor);
        next = mainWindow.findViewById(R.id.next);
        cancel = mainWindow.findViewById(R.id.cancel);
        description = mainWindow.findViewById(R.id.description);
        checkBox = mainWindow.findViewById(R.id.isExtended);
        main = mainWindow.findViewById(R.id.main);
        drive = mainWindow.findViewById(R.id.drive);
    }
    private void style(){
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {activity.styleSave.ThemeColor3, activity.styleSave.ThemeColor3};
        ColorStateList stateList = new ColorStateList(states, colors);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.item_for_driver_installer_listview, strings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                CheckedTextView view = (CheckedTextView) super.getView(position, convertView, parent);
                view.setText(activity.words.get(strings[position]).replace(": ",""));
                view.setTypeface(activity.font);
                view.setTextColor(activity.styleSave.TextColor);
                view.setBackgroundColor(activity.styleSave.ThemeColor1);
                view.setCheckMarkTintList(stateList);
                return view;
            }
        };
        selectedComponent.setAdapter(adapter);
        CustomListViewAdapter driveAdapter = new CustomListViewAdapter(activity,0, StringArrayWork.add(activity.words.get("Select drive:"),activity.pcParametersSave.getDiskList()));
        driveAdapter.TextColor = activity.styleSave.TextColor;
        driveAdapter.BackgroundColor2 = activity.styleSave.ThemeColor1;
        driveAdapter.BackgroundColor1 = activity.styleSave.ThemeColor1;
        drive.setAdapter(driveAdapter);

        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        cancel.setBackgroundColor(activity.styleSave.ThemeColor2);
        next.setBackgroundColor(activity.styleSave.ThemeColor2);
        cancel.setTypeface(activity.font, Typeface.BOLD);
        next.setTypeface(activity.font, Typeface.BOLD);
        description.setTypeface(activity.font);
        checkBox.setTypeface(activity.font);
        cancel.setTextColor(activity.styleSave.TextButtonColor);
        next.setTextColor(activity.styleSave.TextButtonColor);
        description.setTextColor(activity.styleSave.TextColor);
        checkBox.setTextColor(activity.styleSave.TextColor);
        checkBox.setButtonTintList(stateList);

        cancel.setText(activity.words.get("Cancel"));
        next.setText(activity.words.get("Next"));
        description.setText(activity.words.get("Select the type of drivers, the drive to install them and the accessories for which you want to install the drivers"));
        checkBox.setText(activity.words.get("Type: Extended"));
    }
}
