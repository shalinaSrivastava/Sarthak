package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lecturedekhoelearn.in.R;
import com.squareup.picasso.Picasso;

import static com.lecturedekhoelearn.in.Constant.newspath;

public class newsActivity extends AppCompatActivity {
    TextView newstopic, newsdesc,news_time;
    ImageView newsimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        Intent intent = getIntent();
        final String newsTopic = intent.getStringExtra("newstitle");
        String newsDesc = intent.getStringExtra("newsdesc");
        String newsimagelink = intent.getStringExtra("imagelink");
        String newstime=intent.getStringExtra("newstime");


        newstopic = findViewById(R.id.news_topic);
        newsdesc = findViewById(R.id.news_desc);
        newsimage = findViewById(R.id.news_image);
        news_time=findViewById(R.id.news_time);
        news_time.setText(newstime);

        newstopic.setText(newsTopic);
        newsdesc.setText(newsDesc);

        Picasso.get()
                .load(newspath + newsimagelink)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(newsimage);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
