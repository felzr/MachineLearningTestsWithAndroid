package com.example.ramos.machinelearningtests.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ramos.machinelearningtests.R;

public class MainActivity extends AppCompatActivity {

    private Button simplePredict, fuelPredict, digitRecognizing, dogsAndCatsClassification;

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
        digitRecognizing = findViewById(R.id.digit_recognizing);
        digitRecognizing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecognizingDigitsActivity.class);
                startActivity(i);
            }
        });
        dogsAndCatsClassification = findViewById(R.id.dogs_cats_classification);
        dogsAndCatsClassification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CatsAndDogsSorterActivity.class);
                startActivity(i);
            }
        });
    }
}