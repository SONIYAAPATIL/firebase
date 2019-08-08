package com.example.tododemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.example.e_shopapp.ui.LoginActivity;

import com.example.tododemo.R;

import java.util.Timer;


public class SplashActivity extends AppCompatActivity {
    ViewPager viewPager;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Integer[] imageId = {R.drawable.logo3,R.drawable.logotitle};
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 2000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    private static final int NUM_PAGES = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


//        viewPager = (ViewPager) findViewById(R.id.VPSplash);
//        PagerAdapter adapter1 = new SplashSliderAdapter(SplashActivity.this, imageId);
//        viewPager.setAdapter(adapter1);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the login Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, SplashActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer(); // This will create a new Thread
//        timer.schedule(new TimerTask() { // task to be scheduled
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, DELAY_MS, PERIOD_MS);
    }
    }

