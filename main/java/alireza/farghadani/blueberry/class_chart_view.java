package alireza.farghadani.blueberry;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import alireza.farghadani.blueberry.database.databasehelp_class;
import alireza.farghadani.blueberry.list_chart_View.chartAdapter;
import alireza.farghadani.blueberry.list_chart_View.chartItem;
import alireza.farghadani.blueberry.list_chart_View.top_point_sort.Person;
import alireza.farghadani.blueberry.list_chart_View.top_point_sort.PersonPointComparator;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewAdapter;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewItem;

public class class_chart_view extends AppCompatActivity {

    private String ViewLable;
    private String Username;
    private int class_id;
    private int topic_id;


    RecyclerView recyclerView;
    List<chartItem> list_chartview;
    RecyclerView.Adapter adapter;

    databasehelp_class my_class_db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ViewLable = extras.getString("label");
            Username = extras.getString("user");
            class_id = extras.getInt("class_id");
            topic_id = extras.getInt("point_topic") +1;
            TextView pf_name = findViewById(R.id.name);
            pf_name.setText(Username);

            TextView label = findViewById(R.id.label);
            label.setText(ViewLable);
        }

        recyclerView = findViewById(R.id.view_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        my_class_db = new databasehelp_class(getApplicationContext());


        switch (topic_id){
            case 1:
                sort_st_with_point();
                break;
            case 2:
                draw_charts();
                break;

        }


    }

    public void draw_charts(){

        Cursor res = my_class_db.get_students_db(class_id);
        list_chartview = new ArrayList<>();

        while (res.moveToNext()){
                chartItem sort_point = new chartItem(
                        res.getString(1) + " " + res.getString(2),
                        "",
                        ""
                        , 2
                        ,ArrayPoints(res.getString(3))
                );
                list_chartview.add(sort_point);
            }

        adapter = new chartAdapter(list_chartview);
        recyclerView.setAdapter(adapter);
    }

    public void sort_st_with_point(){

        String[] name = {};
        float[] points = {};

        Cursor res = my_class_db.get_students_db(class_id);
        while (res.moveToNext()){
            String newElement = res.getString(1)+" "+res.getString(2);
            String[] newArray = Arrays.copyOf(name, name.length + 1);
            newArray[newArray.length - 1] = newElement;
            name = newArray;
            float newElementint = AveragePoints(res.getString(3));
            float[] newArrayint = Arrays.copyOf(points, points.length + 1);
            newArrayint[newArrayint.length - 1] = newElementint;
            points = newArrayint;

        }

        Person[] people = new Person[name.length];
        for (int i = 0; i < name.length; i++) {
            people[i] = new Person(name[i], points[i]);
        }

        Arrays.sort(people, new PersonPointComparator());

        for (int i = 0; i < people.length; i++) {
            name[i] = people[i].getName();
            points[i] = people[i].getPoint();
        }
        list_chartview = new ArrayList<>();
        ArrayList<Entry> none = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            if (!String.valueOf(points[i]).equals("NaN")){
                chartItem sort_point = new chartItem(
                        name[i],
                        "میانگین نمره دانش آموز "+ points[i],
                        ""
                        , 1
                        ,none
                );
                list_chartview.add(sort_point);
            }
        }

        adapter = new chartAdapter(list_chartview);
        recyclerView.setAdapter(adapter);

    }

    public float AveragePoints(String json_data){
        JSONObject js = null;
        float avarage = 0 ;
        try {
            js = new JSONObject(json_data);
            Iterator<String> keys = js.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                avarage = avarage+ Float.parseFloat(js.getString(key));
            }
            avarage = avarage/js.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Float.parseFloat(String.format("%.2f",avarage));
    }

    public ArrayList<Entry> ArrayPoints(String json_data){
        JSONObject js = null;
        ArrayList<Entry> none = new ArrayList<>();
        int pointer = 0;
        try {
            js = new JSONObject(json_data);
            Iterator<String> keys = js.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                none.add( new Entry(pointer,Float.parseFloat(js.getString(key))));
                pointer ++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return none;
    }

}
