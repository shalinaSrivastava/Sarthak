package com.lecturedekhoelearn.in.activity;

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

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.NewNewsAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.Allnews;
import com.lecturedekhoelearn.in.model.News;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCurrentAffairs extends AppCompatActivity {


    RecyclerView rv_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_current_affairs);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        rv_news = findViewById(R.id.rv_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });
        getAllnews();

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


    private void getAllnews() {


        Call<Allnews> ebookPdfModelsCall = Api_Client.getInstance().getNews();
        ebookPdfModelsCall.enqueue(new Callback<Allnews>() {
            @Override
            public void onResponse(Call<Allnews> call, Response<Allnews> response) {

                if (response.body().getStatus().equals(1)) {

                    Allnews allnews = response.body();

                    ArrayList<News> newsArrayList = (ArrayList<News>) allnews.getGetNews();
                    if (!newsArrayList.isEmpty() && newsArrayList.size() > 0) {
                        NewNewsAdapter adapter = new NewNewsAdapter(NewCurrentAffairs.this, newsArrayList);
                        rv_news.setAdapter(adapter);
                        rv_news.setHasFixedSize(true);
                        rv_news.setLayoutManager(new LinearLayoutManager(NewCurrentAffairs.this, LinearLayoutManager.VERTICAL, false));

                    } else {

                        Toast.makeText(NewCurrentAffairs.this, "Current news not available", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Allnews> call, Throwable t) {

            }
        });


    }
}
