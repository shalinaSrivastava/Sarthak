package com.lecturedekhoelearn.in.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.lecturedekhoelearn.in.R;

public class Contact_us extends AppCompatActivity {


    TextView tv_number, write_to_us,tv_feedback;
    ImageView img_call;

    String s = "+917531858844";
    EditText editfeedback;
    Button btn_contact_us;
    String strfeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        img_call = findViewById(R.id.img_call);
        editfeedback = findViewById(R.id.editfeedback);
        btn_contact_us = findViewById(R.id.btn_contact_us);
        write_to_us = findViewById(R.id.write_to_us);
        tv_feedback = findViewById(R.id.tv_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });


        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri u = Uri.parse("tel:" + s.toString());
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    s.printStackTrace();
                }

            }
        });


        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strfeedback = editfeedback.getText().toString().trim();

                if (strfeedback.trim().equals("")) {
                    editfeedback.setError("Please enter your message");

                } else {

                    tv_feedback.setVisibility(View.VISIBLE);
                    tv_feedback.setText("We have received your message. \n We'll get back to you shortly.");
                  //  Toast.makeText(Contact_us.this, "We have received your message. \n We'll get back to you shortly." , Toast.LENGTH_SHORT).show();
                    btn_contact_us.setVisibility(View.GONE);
                    editfeedback.setVisibility(View.GONE);
                    write_to_us.setVisibility(View.GONE);
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

}