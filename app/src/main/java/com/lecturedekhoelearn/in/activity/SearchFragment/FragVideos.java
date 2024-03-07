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
import com.lecturedekhoelearn.in.adapter.SearchVideoAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SearchModel;
import com.lecturedekhoelearn.in.model.SearchVideoModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragVideos extends Fragment {

    RecyclerView rv_videos;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String ClassId, searchText;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_videos_search, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        rv_videos = rootView.findViewById(R.id.rv_videos);

        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();


        ClassId = preferences.getString("class_id", "");
        searchText = preferences.getString("Searchtext", "");
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

                    ArrayList<SearchVideoModel> searchVideoModels = (ArrayList<SearchVideoModel>) motivationalVideos.getSearchVideoModels();

                    if (!searchVideoModels.isEmpty() && searchVideoModels.size() > 0) {
                        for (int i = 0; i < searchVideoModels.size(); i++) {

                            SearchVideoAdapter searchVideoAdapter = new SearchVideoAdapter(getActivity(), searchVideoModels);
                            rv_videos.setAdapter(searchVideoAdapter);
                            rv_videos.setHasFixedSize(true);
                            rv_videos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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