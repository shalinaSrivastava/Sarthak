package com.lecturedekhoelearn.in.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.SeeAllNotificationAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.AllNotificationModel;
import com.lecturedekhoelearn.in.model.NotificationDetailsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificaitonFragment extends Fragment {

    RecyclerView rv_notification;
    private ProgressDialog pDialog;

    public NotificaitonFragment() {
        // Required empty public constructor
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        rv_notification = view.findViewById(R.id.rv_notification);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        getAllNotication();


        return view;
    }

    public void getAllNotication() {
        pDialog.setMessage("Loading All Notifications...");
        showDialog();

        Call<AllNotificationModel> allNotificationModelCall = Api_Client.getInstance().getNotification();
        allNotificationModelCall.enqueue(new Callback<AllNotificationModel>() {
            @Override
            public void onResponse(Call<AllNotificationModel> call, Response<AllNotificationModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    AllNotificationModel allNotificationModel = response.body();
                    ArrayList<NotificationDetailsModel> topics_details = (ArrayList<NotificationDetailsModel>) allNotificationModel.getNotificationDetailsModels();
                    SeeAllNotificationAdapter analysisAdapter = new SeeAllNotificationAdapter(getActivity(), topics_details);
                    rv_notification.setAdapter(analysisAdapter);
                    rv_notification.setHasFixedSize(true);
                    rv_notification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                } else {

                    hideDialog();
                    Toast.makeText(getActivity(), "No Notification found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllNotificationModel> call, Throwable t) {
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

}
