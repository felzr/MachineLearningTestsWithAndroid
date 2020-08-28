package com.example.ramos.machinelearningtests.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ramos.machinelearningtests.Classifiers.DigitClassfier;
import com.example.ramos.machinelearningtests.Classifiers.DigitResult;
import com.example.ramos.machinelearningtests.R;
import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.IOException;

public class RecognizingDigitsActivity extends AppCompatActivity {
    FingerPaintView mFpvPaint;
    TextView mTvPrediction;
    TextView mTvProbability;
    TextView mTvTimeCost;
    private DigitClassfier mClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizing_digits);
        try {
            mClassifier = new DigitClassfier(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mFpvPaint = findViewById(R.id.paint);
        mTvPrediction = findViewById(R.id.text_prediction);
        mTvProbability = findViewById(R.id.text_probality);
        mTvTimeCost = findViewById(R.id.text_time_coast);

        Button detect = findViewById(R.id.btn_detect);
        Button clear = findViewById(R.id.btn_clear);

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = mFpvPaint.exportToBitmap(DigitClassfier.IMG_WIDTH, DigitClassfier.IMG_HEIGHT);
                DigitResult res = mClassifier.classify(bitmap);
                mTvProbability.setText("Probability: " + res.getProbability() + "");
                mTvPrediction.setText("Prediction: " + res.getNumber() + "");
                mTvTimeCost.setText("TimeCost: " + res.getTimeCost() + "");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFpvPaint.clear();
                mTvPrediction.setText("");
                mTvProbability.setText("");
                mTvTimeCost.setText("");

            }
        });
    }
}