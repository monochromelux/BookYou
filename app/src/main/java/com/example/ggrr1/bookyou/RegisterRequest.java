package com.example.ggrr1.bookyou;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://211.213.95.132/mybookstore/book_register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String user_id, String img_path, String name, String author, String publisher, String published_date, String subject, String professor, String price, String sale_price, String description, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id",user_id);
        parameters.put("img_path",img_path);
        parameters.put("name",name);
        parameters.put("author",author);
        parameters.put("publisher",publisher);
        parameters.put("published_date",published_date);
        parameters.put("subject",subject);
        parameters.put("professor",professor);
        parameters.put("price",price);
        parameters.put("sale_price",sale_price);
        parameters.put("description",description);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
