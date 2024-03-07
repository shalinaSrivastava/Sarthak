package com.lecturedekhoelearn.in.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.PackageCoupon;
import com.lecturedekhoelearn.in.model.PaymentDetailModel;
import com.lecturedekhoelearn.in.model.WalletModel;
import com.lecturedekhoelearn.in.model.WalletModelDetials;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.packgeThumbnailpath;

public class PackageDetailsActivity extends AppCompatActivity {

    TextView tv_pac_name, tv_pac_amount;
    String packagename, pacDescription, amount, pacimage, testType;
    ImageView imagepackage;
    String packgeThumbnailpath = "https://lecturedekho.com/admin/assets/images/package/";
    JustifyTextView tv_description;
    Button btn_pay;
    String packageId;
    String StudentId;
    String Discount;
    String mobile_number,email;
    int discountprice;
    int accurateDicount,afterwallet_amnt;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    CheckBox wallet;

    String WalletAmount;
    TextView tv_wallet_amount, tv_coupon;
    LinearLayout ll_wallet, ll_coupon;

    int afterwalletuse;
    private ProgressDialog pDialog;

    Button btn_coupon;
    EditText et_coupon;
    String url,random_string;
    char[] chars1;
    String CouponCode;
    int afterApplycuponPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Please Wait..");
        pDialog.setCancelable(false);
        StudentId = preferences.getString("studentId", "");

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
        StudentId = preferences.getString("studentId", "");
        packageId = intent.getStringExtra("pid");
        packagename = intent.getStringExtra("packageName");
        pacDescription = intent.getStringExtra("des");
        amount = intent.getStringExtra("amount");
        pacimage = intent.getStringExtra("image");
        testType = intent.getStringExtra("testType");
        Discount = intent.getStringExtra("discount");
        email = preferences.getString("email", "");
        mobile_number = preferences.getString("mobile", "");
        tv_description = findViewById(R.id.tv_description);
        tv_pac_amount = findViewById(R.id.tv_pac_amount);
        tv_pac_name = findViewById(R.id.tv_pac_name);
        imagepackage = findViewById(R.id.image);
        btn_pay = findViewById(R.id.btn_pay);
        wallet = (CheckBox) findViewById(R.id.use_wallet_money);
        ll_wallet = findViewById(R.id.ll_wallet);
        ll_coupon = findViewById(R.id.ll_coupon);
        btn_coupon = findViewById(R.id.btn_coupon);
        et_coupon = findViewById(R.id.et_coupon);
        tv_wallet_amount = findViewById(R.id.tv_wallet_amount);
        tv_coupon = findViewById(R.id.tv_coupon);
        chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        random_string = sb1.toString();
        if (getIntent().getStringExtra("p_id")!=null){
            ll_wallet.setVisibility(View.GONE);
            ll_coupon.setVisibility(View.GONE);
        }
        if (Discount != null && Integer.parseInt(Discount) > 0) {

            discountprice = Integer.parseInt(amount) * Integer.parseInt(Discount) / 100;
            accurateDicount = Integer.parseInt(amount) - discountprice;


          //  tv_pac_amount.setText("Price: \u20B9" + String.valueOf(accurateDicount));
        } else {

            accurateDicount = Integer.parseInt(amount);

           // tv_pac_amount.setText("Price: \u20B9" + String.valueOf(amount));
        }

        tv_pac_name.setText(packagename);

        tv_description.setText("Description: " + Html.fromHtml(pacDescription));

        Picasso.get()
                .load(packgeThumbnailpath + pacimage)
                .error(R.drawable.splashlogo)
                .placeholder(R.drawable.splashlogo)
                .into(imagepackage);


        if (testType.equals("free")) {

            btn_pay.setVisibility(View.GONE);
            tv_pac_amount.setVisibility(View.GONE);
            ll_wallet.setVisibility(View.GONE);
            ll_coupon.setVisibility(View.GONE);

        } else {
            getWalletMoney();
            tv_pac_amount.setVisibility(View.VISIBLE);
            tv_pac_amount.setText("Price: \u20B9" + String.valueOf(accurateDicount));
            if (getIntent().getStringExtra("p_id")==null){
                ll_wallet.setVisibility(View.VISIBLE);
                ll_coupon.setVisibility(View.VISIBLE);
                btn_pay.setVisibility(View.VISIBLE);
            }

        }


        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallet.isChecked()) {
                    afterwalletuse = accurateDicount - Integer.parseInt(WalletAmount);
                    afterwallet_amnt=Integer.parseInt(WalletAmount)-accurateDicount;
                    if(afterwallet_amnt>=0){
                            InsertPaymentDetails(accurateDicount+"",afterwallet_amnt);
                    }else {
                    if (Discount != null && Integer.parseInt(Discount) > 0 && CouponCode != null) {
                        Intent i = new Intent(PackageDetailsActivity.this, StartPaymentActivity.class);
                        editor.putString("packageId",packageId).apply();
                        editor.putString("afterwalletuse",String.valueOf(accurateDicount)).apply();
                        editor.putString("WalletAmount","0").apply();
                        editor.putString("CouponCode",CouponCode).apply();
                        startActivity(i);
                     /*   url = "https://lecturedekho.com/home/payment/" + "/" + StudentId + "/" + packageId + "/" + afterwalletuse + "/" + WalletAmount + "/" + CouponCode;
                        Intent i = new Intent(PackageDetailsActivity.this, WebBrowserPayment.class);
                        i.putExtra("url", url);
                        startActivity(i);*/

                    } else {
                        Intent i = new Intent(PackageDetailsActivity.this, StartPaymentActivity.class);
                        editor.putString("packageId", packageId).apply();
                        editor.putString("afterwalletuse", String.valueOf(afterwalletuse)).apply();
                        editor.putString("WalletAmount", "0").apply();
                        editor.putString("CouponCode", "0").apply();
                        startActivity(i);

                      /*  url = "https://lecturedekho.com/home/payment/" + "/" + StudentId + "/" + packageId + "/" + afterwalletuse + "/" + WalletAmount + "/" + "0";
                        Intent i = new Intent(PackageDetailsActivity.this, WebBrowserPayment.class);
                        i.putExtra("url", url);
                        startActivity(i);*/
                    }
                    }
                } else {
                    //do something else

                    if (Discount != null && Integer.parseInt(Discount) > 0 && CouponCode != null) {
                        afterwalletuse = accurateDicount - Integer.parseInt(WalletAmount);
                        Intent i = new Intent(PackageDetailsActivity.this, StartPaymentActivity.class);
                        editor.putString("packageId",packageId).apply();
                        editor.putString("afterwalletuse",String.valueOf(accurateDicount)).apply();
                        editor.putString("WalletAmount",WalletAmount).apply();
                        editor.putString("CouponCode",CouponCode).apply();
                        startActivity(i);

                      /*  url = "https://lecturedekho.com/home/payment/" + "/" + StudentId + "/" + packageId + "/" + afterwalletuse + "/" + WalletAmount + "/" + CouponCode;
                        Intent i = new Intent(PackageDetailsActivity.this, WebBrowserPayment.class);
                        i.putExtra("url", url);
                        startActivity(i);*/
                    } else {
                        afterwalletuse = accurateDicount;
                        Intent i = new Intent(PackageDetailsActivity.this, StartPaymentActivity.class);
                        editor.putString("packageId", packageId).apply();
                        editor.putString("afterwalletuse", String.valueOf(afterwalletuse)).apply();
                        editor.putString("WalletAmount", WalletAmount).apply();
                        editor.putString("CouponCode", "0").apply();
                        startActivity(i);


                      /*  url = "https://lecturedekho.com/home/payment/" + "/" + StudentId + "/" + packageId + "/" + afterwalletuse + "/" + WalletAmount + "/" + "0";
                        Intent i = new Intent(PackageDetailsActivity.this, WebBrowserPayment.class);
                        i.putExtra("url", url);
                        startActivity(i);*/


                    }
                }
            }
        });


        btn_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CouponCode = et_coupon.getText().toString().trim();
                if (CouponCode.trim().equals("")) {
                    et_coupon.setError("Please enter your name");

                } else {
                    ApplyCoupon();

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getWalletMoney() {
        Call<WalletModel> packageVideoModelCall = Api_Client.getInstance().getWalletMoneydata(StudentId);
        packageVideoModelCall.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                if (response.body().getStatus().equals(1)) {
                    WalletModel walletModel = response.body();
                    ArrayList<WalletModelDetials> walletModelDetials = (ArrayList<WalletModelDetials>) walletModel.getWalletModelDetials();
                    if (!walletModelDetials.isEmpty() && walletModelDetials.size() > 0) {
                        WalletAmount = walletModelDetials.get(0).getCashback();
                        tv_wallet_amount.setText("\u20B9" + String.valueOf(WalletAmount));
                    } else {
                        WalletAmount = "0";
                        tv_wallet_amount.setText("\u20B9" + String.valueOf(WalletAmount));
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletModel> call, Throwable t) {

            }
        });
    }

    private void ApplyCoupon() {
        Call<PackageCoupon> packageVideoModelCall = Api_Client.getInstance().applyCoupon(CouponCode);
        packageVideoModelCall.enqueue(new Callback<PackageCoupon>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<PackageCoupon> call, Response<PackageCoupon> response) {
                if (response.body().getStatus().equals(1)) {
                    Toast.makeText(PackageDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    btn_coupon.setVisibility(View.GONE);
                    tv_coupon.setVisibility(View.VISIBLE);
                    et_coupon.setEnabled(false);
                    if (Discount != null && Integer.parseInt(Discount) > 0) {
                        afterApplycuponPrice = accurateDicount * Integer.parseInt(response.body().getPercentage()) / 100;
                        accurateDicount = accurateDicount - afterApplycuponPrice;
                        tv_coupon.setText("Applied:" + "\u20B9" + String.valueOf(afterApplycuponPrice));
                    } else {
                        afterApplycuponPrice = Integer.parseInt(amount) * Integer.parseInt(response.body().getPercentage()) / 100;
                        accurateDicount = accurateDicount - afterApplycuponPrice;
                        tv_coupon.setText("Applied:" + "\u20B9" + String.valueOf(afterApplycuponPrice));
                    }

                } else {
                    btn_coupon.setVisibility(View.VISIBLE);
                    tv_coupon.setVisibility(View.GONE);
                    et_coupon.setEnabled(true);
                    Toast.makeText(PackageDetailsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PackageCoupon> call, Throwable t) {

            }
        });
    }
    private void InsertPaymentDetails(String pay,int wallet) {
        showDialog();
        Call<PaymentDetailModel> paymentDetailModelCall = Api_Client.getInstance().InsertPaymentDetails(StudentId, random_string, packageId, pay, email, mobile_number, "", "", "",String.valueOf(wallet));
        paymentDetailModelCall.enqueue(new Callback<PaymentDetailModel>() {
            @Override
            public void onResponse(Call<PaymentDetailModel> call, Response<PaymentDetailModel> response) {
                hideDialog();
                Toast.makeText(PackageDetailsActivity.this, "You have successfully activated the premium package !!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PackageDetailsActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            @Override
            public void onFailure(Call<PaymentDetailModel> call, Throwable t) {
                hideDialog();
                //  Toast.makeText(StartPaymentActivity.this, "Payment Failure", Toast.LENGTH_SHORT).show();
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

}