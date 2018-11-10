package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class BookDetail extends AppCompatActivity {
    TextView idtemp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        idtemp = (TextView) findViewById(R.id.idtemp);

        Intent intent = getIntent();
        idtemp.setText(intent.getStringExtra("id"));
    }
}
