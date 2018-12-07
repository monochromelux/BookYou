package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageActivity extends AppCompatActivity {
    ListView messageListView;
    Button btnDelete;
    String userName, userMessage;
    MessageListAdapter messageListAdapter;
    int user_id, book_list_status;
    int count;
    SparseBooleanArray checkedItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 1);
        messageListView = (ListView) findViewById(R.id.messageListVIew);

        btnDelete = (Button) findViewById(R.id.btnDelete);

        messageListAdapter = new MessageListAdapter();
        messageListView.setAdapter(messageListAdapter);

        dataSetting();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = messageListAdapter.getCount() ;
                checkedItems = messageListView.getCheckedItemPositions();

                for (int i = count-1; i >= 0; i--) {
                    if(checkedItems.get(i)) {
                        messageListAdapter.deleteItem(i);
                    }
                }
                // 모든 선택 상태 초기화.
                messageListView.clearChoices() ;
                messageListAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),"메세지가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                        builder.setMessage("서버와 통신이 원할하지 않습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }

                    JSONArray book_list = jsonResponse.getJSONArray("book_list");
                    for(int i=0; i<book_list.length(); i++) {
                        userName = book_list.getJSONObject(i).getString("name");
                        userMessage = book_list.getJSONObject(i).getString("author");
                        messageListAdapter.addItem(userName, userMessage);
                    }
                    messageListView.setAdapter(messageListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ListRequest listRequest = new ListRequest(String.valueOf(user_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        queue.add(listRequest);
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
