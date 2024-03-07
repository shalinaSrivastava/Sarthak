package com.lecturedekhoelearn.in.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MySeriesVideoDetailsActivity;
import com.lecturedekhoelearn.in.adapter.TestSeriesInsideVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.TestInsideVideoDetailsModel;
import com.lecturedekhoelearn.in.model.TestInsideVideoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqFragment extends Fragment {
    RecyclerView rv_test;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mcq, container, false);
        rv_test = view.findViewById(R.id.rv_test);
        sharedPreferences = getActivity().getSharedPreferences(Constant.myprif, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getTestList(sharedPreferences.getString("topic_id", ""));
        return view;
    }

    private void getTestList(String data_s) {
        Call<TestInsideVideoModel> testInsideVideoModelCall = Api_Client.getInstance().getTestmcq_test(data_s);
        testInsideVideoModelCall.enqueue(new Callback<TestInsideVideoModel>() {
            @Override
            public void onResponse(Call<TestInsideVideoModel> call, Response<TestInsideVideoModel> response) {
                if (response.body().getStatus().equals(1)) {
                    TestInsideVideoModel TestInsideVideoModel = response.body();
                    ArrayList<TestInsideVideoDetailsModel> testInsideVideoDetailsModels = (ArrayList<TestInsideVideoDetailsModel>) TestInsideVideoModel.getTestInsideVideoDetailsModels();
                    if (testInsideVideoDetailsModels.size() > 0) {
                        TestSeriesInsideVideoAdapter allTestSeriesAdapter = new TestSeriesInsideVideoAdapter(getActivity(), testInsideVideoDetailsModels);
                        rv_test.setHasFixedSize(true);
                        rv_test.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        allTestSeriesAdapter.notifyDataSetChanged();
                        rv_test.setAdapter(allTestSeriesAdapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Test not found inside video", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TestInsideVideoModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Test not found inside video", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
