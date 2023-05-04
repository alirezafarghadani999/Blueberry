package alireza.farghadani.blueberry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import alireza.farghadani.blueberry.PublicClass.ShamsiDate;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewAdapter;
import alireza.farghadani.blueberry.public_custom_list_view.PublicViewItem;

public class class_manage_screen extends AppCompatActivity implements PublicViewAdapter.Onlistener, PublicViewAdapter.OnLongListener {

    private String profile_name ;
    private String class_name ;
    private int class_id;

    private RecyclerView.Adapter adapter;
    private List<PublicViewItem> list_items;
    private RecyclerView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_screen);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profile_name = extras.getString("profile_name");
            class_name = extras.getString("Name_class");
            class_id = extras.getInt("ID_class");

            TextView pf_name = findViewById(R.id.name);
            pf_name.setText(profile_name);

            TextView label = findViewById(R.id.label);
            label.setText(class_name);
        }

        menu = findViewById(R.id.manage_menu_class);
        menu.setHasFixedSize(true);
        menu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list_items = new ArrayList<>();

        String date = ShamsiDate.getCurrentShamsidate();
        String[] label = {"لیست دانش اموزان"
                ,"لیست حضور غیاب"
                ,"لیست نمره دهی"
                ,"عملکرد دانش اموزان"};
        String[] Des = {"افزودن دانش اموز و مشاهده لیست"
                ,date
                ,"اضافه کردن نمره و پرسش"
                ,"نمایش گزارش از عملکرد دانش اموزان"};

        for (int i = 0;i<=3;i++) {
            PublicViewItem items = new PublicViewItem(label[i], Des[i],0);
            list_items.add(items);
        }
        adapter = new PublicViewAdapter(list_items,class_manage_screen.this,class_manage_screen.this);
        menu.setAdapter(adapter);


    }




    @Override
    public void onlistener_listview(int pos) {
//        Toast.makeText(this,String.valueOf(pos),Toast.LENGTH_SHORT).show();
        Intent i ;
        String label = list_items.get(pos).getLabel();
        String user = profile_name;
        i = new Intent(this,student_manage_Views.class);
        i.putExtra("label",label);
        i.putExtra("user",user);
        i.putExtra("content",pos);
        i.putExtra("class_id",class_id);
        startActivity(i);

    }

    @Override
    public void onlonglistener_listview(int pos) {

    }
}
