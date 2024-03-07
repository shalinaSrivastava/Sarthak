package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LiveClass extends AppCompatActivity {
    RequestQueue requestQueue;
    WebView mywebview;
    String urlG="";
    ProgressDialog pDialog;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_class);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mywebview =  findViewById(R.id.webView);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mywebview.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.getSettings().setUseWideViewPort(true);
        mywebview.getSettings().setDomStorageEnabled(true);
        pDialog = new ProgressDialog(LiveClass.this);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        postData();
    }

    public void postData() {
        SharedPreferences preferences;
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        final String classId = preferences.getString("class_id", "");
        final String studentId = preferences.getString("studentId", "");
        final String preferencesname = preferences.getString("name", "");
        pDialog.setMessage("Loading...PLease wait");
       // pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("client_id", "9d9df704a1eccd2bc4683085ca74b666");
            object.put("auth_key", "7d1c8f521109ad5051a40561dc734d4a98010aa48fbdb6ad2ce002923d04efee");
            object.put("room_id", classId);
            object.put("user_id", studentId +"123456");
            object.put("name", preferencesname);
            object.put("type", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://api.teachmint.com/add/user";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    //    pDialog.hide();
                        String objUrl="";
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            JSONObject urlData=jsonObject.getJSONObject("obj");
                             urlG=urlData.getString("url");
                            setWebView(urlG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                      //  new TMVaaSBuilder()
                        //        .initializeVaas(LiveClass.this,objUrl);
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // pDialog.hide();
                Log.d("Response", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private PermissionRequest myRequest;
    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(String url) {
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.getSettings().setSaveFormData(true);
        mywebview.getSettings().setSupportZoom(false);
        mywebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mywebview.getSettings().setPluginState(WebSettings.PluginState.ON);
       // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);


       /* mywebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                myRequest = request;
                runOnUiThread(() -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        askForPermission(request.getOrigin().toString(), Manifest.permission.RECORD_AUDIO, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                      *//*  String[] PERMISSIONS = {
                                PermissionRequest.RESOURCE_AUDIO_CAPTURE,
                                PermissionRequest.RESOURCE_VIDEO_CAPTURE
                        };
                        request.grant(PERMISSIONS);*//*
                    }
                });*/
               // request.grant(request.getResources());
               /* for (String permission : request.getResources()) {
                    switch (permission) {
                        case "android.webkit.resource.AUDIO_CAPTURE": {
                            askForPermission(request.getOrigin().toString(), Manifest.permission.RECORD_AUDIO, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                            break;
                        }
                    }
                }*/
        //    }
       // });

        mywebview.loadUrl(url);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CAMERA
    };

    @Override
    public void onBackPressed() {
        if (mywebview.canGoBack()) {
            mywebview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // myRequest.grant(myRequest.getResources());
              //  if(pDialog!=null){
              //  pDialog.hide();}
            }
        }
    }
}