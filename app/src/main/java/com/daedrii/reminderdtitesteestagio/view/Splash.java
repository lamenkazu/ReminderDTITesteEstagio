package com.daedrii.reminderdtitesteestagio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.daedrii.reminderdtitesteestagio.R;

public class Splash extends AppCompatActivity {

    Animation logoAnimation, logoSujaAnimation;
    ImageView logoLimpa, logoSuja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        logoAnimation = AnimationUtils.loadAnimation(Splash.this, R.anim.logo_animation);
        logoSujaAnimation = AnimationUtils.loadAnimation(Splash.this, R.anim.logo_suja_animation);

        logoLimpa = findViewById(R.id.splash_logo);
        logoSuja = findViewById(R.id.splash_logo_sujo);

        logoLimpa.setAnimation(logoAnimation);
        logoSuja.setAnimation(logoSujaAnimation);

        Intent intent = new Intent(Splash.this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 2501);




    }
}