package com.example.ggrr1.bookyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String bookName, userName, userTel, userMessage, created;
    MessageListAdapter messageListAdapter;
    int user_id, message_list_status, message_delete_status , message_id;
    int count, checked;

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
                if(count> 0) {
                    checked = messageListView.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        Delete(checked, messageListView, messageListAdapter);
                    }
                }
            }
        });
    }

private void dataSetting(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    message_list_status = jsonResponse.getInt("message_list_status");

                    if(message_list_status == 2){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                        builder.setMessage("서버와 통신이 원할하지 않습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }

                    JSONArray message_list = jsonResponse.getJSONArray("message_list");
                    for(int i=0; i<message_list.length(); i++) {
                        message_id = message_list.getJSONObject(i).getInt("message_id");
                        bookName = message_list.getJSONObject(i).getString("book_name");
                        userName = message_list.getJSONObject(i).getString("name");
                        userTel = message_list.getJSONObject(i).getString("tel");
                        userMessage = message_list.getJSONObject(i).getString("message");
                        created = message_list.getJSONObject(i).getString("created");
                        messageListAdapter.addItem(message_id, bookName, userName, userTel, userMessage, created);
                    }
                    messageListView.setAdapter(messageListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MessageRequest messageRequest = new MessageRequest(String.valueOf(user_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        queue.add(messageRequest);
    };



    private void Delete(final int check, final ListView messageListView, final MessageListAdapter messageListAdapter){
        Toast.makeText(getApplicationContext(), "된디", Toast.LENGTH_LONG);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int message_num = 0;
                switch (which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        message_num = messageListAdapter.deleteItem(check);
                        messageListView.clearChoices();
                        messageListAdapter.notifyDataSetChanged();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);

                                    message_delete_status  = jsonResponse.getInt("message_delete_status");
                                    if(message_delete_status  == 1){
                                        Toast.makeText(getApplicationContext(),"메세지가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                    }else if(message_delete_status   == 2){
                                        Toast.makeText(getApplicationContext(),"서버와 연결이 원활하지 않습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        MessageDeletRequest messageDeletRequest = new MessageDeletRequest(String.valueOf(message_num), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
                        queue.add(messageDeletRequest);
                        break;

                    case DialogInterface.BUTTON_POSITIVE:
                        //No 버튼을 클릭했을때 처리
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
        builder.setMessage("정말 삭제하시겠습니까?")
                .setNegativeButton("Yes", dialogClickListener)
                .setPositiveButton("No", dialogClickListener).show();
    }

}
