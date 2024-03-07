package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.CategoryAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.CategoryModel;
import com.lecturedekhoelearn.in.model.CategoryModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView rv_category;
    private ProgressDialog pDialog;
    String Level_Id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        Level_Id=preferences.getString("level_id","");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        rv_category = findViewById(R.id.rv_category);


        getAllCategory();


    }


    private void getAllCategory() {
        pDialog.setMessage("Getting All Category....");
        showDialog();
        Call<CategoryModel> levelTypeModelCall = Api_Client.getInstance().getCategoryType("1");
        levelTypeModelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    CategoryModel categoryModel = response.body();
                    ArrayList<CategoryModelDetails> notificationDetailsModels = (ArrayList<CategoryModelDetails>) categoryModel.getCategoryModelDetails();
                    CategoryAdapter analysisAdapter = new CategoryAdapter(getApplication(), notificationDetailsModels);
                    rv_category.setAdapter(analysisAdapter);
                    rv_category.setHasFixedSize(true);
                    rv_category.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));


                } else {
                    hideDialog();
                    Toast.makeText(CategoryActivity.this, "No Category found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
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