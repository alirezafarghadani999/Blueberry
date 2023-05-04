package alireza.farghadani.blueberry;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alireza.farghadani.blueberry.database.databasehelp_class;
import alireza.farghadani.blueberry.list_point_View.PointAdapter;
import alireza.farghadani.blueberry.list_point_View.PointItem;

public class point_seter_View extends AppCompatActivity {

    String ViewLable, Username;
    int class_id,topic_id;

    private List<PointItem> point_list_itme;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    databasehelp_class my_class_db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_seter);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ViewLable = extras.getString("label");
            Username = extras.getString("user");
            class_id = extras.getInt("class_id");
            topic_id = extras.getInt("point_topic_id");
            TextView pf_name = findViewById(R.id.name);
            pf_name.setText(Username);

            TextView label = findViewById(R.id.label);
            label.setText(ViewLable);
        }


        recyclerView = findViewById(R.id.view_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        my_class_db = new databasehelp_class(getApplicationContext());

        show_st();


        my_class_db.close();
    }

    public void show_st(){
        point_list_itme = new ArrayList<>();

        Cursor res = my_class_db.get_students_db(class_id);
        while (res.moveToNext()){
            PointItem points = new PointItem(
              res.getString(1),
                    res.getString(2),
                    res.getInt(0),
                    res.getString(3),
                    topic_id
            );
            point_list_itme.add(points);
        }
        adapter = new PointAdapter(point_list_itme,getApplicationContext());
        recyclerView.setAdapter(adapter);


    }
}
