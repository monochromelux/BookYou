package com.example.ggrr1.bookyou;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Button btnTopLog, btnTopJoin;
    Button btnLogin, btnJoin;
    CheckBox checkBox;

    GridLayout layLog, layJoin;
    String loginId, loginPwd;
    boolean checkb;

    Toolbar myToolbar;

    SharedPreferences.Editor editor;

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

        checkBox = (CheckBox) findViewById(R.id.checkBox);

        final EditText emailJoinText = (EditText) findViewById(R.id.join_email);
        final EditText passwordJoinText = (EditText) findViewById(R.id.join_password);
        final EditText nameJoinText = (EditText) findViewById(R.id.join_name);
        final EditText inputPassToJoinText = (EditText) findViewById(R.id.join_inputPassTo);

        final EditText emailLoginText = (EditText) findViewById(R.id.login_email);
        final EditText passwordLoginText = (EditText) findViewById(R.id.login_password);

        SharedPreferences auto;
        auto = getSharedPreferences("auto", 0);
        editor= auto.edit();
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);
        checkb = auto.getBoolean("inputCheck",false);

        if(checkb){
            emailLoginText.setText(auto.getString("inputId", ""));
            passwordLoginText.setText(auto.getString("inputPwd", ""));

            checkBox.setChecked(true);
        }

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        nameJoinText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode){
                    case KeyEvent.KEYCODE_ENTER:
                        return true;
                }
                return false;
            }
        });

        passwordLoginText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode){
                    case KeyEvent.KEYCODE_ENTER:
                        return true;
                }
                return false;
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    checkb = true;
                } else {
                    // if unChecked, removeAll
                    checkb = false;
                    editor.clear();
                    editor.commit();
                }
            }
        });

        btnTopLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layJoin.setVisibility(View.GONE);
                layLog.setVisibility(View.VISIBLE);
            }
        });
        btnTopJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layLog.setVisibility(View.GONE);
                layJoin.setVisibility(View.VISIBLE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailLoginText.getText().toString();
                final String password = passwordLoginText.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                int login_status = jsonResponse.getInt("login_status");

                                if (login_status == 1) {
                                    if(checkb == true){
                                        editor.putString("inputId", email);
                                        editor.putString("inputPwd", password);
                                        editor.putBoolean("inputCheck", true);
                                        editor.commit();
                                    }
                                    int user_id = jsonResponse.getInt("user_id");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                    intent.putExtra("user_id", user_id);
                                    startActivity(intent);
                                    finish();
                                } else if (login_status == 2) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("존재하는 이메일이 없습니다.")
                                            .setNegativeButton("AGAIN", null)
                                            .create()
                                            .show();
                                } else if (login_status == 3) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("비밀번호가 일치하지 않습니다.")
                                            .setPositiveButton("AGAIN", null)
                                            .create()
                                            .show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailJoinText.getText().toString();
                String password = passwordJoinText.getText().toString();
                String inputPassTo = inputPassToJoinText.getText().toString();
                String name = nameJoinText.getText().toString();
                if(isValidValues(email,password,inputPassTo,name)) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);
                                int join_status = jsonResponse.getInt("join_status");

                                if (join_status == 1) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("회원 가입에 성공했습니다.")
                                            .setPositiveButton("OK", null)
                                            .create()
                                            .show();
                                    layJoin.setVisibility(View.GONE);
                                    layLog.setVisibility(View.VISIBLE);
                                } else if (join_status == 2) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("이미 존재하는 이메일입니다.")
                                            .setNegativeButton("AGAIN", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    JoinRequest joinRequest = new JoinRequest(email, password, name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(joinRequest);
                }
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

    private boolean isValidValues(String email, String password, String inputPassTo, String name){
        boolean isResult = false;

        String regExpn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(email.isEmpty() || password.isEmpty() || inputPassTo.isEmpty() || name.isEmpty()){
            isResult = false;
            Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            isResult = true;
            if (!(email.matches(regExpn)))
            {
                isResult = false;
                Toast.makeText(LoginActivity.this,"이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!(inputPassTo.equals(password))) {
                    isResult = false;
                    Toast.makeText(this, "비밀번호의 값이 같지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return isResult;
    }
}
