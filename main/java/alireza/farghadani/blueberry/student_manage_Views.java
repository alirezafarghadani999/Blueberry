package alireza.farghadani.blueberry;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import alireza.farghadani.blueberry.PublicClass.ShamsiDate;
import alireza.farghadani.blueberry.database.databasehelp_class;
import alireza.farghadani.blueberry.list_presentation_View.PresentationAdapter;
import alireza.farghadani.blueberry.list_presentation_View.PresentationItem;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewAdapter;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewItem;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

public class student_manage_Views extends AppCompatActivity implements PublicViewAdapter.Onlistener, PresentationAdapter.select_date, PublicViewAdapter.OnLongListener {

    String ViewLable, Username;
    int pos_click, class_id;

    RecyclerView recyclerView;
    List<PublicViewItem> list_studnet;
    List<PublicViewItem> list_pointTopic;
    List<PresentationItem> list_presentation;
    RecyclerView.Adapter adapter;



    databasehelp_class my_class_db;

    ImageButton float_btn;

    private int[] output_date = ShamsiDate.GetIntArrayShamsidate();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);
        getSupportActionBar().hide();



        float_btn = findViewById(R.id.float_btn);
        float_btn.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ViewLable = extras.getString("label");
            Username = extras.getString("user");
            class_id = extras.getInt("class_id");
            pos_click = extras.getInt("content");

            TextView pf_name = findViewById(R.id.name);
            pf_name.setText(Username);

            TextView label = findViewById(R.id.label);
            label.setText(ViewLable);
        }


        my_class_db = new databasehelp_class(getApplicationContext());

        recyclerView = findViewById(R.id.view_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        switch (pos_click){

            case 0:
                show_list_student();
                float_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show_add_st_Dialog();
                    }
                });
                break;
            case 1:
                peresentation();
                break;

            case 2:
                point_menu();
                float_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show_add_point_topic();
                    }
                });
                break;

            case 3:
                show_Analyze();
                break;


        }


    }

    public void show_Analyze() {
        String[] menu = {"رتبه بندی کلاس",
        "نمایش نموداری",
        "پیشبینی با AI"};
        String[] menu_Des = {
                "رتبه بندی دانش اموزان بر اساس نمره",
        "رسم نمودار عملکرد دانش اموزان و کلاس",
        "با استفاده از هوش مصنوعی عملکرد دانش اموز ها رو پیش بینی کن"};
        list_studnet = new ArrayList<>();
        for (int i = 0; i < menu.length; i++) {
        PublicViewItem menus = new PublicViewItem(
                menu[i]
                ,menu_Des[i],
                i
        );
        list_studnet.add(menus);
        }

        adapter = new PublicViewAdapter(list_studnet,student_manage_Views.this,student_manage_Views.this);
        recyclerView.setAdapter(adapter);

    }

    public void peresentation(){

        show_list_presentation_student();
        show_deta_picker();


    }

    public void show_list_presentation_student(){


        list_presentation = new ArrayList<>();

        PresentationItem date = new PresentationItem(
                1,
                output_date[0],output_date[1],output_date[2],
                0,
                null
                ,null,
                null
        );

        list_presentation.add(date);

        Cursor res = my_class_db.get_students_db(class_id);
        while (res.moveToNext()){


            PresentationItem student = new PresentationItem(
              2,
                    output_date[0],output_date[1],output_date[2],
                    res.getInt(0),
                    res.getString(1)+" "+res.getString(2)
                    ,false,
                    res.getString(4)
            );
            list_presentation.add(student);
        }
        adapter = new PresentationAdapter(list_presentation,getApplicationContext(),student_manage_Views.this);
        recyclerView.setAdapter(adapter);


    }
//---------------------------
    public void point_menu(){

        float_btn.setVisibility(View.VISIBLE);
        show_list_PointTopic();

    }

    public void show_add_point_topic(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_point_topic, null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();

        TextView topic = customView.findViewById(R.id.point_topic);
        TextView Des = customView.findViewById(R.id.Des);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel  = customView.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!topic.getText().toString().equals("")) {

                    my_class_db.incret_PointTopic_db(topic.getText().toString(),Des.getText().toString(),class_id);
                    show_list_PointTopic();
                    dialog.hide();

                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });


// Show the dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

    public void show_edit_point_topic(int id,String name,String Desc){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_point_topic, null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();

        TextView topic = customView.findViewById(R.id.point_topic);
        TextView Des = customView.findViewById(R.id.Des);
        TextView label = customView.findViewById(R.id.labal);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel  = customView.findViewById(R.id.cancel);


        topic.setText(name);
        if (!Des.equals(null)){

            Des.setText(Desc);
        }

        ok.setText("اعمال  تغییرات");
        Cancel.setText("حذف");
        label.setText("ویرایش لیست");


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!topic.getText().toString().equals("")) {
                    my_class_db.update_PointTopic_db(id, topic.getText().toString(),Des.getText().toString());
                    dialog.hide();
                    show_list_PointTopic();
                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_class_db.deletePointTopicData(id);
                Cursor point_data = my_class_db.get_students_db(class_id);
                while (point_data.moveToNext()){
                    try {
                        JSONObject js = new JSONObject(point_data.getString(3));
                        js.remove(String.valueOf(id));
                        my_class_db.update_student_db(point_data.getInt(0),"","",js.toString(),"",null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                dialog.hide();
                show_list_PointTopic();
            }
        });


// Show the dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }


    public void show_list_PointTopic(){
        list_pointTopic = new ArrayList<>();

        Cursor res = my_class_db.get_PointTopic_db(class_id);
        while (res.moveToNext()){
            PublicViewItem pointTopic = new PublicViewItem(
                    res.getString(1),
                    res.getString(2)
                    ,res.getInt(0)
            );
            list_pointTopic.add(pointTopic);
        }
        adapter = new PublicViewAdapter(list_pointTopic,student_manage_Views.this,student_manage_Views.this);
        recyclerView.setAdapter(adapter);
    }

    public void show_list_student(){
        list_studnet = new ArrayList<>();

        Cursor res = my_class_db.get_students_db(class_id);
        while (res.moveToNext()){
            PublicViewItem students = new PublicViewItem(
                    res.getString(1)+" \0"+res.getString(2),
                    res.getString(6)
                    ,res.getInt(0)
            );
            list_studnet.add(students);
        }
        adapter = new PublicViewAdapter(list_studnet,student_manage_Views.this,student_manage_Views.this);
        recyclerView.setAdapter(adapter);
        float_btn.setVisibility(View.VISIBLE);

    }

    public void show_add_st_Dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_student, null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();

        TextView name = customView.findViewById(R.id.st_name);
        TextView family = customView.findViewById(R.id.st_family);
        TextView Des = customView.findViewById(R.id.Des);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel  = customView.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("") || !family.getText().toString().equals("")) {
                    my_class_db.incret_student_db(name.getText().toString(), family.getText().toString(),class_id,Des.getText().toString());
                    dialog.hide();
                    show_list_student();

                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });


// Show the dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


    }

    public void show_edit_st_dialog(int id,String full_name,String Des) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_student, null);
        builder.setView(customView);
        AlertDialog dialog = builder.create();


        TextView st_name = customView.findViewById(R.id.st_name);
        TextView st_family = customView.findViewById(R.id.st_family);
        TextView st_Des = customView.findViewById(R.id.Des);
        TextView label = customView.findViewById(R.id.labal);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel = customView.findViewById(R.id.cancel);

        String[] name = full_name.split(" \0");
        st_name.setText(name[0]);
        st_family.setText(name[1]);
        if (!Des.equals(null)){

        st_Des.setText(Des);
        }

        ok.setText("اعمال  تغییرات");
        Cancel.setText("حذف");
        label.setText("ویرایش دانش اموز");


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!st_name.getText().toString().equals("") || !st_family.getText().toString().equals("")) {
                    my_class_db.update_student_db(id, st_name.getText().toString(), st_family.getText().toString(),"","",st_Des.getText().toString());
                    dialog.hide();
                    show_list_student();
                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_class_db.deleteStudentData(id);
                dialog.hide();
                show_list_student();

            }
        });


// Show the dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void show_deta_picker(){

        Typeface typeface = Typeface.createFromAsset(getAssets(), "mikhak_medium.ttf");

        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("انتخاب")
                .setNegativeButton("بیخیال")
                .setTodayButton("برو به امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setActionTextColor(Color.BLACK)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setTypeFace(typeface)
                .setPickerBackgroundDrawable(R.drawable.dialog_bg)
                .setListener(new PersianPickerListener() {

                    @Override
                    public void onDateSelected(PersianPickerDate persianCalendar) {
                        output_date = new int[]{persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay()};
                        show_list_presentation_student();


                    }

                    @Override
                    public void onDismissed() {
                    }


                });

        picker.show();
    }




    @Override
    public void date_selecor_click(int pos) {
        show_deta_picker();
    }

    @Override
    public void onlistener_listview(int pos) {
        switch (pos_click) {

            case 0:
                show_edit_st_dialog(list_studnet.get(pos).getID(),
                        list_studnet.get(pos).getLabel(),
                        list_studnet.get(pos).getDescription());
                break;

            case 2:

                Intent i = new Intent(this,point_seter_View.class);
                i.putExtra("label",list_pointTopic.get(pos).getLabel());
                i.putExtra("user",Username);
                i.putExtra("class_id",class_id);
                i.putExtra("point_topic_id",list_pointTopic.get(pos).getID());
                startActivity(i);
                break;

            case 3:
                Intent chart_view = new Intent(this,class_chart_view.class);
                chart_view.putExtra("label",list_studnet.get(pos).getLabel());
                chart_view.putExtra("user",Username);
                chart_view.putExtra("class_id",class_id);
                chart_view.putExtra("point_topic",pos);
                startActivity(chart_view);
                break;

    }}

    @Override
    public void onlonglistener_listview(int pos) {
        switch (pos_click) {

            case 2:
                show_edit_point_topic(list_pointTopic.get(pos).getID(),
                        list_pointTopic.get(pos).getLabel(),
                        list_pointTopic.get(pos).getDescription());
                break;

        }
    }
}
