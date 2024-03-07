package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.TabAdapter;

public class OptionsForVideo extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static String Useremail, Username, StudentId, ClassId, courseId;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otions_for_video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        courseId = preferences.getString("courseId", "");
        Username = preferences.getString("name", "");
        Useremail = preferences.getString("email", "");
        ClassId = preferences.getString("class_id", "");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout =  findViewById(R.id.tabs);
        viewPager =  findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
