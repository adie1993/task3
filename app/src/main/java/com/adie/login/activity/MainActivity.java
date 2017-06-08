package com.adie.login.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adie.login.R;
import com.adie.login.utils.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //deklarasi variabel yang diperlukan
    private TextView logout,display;
    String email;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView)findViewById(R.id.disp);
        logout = (TextView)findViewById(R.id.logout);
        //inisialisasi session manager
        sessionManager =new SessionManager(getApplicationContext());
        //cek login pada data session
        sessionManager.checkLogin();

        //memanggil data dari session
        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(sessionManager.KEY_EMAIL);

        //menampilkan pada textview
        display.setText(email);

        //trigger logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trigger logout session lalu kembali ke sign in
                sessionManager.logoutUser();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
