package com.hazelhunt.passkeeper.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    public static final String TAG = "HttpRequest";

    public static int RESPONSE_CODE;

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        RESPONSE_CODE = connection.getResponseCode();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return "".getBytes();
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();



            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();

        } finally {
            connection.disconnect();
        }
    }

    public void postData(String url,JSONObject json) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost post = (HttpPost) createPostForJSONObject(json, url);
            HttpResponse response = httpclient.execute(post);
            Log.d(TAG, isOKResponseCode() ? "200" : "403");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static HttpUriRequest createPostForJSONObject(JSONObject params, String url) {
        HttpPost post = new HttpPost(url);
        post.setEntity(createStringEntity(params));
        return post;
    }

    private static HttpEntity createStringEntity(JSONObject params) {
        StringEntity se = null;
        try {
            se = new StringEntity(params.toString(), "UTF-8");
            se.setContentType("application/json; charset=UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Failed to create StringEntity", e);
        }
        return se;
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public boolean isOKResponseCode() {
        return RESPONSE_CODE == 200;
    }

}
