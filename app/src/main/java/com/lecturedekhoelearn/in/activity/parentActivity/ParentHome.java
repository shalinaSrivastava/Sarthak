package com.lecturedekhoelearn.in.activity.parentActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ParentHome extends Fragment implements OnChartValueSelectedListener {

    PieChart pieChart;
    ArrayList<PieEntry> yvalues  = new ArrayList<>();
    ArrayList<StudentActivityModel> arrayList=new ArrayList<>();
    PieDataSet dataSet;
    TextView d_time,last_login;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_parent_home, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        pieChart=view.findViewById(R.id.pichart);
       // pieChart.setUsePercentValues(true);
        last_login=view.findViewById(R.id.last_logi);
        d_time=view.findViewById(R.id.dtime);
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        getAdana(preferences.getString("studentId", ""));
        lastlogin(preferences.getString("studentId", ""));
        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
    }

    private void getAdana(String data) {
        String urlParameters ="student_id="+data;
        //showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                JSONObject jsonObject = jsonObject1.getJSONObject("series");
                String status = jsonObject1.getString("status");
                // hideDialog();
                if (status.equals("1")) {
                    JSONArray jsonArray=jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        StudentActivityModel studentActivityModel = new StudentActivityModel();
                        double time_total=parseTimeToMinutes(jsonObject2.getString("TotalTime"));
                        studentActivityModel.setStrt_time(jsonObject2.getString("TotalTime"));
                        studentActivityModel.setSubject(jsonObject2.getString("subject"));
                        arrayList.add(studentActivityModel);
                        yvalues.add(new PieEntry((float) time_total,jsonObject2.getString("subject"),i));
                    }
                    //yvalues.add(new PieEntry(8f,"asdfasdfasdfas"));
                    dataSet = new PieDataSet(yvalues, "Student Data");
                    PieData datae = new PieData(dataSet);
                    // In Percentage term
                    datae.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return super.getFormattedValue(value);
                        }
                    });
                    // Default value
                    //data.setValueFormatter(new DefaultValueFormatter(0));
                    pieChart.setData(datae);
                    pieChart.invalidate(); // refresh
                    //pieChart.setDescription("This is Pie Chart");
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setTransparentCircleRadius(25f);
                    pieChart.setHoleRadius(25f);
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    datae.setValueTextSize(13f);
                    datae.setValueTextColor(Color.WHITE);
                   // pieChart.setOnChartValueSelectedListener(this);
                    pieChart.animateXY(1400, 1400);
                  //  yvalues.add(new PieEntry(30f,arrayList.get(2).getSubject()));
                }
            } catch (Exception e) {
                //    hideDialog();
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/subjectwise_total_time");
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


    private void lastlogin(String data) {
        String urlParameters ="student_id="+data;
        //showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                JSONObject jsonObject=jsonObject1.getJSONObject("news");
                String last_time=jsonObject.getString("last_login");
                last_login.setText(parseDateToddMMyyyy(last_time));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/get_last_login");
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
