package com.lecturedekhoelearn.in.activity.parentActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.BuyTestSeries;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.util.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;


public class ParentProfile extends Fragment {

    TextView c_name,c_email,c_phone,p_name,p_phone,p_email,paynment_txt;
    private ProgressDialog pDialog;
    Context context;
    String payment_dat="";
    Button buy_now;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_parent_profile, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Please Wait..");
        pDialog.setCancelable(false);
        context=getActivity();
        p_name=view.findViewById(R.id.p_name);
        p_email=view.findViewById(R.id.p_email);
        p_phone=view.findViewById(R.id.p_phone);
        c_name=view.findViewById(R.id.c_name);
        c_email=view.findViewById(R.id.c_email);
        c_phone=view.findViewById(R.id.c_phone);
        paynment_txt=view.findViewById(R.id.paynment_txt);
        buy_now=view.findViewById(R.id.buy_now);
        getp_data();
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BuyTestSeries.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getp_data() {
        String urlParameters ="email="+ AppPreferences.getP_email(context) +"&password="+AppPreferences.getUserid(context);
        showDialog();
        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(response -> {
            hideDialog();
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                String status = jsonObject1.getString("status");
                if (status.equals("1")) {
                    JSONObject jsonObject=jsonObject1.getJSONObject("Detail");
                    p_phone.setText(jsonObject.getString("mobile"));
                    p_email.setText(jsonObject.getString("email"));
                    p_name.setText(jsonObject.getString("name"));
                    JSONObject student_data=jsonObject1.getJSONObject("student_detail");
                    c_name.setText(student_data.getString("name"));
                    c_phone.setText(student_data.getString("mobile"));
                    c_email.setText(student_data.getString("email"));
                    payment_dat=student_data.getString("student_type");
                    paynment_txt.setText(student_data.getString("student_type"));
                    if (payment_dat.equalsIgnoreCase("premium")){
                        buy_now.setVisibility(View.GONE);
                    }else {
                        buy_now.setVisibility(View.VISIBLE);
                    }

                }
            } catch (Exception e) {
                //hideDialog();
                e.printStackTrace();
            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/parent_login");
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
