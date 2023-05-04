package alireza.farghadani.blueberry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alireza.farghadani.blueberry.database.databasehelp_profile;
import alireza.farghadani.blueberry.network.API;
import alireza.farghadani.blueberry.network.ApiInterface;
import alireza.farghadani.blueberry.network.user_login_st;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login_page extends AppCompatActivity {

    private TextWatcher textWatcher;
    EditText ed;
    ApiInterface apiInterface;
    Boolean login_time = true;
    databasehelp_profile dp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();

        ed = findViewById(R.id.login_code);
        Button bt = findViewById(R.id.login_bt);
        bt.setAlpha(0.5f);
        apiInterface = API.getAPI().create(ApiInterface.class);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (3 <= ed.getText().toString().length()){
                    bt.setEnabled(true);
                    bt.setAlpha(1);

                }else{
                    bt.setEnabled(false);
                    bt.setAlpha(0.5f);

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}});



    }

    public void login(){

        String code = ed.getText().toString();
        Call<user_login_st> lCall = apiInterface.loginCall(code);
        Intent i = new Intent(this,MainActivity.class);

        lCall.enqueue(new Callback<user_login_st>(){

            @Override
            public void onResponse(Call<user_login_st> call, Response<user_login_st> response) {
                if(response.body().getApilogin_code().equals("t")){
                    Toast.makeText(getApplicationContext(), String.format("%s عزیز خوش امدید",response.body().getApiname()), Toast.LENGTH_SHORT).show();

                    if (login_time) {
                        dp = new databasehelp_profile(getApplicationContext());
                        dp.incret_profile_db(response.body().getApiname(), response.body().getApifamily());
                        dp.close();
                        startActivity(i);
                        finish();
                    }
                    login_time = false;

                }else if (response.body().getApilogin_code().equals("f")){
                    Toast.makeText(getApplicationContext(), "کد شما نامعتبر است !!", Toast.LENGTH_SHORT).show();
                    login_time = true;
                }
            }

            @Override
            public void onFailure(Call<user_login_st> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "روند انجام با مشکل مواجه شده است !!", Toast.LENGTH_SHORT).show();
                Log.e("error",t.toString());

            }
        });


    }
}
