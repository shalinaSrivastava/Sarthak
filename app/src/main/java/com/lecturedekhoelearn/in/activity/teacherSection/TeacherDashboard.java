package com.lecturedekhoelearn.in.activity.teacherSection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.Contact_us;
import com.lecturedekhoelearn.in.activity.CurrentAffairsActivity;
import com.lecturedekhoelearn.in.activity.SeeAllNotication;
import com.lecturedekhoelearn.in.activity.Wallet;
import com.lecturedekhoelearn.in.activity.queryActivity.UserAllQuery;
import com.lecturedekhoelearn.in.fragment.MockTestFragment;
import com.lecturedekhoelearn.in.fragment.NotificaitonFragment;
import com.lecturedekhoelearn.in.fragment.SectionalFragment;
import com.lecturedekhoelearn.in.fragment.TeacherFragment;
import com.lecturedekhoelearn.in.fragment.VideosFragment;
import com.lecturedekhoelearn.in.login.LoginActivity;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.WindowManager;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lecturedekhoelearn.in.Constant.teacherProfilethubnails;

public class TeacherDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final TeacherFragment homeFragment = new TeacherFragment();
    final SectionalFragment sectionalFragment = new SectionalFragment();
    final MockTestFragment mockTestFragment = new MockTestFragment();
    final VideosFragment videosFragment = new VideosFragment();
    final NotificaitonFragment notificaitonFragment = new NotificaitonFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Useremail, Username, StudentId, ClassId, courseId;
    CircleImageView imageView;
    String profilePic;
    TextView user_name, tv_email;


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


                case R.id.action_videos:
                    fm.beginTransaction().hide(active).show(videosFragment).commit();
                    active = videosFragment;
                    return true;

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
        setContentView(R.layout.activity_teacher_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        StudentId = preferences.getString("studentId", "");
        courseId = preferences.getString("courseId", "");
        Username = preferences.getString("name", "");
        Useremail = preferences.getString("email", "");
        ClassId = preferences.getString("class_id", "");
        profilePic = preferences.getString("profilePic", "");


        DrawerLayout drawer = findViewById(R.id.drawer_teacher_layout);
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

        Picasso.get()
                .load(teacherProfilethubnails + profilePic)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(imageView);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.main_container, notificaitonFragment, "4").hide(notificaitonFragment).commit();
        fm.beginTransaction().add(R.id.main_container, videosFragment, "3").hide(videosFragment).commit();
        fm.beginTransaction().add(R.id.main_container, mockTestFragment, "2").hide(mockTestFragment).commit();
        fm.beginTransaction().add(R.id.main_container, homeFragment, "1").commit();


    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_search) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.nav_profile) {

            startActivity(new Intent(TeacherDashboard.this, StudentProfile.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else */

        if (id == R.id.nav_my_querry) {

            startActivity(new Intent(TeacherDashboard.this, UserAllQuery.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else if (id == R.id.nav_current_affairs) {

            startActivity(new Intent(TeacherDashboard.this, CurrentAffairsActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);


        } else if (id == R.id.nav_student) {

            startActivity(new Intent(TeacherDashboard.this, StudentListActivity.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);


        } else if (id == R.id.nav_setting) {

            startActivity(new Intent(TeacherDashboard.this, Wallet.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);


        } else if (id == R.id.nav_contact_us) {

            startActivity(new Intent(TeacherDashboard.this, Contact_us.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else if (id == R.id.nav_share_app) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.lecturedekhoedu.in");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else if (id == R.id.nav_notification) {

            startActivity(new Intent(TeacherDashboard.this, SeeAllNotication.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        } else if (id == R.id.nav_logout) {

            showLogoutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_teacher_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showLogoutDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(TeacherDashboard.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(TeacherDashboard.this);
        }
        builder.setTitle("Log out")
                .setMessage("Are you sure you want to Log Out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.apply();
                        Intent logoutIntent = new Intent(TeacherDashboard.this, LoginActivity.class);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_teacher_layout);
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



}