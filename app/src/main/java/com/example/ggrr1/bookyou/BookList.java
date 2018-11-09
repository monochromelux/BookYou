package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class BookList extends AppCompatActivity {

    Button register;
    ListView booklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        booklist = (ListView) findViewById(R.id.listview);
        register = (Button) findViewById(R.id.register);

        dataSetting();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookRegister.class);
                startActivity(intent);
            }
        });

        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void dataSetting(){

        MyAdapter mMyAdapter = new MyAdapter();

        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "리눅스 프로그래밍", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test1", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test2", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test3", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test4", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test5", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo),
                "test6", "김성우, 이중화, 이종민", "28,000원", "15,400원");
        /* 리스트뷰에 어댑터 등록 */
        booklist.setAdapter(mMyAdapter);
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
