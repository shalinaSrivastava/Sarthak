package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ScholarshipAdapter;
import com.lecturedekhoelearn.in.adapter.SeeAllNotificationAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.AllNotificationModel;
import com.lecturedekhoelearn.in.model.NotificationDetailsModel;
import com.lecturedekhoelearn.in.model.ScholarShipModel;
import com.lecturedekhoelearn.in.model.ScholarShipModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    RecyclerView rv_scholarship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_scholarship = findViewById(R.id.rv_scholarship);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {

            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        getAllScholarshipdata();
    }


    public void getAllScholarshipdata() {
        pDialog.setMessage("Loading All Scholarship...");
        showDialog();
        Call<ScholarShipModel> allNotificationModelCall = Api_Client.getInstance().getScholarship();
        allNotificationModelCall.enqueue(new Callback<ScholarShipModel>() {
            @Override
            public void onResponse(Call<ScholarShipModel> call, Response<ScholarShipModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    ScholarShipModel allNotificationModel = response.body();
                    ArrayList<ScholarShipModelDetails> notificationDetailsModels = (ArrayList<ScholarShipModelDetails>) allNotificationModel.getScholarShipModelDetails();
                    ScholarshipAdapter analysisAdapter = new ScholarshipAdapter(getApplication(), notificationDetailsModels);
                    rv_scholarship.setAdapter(analysisAdapter);
                    rv_scholarship.setHasFixedSize(true);
                    rv_scholarship.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                } else {
                    hideDialog();
                    Toast.makeText(ScholarshipActivity.this, "No Scholarship List Found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ScholarShipModel> call, Throwable t) {
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
