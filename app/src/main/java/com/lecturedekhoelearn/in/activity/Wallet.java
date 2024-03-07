package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.WalletModel;
import com.lecturedekhoelearn.in.model.WalletModelDetials;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wallet extends AppCompatActivity {

    private ProgressDialog pDialog;
    TextView totalAmount;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String StudentId;
    Button add_money;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        totalAmount = findViewById(R.id.totalAmount);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        add_money = findViewById(R.id.add_money);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {

            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://lecturedekho.com/home/add_money/"+ "/" + StudentId;
                Intent i = new Intent(Wallet.this, WebBrowserPayment.class);
                i.putExtra("url", url);
                startActivity(i);
            }
        });
        getWalletMoney();
    }


    private void getWalletMoney() {
        pDialog.setTitle("Fetching Wallet Amounts...");
        showDialog();

        Call<WalletModel> packageVideoModelCall = Api_Client.getInstance().getWalletMoneydata(StudentId);
        packageVideoModelCall.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {


                if (response.body().getStatus().equals(1)) {

                    hideDialog();
                    WalletModel walletModel = response.body();

                    ArrayList<WalletModelDetials> walletModelDetials = (ArrayList<WalletModelDetials>) walletModel.getWalletModelDetials();

                    if (!walletModelDetials.isEmpty() && walletModelDetials.size() > 0) {

                        String Walletamount = walletModelDetials.get(0).getCashback();

                        totalAmount.setText(Walletamount);

                    } else {

                        Toast.makeText(Wallet.this, "Wallet amounts not found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    hideDialog();

                    Toast.makeText(Wallet.this, "Wallet amounts not found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<WalletModel> call, Throwable t) {
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
