package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.model.StudentAnalysisModel;

public class StudentAnalysisActivity extends AppCompatActivity  {


    private PieChart piechart;

    TextView rank, totalquesno, attempt, correct, incorrect, marks, _user_Skipped;
    Button btnDashboard;


    BarChart chart;
    private StudentAnalysisModel resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_analysis);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        resultData = (StudentAnalysisModel) getIntent().getSerializableExtra("Result");
        btnDashboard = findViewById(R.id.go_dashboard);
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentAnalysisActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        chart = (BarChart) findViewById(R.id.chart1);

        rank = findViewById(R.id.rank);
        totalquesno = findViewById(R.id.total);
        marks = findViewById(R.id.marks);
        attempt = findViewById(R.id.attempt);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        _user_Skipped = findViewById(R.id.tv_user_skiped);

        int totalquestion = Integer.parseInt(resultData.getAttend_question()) + Integer.parseInt(resultData.getSkip());

        _user_Skipped.setText("Skipped question : " + String.valueOf(resultData.getSkip()));
        totalquesno.setText("Total Question : " + String.valueOf(totalquestion));
        attempt.setText("Attempted : " + String.valueOf(resultData.getAttend_question()));
        correct.setText("Correct : " + String.valueOf(resultData.getRight_answer()));
        incorrect.setText("Incorrect : " + String.valueOf(resultData.getWrong_answer()));
        marks.setText("Total marks scored : " + String.valueOf(resultData.getGet_mark()));
        rank.setText("Your Rank : " + String.valueOf(resultData.getRank()));


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
