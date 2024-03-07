package com.lecturedekhoelearn.in.fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.InviteCardAdapter;
import com.lecturedekhoelearn.in.adapter.MotivationalVideoAdapter;
import com.lecturedekhoelearn.in.adapter.NewsAdapter;
import com.lecturedekhoelearn.in.adapter.TeachWiseStudentDetailsAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.Allnews;
import com.lecturedekhoelearn.in.model.InviteData;
import com.lecturedekhoelearn.in.model.MotivationalVideos;
import com.lecturedekhoelearn.in.model.MotivationalVideosDetails;
import com.lecturedekhoelearn.in.model.News;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import com.lecturedekhoelearn.in.model.TeacherModel.TeacherWiseStudentDetails;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TeacherFragment extends Fragment {


    private List<InviteData> inviteData;
    PieChart pieChart,pieChart1,pieChart2;
    ArrayList<PieEntry> yvalues  = new ArrayList<>();
    ArrayList<PieEntry> yvalues1  = new ArrayList<>();
    ArrayList<PieEntry> yvalues2  = new ArrayList<>();
    ArrayList<TeacherWiseStudentDetails> arrayList=new ArrayList<>();
    PieDataSet dataSet,dataSet1,dataSet2;
    InviteCardAdapter inviteCardAdapter;
    LinearLayoutManager horizontalLayoutManager;
    private RecyclerView rv_invite, rv_videos, rv_news,rv_moti_videos;
    TextView tv_news, class_id,class_id_one, tv_invite,tv_motivational,tv_teacher,priminum_s,total_s,free_s,priminum_s_one,total_s_one,free_s_one;;
    private String ClassId;
    LinearLayout chart_one;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private RecyclerView rv_subject,rv_teacher_list,rv_student_details;
    public TeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_teacher_fragment, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        rv_student_details=view.findViewById(R.id.rv_student_details);
        pieChart=view.findViewById(R.id.pichart);
        pieChart.setUsePercentValues(true);
        pieChart1=view.findViewById(R.id.pichart_one);
        pieChart1.setUsePercentValues(true);
        ClassId = preferences.getString("class_id", "");
        priminum_s=view.findViewById(R.id.priminum_s);
        total_s=view.findViewById(R.id.total_s);
        free_s=view.findViewById(R.id.free_s);
        final RippleBackground rippleBackground = (RippleBackground) view.findViewById(R.id.content);
        ImageView imageView = (ImageView) view.findViewById(R.id.centerImage);
        rippleBackground.startRippleAnimation();
        chart_one=view.findViewById(R.id.chart_one);
        total_s_one=view.findViewById(R.id.total_s_one);
        free_s_one=view.findViewById(R.id.free_s_one);
        priminum_s_one=view.findViewById(R.id.priminum_s_one);
        class_id=view.findViewById(R.id.class_id);
        class_id_one=view.findViewById(R.id.class_id_one);
        final RippleBackground rippleBackground2 = (RippleBackground) view.findViewById(R.id.content2);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.centerImage2);
        rippleBackground2.startRippleAnimation();

        rv_invite = view.findViewById(R.id.rv_invite);
        rv_news = view.findViewById(R.id.rv_news);
        tv_invite = view.findViewById(R.id.tv_invite);
        tv_news = view.findViewById(R.id.tv_news);
        rv_moti_videos = view.findViewById(R.id.rv_moti_videos);
        tv_motivational = view.findViewById(R.id.tv_motivational);


        newsAndNotification_Detail();
        getInviteCard();
        getMotivationalVideos();
        getAdana(preferences.getString("studentId", ""));
        return view;
    }


    private List<InviteData> invitedata() {

        List<InviteData> inviteData1 = new ArrayList<>();

        inviteData1.add(new InviteData(R.drawable.ic_share, "Invite friends and get Package free"));
       // inviteData1.add(new InviteData(R.drawable.ic_car, "Invite friends and get Package free"));
       // inviteData1.add(new InviteData(R.drawable.ic_car, "Invite friends and get Package free"));
        return inviteData1;
    }

    private void getInviteCard() {


        inviteData = invitedata();
        inviteCardAdapter = new InviteCardAdapter(inviteData, getActivity());
        horizontalLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rv_invite.setLayoutManager(horizontalLayoutManager);
        rv_invite.setAdapter(inviteCardAdapter);

    }

    private void getMotivationalVideos() {
        Call<MotivationalVideos> call = Api_Client.getInstance().getMotivationalVideo();
        call.enqueue(new Callback<MotivationalVideos>() {
            @Override
            public void onResponse(Call<MotivationalVideos> call, Response<MotivationalVideos> response) {
                if (response.body().getStatus().equals(1)) {
                    MotivationalVideos motivationalVideos = response.body();
                    ArrayList<MotivationalVideosDetails> subVideos = (ArrayList<MotivationalVideosDetails>) motivationalVideos.getMotivationalVideosDetails();
                    if (subVideos.size() > 0) {
                        tv_motivational.setVisibility(View.VISIBLE);
                        MotivationalVideoAdapter adapter = new MotivationalVideoAdapter(getActivity(), subVideos);
                        rv_moti_videos.setAdapter(adapter);
                        rv_moti_videos.setHasFixedSize(true);
                        rv_moti_videos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                    } else {
                        tv_motivational.setVisibility(View.GONE);
                    }

                } else {
                    tv_motivational.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No videos found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MotivationalVideos> call, Throwable t) {
                Toast.makeText(getActivity(), "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void newsAndNotification_Detail() {
        Call<Allnews> call = Api_Client.getInstance().getNews();
        call.enqueue(new Callback<Allnews>() {
            @Override
            public void onResponse(Call<Allnews> call, Response<Allnews> response) {
                if (response.body().getStatus().equals(1)) {
                    Allnews allnews = response.body();
                    ArrayList<News> subNews = (ArrayList<News>) allnews.getGetNews();
                    if (subNews.size() > 0) {
                        tv_news.setVisibility(View.VISIBLE);
                        NewsAdapter adapter = new NewsAdapter(getActivity(), subNews);
                        rv_news.setAdapter(adapter);
                        rv_news.setHasFixedSize(true);
                        rv_news.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                    } else {

                        tv_news.setVisibility(View.GONE);

                    }


                } else {
                    tv_news.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Allnews> call, Throwable t) {
                //  Toast.makeText(SmartPrefDashBoard.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getAdana(String data) {
        String urlParameters ="teacher_id="+data;
        //showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                String status = jsonObject1.getString("status");
                // hideDialog();
                if (status.equals("1")) {
                    JSONArray jsonArray=jsonObject1.getJSONArray("data");
                    arrayList.clear();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        TeacherWiseStudentDetails teacherWiseStudentDetails=new TeacherWiseStudentDetails();
                        teacherWiseStudentDetails.setClass_id(jsonObject.getInt("class_id"));
                        teacherWiseStudentDetails.setFree_student(jsonObject.getInt("free_student"));
                        teacherWiseStudentDetails.setPre_student(jsonObject.getInt("premium_student"));
                        teacherWiseStudentDetails.setTotal_student(jsonObject.getInt("total_student"));
                        arrayList.add(teacherWiseStudentDetails);
                    }
                  /*  TeachWiseStudentDetailsAdapter teachWiseStudentDetailsAdapter=new TeachWiseStudentDetailsAdapter(getActivity(),arrayList);
                    rv_student_details.setAdapter(teachWiseStudentDetailsAdapter);
                    rv_student_details.setHasFixedSize(true);
                    rv_student_details.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                 */
                    if(arrayList.size()==1){
                       setChartData();
                    }
                    if(arrayList.size()==2){
                        setChartData();
                        chart_one.setVisibility(View.VISIBLE);
                        int primuum=arrayList.get(1).getPre_student();
                        int free=arrayList.get(1).getFree_student();
                        yvalues1.add(new PieEntry(primuum,"premium_student"));
                        yvalues1.add(new PieEntry(free,"free_student"));
                        total_s_one.setText("Total Student : "+arrayList.get(1).getTotal_student());
                        free_s_one.setText("Free Student : "+free);
                        priminum_s_one.setText("Premium Student : "+primuum);
                        class_id_one.setText("Student Details of class "+arrayList.get(1).getClass_id());
                        //yvalues.add(new PieEntry(8f,"asdfasdfasdfas"));
                        dataSet1 = new PieDataSet(yvalues1, "Student Data");
                        PieData datae1 = new PieData(dataSet1);
                        // In Percentage term
                        datae1.setValueFormatter(new PercentFormatter());
                        // Default value
                        //data.setValueFormatter(new DefaultValueFormatter(0));
                        pieChart1.setData(datae1);
                        pieChart1.invalidate(); // refresh
                        //pieChart.setDescription("This is Pie Chart");
                        pieChart1.setDrawHoleEnabled(true);
                        pieChart1.setTransparentCircleRadius(25f);
                        pieChart1.setHoleRadius(25f);
                        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                        datae1.setValueTextSize(13f);
                        datae1.setValueTextColor(Color.WHITE);
                        pieChart1.animateXY(1400, 1400);
                    }

                    //  yvalues.add(new PieEntry(30f,arrayList.get(2).getSubject()));
                }
            } catch (Exception e) {
                //    hideDialog();
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/teacher_dashboard_students_detail");
    }

    public static double parseTimeToMinutes(String hourFormat) {
        double minutes = 0;
        String[] split = hourFormat.split(":");
        try {
            minutes += Double.parseDouble(split[0])*60;
            minutes += Double.parseDouble(split[1]);
            minutes += Double.parseDouble(split[2])/60;
            return minutes;
        } catch (Exception e) {
            return -1;
        }
    }

public void setChartData(){
    int primuum=arrayList.get(0).getPre_student();
    int free=arrayList.get(0).getFree_student();
    yvalues.add(new PieEntry(primuum,"premium_student"));
    yvalues.add(new PieEntry(free,"free_student"));
    total_s.setText("Total Student : "+arrayList.get(0).getTotal_student());
    free_s.setText("Free Student : "+free);
    priminum_s.setText("Premium Student : "+primuum);
    class_id.setText("Student Details of class "+arrayList.get(0).getClass_id());
    //yvalues.add(new PieEntry(8f,"asdfasdfasdfas"));
    dataSet = new PieDataSet(yvalues, "Student Data");
    PieData datae = new PieData(dataSet);
    // In Percentage term
    datae.setValueFormatter(new PercentFormatter());
    // Default value
    // data.setValueFormatter(new DefaultValueFormatter(0));
    pieChart.setData(datae);
    pieChart.invalidate(); // refresh
    //pieChart.setDescription("This is Pie Chart");
    pieChart.setDrawHoleEnabled(true);
    pieChart.setTransparentCircleRadius(25f);
    pieChart.setHoleRadius(25f);
    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
    datae.setValueTextSize(13f);
    datae.setValueTextColor(Color.WHITE);
    pieChart.animateXY(1400, 1400);
}



}