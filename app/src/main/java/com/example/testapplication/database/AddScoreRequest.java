package com.example.testapplication.database;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddScoreRequest extends StringRequest {
    private static final String URL = "https://abdi21.000webhostapp.com/addScore.php";
    private Map<String, String> params;

    public AddScoreRequest(String name, int score, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("score", String.valueOf(score));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
