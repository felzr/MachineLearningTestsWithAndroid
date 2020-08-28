package com.example.ramos.machinelearningtests.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramos.machinelearningtests.Classifiers.DogsAndCatsClassifier;
import com.example.ramos.machinelearningtests.R;
import com.example.ramos.machinelearningtests.Utils.Permissao;

import java.io.IOException;
import java.util.List;

public class CatsAndDogsSorterActivity extends AppCompatActivity {
    private ImageView image;
    private TextView textViewResult;
    private Button openGalery, openCamera, predict;
    private int PERMISSION_ALL = 1;
    private final int PICK_IMAGE_REQUEST = 22;
    private final int REQUEST_IMAGE_CAPTURE = 23;
    private Bitmap picture;
    private Uri filePath;
    private int mInputSize = 224;
    private String mModelPath = "converted_model.tflite";
    private String mLabelPath = "label.txt";
    private DogsAndCatsClassifier classifier;
    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats_and_dogs_sorter);

        if (!Permissao.validaPermissoes(PERMISSION_ALL, CatsAndDogsSorterActivity.this, PERMISSIONS)) {
            finishThis();
        }
        try {
            initClassifier();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setViewListners();
    }

    private void setViewListners() {
        openCamera = findViewById(R.id.btn_camera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        openGalery = findViewById(R.id.btn_galery);
        openGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });
        predict = findViewById(R.id.btn_predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picture != null) {
                    predict(picture);
                }
            }
        });
        image = findViewById(R.id.image_view);
        textViewResult = findViewById(R.id.result);
    }

    private void predict(Bitmap picture) {
        List<DogsAndCatsClassifier.Recognition> result = classifier.recognizeImage(picture);
        Toast.makeText(this, result.get(0).toString(), Toast.LENGTH_SHORT).show();
        textViewResult.setText(result.get(0).toString());
    }

    private void initClassifier() throws IOException {
        classifier = new DogsAndCatsClassifier(getAssets(), mModelPath, mLabelPath, mInputSize);
    }

    private void finishThis() {
        Toast.makeText(getApplicationContext(), "To access this topic, you must give the requested permissions", Toast.LENGTH_LONG).show();
        finish();
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    public void openGalery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select your Picture..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {

                picture = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                image.setImageBitmap(picture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            picture = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(picture);
        }
    }
}