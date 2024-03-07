package com.lecturedekhoelearn.in.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.QueryAdapterSubject;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SubjectModel;
import com.lecturedekhoelearn.in.model.SubjectModelDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Doubtsfragment extends Fragment {


    private ProgressDialog pDialog;
    private RecyclerView rv_subject;
    private String ClassId;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Doubtsfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_doubtsfragment, container, false);
       // getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();


        ClassId = preferences.getString("class_id", "");
        rv_subject = view.findViewById(R.id.rv_subject);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        getAllSubject();

        return view;
    }

    private void getAllSubject() {

        Call<SubjectModel> subjectModelCall = Api_Client.getInstance().getSubject(ClassId);
        subjectModelCall.enqueue(new Callback<SubjectModel>() {
            @Override
            public void onResponse(Call<SubjectModel> call, Response<SubjectModel> response) {
                if (response.body().getStatus().equals(1)) {
                    SubjectModel subjectModel = response.body();
                    ArrayList<SubjectModelDetails> subjectModelDetails = (ArrayList<SubjectModelDetails>) subjectModel.getSubjectModelDetails();
                    if (subjectModelDetails.size() > 0) {
                        rv_subject.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                        QueryAdapterSubject subjectAdapter = new QueryAdapterSubject(getActivity(), subjectModelDetails);
                        rv_subject.setAdapter(subjectAdapter);
                        rv_subject.setHasFixedSize(true);

                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectModel> call, Throwable t) {

            }
        });
    }


}
