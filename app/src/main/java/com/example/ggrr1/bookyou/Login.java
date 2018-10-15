package com.example.ggrr1.bookyou;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnTopLog, btnTopJoin;
    Button btnLogin, btnJoin;
    GridLayout layLog, layJoin;
    String color_black = "#000000";
    String color_pink = "#FE393A";

    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnTopLog = (Button) findViewById(R.id.btnTopLogin);
        btnTopJoin = (Button) findViewById(R.id.btnTopJoin);

        layLog = (GridLayout) findViewById(R.id.LoginLayout);
        layJoin = (GridLayout) findViewById(R.id.JoinLayout);

        btnLogin = (Button) findViewById(R.id.btnjoin);
        btnJoin = (Button) findViewById(R.id.btnlogin);

        myToolbar = (Toolbar) findViewById(R.id.toolbar_title);
        setSupportActionBar(myToolbar);

 //       myToolbar.setOutlineProvider (null);

        btnTopLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layJoin.setVisibility(View.GONE);
                layLog.setVisibility(View.VISIBLE);
                btnTopJoin.setBackgroundResource(R.drawable.gray_join);
                btnTopLog.setBackgroundResource(R.drawable.white_login);
                btnTopLog.setTextColor(Color.parseColor(color_pink));
                btnTopJoin.setTextColor(Color.parseColor(color_black));
            }
        });
        btnTopJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layLog.setVisibility(View.GONE);
                layJoin.setVisibility(View.VISIBLE);
                btnTopLog.setBackgroundResource(R.drawable.gray_login);
                btnTopJoin.setBackgroundResource(R.drawable.white_join);
                btnTopLog.setTextColor(Color.parseColor(color_black));
                btnTopJoin.setTextColor(Color.parseColor(color_pink));
            }
        });

    }

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
