package com.example.jean.machinelearningtests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button simplePredict, fuelPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simplePredict = findViewById(R.id.simple_predict);
        simplePredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SimplePredictActivity.class);
                startActivity(i);
            }
        });
        fuelPredict = findViewById(R.id.fuel_predict);
        fuelPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FuelPredictionActivity.class);
                startActivity(i);
            }
        });
    }
}