package com.idc.milab.weather;

import android.content.Context;

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
	private final static String REQUEST_URL = "http://10.0.2.2:8080/weather";

	public class WeatherResponse {
		public boolean isError;
		public String location;
		public int temp;
		public String description;
		public int forecastHigh;
		public int forecastLow;
		public String forecastDescription;

		public WeatherResponse(boolean isError, String location, int temp, String description, int forecastHigh, int forecastLow, String forecastDescription) {
			this.isError = isError;
			this.location = location;
			this.temp = temp;
			this.description = description;
			this.forecastHigh = forecastHigh;
			this.forecastLow = forecastLow;
			this.forecastDescription = forecastDescription;
		}

	}

	public interface WeatherResponseListener {
		public void onResponse(WeatherResponse response);
	}

	public WeatherFetcher(Context context) {
		_queue = Volley.newRequestQueue(context);
	}

	private WeatherResponse createErrorResponse() {
		return new WeatherResponse(true, null, 0, null, 0, 0, null);
	}

	public void dispatchRequest(final WeatherResponseListener listener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							WeatherResponse res = new WeatherResponse(false,
									response.getString("location"),
									response.getInt("temp"),
									response.getString("desc"),
									response.getJSONObject("forecast").getInt("high"),
									response.getJSONObject("forecast").getInt("low"),
									response.getJSONObject("forecast").getString("desc"));
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
