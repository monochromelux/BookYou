package com.example.ggrr1.bookyou;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {
    final static private String URL = "http://211.213.95.132/mybookstore/book_delete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String book_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("book_id",book_id);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
