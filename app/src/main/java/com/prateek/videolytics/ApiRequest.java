package com.prateek.videolytics;

import com.google.gson.Gson;
import com.prateek.videolytics.models.Responsv;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.prateek.videolytics.Credentials.ACCESS_KEY_UNSPLASH;
import static com.prateek.videolytics.Credentials.TEMP;

public class ApiRequest {

    // With default 3 pictures response per page from unsplash.
    public static Responsv getApi(String keyword) {
        return getApi(keyword, 3);
    }

    public static Responsv getApi(String keyword, int resultPerPage){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.unsplash.com/search/photos?query="+keyword+"&per_page="+resultPerPage+"&client_id="+ACCESS_KEY_UNSPLASH)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();

        String response = "Empty";
        Responsv resx = null;

        try {
            response = client.newCall(request).execute().body().string();
            Gson g = new Gson();
            resx = g.fromJson(response, Responsv.class);

            // resx = g.fromJson(TEMP, Responsv.class);
            // response = client.newCall(request).execute().body().string();
        } catch (Exception e) {

            e.printStackTrace();
        }

        // return response;
        return resx;
    }






}
