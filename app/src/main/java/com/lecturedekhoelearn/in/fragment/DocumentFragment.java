package com.lecturedekhoelearn.in.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.TopicWiseDocADapter;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.TopicWiseDocModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class DocumentFragment extends Fragment {

    RecyclerView docs;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    String TopicId, StudentId, ClassId, SubjectId;
    TopicWiseDocADapter topicWiseDocADapter;
    ArrayList<TopicWiseDocModel> topics_details=new ArrayList<>();
    public static final int progress_bar_type = 0;
    String pdf_name="";

    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_docemnt, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getActivity().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 112);
        editor = preferences.edit();
        docs=view.findViewById(R.id.rv_doc);
        StudentId = preferences.getString("studentId", "");
        ClassId = preferences.getString("class_id", "");
        SubjectId = preferences.getString("subject_Id", "");
        TopicId = getActivity().getIntent().getStringExtra("topic_id");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        topicWiseDocADapter=new TopicWiseDocADapter(getActivity(),topics_details);
        topicWiseDocADapter.setOnItemClickListener(new TopicWiseDocADapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (topics_details.get(position).getPackage_type().equals("0")) {
                    pdf_name = topics_details.get(position).getPdf();
                    try {
                        File pdfFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + pdf_name);
                        if (pdfFile.exists()) {
                            openFile(pdfFile);
                        } else
                            new DownloadFileFromURL().execute("https://lecturedekho.com/admin/assets/pdf/" + topics_details.get(position).getPdf());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(), "Only for Prime Members", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        getdoc();
        return view;
    }

    private void getdoc() {
        String urlParameters = "class_id=" +ClassId+"&subject_id="+SubjectId+"&topic_id="+TopicId+"&student_id="+StudentId;
        ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(new ServerConnector.onAsyncTaskComplete() {
            @Override
            public void OnResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        JSONArray  jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            TopicWiseDocModel topicWiseDocModel=new TopicWiseDocModel();
                            topicWiseDocModel.setTitle(jsonObject1.getString("title"));
                            topicWiseDocModel.setPackage_type("0");
                            topicWiseDocModel.setPdf(jsonObject1.getString("pdf"));
                            topicWiseDocModel.setThumbnail(jsonObject1.getString("thumbnail"));
                            topics_details.add(topicWiseDocModel);
                        }
                        topicWiseDocADapter=new TopicWiseDocADapter(getActivity(),topics_details);
                        docs.setAdapter(topicWiseDocADapter);
                        docs.setHasFixedSize(true);
                        docs.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                    }else if (status.equals("2")){
                        JSONArray  jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            TopicWiseDocModel topicWiseDocModel=new TopicWiseDocModel();
                            topicWiseDocModel.setTitle(jsonObject1.getString("title"));
                            topicWiseDocModel.setPackage_type(jsonObject1.getString("package_type"));
                            topicWiseDocModel.setPdf(jsonObject1.getString("pdf"));
                            topicWiseDocModel.setThumbnail(jsonObject1.getString("thumbnail"));
                            topics_details.add(topicWiseDocModel);
                        }
                        topicWiseDocADapter=new TopicWiseDocADapter(getActivity(),topics_details);
                        docs.setAdapter(topicWiseDocADapter);
                        docs.setHasFixedSize(true);
                        docs.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                    }

                } catch (Exception e) {
                     e.printStackTrace();
                }

            }
        });
        loginConnection.execute("https://lecturedekho.com/admin/api/app_pdf_in_test_series");

    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onCreateDialog();
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/"+pdf_name);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            pDialog.dismiss();
            File pdfFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+pdf_name);
            openFile(pdfFile);
        }

    }




    public void onCreateDialog() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();

    }


    private void openFile(File url) {

        try {

          //  Uri uri = Uri.fromFile(url);
            Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Objects.requireNonNull(getActivity()).startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }
}


