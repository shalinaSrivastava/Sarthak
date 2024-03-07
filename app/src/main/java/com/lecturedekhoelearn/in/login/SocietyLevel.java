package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.SocietyLevelAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.LevelTypeModel;
import com.lecturedekhoelearn.in.model.LevelTypeModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocietyLevel extends AppCompatActivity {

    RecyclerView rv_level;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        rv_level = findViewById(R.id.rv_level);
        getAllLevel();

    }


    private void getAllLevel() {
        pDialog.setMessage("Getting All levels....");
        showDialog();
        Call<LevelTypeModel> levelTypeModelCall = Api_Client.getInstance().getLevelType();
        levelTypeModelCall.enqueue(new Callback<LevelTypeModel>() {
            @Override
            public void onResponse(Call<LevelTypeModel> call, Response<LevelTypeModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    LevelTypeModel allNotificationModel = response.body();
                    ArrayList<LevelTypeModelDetails> notificationDetailsModels = (ArrayList<LevelTypeModelDetails>) allNotificationModel.getLevelTypeModelDetails();
                    SocietyLevelAdapter analysisAdapter = new SocietyLevelAdapter(getApplication(), notificationDetailsModels);
                    rv_level.setAdapter(analysisAdapter);
                    rv_level.setHasFixedSize(true);
                    rv_level.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));


                } else {
                    hideDialog();
                    Toast.makeText(SocietyLevel.this, "No level found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LevelTypeModel> call, Throwable t) {
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



}
