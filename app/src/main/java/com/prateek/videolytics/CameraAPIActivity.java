package com.prateek.videolytics;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.prateek.videolytics.models.Responsv;
import com.prateek.videolytics.models.ResultsItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// **********************************************
// ******** Follow - Camera API tutorial ********
// https://android.jlelse.eu/the-least-you-can-do-with-camera2-api-2971c8c81b8b
// **********************************************
// *********Code by Prateek Rokadiya*********

public class CameraAPIActivity extends AppCompatActivity {

    private static final Integer MAX_LABEL_RESULTS = 10;
    TextureView textureView;
    Button btn_capture;

    Integer CAMERA_REQUEST_CODE = 1;
    CameraManager cameraManager;
    Integer cameraFacing;
    String cameraId;
    //    Thread backgroundThread;
    //    HandlerThread backgroundHandler;
    HandlerThread backgroundThread;
    Handler backgroundHandler;

    TextureView.SurfaceTextureListener surfaceTextureListener;
    private Size previewSize;


    private CameraDevice cameraDevice;
    private CameraDevice.StateCallback stateCallback;
    private CaptureRequest captureRequest;
    private CaptureRequest.Builder captureRequestBuilder;
    private CameraCaptureSession cameraCaptureSession;
    private File galleryFolder;
    private String TAG = CameraAPIActivity.class.getSimpleName();
    private int MAX_DIMENSION = 1200;

    private static String CLOUD_VISION_API_KEY = Credentials.KEY;
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_api);

        stateCallback = new CameraDevice.StateCallback() {

            @Override
            public void onOpened(CameraDevice cameraDevice) {
                CameraAPIActivity.this.cameraDevice = cameraDevice;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(CameraDevice cameraDevice) {
                cameraDevice.close();
                CameraAPIActivity.this.cameraDevice = null;
            }

            @Override
            public void onError(CameraDevice cameraDevice, int error) {
                cameraDevice.close();
                CameraAPIActivity.this.cameraDevice = null;
            }
        };

        textureView = (TextureView) findViewById(R.id.textureView);
        btn_capture = (Button) findViewById(R.id.btn_capture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openBackgroundThread();
        if (textureView.isAvailable()) {
            setUpCamera();
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_REQUEST_CODE);

        createImageGallery();


        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraFacing = CameraCharacteristics.LENS_FACING_BACK;

        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
//                Set-up Camera
//                Open Camera

                setUpCamera();
                openCamera();

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView imageDetail = (TextView) findViewById(R.id.result_view);
                imageDetail.setText("Detecting.");
//                imageDetail.setBackgroundColor(Color.argb(1, 255, 255, 255));

                //TODO - TAKE / Click photo
                onTakePhotoButtonClicked();
//                callForSimilarPictures("Hello");


            }
        });

    }

    private void callForSimilarPictures(String keyword) {
//        keyword = "hello";

        Responsv Jobject = ApiRequest.getApi(keyword);
        if (Jobject != null){

            if (Jobject.getResults().size() > 0){
                for (ResultsItem resultx : Jobject.getResults()){
                    Log.d(TAG, "onClick: -----------------------------------------------------");
                    Log.d(TAG, "REGULAR onClick: "+resultx.getUrls().getRegular());
                    Log.d(TAG, "THUMB onClick: "+resultx.getUrls().getThumb());
                    Log.d(TAG, "onClick: -----------------------------------------------------");

                }
//                        Log.d(TAG, "RAW onClick: "+Jobject.getResults().get(0).getUrls().getRaw());
//                        Log.d(TAG, "THUMB onClick: "+Jobject.getResults().get(0).getUrls().getThumb());
            }

        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        closeCamera();
        closeBackgroundThread();
    }

    private void setUpCamera() {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        cameraFacing) {
                    StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(
                            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                    this.cameraId = cameraId;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraId, stateCallback, backgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openBackgroundThread() {
        backgroundThread = new HandlerThread("camera_background_thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void closeBackgroundThread() {
        if (backgroundHandler != null) {
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }
    }

    private void createPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null) {
                                return;
                            }

                            try {
                                captureRequest = captureRequestBuilder.build();
                                CameraAPIActivity.this.cameraCaptureSession = cameraCaptureSession;
                                CameraAPIActivity.this.cameraCaptureSession.setRepeatingRequest(captureRequest,
                                        null, backgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {

                        }
                    }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Write Image
    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        galleryFolder = new File(storageDirectory, getResources().getString(R.string.app_name));
        if (!galleryFolder.exists()) {
            boolean wasCreated = galleryFolder.mkdirs();
            if (!wasCreated) {
                Log.e("CapturedImages", "Failed to create directory");
            }
        } else{
            Log.d("CapturedImages", "createImageGallery: EXISTS");
        }
    }

    private File createImageFile(File galleryFolder) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "videolytics_image_" + timeStamp + "_";
        return File.createTempFile(imageFileName, ".jpg", galleryFolder);
    }

    public File getCameraFile(String FILE_NAME) {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    public void onTakePhotoButtonClicked() {
//        FileOutputStream outputPhoto = null;
        try {
//            File thisIsFile = createImageFile(galleryFolder);
//            outputPhoto = new FileOutputStream(thisIsFile);
//            textureView.getBitmap()
//                    .compress(Bitmap.CompressFormat.PNG, 100, outputPhoto);
//            callCloudVision(textureView.getBitmap());

//            Bitmap smallerBitmap = scaleBitmapDown(textureView.getBitmap(), 300);
            Bitmap smallerBitmap = scaleBitmapDown(textureView.getBitmap(), 300);
            callCloudVision(smallerBitmap, 80);
            //For Cloud Vision
//            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile(outputPhoto.getFD().));
//            uploadImage(Uri.fromFile(new File(String.valueOf(outputPhoto))));
//            uploadImage(photoUri);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Log.d(TAG, "onTakePhotoButtonClicked: TRY");
//                if (outputPhoto != null) {
//                    outputPhoto.close();
//                }
//            } catch (IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    For cloud vision
    private void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

//                callCloudVision(bitmap);
//                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
//                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
//            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }

    }

    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap, final Integer jpegQuality) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, jpegQuality, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
//                Feature textDetection = new Feature();
//                textDetection.setType("DOCUMENT_TEXT_DETECTION");
////                textDetection.setMaxResults(MAX_LABEL_RESULTS);
//                add(textDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }



    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<CameraAPIActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        private String TAG = LableDetectionTask.class.getSimpleName();

        LableDetectionTask(CameraAPIActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            Log.d(TAG, "VISION onPostExecute nosplit result: "+result);
            CameraAPIActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
//                TextView imageDetail = activity.findViewById(R.id.image_details);
//                imageDetail.setText(result);
                TextView imageDetail = activity.findViewById(R.id.result_view);
//                result = result.subSequence(21,result.length()).toString();
                result = result.subSequence(21,result.length()).toString();
                imageDetail.setText(result);

                String[] lines = result.split("[\\r\\n]+");
                int count = 0, keyXcount = 0;
                for (String line : lines){
                    count += 1;
                    imageDetail.setText(imageDetail.getText().toString()+"\n"+count+"> "+ line);
                    if (line.split(":").length > 1){
                        keyXcount += 1;
                        if (keyXcount == 1){
                            imageDetail.setText(imageDetail.getText().toString()+"\n -- \n"+keyXcount+">> "+line.split(":")[1]+"\n -- \n");
                        }
                    }
                }

                imageDetail.setBackgroundColor(Color.parseColor("#000000"));
                Log.d(TAG, "onPostExecute: result::"+result);
            }
        }
    }


    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private void callCloudVision(Bitmap bitmap, Integer jpegQuality) {
        // Switch text to loading
//        mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap, jpegQuality));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("I found these things:\n\n");

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing");
        }

        return message.toString();
    }

}
