package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookList extends AppCompatActivity {

    Button register;
    ListView booklist;
    int user_id;
    int book_list_status;
    String book_id, name, author, price, sale_price, created;
    MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 1);

        booklist = (ListView) findViewById(R.id.listview);
        register = (Button) findViewById(R.id.register);

        mMyAdapter = new MyAdapter();

        dataSetting();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookRegister.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BookDetail.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }
    private void dataSetting(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    book_list_status = jsonResponse.getInt("book_list_status");

                    if(book_list_status == 2){
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookList.this);
                        builder.setMessage("서버와 통신이 원할하지 않습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }

                    JSONArray book_list = jsonResponse.getJSONArray("book_list");
                    for(int i=0; i<book_list.length(); i++) {
                        book_id = book_list.getJSONObject(i).getString("id");
                        name = book_list.getJSONObject(i).getString("name");
                        author = book_list.getJSONObject(i).getString("author");
                        price = book_list.getJSONObject(i).getString("price");
                        sale_price = book_list.getJSONObject(i).getString("sale_price");
                        created = book_list.getJSONObject(i).getString("created");
                        mMyAdapter.addItem(book_id, name, author, price, sale_price, created);
                    }
                    booklist.setAdapter(mMyAdapter);
                    booklist.setOnItemClickListener(bookClick);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ListRequest listRequest = new ListRequest(String.valueOf(user_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookList.this);
        queue.add(listRequest);
    }
    private OnItemClickListener bookClick = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String book_id = ((ItemData)mMyAdapter.getItem(position)).getBook_id();

            // new Intent(현재 Activity의 Context, 시작할 Activity 클래스)
            Intent intent = new Intent(BookList.this, BookDetail.class);
            // putExtra(key, value)
            intent.putExtra("user_id", user_id);
            intent.putExtra("book_id", book_id);
            startActivity(intent);
        }
    };

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
