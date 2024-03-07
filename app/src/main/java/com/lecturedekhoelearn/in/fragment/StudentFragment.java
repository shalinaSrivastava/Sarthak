package com.lecturedekhoelearn.in.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.LiveClass;
import com.lecturedekhoelearn.in.activity.SearchViewActivity;
import com.lecturedekhoelearn.in.activity.TopicActivity;
import com.lecturedekhoelearn.in.adapter.ActivityAdapter;
import com.lecturedekhoelearn.in.adapter.CustomCardAdapter;
import com.lecturedekhoelearn.in.adapter.InviteCardAdapter;
import com.lecturedekhoelearn.in.adapter.SubjectAdapter;
import com.lecturedekhoelearn.in.adapter.TopicAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.CustomData;
import com.lecturedekhoelearn.in.model.InviteData;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import com.lecturedekhoelearn.in.model.SubjectModel;
import com.lecturedekhoelearn.in.model.SubjectModelDetails;
import com.lecturedekhoelearn.in.model.TopicModel;
import com.lecturedekhoelearn.in.model.TopicModelDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class StudentFragment extends Fragment {


    private List<InviteData> inviteData;
    InviteCardAdapter inviteCardAdapter;
    CustomCardAdapter customCardAdapter;
    private List<CustomData> customData;
    LinearLayoutManager horizontalLayoutManager;
    private RecyclerView rv_invite, rv_videos, rv_news, rv_moti_videos, rv_customdata;
    TextView tv_news, tv_videos, tv_invite, tv_motivational, tv_teacher, tv_free_packges, tv_user_name;
    private String ClassId, studentId;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String gmrng, goodaftr, gdvng, goodnght, preferencesname;
    private RecyclerView rv_subject, rv_teacher_list, rv_free_packages;
    SearchView svSearch;
    String searchText;
    private View rootView;
    LinearLayout goLIve;

    public StudentFragment() {
        // Required empty public constructor
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity(studentId);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ClassId = preferences.getString("class_id", "");
        studentId = preferences.getString("studentId", "");
        preferencesname = preferences.getString("name", "");
        getActivity(studentId);
        rootView = view.findViewById(R.id.root_view);
        rv_invite = view.findViewById(R.id.rv_invite);
        rv_news = view.findViewById(R.id.rv_news);
        tv_invite = view.findViewById(R.id.tv_invite);
        tv_news = view.findViewById(R.id.tv_news);
        rv_subject = view.findViewById(R.id.rv_subject);
        rv_moti_videos = view.findViewById(R.id.rv_moti_videos);
        tv_motivational = view.findViewById(R.id.tv_motivational);
        rv_customdata = view.findViewById(R.id.rv_customdata);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        svSearch = view.findViewById(R.id.svSearch);
        svSearch.setQueryHint(getString(R.string.search_here));
        tv_user_name.setText(preferencesname);
        goLIve=view.findViewById(R.id.go_live_btn);
        goLIve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LiveClass.class);
                startActivity(i);
            }
        });

        getInviteCard();
        getAllSubject();
        getCustomData();


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            gmrng = "Good Morning";
            TextView Text = (TextView) view.findViewById(R.id.tv_morng);
            Text.setText(gmrng);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            goodaftr = "Good Afternoon";
            TextView Text = (TextView) view.findViewById(R.id.tv_morng);
            Text.setText(goodaftr);
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            gdvng = "Good Evening";
            TextView Text = (TextView) view.findViewById(R.id.tv_morng);
            Text.setText(gdvng);
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            goodnght = "Good Night";
            TextView Text = (TextView) view.findViewById(R.id.tv_morng);
            Text.setText(goodnght);
        }


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            svSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        }

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                editor.putString("Searchtext", query).apply();
                Intent i = new Intent(getActivity(), SearchViewActivity.class);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }
        });


        return view;

    }

    private List<InviteData> invitedata() {

        List<InviteData> inviteData1 = new ArrayList<>();

        inviteData1.add(new InviteData(R.drawable.ic_share, "Invite friends and get Package free"));

        return inviteData1;
    }

    private void getInviteCard() {
        inviteData = invitedata();
        inviteCardAdapter = new InviteCardAdapter(inviteData, getActivity());
        horizontalLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rv_invite.setLayoutManager(horizontalLayoutManager);
        rv_invite.setAdapter(inviteCardAdapter);
    }


    private List<CustomData> customData() {

        List<CustomData> inviteData1 = new ArrayList<>();

        inviteData1.add(new CustomData(R.drawable.ic_motivational_ic, "Motivational Videos"));
        inviteData1.add(new CustomData(R.drawable.ic_quiz_dash, "Daily Quiz"));
        inviteData1.add(new CustomData(R.drawable.ic_newspaper_dash, "News & updates"));

        return inviteData1;
    }


    private void getCustomData() {
        customData = customData();
        rv_customdata.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        CustomCardAdapter subjectAdapter = new CustomCardAdapter(customData, getActivity());
        rv_customdata.setAdapter(subjectAdapter);
        rv_customdata.setHasFixedSize(true);

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
                        SubjectAdapter subjectAdapter = new SubjectAdapter(getActivity(), subjectModelDetails);
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
    @Override
    public void onResume() {
        super.onResume();

        svSearch.setQuery("", false);
        rootView.requestFocus();
    }

    private void getActivity(String data) {
        String urlParameters ="student_id="+data;
        //showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/insert_last_login");
    }
}

