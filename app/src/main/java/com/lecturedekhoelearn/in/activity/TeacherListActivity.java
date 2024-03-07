package com.lecturedekhoelearn.in.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TeacherRatingDetailsMode;
import com.lecturedekhoelearn.in.model.TeacherRatingModel;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.teacherProfilethubnails;

public class TeacherListActivity extends AppCompatActivity implements RatingDialogListener {

    private ProgressDialog pDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String StudentId, teacherId, TeacherRaing, TeacherProfile, TeacherNumber, temail, teachName,teacher_about;
    ImageView image;
    LinearLayout ll_rating;
    RatingBar rate_img;
    Button btn_whats_call;
    TextView tv_number, teacher_mail, teacher_name,teacher_qualification;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        ll_rating = findViewById(R.id.ll_rating);
        rate_img = findViewById(R.id.rate_img);
        image = findViewById(R.id.image);
        tv_number = findViewById(R.id.tv_number);
        teacher_mail = findViewById(R.id.teacher_mail);
        teacher_name = findViewById(R.id.teacher_name);
        btn_whats_call = findViewById(R.id.btn_whats_call);
        teacher_qualification = findViewById(R.id.teacher_qualification);

        teacherId = preferences.getString("teacher_id", "");
        StudentId = preferences.getString("studentId", "");
        TeacherRaing = preferences.getString("teacher_rating", "");
        TeacherProfile = preferences.getString("teacher_profile", "");
        TeacherNumber = preferences.getString("teacherNumber", "");
        temail = preferences.getString("teacher_email", "");
        teachName = preferences.getString("teachName", "");
        teacher_about = preferences.getString("teacherAbout", "");


        if (TeacherRaing.isEmpty()) {
        } else {
            rate_img.setRating(Float.parseFloat(TeacherRaing));
        }

        tv_number.setText(TeacherNumber);
        teacher_mail.setText(temail);
        teacher_name.setText(teachName);
        teacher_qualification.setText(teacher_about);

        Picasso.get()
                .load(teacherProfilethubnails + TeacherProfile)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into((image));


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        ll_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AppRatingDialog.Builder()
                        .setPositiveButtonText("Submit")
                        .setNegativeButtonText("Cancel")
                        .setNeutralButtonText("Later")
                        .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                        .setDefaultRating(2)
                        .setTitle("Rate this application")
                        .setDescription("Please select some stars and give your feedback")
                        .setCommentInputEnabled(true)
                        .setDefaultComment("This app is pretty cool !")
                        .setStarColor(R.color.starColor)
                        .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                        .setTitleTextColor(R.color.titleTextColor)
                        .setDescriptionTextColor(R.color.gen_white)
                        .setHint("Please write your comment here ...")
                        .setHintTextColor(R.color.hintTextColor)
                        .setCommentTextColor(R.color.commentTextColor)
                        .setCommentBackgroundColor(R.color.colorPrimaryDark)
                        .setWindowAnimation(R.style.MyDialogFadeAnimation)
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .create(TeacherListActivity.this)
                        .show();


            }
        });

        btn_whats_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeacherListActivity.this, "Hiii", Toast.LENGTH_SHORT).show();
                try {
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    //  String waNumber = TeacherNumber.replace("+91", "");
                    sendIntent.putExtra("jid", "91"+TeacherNumber + "@s.whatsapp.net");
                    startActivity(sendIntent);
                }catch (Exception e){
                    e.printStackTrace();
                }

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

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {

        Call<TeacherRatingModel> userRateResponseCall = Api_Client.getInstance().updateRateUS(teacherId, String.valueOf(i), StudentId);
        userRateResponseCall.enqueue(new Callback<TeacherRatingModel>() {
            @Override
            public void onResponse(Call<TeacherRatingModel> call, Response<TeacherRatingModel> response) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(TeacherListActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    TeacherRatingModel teacherRatingModel = response.body();
                    TeacherRatingDetailsMode teacherRatingDetailsMode = teacherRatingModel.getTeacherRatingDetailsMode();
                    String rating = teacherRatingDetailsMode.getRate_us();
                    Toast.makeText(TeacherListActivity.this, rating, Toast.LENGTH_SHORT).show();
                    rate_img.setRating(Float.parseFloat(rating));

                } else {
                    TeacherRatingModel teacherRatingModel = response.body();
                    TeacherRatingDetailsMode teacherRatingDetailsMode = teacherRatingModel.getTeacherRatingDetailsMode();
                    String rating = teacherRatingDetailsMode.getRate_us();
                    Toast.makeText(TeacherListActivity.this, rating, Toast.LENGTH_SHORT).show();
                    Toast.makeText(TeacherListActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    rate_img.setRating(Float.parseFloat(rating));
                }
            }

            @Override
            public void onFailure(Call<TeacherRatingModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}