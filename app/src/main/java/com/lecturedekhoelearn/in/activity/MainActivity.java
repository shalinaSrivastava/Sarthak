package com.lecturedekhoelearn.in.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.BuildConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.bookmark.OptionsForBookmark;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.fragment.Doubtsfragment;
import com.lecturedekhoelearn.in.fragment.MockTestFragment;
import com.lecturedekhoelearn.in.fragment.NotificaitonFragment;
import com.lecturedekhoelearn.in.fragment.StudentFragment;
import com.lecturedekhoelearn.in.login.LoginActivity;
import com.lecturedekhoelearn.in.model.FirebaseModel;
import com.lecturedekhoelearn.in.model.UpdateProfileModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.userProfilePath;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final StudentFragment homeFragment = new StudentFragment();
    final MockTestFragment mockTestFragment = new MockTestFragment();
    final Doubtsfragment doubtsfragment = new Doubtsfragment();
    //final VideosFragment videosFragment = new VideosFragment();
    final NotificaitonFragment notificaitonFragment = new NotificaitonFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;
    String profilePic;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Useremail, Username, StudentId, ClassId, courseId;

    CircleImageView imageView;
    TextView user_name, tv_email;
    private static final String TAG = "MainActivity";

    String deviceid;
    String FcmToken;

    String userProfile;
    String refer_code;
    String mobile;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.action_mock_test:
                    fm.beginTransaction().hide(active).show(mockTestFragment).commit();
                    active = mockTestFragment;
                    return true;
                case R.id.action_doubts:
                    fm.beginTransaction().hide(active).show(doubtsfragment).commit();
                    active = doubtsfragment;
                    return true;
                /*case R.id.action_videos:
                    fm.beginTransaction().hide(active).show(videosFragment).commit();
                    active = videosFragment;
                    return true;*/
                case R.id.action_notification:
                    fm.beginTransaction().hide(active).show(notificaitonFragment).commit();
                    active = notificaitonFragment;
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /// getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        deviceid = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);

        StudentId = preferences.getString("studentId", "");
        courseId = preferences.getString("courseId", "");
        Username = preferences.getString("name", "");
        Useremail = preferences.getString("email", "");
        ClassId = preferences.getString("class_id", "");
        profilePic = preferences.getString("profilePic", "");
        refer_code = preferences.getString("refer", "");
        mobile = preferences.getString("mobile", "");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);

        user_name = (TextView) headerview.findViewById(R.id.tv_user);
        tv_email = (TextView) headerview.findViewById(R.id.tv_email);
        imageView = (CircleImageView) headerview.findViewById(R.id.imageView);
        user_name.setText(Username);
        tv_email.setText(Useremail);


       // CheckedUpdatedImages();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        FcmToken = task.getResult().getToken();
                        Updatetoken();

                    }
                });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, notificaitonFragment, "5").hide(notificaitonFragment).commit();
        //fm.beginTransaction().add(R.id.main_container, videosFragment, "4").hide(videosFragment).commit();
        fm.beginTransaction().add(R.id.main_container, doubtsfragment, "3").hide(doubtsfragment).commit();
        fm.beginTransaction().add(R.id.main_container, mockTestFragment, "2").hide(mockTestFragment).commit();
        fm.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("LectureDekho")
                    .setMessage("Are you sure you want to Exit From LectureDekho?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
            // super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_test_series) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, MyPurchasedTestSeriesActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_buy_pack) {
            startActivity(new Intent(MainActivity.this, BuyTestSeries.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_daily_quiz) {
            startActivity(new Intent(MainActivity.this, DailyQuizActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, StudentProfile.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_my_teacher) {
            startActivity(new Intent(MainActivity.this, MyTeachersActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_current_affairs) {
            startActivity(new Intent(MainActivity.this, NewCurrentAffairs.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(MainActivity.this, Wallet.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(MainActivity.this, Contact_us.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_scholarship) {
            startActivity(new Intent(MainActivity.this, ScholarshipActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.nav_book_study) {
            startActivity(new Intent(MainActivity.this, OptionsForBookmark.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);


        } else if (id == R.id.nav_share_app) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "LectureDekho");
                String shareMessage = "\n Use referral code:" + refer_code + ",for Cashback \n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_rate_us) {
            rateApp();

        } else if (id == R.id.nav_notification) {

            startActivity(new Intent(MainActivity.this, SeeAllNotication.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else if (id == R.id.nav_logout) {

            showLogoutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showLogoutDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Log out")
                .setMessage("Are you sure you want to Log Out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.apply();
                        Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    private void Updatetoken() {
        Call<FirebaseModel> firebaseModelCall = Api_Client.getInstance().updatetoken(ClassId, StudentId, FcmToken, deviceid);
        firebaseModelCall.enqueue(new Callback<FirebaseModel>() {
            @Override
            public void onResponse(Call<FirebaseModel> call, Response<FirebaseModel> response) {
                Log.d("fire","successs");
            }

            @Override
            public void onFailure(Call<FirebaseModel> call, Throwable t) {
                Log.d("fire","failed");
            }
        });

    }

    public void CheckedUpdatedImages() {
        Call<UpdateProfileModel> topicsCall = Api_Client.getInstance().CheckProfileImage(StudentId);
        topicsCall.enqueue(new Callback<UpdateProfileModel>() {

            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.body().getStatus().equals(1)) {

                    UpdateProfileModel updateProfileModel = response.body();
                    userProfile = updateProfileModel.getProfile_pic();

                    Picasso.get().load(userProfilePath + userProfile).placeholder(R.drawable.profile_pic_place_holder)// Place holder image from drawable folder
                            .error(R.drawable.profile_pic_place_holder)
                            .into(imageView);

                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
    }


    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
}