package com.lecturedekhoelearn.in.activity.SearchFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.SearchTopicAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SearchModel;
import com.lecturedekhoelearn.in.model.SearchTopicModel;
import com.lecturedekhoelearn.in.model.SearchVideoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragTopic extends Fragment {


    RecyclerView rv_data;
    SharedPreferences.Editor editor;
    String ClassId, searchText;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_topic, container, false);
       getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_data = rootView.findViewById(R.id.rv_data);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        searchText = preferences.getString("Searchtext", "");
        ClassId = preferences.getString("class_id", "");
        getSearchResult(searchText);


        return rootView;
    }


    private void getSearchResult(String searchText) {
        Call<SearchModel> call = Api_Client.getInstance().SearchCAll(searchText, ClassId);
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.body().getStatus().equals(1)) {
                    SearchModel motivationalVideos = response.body();
                    ArrayList<SearchTopicModel> searchTopicModels = (ArrayList<SearchTopicModel>) motivationalVideos.getSearchTopicModels();

                    ArrayList<SearchVideoModel> searchVideoModels = (ArrayList<SearchVideoModel>) motivationalVideos.getSearchVideoModels();

                    if (!searchTopicModels.isEmpty() && searchTopicModels.size() > 0 || !searchVideoModels.isEmpty() && searchVideoModels.size() > 0) {
                        for (int i = 0; i < searchTopicModels.size(); i++) {
                            SearchTopicAdapter teacherListAdapter = new SearchTopicAdapter(getActivity(), searchTopicModels);
                            rv_data.setAdapter(teacherListAdapter);
                            rv_data.setHasFixedSize(true);
                            rv_data.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                        }


                    }
                } else {

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });

    }


}