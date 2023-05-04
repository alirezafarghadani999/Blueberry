package alireza.farghadani.blueberry;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import alireza.farghadani.blueberry.database.databasehelp_profile;

public class splash_screen extends AppCompatActivity {


    boolean logined = false;
    databasehelp_profile dp;
    private View circularRevealView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


        layout = findViewById(R.id.layout);
        circularRevealView = findViewById(R.id.circular_reveal_view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dp = new databasehelp_profile(getApplicationContext());
        Cursor res = dp.get_profile_db();

        if (res.getCount() == 1){
            logined = true;
        }
        dp.close();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (logined){
                    intent = new Intent(splash_screen.this, MainActivity.class);
                }else {
                    intent = new Intent(splash_screen.this, login_page.class);
                }


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int cx = (circularRevealView.getLeft() + circularRevealView.getRight()) / 2;
                    int cy = (circularRevealView.getTop() + circularRevealView.getBottom());

                    int finalRadius = Math.max(layout.getWidth(), layout.getHeight());

                    Animator circularReveal = null;
                    circularReveal = ViewAnimationUtils.createCircularReveal(circularRevealView, cx, cy, 0, finalRadius);
                    circularRevealView.setVisibility(View.VISIBLE);
                    circularReveal.start();
                    circularReveal.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    });
                }else {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_in);
                    startActivity(intent);
                    finish();
                }




            }
        }, 2000); // Delay for 2 seconds
    }
}
