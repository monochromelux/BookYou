package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.graphics.Color;
import android.net.nsd.NsdManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnJoin = (Button) findViewById(R.id.btnjoin);

        final EditText emailJoinText = (EditText) findViewById(R.id.join_email);
        final EditText passwordJoinText = (EditText) findViewById(R.id.join_password);
        final EditText nameJoinText = (EditText) findViewById(R.id.join_name);
        EditText inputPassToJoinText = (EditText) findViewById(R.id.join_inputPassTo);
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookList.class);
                startActivity(intent);

            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailJoinText.getText().toString();
                String password = passwordJoinText.getText().toString();
                String name = nameJoinText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("회원 가입에 성공했습니다.")
                                        .setPositiveButton("OK",null)
                                        .create()
                                        .show();
                                layJoin.setVisibility(View.GONE);
                                layLog.setVisibility(View.VISIBLE);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("회원 가입에 실패했습니다.")
                                        .setNegativeButton("AGAIN",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
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
