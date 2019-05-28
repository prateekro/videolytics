package com.prateek.videolytics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.engine.OpenCVEngineInterface;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            // OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
            Log.e("OpenCv", "Unable to load OpenCV");
        } else
            Log.d("OpenCv", "OpenCV loaded");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button btn_camera = (Button) findViewById(R.id.button);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCamera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(goToCamera);
            }
        });

        btn_camera.setClickable(false);
        btn_camera.setText("OLD - Doesn't work");
        btn_camera.setAlpha((float) 0.5);

        Button btn_camera2 = (Button) findViewById(R.id.btn_camera2_api);
        btn_camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCamera2api = new Intent(MainActivity.this, CameraAPIActivity.class);
                startActivity(goToCamera2api);
            }
        });
    }

}