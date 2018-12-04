package com.example.ggrr1.bookyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDetail extends AppCompatActivity {
    TextView textName, textAuthor, textPublisher, textPublishedDate, textSubject, textProfessor, textPrice, textSalePrice, textDescription;
    Button btnModify,btnDelete;

    String img_path, book_id, name, author, publisher, published_date, subject, professor, price, sale_price, description;
    int user_id, book_detail_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 1);
        book_id = intent.getStringExtra("book_id");

        textName = (TextView) findViewById(R.id.name);
        textAuthor = (TextView) findViewById(R.id.author);
        textPublisher = (TextView) findViewById(R.id.publisher);
        textPublishedDate = (TextView) findViewById(R.id.published_date);
        textSubject = (TextView) findViewById(R.id.subject);
        textProfessor = (TextView) findViewById(R.id.professor);
        textPrice = (TextView) findViewById(R.id.price);
        textSalePrice = (TextView) findViewById(R.id.sale_price);
        textDescription = (TextView) findViewById(R.id.description);

        btnModify = (Button) findViewById(R.id.btnModify);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookUpdate.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("book_id", book_id);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_NEGATIVE:
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);

                                            book_detail_status = jsonResponse.getInt("book_detail_status");
                                            if(book_detail_status == 1){
                                                Toast.makeText(getApplicationContext(),"책이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(BookDetail.this, BookList.class);
                                                i.putExtra("user_id",user_id);
                                                startActivity(i);
                                                finish();

                                            }else if(book_detail_status  == 2){
                                                Toast.makeText(getApplicationContext(),"서버와 연결이 원활하지 않습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeleteRequest deleteRequest = new DeleteRequest(String.valueOf(book_id), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(BookDetail.this);
                                queue.add(deleteRequest);
                                break;

                            case DialogInterface.BUTTON_POSITIVE:
                                //No 버튼을 클릭했을때 처리
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetail.this);
                builder.setMessage("정말 삭제하시겠습니까?")
                        .setNegativeButton("Yes", dialogClickListener)
                        .setPositiveButton("No", dialogClickListener).show();
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    book_detail_status = jsonResponse.getInt("book_detail_status");

                    if(book_detail_status == 2){
                        Toast.makeText(getApplicationContext(),"서버와 연결이 원활하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }

                    JSONArray book_detail = jsonResponse.getJSONArray("book_detail");
                    for(int i=0; i<book_detail.length(); i++) {
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
                    }

                    textName.setText(name);
                    textAuthor.setText(author);
                    textPublisher.setText(publisher);
                    textPublishedDate.setText(published_date);
                    textSubject.setText(subject);
                    textProfessor.setText(professor);
                    textPrice.setText(price);
                    textSalePrice.setText(sale_price);
                    textDescription.setText(description);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DetailRequest detailRequest = new DetailRequest(String.valueOf(book_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookDetail.this);
        queue.add(detailRequest);
    }

}

