package com.example.testapplication.database;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class GetScoreRequest extends StringRequest {
    private static final String URL = "https://abdi21.000webhostapp.com/getScore.php";

    public GetScoreRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }
}
