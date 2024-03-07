package com.lecturedekhoelearn.in.fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ActivityAdapter;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.StudentActivityModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ChildActivityFragment extends Fragment {
    private RecyclerView rv_notification;
    private ProgressDialog pDialog;
    private ArrayList<StudentActivityModel> arrayList=new ArrayList<>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_child_activity, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        rv_notification = view.findViewById(R.id.rv_notification);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        getActivity(preferences.getString("studentId", ""));
        return view;
    }

   /* private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
*/
    private void getActivity(String data) {
        String urlParameters ="mobile="+data;
        //showDialog();


        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {


            try {
                JSONObject jsonObject1 = new JSONObject(response);
                JSONArray jsonArray = jsonObject1.getJSONArray("Activity");
                String status = jsonObject1.getString("status");
               // hideDialog();
                if (status.equals("1")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        StudentActivityModel studentActivityModel=new StudentActivityModel();
                        studentActivityModel.setMobile(jsonObject.getString("student_id"));
                        studentActivityModel.setEnd_time(jsonObject.getString("act_date"));
                        studentActivityModel.setStrt_time(jsonObject.getString("start_time"));
                        studentActivityModel.setVideo_name(jsonObject.getString("video_name"));
                        studentActivityModel.setImage(jsonObject.getString("image"));
                        studentActivityModel.setTopic(jsonObject.getString("topic"));
                        studentActivityModel.setSubject(jsonObject.getString("subject"));
                        arrayList.add(studentActivityModel);
                    }
                    ActivityAdapter analysisAdapter = new ActivityAdapter(getActivity(), arrayList);
                    rv_notification.setAdapter(analysisAdapter);
                    rv_notification.setHasFixedSize(true);
                    rv_notification.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                }
            } catch (Exception e) {
            //    hideDialog();
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/showactivity");
    }
}
