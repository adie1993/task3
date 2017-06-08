package com.adie.login.activity;

/**
 * Created by Adie on 28/04/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.adie.login.R;
import com.adie.login.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity{
    SessionManager session;
    private CountDownTimer mCountDownTimer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //melakukan delay selama 2 detik
        this.mCountDownTimer = new CountDownTimer(1000, 2000) {
            public void onTick(long l) {
            }

            public void onFinish() {
                //inisialisasi session
                session = new SessionManager(getApplicationContext());
                //cek login
                session.checkLogin();
                //jika sudah login maka akan menuju mainactivity
                if(session.isLoggedIn()) {
                    Intent a = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(a);
                    finish();
                }

            }
        }.start();
    }


}
