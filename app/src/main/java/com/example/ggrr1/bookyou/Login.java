package com.example.ggrr1.bookyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    ImageButton btnLog, btnJoin;
    GridLayout layLog, layJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLog = (ImageButton) findViewById(R.id.btnLog_in);
        btnJoin = (ImageButton) findViewById(R.id.btnJoin_in);
        layLog = (GridLayout) findViewById(R.id.LoginLayout);
        layJoin = (GridLayout) findViewById(R.id.JoinLayout);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layJoin.setVisibility(View.GONE);
                layLog.setVisibility(View.VISIBLE);
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layLog.setVisibility(View.GONE);
                layJoin.setVisibility(View.VISIBLE);
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
