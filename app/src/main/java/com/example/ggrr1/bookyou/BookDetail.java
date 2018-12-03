package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookDetail extends AppCompatActivity {
    EditText name;
    String book_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        name = (EditText) findViewById(R.id.name);

        Intent intent = getIntent();
        book_id = intent.getStringExtra("book_id");


    }


}

class DetailRequest extends StringRequest {
    final static private String URL = "http://211.213.95.132/mybookstore/book_detail.php";
    private Map<String, String> parameters;

    public DetailRequest(String book_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("book_id", book_id);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}

