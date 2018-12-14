package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookUpdateActivity extends AppCompatActivity {
    EditText editName, editAuthor, editPublisher, editPublishedDate, editSubject, editProfessor, editPrice, editSalePrice, editDescription;
    Button btnUpdate;

    String book_id, name, author, publisher, published_date, subject, professor, price, sale_price, description;
    String img_path = " ";
    int user_id, book_detail_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_update);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 1);
        book_id = intent.getStringExtra("book_id");

        editName = (EditText) findViewById(R.id.name);
        editAuthor = (EditText) findViewById(R.id.author);
        editPublisher = (EditText) findViewById(R.id.publisher);
        editPublishedDate = (EditText) findViewById(R.id.published_date);
        editSubject = (EditText) findViewById(R.id.subject);
        editProfessor = (EditText) findViewById(R.id.professor);
        editPrice = (EditText) findViewById(R.id.price);
        editSalePrice = (EditText) findViewById(R.id.sale_price);
        editDescription = (EditText) findViewById(R.id.description);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    book_detail_status = jsonResponse.getInt("book_detail_status");

                    if (book_detail_status == 2) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookUpdateActivity.this);
                        builder.setMessage("책 내용을 불러오지 못 했습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }

                    JSONArray book_detail = jsonResponse.getJSONArray("book_detail");
                    for (int i = 0; i < book_detail.length(); i++) {
                        img_path = book_detail.getJSONObject(i).getString("img_path");
                        name = book_detail.getJSONObject(i).getString("name");
                        author = book_detail.getJSONObject(i).getString("author");
                        publisher = book_detail.getJSONObject(i).getString("publisher");
                        published_date = book_detail.getJSONObject(i).getString("published_date");
                        subject = book_detail.getJSONObject(i).getString("subject");
                        professor = book_detail.getJSONObject(i).getString("professor");
                        price = book_detail.getJSONObject(i).getString("price");
                        sale_price = book_detail.getJSONObject(i).getString("sale_price");
                        description = book_detail.getJSONObject(i).getString("description");

                        editName.setText(name);
                        editAuthor.setText(author);
                        editPublisher.setText(publisher);
                        editPublishedDate.setText(published_date);
                        editSubject.setText(subject);
                        editProfessor.setText(professor);
                        editPrice.setText(price);
                        editSalePrice.setText(sale_price);
                        editDescription.setText(description);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DetailRequest detailRequest = new DetailRequest(String.valueOf(book_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookUpdateActivity.this);
        queue.add(detailRequest);

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean Value = false;
                if(editName.getText().toString().isEmpty()){
                    Value = false;
                    Toast.makeText(getApplicationContext(),"이름을 등록해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(editPrice.getText().toString().isEmpty() || editSalePrice.getText().toString().isEmpty()) {
                        Value = false;
                        Toast.makeText(getApplicationContext(), "가격정보를 등록해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        updateBook();
                    }
                }
            }
        });
    }

    private void updateBook(){
        name = editName.getText().toString();
        author = editAuthor.getText().toString();
        publisher = editPublisher.getText().toString();
        published_date = editPublishedDate.getText().toString();
        subject = editSubject.getText().toString();
        professor = editProfessor.getText().toString();
        price = editPrice.getText().toString();
        sale_price = editSalePrice.getText().toString();
        description = editDescription.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);

                    book_detail_status = jsonResponse.getInt("book_detail_status");
                    if (book_detail_status == 1) {
                        Toast.makeText(getApplicationContext(),"책 내용이 수정되었습니다.",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), BookListActivity.class);
                        i.putExtra("user_id",user_id);
                        startActivity(i);
                        finish();
                    }else if(book_detail_status == 2) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookUpdateActivity.this);
                        builder.setMessage("서버와 연결이 원활하지 않습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        UpdateRequest updateRequest = new UpdateRequest(book_id, img_path, name, author, publisher, published_date, subject, professor, price, sale_price, description, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookUpdateActivity.this);
        queue.add(updateRequest);

    }
}

