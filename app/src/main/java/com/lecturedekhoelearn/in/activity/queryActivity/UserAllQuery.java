package com.lecturedekhoelearn.in.activity.queryActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.AllQueryAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.queryModel.AllQuery;
import com.lecturedekhoelearn.in.model.queryModel.AllQueryDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAllQuery extends AppCompatActivity {

    SharedPreferences preferences;
    String id, Teacherid;
    RecyclerView recyclerView;
    TextView nodata;
    FloatingActionButton fab;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_query);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        id = preferences.getString("studentId", "");
        Teacherid = preferences.getString("teacher_id", "");

        nodata = findViewById(R.id.nodata);
        fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recycleAssignment);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAllQuery.this, userQuery.class));
            }
        });
        loadAllQuery();

    }

    private void loadAllQuery() {
        pDialog.setTitle("Loading All Queries...");
        showDialog();
        Call<AllQuery> allQuerycall = Api_Client.getInstance().getAllQuery(id,Teacherid);
        allQuerycall.enqueue(new Callback<AllQuery>() {
            @Override
            public void onResponse(Call<AllQuery> call, Response<AllQuery> response) {
                if (response.body().getStatus().equals(1)) {
                    AllQuery subject = response.body();
                    hideDialog();
                    ArrayList<AllQueryDetails> allQueryDetails = (ArrayList<AllQueryDetails>) subject.getGetDetails();
                    AllQueryAdapter allQueryAdapter = new AllQueryAdapter(UserAllQuery.this, allQueryDetails);
                    recyclerView.setAdapter(allQueryAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
                } else {
                    hideDialog();
                    nodata.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<AllQuery> call, Throwable t) {
                hideDialog();
                Toast.makeText(UserAllQuery.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
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