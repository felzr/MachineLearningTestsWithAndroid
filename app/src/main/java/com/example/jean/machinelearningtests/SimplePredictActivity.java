package com.example.jean.machinelearningtests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class SimplePredictActivity extends AppCompatActivity {
    private EditText editText;
    private Button predict;
    private TextView result;
    Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_predict);
        editText = findViewById(R.id.edt_text);
        predict = findViewById(R.id.btn_predict);
        result = findViewById(R.id.text_result);
        try {
            interpreter = new Interpreter(loadModel(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float r = doInference(editText.getText().toString());
                result.setText("resultado: " + r);


            }
        });
    }

    private MappedByteBuffer loadModel() throws IOException {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("linear.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long starOffSet = assetFileDescriptor.getStartOffset();
        long lenght = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, starOffSet, lenght);
    }

    float doInference(String val) {
        float[] input = new float[1];
        input[0] = Float.parseFloat(val);
        float[][] output = new float[1][1];
        interpreter.run(input, output);
        return output[0][0];
    }
}