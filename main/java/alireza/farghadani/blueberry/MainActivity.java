package alireza.farghadani.blueberry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alireza.farghadani.blueberry.database.databasehelp_class;
import alireza.farghadani.blueberry.database.databasehelp_profile;
import alireza.farghadani.blueberry.list_class_View.classsItem;
import alireza.farghadani.blueberry.list_class_View.classsAdapter;
import alireza.farghadani.blueberry.list_news_View.newsAdapter;
import alireza.farghadani.blueberry.list_news_View.newsItem;
import alireza.farghadani.blueberry.list_profile_View.profileAdapter;
import alireza.farghadani.blueberry.list_profile_View.profileItem;
import alireza.farghadani.blueberry.network.news;
import alireza.farghadani.blueberry.network.API;
import alireza.farghadani.blueberry.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements classsAdapter.OnClassListener, classsAdapter.OnclickClassListener {


    private RecyclerView recyclerView;
    private RecyclerView newsrecyclerView;

    private ImageButton float_btn_class;
    private RecyclerView.Adapter adapter;
    private List<classsItem> list_classes_Itme;

    private List<newsItem> list_news_Itme;
    private List<profileItem> list_profile_Itme;


    TextView cl,pf,nw;
    ViewGroup includedL;

    databasehelp_class my_class_db;
    databasehelp_profile my_profile_db;

    ApiInterface apiInterface;

    private String profile_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();




        my_profile_db = new databasehelp_profile(getApplicationContext());
        Cursor res = my_profile_db.get_profile_db();
        if (res.getCount() == 1){
        res.move(1);
        TextView name = findViewById(R.id.name);
        profile_name = res.getString(1)+" "+res.getString(2);
        name.setText(profile_name);
        }else {
            finish();
        }
        my_profile_db.close();




        cl = findViewById(R.id.class_menu);
        pf= findViewById(R.id.profile_menu);
        nw = findViewById(R.id.news_menu);
        includedL = findViewById(R.id.included_l);

        View list_class = LayoutInflater.from(this).inflate(R.layout.includeview_list_class, includedL, false);
        View news = LayoutInflater.from(this).inflate(R.layout.includeview_news, includedL, false);
        View profile = LayoutInflater.from(this).inflate(R.layout.includeview_profile, includedL, false);
        includedL.removeAllViewsInLayout();
        includedL.addView(list_class);
        classes();
        float_btn_class();

        apiInterface = API.getAPI().create(ApiInterface.class);





        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.setBackgroundResource(R.drawable.custom_buttom_bar_selected);
                pf.setBackground(Drawable.createFromPath(""));
                nw.setBackground(Drawable.createFromPath(""));

                cl.setTextColor(Color.parseColor("#F6FBFF"));
                pf.setTextColor(Color.parseColor("#5695C6"));
                nw.setTextColor(Color.parseColor("#5695C6"));

                ViewGroup previousParent = (ViewGroup) includedL.getParent();
                if (previousParent != null) {
                includedL.removeView(list_class);
                includedL.removeView(profile);
                includedL.removeView(news);


            }
                includedL.addView(list_class);
                classes();

            }
        });
        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pf.setBackgroundResource(R.drawable.custom_buttom_bar_selected);
                cl.setBackground(Drawable.createFromPath(""));
                nw.setBackground(Drawable.createFromPath(""));

                pf.setTextColor(Color.parseColor("#F6FBFF"));
                cl.setTextColor(Color.parseColor("#5695C6"));
                nw.setTextColor(Color.parseColor("#5695C6"));

                ViewGroup previousParent = (ViewGroup) includedL.getParent();
                if (previousParent != null) {
                    includedL.removeView(list_class);
                    includedL.removeView(profile);
                    includedL.removeView(news);
                }
                includedL.addView(profile);
                profiles();

            }
        });
        nw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nw.setBackgroundResource(R.drawable.custom_buttom_bar_selected);
                pf.setBackground(Drawable.createFromPath(""));
                cl.setBackground(Drawable.createFromPath(""));

                nw.setTextColor(Color.parseColor("#F6FBFF"));
                pf.setTextColor(Color.parseColor("#5695C6"));
                cl.setTextColor(Color.parseColor("#5695C6"));

                ViewGroup previousParent = (ViewGroup) includedL.getParent();
                if (previousParent != null) {
                    includedL.removeView(list_class);
                    includedL.removeView(profile);
                    includedL.removeView(news);
                }
                includedL.addView(news);
                news();



            }
        });

    }

   
    public void float_btn_class(){
        float_btn_class = findViewById(R.id.float_add_class);
        float_btn_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_add_Dialog();
            }
        });
    }


    public void classes(){

        recyclerView = findViewById(R.id.recyclerView_classs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        list_classes_Itme = new ArrayList<>();
        my_class_db = new databasehelp_class(this);
        Cursor res = my_class_db.get_classs_db();

        if (res.getCount() != 0){

            while (res.moveToNext()) {
                classsItem listitem = new classsItem(
                        res.getString(1)
                        , res.getString(2)
                        ,res.getInt(0)
                );
                list_classes_Itme.add(listitem);
            }
        }

        adapter = new classsAdapter(list_classes_Itme,MainActivity.this,MainActivity.this);
        recyclerView.setAdapter(adapter);
        my_class_db.close();


    }

    public void profiles(){
        recyclerView = findViewById(R.id.recyclerView_profile);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        list_profile_Itme = new ArrayList<>();
        profileItem list_item_profile = new profileItem(
                "این بخش به زودی"
                ,"به زودی"
        );
        list_profile_Itme.add(list_item_profile);

        adapter = new profileAdapter(list_profile_Itme,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void news(){

        newsrecyclerView = findViewById(R.id.recyclerView_news);
        newsrecyclerView.setHasFixedSize(true);
        newsrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list_news_Itme = new ArrayList<>();

        get_news(1);



    }

    public void get_news(int num){
        Call<news> nCall = apiInterface.newsCall(String.valueOf(num));
        nCall.enqueue(new Callback<news>() {
            @Override
            public void onResponse(Call<news> call, Response<news> response) {

                newsItem list_item_news = new newsItem(
                        response.body().getApititle()
                        , response.body().getApidescription()
                );
                list_news_Itme.add(list_item_news);

                adapter = new newsAdapter(list_news_Itme, getApplicationContext());
                newsrecyclerView.setAdapter(adapter);

                get_news(num+1);
                return;
            }

            @Override
            public void onFailure(Call<news> call, Throwable t) {
                if (num == 1) {
                    Toast.makeText(getApplicationContext(), "فکر کنم به اینترنت وصل نیستی ;(", Toast.LENGTH_SHORT).show();
                }
                return;

            }
        });
    }

    public void show_add_Dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_class, null);
        Drawable drawable = getResources().getDrawable(R.drawable.dialog_bg);
        customView.setBackground(drawable);
        builder.setView(customView);
        AlertDialog dialog = builder.create();

        TextView class_name = customView.findViewById(R.id.class_name);
        TextView lesson_name = customView.findViewById(R.id.lesson_name);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel  = customView.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!class_name.getText().toString().equals("") || !lesson_name.getText().toString().equals("")) {
                    my_class_db = new databasehelp_class(getApplicationContext());
                    my_class_db.incret_class_db(class_name.getText().toString(), lesson_name.getText().toString());
                    dialog.hide();
                    classes();
                    my_class_db.close();

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

    public void show_edit_dialog(int id,String name,String lesson){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_input_class, null);
        Drawable drawable = getResources().getDrawable(R.drawable.dialog_bg);
        customView.setBackground(drawable);
        builder.setView(customView);
        AlertDialog dialog = builder.create();


        TextView class_name = customView.findViewById(R.id.class_name);
        TextView lesson_name = customView.findViewById(R.id.lesson_name);
        TextView label = customView.findViewById(R.id.labal);

        Button ok = customView.findViewById(R.id.ok);
        Button Cancel  = customView.findViewById(R.id.cancel);

        class_name.setText(name);
        lesson_name.setText(lesson);
        ok.setText("اعمال  تغییرات");
        Cancel.setText("حذف");
        label.setText("ویرایش کلاس");


        my_class_db = new databasehelp_class(getApplicationContext());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!class_name.getText().toString().equals("") || !lesson_name.getText().toString().equals("")) {
                    my_class_db.update_class_db(id,class_name.getText().toString(),lesson_name.getText().toString());
                    my_class_db.close();
                    dialog.hide();
                    classes();
                }

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_class_db.deleteClassData(id);
                my_class_db.close();
                dialog.hide();
                classes();

            }
        });


// Show the dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void go_to_class_manager(int ID,String Class_name){
        Intent i = new Intent(this,class_manage_screen.class);
        i.putExtra("ID_class",ID);
        i.putExtra("Name_class",Class_name);
        i.putExtra("profile_name",profile_name);
        startActivity(i);
    }


    @Override
    public void onclasslongclick_list(int position) {
        show_edit_dialog(list_classes_Itme.get(position).getID(),list_classes_Itme.get(position).getTitle(),list_classes_Itme.get(position).getLesson());
    }


    @Override
    public void onclicklistener_list_class(int pos){
        go_to_class_manager(list_classes_Itme.get(pos).getID(),list_classes_Itme.get(pos).getTitle());
    }


}