package com.lecturedekhoelearn.in.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.PurchasedTestSeriesAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModel;
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPurchasedTestSeriesActivity extends AppCompatActivity {

    RecyclerView rv_test_package;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String classId, StudentId;

    RelativeLayout ll_package;
    Button btn_seepackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchased_test_series);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        classId = preferences.getString("class_id", "");
        StudentId = preferences.getString("studentId", "");

        rv_test_package = findViewById(R.id.rv_test_package);
        ll_package = findViewById(R.id.ll_package);
        btn_seepackages = findViewById(R.id.btn_seepackages);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        btn_seepackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MyPurchasedTestSeriesActivity.this, BuyTestSeries.class);
                startActivity(i);
            }
        });
        getMyTestPurchasedTestSeries();
    }


    public void getMyTestPurchasedTestSeries() {
        Call<MyPurchasedTestSeriesModel> call = Api_Client.getInstance().getMyTestSeries(StudentId, classId);
        call.enqueue(new Callback<MyPurchasedTestSeriesModel>() {
            @Override
            public void onResponse(Call<MyPurchasedTestSeriesModel> call, Response<MyPurchasedTestSeriesModel> response) {

                if (response.body().getStatus().equals(1)) {
                    rv_test_package.setVisibility(View.VISIBLE);
                    ll_package.setVisibility(View.GONE);
                    MyPurchasedTestSeriesModel myTestSeriesModel = response.body();
                    ArrayList<MyPurchasedTestSeriesModelDetails> myTestSeriesModelDetails = (ArrayList<MyPurchasedTestSeriesModelDetails>) myTestSeriesModel.getMyTestSeriesModelDetails();
                    ArrayList<MyPurchasedTestSeriesModelDetails> freepaidTest = new ArrayList<MyPurchasedTestSeriesModelDetails>();
                    if (myTestSeriesModelDetails != null && !myTestSeriesModelDetails.isEmpty() && myTestSeriesModelDetails.size() > 0) {
                        for (MyPurchasedTestSeriesModelDetails myPurchasedTestSeriesModelDetails : myTestSeriesModelDetails) {

                            if (myPurchasedTestSeriesModelDetails.getPackage_id() != null && myPurchasedTestSeriesModelDetails.getStudent_id() != null) {
                                freepaidTest.add(myPurchasedTestSeriesModelDetails);
                            } else {
                                rv_test_package.setVisibility(View.GONE);
                                ll_package.setVisibility(View.VISIBLE);
                                //  Toast.makeText(MyPurchasedTestSeriesActivity.this, "Please Subscribed your Pacakges ", Toast.LENGTH_SHORT).show();

                            }
                        }
                        rv_test_package.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        PurchasedTestSeriesAdapter testSeriesAdapter = new PurchasedTestSeriesAdapter(getApplicationContext(), freepaidTest);
                        testSeriesAdapter.notifyDataSetChanged();
                        rv_test_package.setAdapter(testSeriesAdapter);

                    } else {

                        rv_test_package.setVisibility(View.GONE);
                        ll_package.setVisibility(View.VISIBLE);

                        //  Toast.makeText(MyPurchasedTestSeriesActivity.this, "No purchased Test series found ", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<MyPurchasedTestSeriesModel> call, Throwable t) {

            }
        });


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
