package com.lecturedekhoelearn.in.activity.queryActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.QueryDiscussionListAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.queryModel.QueryDescussion;
import com.lecturedekhoelearn.in.model.queryModel.QueryReply;
import com.lecturedekhoelearn.in.model.queryModel.Query_Descussion_Details;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserQueryDiscussion extends AppCompatActivity {
    SharedPreferences preferences;
    RecyclerView recycle;
    EditText title, des;
    Button send;
    private String User_id, Query_id;
    private ProgressDialog pDialog;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_query_discussion);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        Query_id = getIntent().getStringExtra("query_id");
        User_id = preferences.getString("studentId","");

        title = findViewById(R.id.et_title);
        des = findViewById(R.id.et_des);
        send = findViewById(R.id.send_icon);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(false);

        recycle = findViewById(R.id.recyler);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(mManager);
        getDescussion();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReply();
            }
        });
    }

    public void getDescussion() {
        pDialog.setTitle("Get Discussion Queries");
        showDialog();
        Call<QueryDescussion> queryCall = Api_Client.getInstance().getQueryDec(Query_id);
        queryCall.enqueue(new Callback<QueryDescussion>() {

            @Override
            public void onResponse(Call<QueryDescussion> call, Response<QueryDescussion> response) {

                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    QueryDescussion query = response.body();
                    ArrayList<Query_Descussion_Details> query_details = (ArrayList<Query_Descussion_Details>) query.getQuery_descussion_details();
                    final QueryDiscussionListAdapter des_adapter = new QueryDiscussionListAdapter(getApplication(), query_details);
                    recycle.setAdapter(des_adapter);
                    recycle.setHasFixedSize(true);
                    // Scroll to bottom on new messages
                    des_adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onItemRangeInserted(int positionStart, int itemCount) {
                            mManager.smoothScrollToPosition(recycle, null, des_adapter.getItemCount());
                        }
                    });

                    recycle.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
                } else {

                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<QueryDescussion> call, Throwable t) {
                hideDialog();
            }
        });
    }


    public void getReply() {
        String reply = des.getText().toString().trim();
        pDialog.setTitle("Fetching Reply...");
        showDialog();
        Call<QueryReply> queryCall = Api_Client.getInstance().getDiscussion(User_id, Query_id, reply);
        queryCall.enqueue(new Callback<QueryReply>() {
            @Override
            public void onResponse(Call<QueryReply> call, Response<QueryReply> response) {
                QueryReply query = response.body();
                System.out.println("response " + response);
                if (response.body().getSuccess().equals(1)) {
                    hideDialog();
                    des.setText("");
                    getDescussion();
                }
            }

            @Override
            public void onFailure(Call<QueryReply> call, Throwable t) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
