package com.lecturedekhoelearn.in.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.BuyTestSeries;
import com.lecturedekhoelearn.in.activity.PackageDetailsActivity;
import com.lecturedekhoelearn.in.adapter.BuyPackageAdapter;
import com.lecturedekhoelearn.in.adapter.PurchasedTestSeriesAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.BuyPackageModel;
import com.lecturedekhoelearn.in.model.BuyPackageModelDetails;
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModel;
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModelDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MockTestFragment extends Fragment {
    RecyclerView rv_test_package;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String classId, StudentId;
    TextView tv_subscribe,tv_pac_name,tv_pac_amount;
    JustifyTextView tv_description_j;
    ImageView pack_image;
    LinearLayout pack_data;


    private RelativeLayout ll_package;
    private Button btn_seepackages;
    String packgeThumbnailpath = "https://lecturedekho.com/admin/assets/images/package/";

    public MockTestFragment() {
        // Required empty public constructor

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mocktest, container, false);
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        tv_description_j=view.findViewById(R.id.tv_description_j);
        tv_pac_amount=view.findViewById(R.id.tv_pac_amount);
        tv_pac_name=view.findViewById(R.id.tv_pac_name);
        pack_image=view.findViewById(R.id.image);
        pack_data=view.findViewById(R.id.pack_data);
        classId = preferences.getString("class_id", "");
        StudentId = preferences.getString("studentId", "");

        rv_test_package = view.findViewById(R.id.rv_test_package);
        ll_package = view.findViewById(R.id.ll_package);
        tv_subscribe=view.findViewById(R.id.tv_subscribe);
        forBuyAllPackages();


        btn_seepackages = view.findViewById(R.id.btn_seepackages);
        btn_seepackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), BuyTestSeries.class);
                startActivity(i);
            }
        });

        return view;


    }

    public void forBuyAllPackages() {

        Call<BuyPackageModel> call = Api_Client.getInstance().forBuyAllPacakge(StudentId, classId);
        call.enqueue(new Callback<BuyPackageModel>() {
            @Override
            public void onResponse(Call<BuyPackageModel> call, Response<BuyPackageModel> response) {

                if (response.body().getStatus().equals(1)) {
                    BuyPackageModel buyPackageModel = response.body();
                    ArrayList<BuyPackageModelDetails> buyPackageModelDetails = (ArrayList<BuyPackageModelDetails>) buyPackageModel.getBuyPackageModelDetails();
                    ArrayList<BuyPackageModelDetails> buyTest = new ArrayList<BuyPackageModelDetails>();
                    if (buyPackageModelDetails != null && !buyPackageModelDetails.isEmpty() && buyPackageModelDetails.size() > 0) {
                        for (BuyPackageModelDetails buyPackageModelDetails1 : buyPackageModelDetails) {
                            // if (buyPackageModelDetails1.getPackage_id() == null || buyPackageModelDetails1.getPackage_id().isEmpty() && buyPackageModelDetails1.getStudent_id() == null || buyPackageModelDetails1.getStudent_id().isEmpty()) {
                            buyTest.add(buyPackageModelDetails1);
                            if (buyPackageModelDetails1.getPackage_id()!=null){
                                buyTest.add(buyPackageModelDetails1);
                            }
                            // }
                        }
                        if (buyTest.get(0).getPackage_id()!=null){
                            pack_data.setVisibility(View.VISIBLE);
                            Picasso.get()
                                    .load(packgeThumbnailpath + buyTest.get(0).getThumbnail())
                                    .error(R.drawable.splashlogo)
                                    .placeholder(R.drawable.splashlogo)
                                    .into(pack_image);
                            tv_pac_amount.setText("Price: \u20B9" +buyTest.get(0).getPrice());
                            tv_pac_name.setText(buyTest.get(0).getName());
                            tv_description_j.setText("Description: " + Html.fromHtml(buyTest.get(0).getDescription()));
                        }else {
                            rv_test_package.setVisibility(View.VISIBLE);
                            BuyPackageAdapter analysisAdapter = new BuyPackageAdapter(getActivity(), buyTest);
                            rv_test_package.setAdapter(analysisAdapter);
                            rv_test_package.setHasFixedSize(true);
                            rv_test_package.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        }
                    } else {
                        Toast.makeText(getActivity(), "No Buy Test series found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BuyPackageModel> call, Throwable t) {

            }
        });


    }
}

