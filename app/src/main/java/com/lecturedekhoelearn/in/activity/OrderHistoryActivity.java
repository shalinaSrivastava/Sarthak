package com.lecturedekhoelearn.in.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.PaymentHistroyAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.PaymentHistoryDetailsModel;
import com.lecturedekhoelearn.in.model.PaymentHistroyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {


    RecyclerView rv_order;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String studentId;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_order = findViewById(R.id.rv_order);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        studentId = preferences.getString("studentId", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        getOrderHistory();
    }


    public void getOrderHistory() {

        pDialog.setTitle("Loading Orders History...");
        showDialog();

        Call<PaymentHistroyModel> paymentHistroyModelCall = Api_Client.getInstance().getOrderHistory(studentId);
        paymentHistroyModelCall.enqueue(new Callback<PaymentHistroyModel>() {
            @Override
            public void onResponse(Call<PaymentHistroyModel> call, Response<PaymentHistroyModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    PaymentHistroyModel paymentHistroyModel = response.body();
                    ArrayList<PaymentHistoryDetailsModel> paymentHistoryDetailsModels = (ArrayList<PaymentHistoryDetailsModel>) paymentHistroyModel.getPaymentHistoryDetailsModels();
                    if (!paymentHistoryDetailsModels.isEmpty() && paymentHistoryDetailsModels.size() > 0) {
                        PaymentHistroyAdapter adapter = new PaymentHistroyAdapter(OrderHistoryActivity.this, paymentHistoryDetailsModels);
                        rv_order.setAdapter(adapter);
                        rv_order.setHasFixedSize(true);
                        rv_order.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL, false));

                    } else {

                        Toast.makeText(OrderHistoryActivity.this, "You haven't Purchased Any package ", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    hideDialog();

                    Toast.makeText(OrderHistoryActivity.this, "You haven't Purchased Any package", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PaymentHistroyModel> call, Throwable t) {
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
