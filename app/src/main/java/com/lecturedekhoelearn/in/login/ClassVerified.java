package com.lecturedekhoelearn.in.login;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ClassAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.ClassModel;
import com.lecturedekhoelearn.in.model.ClassModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassVerified extends AppCompatActivity {

    RecyclerView rv_class;
    private ProgressDialog pDialog;
    String CategoryId;
    Button btn_next, btn_previous;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_verified);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_class = findViewById(R.id.rv_class);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        CategoryId = preferences.getString("category_id", "");

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        getStudentClass();
    }

    public void getStudentClass() {

        pDialog.setMessage("Getting All Classes....");
        showDialog();
        Call<ClassModel> levelTypeModelCall = Api_Client.getInstance().getClasses(CategoryId);
        levelTypeModelCall.enqueue(new Callback<ClassModel>() {
            @Override
            public void onResponse(Call<ClassModel> call, Response<ClassModel> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        hideDialog();
                        ClassModel classModel = response.body();
                        ArrayList<ClassModelDetails> classModelDetails = (ArrayList<ClassModelDetails>) classModel.getClassModelDetails();
                        rv_class.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        ClassAdapter analysisAdapter = new ClassAdapter(getApplication(), classModelDetails);
                        rv_class.setAdapter(analysisAdapter);
                        rv_class.setHasFixedSize(true);
                    } else {
                        hideDialog();
                        Toast.makeText(ClassVerified.this, "No Class found", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ClassModel> call, Throwable t) {
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
