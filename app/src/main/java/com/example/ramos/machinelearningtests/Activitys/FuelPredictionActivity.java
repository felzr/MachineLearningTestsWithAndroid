package com.example.ramos.machinelearningtests.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ramos.machinelearningtests.R;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FuelPredictionActivity extends AppCompatActivity {
    float[] mean = {5.477707f, 195.318471f, 104.869427f, 2990.251592f, 15.559236f, 75.898089f, 0.624204f, 0.178344f, 0.197452f};
    float[] std = {1.699788f, 104.331589f, 38.096214f, 843.898596f, 2.789230f, 3.675642f, 0.485101f, 0.383413f, 0.398712f};

    Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_prediction);
        try {
            interpreter = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final EditText cylinders = findViewById(R.id.edt_cylinders);
        final EditText displacement = findViewById(R.id.edt_displacement);
        final EditText horsePower = findViewById(R.id.edt_horsepower);
        final EditText weight = findViewById(R.id.edt_weight);
        final EditText accelaration = findViewById(R.id.edt_accelaration);
        final EditText modelYear = findViewById(R.id.edt_model_year);

        final Spinner origin = findViewById(R.id.spn_origin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"USA", "Europe", "Japan"});
        origin.setAdapter(arrayAdapter);

        final TextView result = findViewById(R.id.text_result);

        Button btn = findViewById(R.id.btn_predict);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[][] floats = new float[1][9];
                floats[0][0] = (Float.parseFloat(cylinders.getText().toString()) - mean[0]) / std[0];
                floats[0][1] = (Float.parseFloat(displacement.getText().toString()) - mean[1]) / std[1];
                floats[0][2] = (Float.parseFloat(horsePower.getText().toString()) - mean[2]) / std[2];
                floats[0][3] = (Float.parseFloat(weight.getText().toString()) - mean[3]) / std[3];
                floats[0][4] = (Float.parseFloat(accelaration.getText().toString()) - mean[4]) / std[4];
                floats[0][5] = (Float.parseFloat(modelYear.getText().toString()) - mean[5]) / std[5];

                switch (origin.getSelectedItemPosition()) {
                    case 0:
                        floats[0][6] = (1 - mean[6]) / std[6];
                        floats[0][7] = (0 - mean[7]) / std[7];
                        floats[0][8] = (0 - mean[8]) / std[8];
                        break;
                    case 1:
                        floats[0][6] = (0 - mean[6]) / std[6];
                        floats[0][7] = (1 - mean[7]) / std[7];
                        floats[0][8] = (0 - mean[8]) / std[8];
                        break;
                    case 2:
                        floats[0][6] = (0 - mean[6]) / std[6];
                        floats[0][7] = (0 - mean[7]) / std[7];
                        floats[0][8] = (1 - mean[8]) / std[8];
                        break;
                }

                float res = doInference(floats);
                result.setText(res + "");

            }
        });

    }

    public float doInference(float[][] input) {
        float[][] output = new float[1][1];

        interpreter.run(input, output);

        return output[0][0];
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("automobile.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length);
    }
}