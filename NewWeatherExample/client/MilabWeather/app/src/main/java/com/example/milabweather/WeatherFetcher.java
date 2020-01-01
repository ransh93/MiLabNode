package com.example.milabweather;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class WeatherFetcher {
    private RequestQueue _queue;
    private final static String REQUEST_URL = "http://192.168.1.32:8080/weather";

    public class WeatherResponse {
        public boolean isError;
        public String location;
        public int temp;
        public String description;
        public int forecastHigh;
        public int forecastLow;
        public String forecastDescription;

        public WeatherResponse(boolean isError, String location, int temp) {
            this.isError = isError;
            this.location = location;
            this.temp = temp;
        }

    }

    public interface WeatherResponseListener {
        public void onResponse(WeatherResponse response);
    }

    public WeatherFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    private WeatherResponse createErrorResponse() {
        return new WeatherResponse(true, null, 0);
    }

    public void dispatchRequest(final WeatherResponseListener listener) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            WeatherResponse res = new WeatherResponse(false,
                                    response.getString("name"),
                                    response.getJSONObject("main").getInt("temp"));
                            listener.onResponse(res);
                        }
                        catch (JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }
}
