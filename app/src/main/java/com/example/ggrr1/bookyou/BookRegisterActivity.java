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

    String img_path, name, author, publisher, published_date, subject, professor, price, sale_price, description;

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

        checkPermissions();

        //btnImage = (ImageButton) findViewById(R.id.btnImage);

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

//        btnImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        takePicture();
//                    }
//                };
//
//                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        chooseAlbum();
//                    }
//                };
//
//                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        dialog.dismiss();
//                    }
//                };
//                new AlertDialog.Builder(BookRegisterActivity.this)
//                        .setTitle("책 이미지 선택")
//                        .setPositiveButton("사진촬영", cameraListener)
//                        .setNeutralButton("앨범선택", albumListener)
//                        .setNegativeButton("취소", cancelListener)
//                        .show();
//            }
//        });

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

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    //아래는 권한 요청 Callback 함수입니다. PERMISSION_GRANTED로 권한을 획득했는지 확인할 수 있습니다. 아래에서는 !=를 사용했기에
//권한 사용에 동의를 안했을 경우를 if문으로 코딩되었습니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }


    //권한 획득에 동의를 하지 않았을 경우 아래 Toast 메세지를 띄우며 해당 Activity를 종료시킵니다.
    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //사진을 찍기 위하여 설정합니다.
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(BookRegisterActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();              finish();
        }
        if (photoFile != null) {
            imagePath = FileProvider.getUriForFile(BookRegisterActivity.this,
                    "com.example.ggrr1.bookyou.provider", photoFile); //FileProvider의 경우 이전 포스트를 참고하세요.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath); //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }


    // Android M에서는 Uri.fromFile 함수를 사용하였으나 7.0부터는 이 함수를 사용할 시 FileUriExposedException이
    // 발생하므로 아래와 같이 함수를 작성합니다. 이전 포스트에 참고한 영문 사이트를 들어가시면 자세한 설명을 볼 수 있습니다.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/test/"); //test라는 경로에 이미지를 저장하기 위함
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    private void chooseAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK); //ACTION_PICK 즉 사진을 고르겠다!
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(BookRegisterActivity.this, "오류가 발생하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if(data==null){
                return;
            }
            imagePath = data.getData();
            cropImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            MediaScannerConnection.scanFile(BookRegisterActivity.this, //앨범에 사진을 보여주기 위해 Scan을 합니다.
                    new String[]{imagePath.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        } else if (requestCode == CROP_FROM_CAMERA) {
            try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagePath);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 1000, 1000);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs); //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축


                //여기서는 ImageView에 setImageBitmap을 활용하여 해당 이미지에 그림을 띄우시면 됩니다.
                btnImage.setImageBitmap(thumbImage);
                Toast.makeText(getApplicationContext(),String.valueOf(imagePath),Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage().toString());
            }
        }
    }
    public void cropImage() {
        this.grantUriPermission("com.android.camera", imagePath,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imagePath, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, imagePath,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra("crop", "true");
            intent.putExtra("outputX",300);
            intent.putExtra("outputY",300);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + "/test/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());

            imagePath = FileProvider.getUriForFile(BookRegisterActivity.this,
                    "com.example.ggrr1.bookyou.provider", tempFile);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //Bitmap 형태로 받기 위해 해당 작업 진행

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            grantUriPermission(res.activityInfo.packageName, imagePath,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);


        }

    }
    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
