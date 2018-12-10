package com.lendlibrary.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth=FirebaseAuth.getInstance();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser()!=null){
                    finish();
                    Intent login = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                }
                else{
                    finish();
                    Intent notLogin = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    notLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(notLogin);
                }
            }
        },2000);
    }
}
