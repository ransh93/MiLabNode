package com.idc.milab.weather;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void fetchWeather(final View view) {
		final WeatherFetcher fetcher = new WeatherFetcher(view.getContext());
		final String city = ((EditText)findViewById(R.id.edit_city)).getText().toString();
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Fetching weather for " + city + "...");
		progressDialog.show();

		fetcher.dispatchRequest(city, new WeatherFetcher.WeatherResponseListener() {
			@Override
			public void onResponse(WeatherFetcher.WeatherResponse response) {
				progressDialog.hide();

				if (response.isError) {
					Toast.makeText(view.getContext(), "Error while fetching weather", Toast.LENGTH_LONG);
					return;
				}

				((TextView)MainActivity.this.findViewById(R.id.text_weather)).setText(response.location);
				((TextView)MainActivity.this.findViewById(R.id.text_temprature)).setText(String.valueOf(response.temp));
				((TextView)MainActivity.this.findViewById(R.id.text_description)).setText(response.description);
			}
		});
	}
}
