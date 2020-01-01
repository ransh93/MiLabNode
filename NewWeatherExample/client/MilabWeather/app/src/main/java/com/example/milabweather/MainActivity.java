package com.example.milabweather;


import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fetchButton = findViewById(R.id.fetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fetchWeather(view);
            }
        });
    }

    public void fetchWeather(final View view) {
        final WeatherFetcher fetcher = new WeatherFetcher(view.getContext());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching weather...");
        progressDialog.show();

        fetcher.dispatchRequest(new WeatherFetcher.WeatherResponseListener() {
            @Override
            public void onResponse(WeatherFetcher.WeatherResponse response) {
                progressDialog.hide();

                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching weather", Toast.LENGTH_LONG);
                    return;
                }

                ((TextView)MainActivity.this.findViewById(R.id.location)).setText(response.location);
                ((TextView)MainActivity.this.findViewById(R.id.temprature)).setText(String.valueOf(response.temp));

            }
        });
    }
}