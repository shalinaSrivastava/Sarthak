package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.SeeAllNotificationAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.AllNotificationModel;
import com.lecturedekhoelearn.in.model.NotificationDetailsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllNotication extends AppCompatActivity {

    RecyclerView rv_notification;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_notication);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });
        rv_notification = findViewById(R.id.rv_notification);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        getAllNotication();
    }

    public void getAllNotication() {
        pDialog.setMessage("Loading All Notifications...");
        showDialog();

        Call<AllNotificationModel> allNotificationModelCall = Api_Client.getInstance().getNotification();
        allNotificationModelCall.enqueue(new Callback<AllNotificationModel>() {
            @Override
            public void onResponse(Call<AllNotificationModel> call, Response<AllNotificationModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    AllNotificationModel allNotificationModel = response.body();
                    ArrayList<NotificationDetailsModel> notificationDetailsModels = (ArrayList<NotificationDetailsModel>) allNotificationModel.getNotificationDetailsModels();
                    SeeAllNotificationAdapter analysisAdapter = new SeeAllNotificationAdapter(getApplication(), notificationDetailsModels);
                    rv_notification.setAdapter(analysisAdapter);
                    rv_notification.setHasFixedSize(true);
                    rv_notification.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));


                } else {
                    hideDialog();
                    Toast.makeText(SeeAllNotication.this, "No Notification found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AllNotificationModel> call, Throwable t) {
                hideDialog();
            }
        });

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}