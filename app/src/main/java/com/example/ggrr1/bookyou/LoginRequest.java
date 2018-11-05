package com.example.ggrr1.bookyou;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "";
    private Map<String, String> parameters;

    public LoginRequest(String email, String password, String name, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email",email);
        parameters.put("password",password);
        parameters.put("name",name);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
