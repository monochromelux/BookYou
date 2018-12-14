package com.example.ggrr1.bookyou;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookRegisterActivity extends AppCompatActivity {
    ImageButton btnImage;
    EditText editName, editAuthor, editPublisher, editPublishedDate, editSubject, editProfessor, editPrice, editSalePrice, editDescription;
    Button btnRegister;

    String name, author, publisher, published_date, subject, professor, price, sale_price, description;
    String img_path = " ";
    private Bitmap bitmap;

    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    final static private String URL = "http://211.213.95.132/mybookstore/book_register.php";
    Uri imagePath;

    private int user_id;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; //권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",1);

        editName = (EditText) findViewById(R.id.name);
        editAuthor = (EditText) findViewById(R.id.author);
        editPublisher = (EditText) findViewById(R.id.publisher);
        editPublishedDate = (EditText) findViewById(R.id.published_date);
        editSubject = (EditText) findViewById(R.id.subject);
        editProfessor = (EditText) findViewById(R.id.professor);
        editPrice = (EditText) findViewById(R.id.price);
        editSalePrice = (EditText) findViewById(R.id.sale_price);
        editDescription = (EditText) findViewById(R.id.description);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
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
                        registerBook();
                    }
                }
            }
        });

    }

    private void registerBook() {

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

                    int book_detail_status = jsonResponse.getInt("book_detail_status");

                    if(book_detail_status == 1){
                        Toast.makeText(getApplicationContext(),"책이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                        finish();
                    }else if(book_detail_status == 2){
                        Toast.makeText(getApplicationContext(),"서버와 연결이 원활하지 않습니다.",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(String.valueOf(user_id), img_path, name, author, publisher, published_date, subject, professor, price, sale_price, description, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookRegisterActivity.this);
        queue.add(registerRequest);
    }
}
