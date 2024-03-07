package com.lecturedekhoelearn.in.activity.parentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MainActivity;
import com.lecturedekhoelearn.in.fragment.ChildActivityFragment;
import com.lecturedekhoelearn.in.fragment.StudentFragment;
import com.lecturedekhoelearn.in.login.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentDashboard extends AppCompatActivity {


    String Useremail, Username, StudentId, ClassId, courseId;
    CircleImageView imageView;
    String profilePic;
    TextView user_name, tv_email;
    LinearLayout ll_mock_test;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    final FragmentManager fm = getSupportFragmentManager();
    final ChildActivityFragment homeFragment = new ChildActivityFragment();
    final ParentHome parentHome = new ParentHome();
    final SubjectFragment subjectFragment = new SubjectFragment();
    final ParentProfile parentProfile=new ParentProfile();
    Fragment active = parentHome;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    fm.beginTransaction().hide(active).show(parentHome).commit();
                    active = parentHome;
                    return true;
                case R.id.action_mock_test:
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.action_doubts:
                    fm.beginTransaction().hide(active).show(subjectFragment).commit();
                    active = subjectFragment;
                    return true;
                case R.id.p_profile:
                    fm.beginTransaction().hide(active).show(parentProfile).commit();
                    active = parentProfile;
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        Username = preferences.getString("name", "");
        Useremail = preferences.getString("email", "");
        ll_mock_test=findViewById(R.id.ll_mock_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
                toolbar.setTitle(Username);
            }
        });

        ll_mock_test.setOnClickListener(v -> {
            Intent intent=new Intent(this,PStudentActivity.class);
            startActivity(intent);
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      //  fm.beginTransaction().add(R.id.main_container, notificaitonFragment, "5").hide(notificaitonFragment).commit();
        fm.beginTransaction().add(R.id.main_container, parentProfile, "4").hide(parentProfile).commit();
        fm.beginTransaction().add(R.id.main_container, subjectFragment, "3").hide(subjectFragment).commit();
        fm.beginTransaction().add(R.id.main_container, homeFragment, "2").hide(homeFragment).commit();
        fm.beginTransaction().add(R.id.main_container, parentHome, "1").commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parent_menu, menu);
        return true;
    }

    private void showLogoutDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ParentDashboard.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ParentDashboard.this);
        }
        builder.setTitle("Log out")
                .setMessage("Are you sure you want to Log Out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.apply();
                        Intent logoutIntent = new Intent(ParentDashboard.this, LoginActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;

            case R.id.action_setting:
                showLogoutDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
