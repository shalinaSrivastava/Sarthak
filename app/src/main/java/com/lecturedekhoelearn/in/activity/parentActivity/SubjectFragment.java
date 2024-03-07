package com.lecturedekhoelearn.in.activity.parentActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;


import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ActivityAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import com.lecturedekhoelearn.in.model.SubjectModel;
import com.lecturedekhoelearn.in.model.SubjectModelDetails;
import com.lecturedekhoelearn.in.model.TopicModel;
import com.lecturedekhoelearn.in.model.TopicModelDetails;
import com.lecturedekhoelearn.in.util.MyXAxisValueFormatter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class SubjectFragment extends Fragment {
    private RecyclerView rv_subject, rv_topic;
    private String ClassId;
    private TextView tvX, total_time;
    String topic_name = "";
    ArrayList<BarEntry> entries = new ArrayList<>();
    LineDataSet lineDataSet;
    ArrayList<SubjectModelDetails> subjectModelDetails = new ArrayList<>();
    ArrayList<TopicModelDetails> topics_details = new ArrayList<>();
    ChildSubjectAdapter analysisAdapter;
    ChildTopicAdapter topic_adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String[] day;
    BarChart barChart;
    private ProgressDialog pDialog;
    ArrayList<String> arrayList = new ArrayList<>();

    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;

    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Please Wait..");
        pDialog.setCancelable(false);
        context=getActivity();
        rv_subject = view.findViewById(R.id.rv_subject);
        rv_topic = view.findViewById(R.id.rv_topic);
        barChart = view.findViewById(R.id.lineChart);
        total_time = view.findViewById(R.id.total_time);
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ClassId = preferences.getString("class_id", "");
        getAllSubject();
        analysisAdapter = new ChildSubjectAdapter(getActivity(), subjectModelDetails);
        analysisAdapter.setOnItemClickListener(new ChildSubjectAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                getAllTopics(subjectModelDetails.get(position).getId());
            }

            @Override
            public void onItemLongClick(int position, View v) {
            }
        });
        topic_adapter = new ChildTopicAdapter(getActivity(), topics_details);
        topic_adapter.setOnItemClickListener(new ChildTopicAdapter.ClickListenerTopic() {
            @Override
            public void onItemClick(int position, View v) {
                gettopicdata(preferences.getString("studentId", ""), topics_details.get(position).getTopics());
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void getAllSubject() {
        showDialog();
        Call<SubjectModel> subjectModelCall = Api_Client.getInstance().getSubject(ClassId);
        subjectModelCall.enqueue(new Callback<SubjectModel>() {
            @Override
            public void onResponse(Call<SubjectModel> call, Response<SubjectModel> response) {
               // hideDialog();
                try {
                    if (response.body().getStatus().equals(1)) {
                        SubjectModel subjectModel = response.body();
                        subjectModelDetails = (ArrayList<SubjectModelDetails>) subjectModel.getSubjectModelDetails();
                        if (subjectModelDetails.size() > 0) {
                            analysisAdapter = new ChildSubjectAdapter(getActivity(), subjectModelDetails);
                            rv_subject.setAdapter(analysisAdapter);
                            rv_subject.setHasFixedSize(true);
                            rv_subject.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                            getAllTopics(subjectModelDetails.get(0).getId());
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubjectModel> call, Throwable t) {
            hideDialog();
            }
        });
    }

    public void getAllTopics(String subjectId) {
        showDialog();
        Call<TopicModel> topicsCall = Api_Client.getInstance().getAllTopics(ClassId, subjectId);
        topicsCall.enqueue(new Callback<TopicModel>() {

            @Override
            public void onResponse(Call<TopicModel> call, Response<TopicModel> response) {
                    hideDialog();
                if (response.body().getStatus().equals(1)) {
                    TopicModel topics = response.body();
                    topics_details = (ArrayList<TopicModelDetails>) topics.getVideoTopicDetails();
                    if (!topics_details.isEmpty() && topics_details.size() > 0) {
                        topic_adapter = new ChildTopicAdapter(getActivity(), topics_details);
                        rv_topic.setAdapter(topic_adapter);
                        rv_topic.setHasFixedSize(true);
                        rv_topic.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                        gettopicdata(preferences.getString("studentId", ""), topics_details.get(0).getTopics());
                    } else {
                        Toast.makeText(getActivity(), "Topics Not Found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopicModel> call, Throwable t) {
                hideDialog();
                Toast.makeText(getActivity(), "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gettopicdata(String subjectId, String topic) {
        String urlParameters = "student_id=" + subjectId + "&topic=" + topic;
        showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
            hideDialog();
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                String status = jsonObject1.getString("status");
                StringBuilder value_data = new StringBuilder();
                entries.clear();
                arrayList.clear();
               // entries(new BarEntry(0,(int)0));
                if (status.equals("1")) {
                    JSONObject jsonObject = jsonObject1.getJSONObject("lastlogindate");
                    JSONObject total_t = jsonObject1.getJSONObject("total_time");
                    total_time.setText((int) parseTimeToMinutes(total_t.getString("total_time")) + " Min");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        double time_total = parseTimeToMinutes(jsonObject2.getString("total_time"));
                        //  data=time_total;
                        entries.add(new BarEntry(i + 1, (int) time_total));
                      /*  if(entries.size()==1){
                            arrayList.add("0");
                        }*/
                        arrayList.add(jsonObject2.getString("act_date"));
                        //e_data=time_total;
                    }

                    if (entries.size()>=1){
                        arrayList.add(0,"0");
                        getData(entries, arrayList);
                    }else {
                        arrayList.clear();
                        getData(entries,arrayList);
                    }
                }
            } catch (Exception e) {
                //    hideDialog();
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/topicwise_total_time");
    }

    private void getData(ArrayList<BarEntry> arrayList, ArrayList<String> arrayList1) {

        barDataSet = new BarDataSet(arrayList, "Study Time");
        barData = new BarData(barDataSet);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        String[] months = new String[arrayList1.size()];
        months = arrayList1.toArray(months);
        String[] finalMonths = months;
        String[] finalMonths1 = months;
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int value_i=(int) value;
                if (finalMonths1.length>value_i&&value_i>=0){
                    return finalMonths[(int) value];
                }else {
                    return "";
                }
              //  return finalMonths[(int) value];
            }
        };
        ValueFormatter formatter1 = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + " min";
            }
        };

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setValueFormatter(formatter1);
        xAxis.setValueFormatter(formatter);

        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setScaleXEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDragDecelerationEnabled(false);
        barChart.setFitBars(true);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.animateX(2500);
        barChart.invalidate();
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
    }

    public static double parseTimeToMinutes(String hourFormat) {
        double minutes = 0;
        String[] split = hourFormat.split(":");
        try {
            minutes += Double.parseDouble(split[0]) * 60;
            minutes += Double.parseDouble(split[1]);
            minutes += Double.parseDouble(split[2]) / 60;
            return minutes;
        } catch (Exception e) {
            return 0;
        }
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
