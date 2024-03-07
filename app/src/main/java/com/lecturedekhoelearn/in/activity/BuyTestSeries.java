package com.lecturedekhoelearn.in.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.BuyPackageAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.BuyPackageModel;
import com.lecturedekhoelearn.in.model.BuyPackageModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyTestSeries extends AppCompatActivity {

    RecyclerView rv_buy_package;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String classId, StudentId,type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_test_series);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        classId = preferences.getString("class_id", "");
        StudentId = preferences.getString("studentId", "");

        rv_buy_package = findViewById(R.id.rv_buy_package);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });
        forBuyAllPackages();
    }


    public void forBuyAllPackages() {

        Call<BuyPackageModel> call = Api_Client.getInstance().forBuyAllPacakge(StudentId, classId);
        call.enqueue(new Callback<BuyPackageModel>() {
            @Override
            public void onResponse(Call<BuyPackageModel> call, Response<BuyPackageModel> response) {

                if (response.body().getStatus().equals(1)) {
                    BuyPackageModel buyPackageModel = response.body();
                    ArrayList<BuyPackageModelDetails> buyPackageModelDetails = (ArrayList<BuyPackageModelDetails>) buyPackageModel.getBuyPackageModelDetails();
                    ArrayList<BuyPackageModelDetails> buyTest = new ArrayList<BuyPackageModelDetails>();
                     if (buyPackageModelDetails != null && !buyPackageModelDetails.isEmpty() && buyPackageModelDetails.size() > 0) {
                        for (BuyPackageModelDetails buyPackageModelDetails1 : buyPackageModelDetails) {
                           // if (buyPackageModelDetails1.getPackage_id() == null || buyPackageModelDetails1.getPackage_id().isEmpty() && buyPackageModelDetails1.getStudent_id() == null || buyPackageModelDetails1.getStudent_id().isEmpty()) {
                                buyTest.add(buyPackageModelDetails1);
                                if (buyPackageModelDetails1.getPackage_id()!=null){
                                    buyTest.add(buyPackageModelDetails1);
                                }
                           // }
                        }
                         if (buyTest.get(0).getPackage_id()!=null){
                             Intent i12 = new Intent(BuyTestSeries.this, PackageDetailsActivity.class);
                             i12.putExtra("pid", buyTest.get(0).getId());
                             i12.putExtra("p_id", buyTest.get(0).getPackage_id());
                             i12.putExtra("packageName", buyTest.get(0).getName());
                             i12.putExtra("amount", buyTest.get(0).getPrice());
                             i12.putExtra("image", buyTest.get(0).getThumbnail());
                             i12.putExtra("des", buyTest.get(0).getDescription());
                             i12.putExtra("testType", buyTest.get(0).getType());
                             i12.putExtra("discount", buyTest.get(0).getDiscount());
                             i12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(i12);
                             finish();
                         }else {
                             BuyPackageAdapter analysisAdapter = new BuyPackageAdapter(getApplication(), buyTest);
                             rv_buy_package.setAdapter(analysisAdapter);
                             rv_buy_package.setHasFixedSize(true);
                             rv_buy_package.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
                         }
                    } else {
                        Toast.makeText(BuyTestSeries.this, "No Buy Test series found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BuyPackageModel> call, Throwable t) {

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