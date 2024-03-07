package com.lecturedekhoelearn.in.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.model.TeacherModel.StudentListDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.lecturedekhoelearn.in.Constant.studentProfilethumbnail;


public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    Context context;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private ArrayList<StudentListDetailsModel> testInsideVideoDetailsModelArrayList;
    private String testName, email;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String profilePic;

    public StudentListAdapter(Activity context, ArrayList<StudentListDetailsModel> testInsideVideoDetailsModels) {
        this.context = context;
        this.testInsideVideoDetailsModelArrayList = testInsideVideoDetailsModels;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_student_list, viewGroup, false);
        preferences = context.getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final StudentListDetailsModel allTestPacakgeDetails = testInsideVideoDetailsModelArrayList.get(i);

        testName = allTestPacakgeDetails.getName();
        email = allTestPacakgeDetails.getEmail();

        myViewHolder.title.setText(testName);
        myViewHolder.artist.setText(email);
        String [] j_date_data=allTestPacakgeDetails.getCreated_at().split(" ");
        myViewHolder.j_date.setText("Joining Date : "+j_date_data[0]);
        if (allTestPacakgeDetails.getPayment_date()!=null) {
            String[] p_date_data = allTestPacakgeDetails.getPayment_date().split(" ");
            myViewHolder.p_date.setText("Payment Date : " + p_date_data[0]);
        }
        Picasso.get()
                .load(studentProfilethumbnail + profilePic)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(myViewHolder.list_image);


        myViewHolder.rl_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return testInsideVideoDetailsModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_student;
        ImageView list_image;
        TextView title, artist,j_date,p_date;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_student = itemView.findViewById(R.id.rl_student);
            list_image = itemView.findViewById(R.id.list_image);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
            j_date=itemView.findViewById(R.id.j_date);
            p_date=itemView.findViewById(R.id.p_date);


        }
    }
}