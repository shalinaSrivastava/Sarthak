package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ScholarshipResultAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SchResultModelDetails;
import com.lecturedekhoelearn.in.model.ScholarshipResultModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipResultActivity extends AppCompatActivity {


    TextView title;
    String packagename, pacDescription, pacimage;
    ImageView imagepackage;
    String bannerPath = "https://www.lecturedekho.com/admin/assets/result/";
    JustifyTextView tv_description;
    Button btn_image;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String userProfile, studentId, scholarId;
    private ProgressDialog pDialog;

    RecyclerView rv_scholarship;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_result);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        packagename = preferences.getString("title", "");
        pacDescription = preferences.getString("desc", "");
        pacimage = preferences.getString("banner", "");
        studentId = preferences.getString("studentId", "");
        scholarId = preferences.getString("sch_id", "");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        title = findViewById(R.id.tv_pac_name);
        tv_description = findViewById(R.id.tv_description);
        btn_image = findViewById(R.id.btn_image);
        imagepackage = findViewById(R.id.image);
        rv_scholarship = findViewById(R.id.rv_scholarship);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        Picasso.get()
                .load(bannerPath + pacimage)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(imagepackage);
        title.setText(packagename);
        tv_description.setText("Description: " + Html.fromHtml(pacDescription));

        getSchResultDeatils();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getSchResultDeatils() {
        pDialog.setMessage("Loading Scholarship Results...");
        showDialog();
        Call<ScholarshipResultModel> allNotificationModelCall = Api_Client.getInstance().getAllScholarResult(studentId, scholarId);
        allNotificationModelCall.enqueue(new Callback<ScholarshipResultModel>() {
            @Override
            public void onResponse(Call<ScholarshipResultModel> call, Response<ScholarshipResultModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    ScholarshipResultModel allNotificationModel = response.body();
                    ArrayList<SchResultModelDetails> notificationDetailsModels = (ArrayList<SchResultModelDetails>) allNotificationModel.getSchResultModelDetailsList();
                    ScholarshipResultAdapter analysisAdapter = new ScholarshipResultAdapter(getApplication(), notificationDetailsModels);
                    rv_scholarship.setAdapter(analysisAdapter);
                    rv_scholarship.setHasFixedSize(true);
                    rv_scholarship.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                } else {
                    hideDialog();
                    Toast.makeText(ScholarshipResultActivity.this, "No Scholarship Results Found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ScholarshipResultModel> call, Throwable t) {
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





/*extends AppCompatActivity {

    private ProgressDialog pDialog;
    RecyclerView rv_scholarship;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String StudentId;
    String scholarid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_result);

        rv_scholarship = findViewById(R.id.rv_scholarship);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        scholarid = preferences.getString("sch_id", "");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {

            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        getSchResultDeatils();
    }


    public void getSchResultDeatils() {
        pDialog.setMessage("Loading Scholarship Results...");
        showDialog();
        Call<ScholarshipResultModel> allNotificationModelCall = Api_Client.getInstance().getAllScholarResult(StudentId,scholarid);
        allNotificationModelCall.enqueue(new Callback<ScholarshipResultModel>() {
            @Override
            public void onResponse(Call<ScholarshipResultModel> call, Response<ScholarshipResultModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    ScholarshipResultModel allNotificationModel = response.body();
                    ArrayList<SchResultModelDetails> notificationDetailsModels = (ArrayList<SchResultModelDetails>) allNotificationModel.getSchResultModelDetailsList();
                    ScholarshipResultAdapter analysisAdapter = new ScholarshipResultAdapter(getApplication(), notificationDetailsModels);
                    rv_scholarship.setAdapter(analysisAdapter);
                    rv_scholarship.setHasFixedSize(true);
                    rv_scholarship.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));

                } else {
                    hideDialog();
                    Toast.makeText(ScholarshipResultActivity.this, "No Scholarship Results Found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ScholarshipResultModel> call, Throwable t) {
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
*/