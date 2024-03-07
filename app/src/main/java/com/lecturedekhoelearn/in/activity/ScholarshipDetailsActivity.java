package com.lecturedekhoelearn.in.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lecturedekhoelearn.in.BuildConfig;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.adapter.ScholarshipResultAdapter;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.SchResultModelDetails;
import com.lecturedekhoelearn.in.model.ScholarshipResultModel;
import com.lecturedekhoelearn.in.model.UploadScholarResult;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipDetailsActivity extends AppCompatActivity {


    TextView title, tv_scholar_result;
    String packagename, pacDescription, pacimage;
    ImageView imagepackage;
    String bannerPath = "https://www.lecturedekho.com/admin/assets/result/";
    JustifyTextView tv_description;
    Button btn_image;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    File mPhotoFile;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    private String user_dp = "";

    FileCompressor mCompressor;
    String userProfile, studentId, scholarId;
    private ProgressDialog pDialog;

    RecyclerView rv_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_details);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        packagename = preferences.getString("title", "");
        pacDescription = preferences.getString("desc", "");
        pacimage = preferences.getString("banner", "");
        studentId = preferences.getString("studentId", "");
        scholarId = preferences.getString("sch_id", "");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        title = findViewById(R.id.tv_pac_name);
        tv_description = findViewById(R.id.tv_description);
        btn_image = findViewById(R.id.btn_image);
        imagepackage = findViewById(R.id.image);
        rv_result = findViewById(R.id.rv_result);
        tv_scholar_result = findViewById(R.id.tv_scholar_result);
        mCompressor = new FileCompressor(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        Picasso.get()
                .load(bannerPath + pacimage)
                .error(R.drawable.ic_empty)
                .placeholder(R.drawable.splashlogo)
                .into(imagepackage);
        title.setText(packagename);
        tv_description.setText("Description: " + Html.fromHtml(pacDescription));

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        getSchResultDeatils();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ScholarshipDetailsActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
                   // Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
                    updateImage(studentId, user_dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    user_dp = getFileToByte(mPhotoFile.getAbsolutePath());
                 //   Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
                    updateImage(studentId, user_dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }


    public void updateImage(String studentId, String profile_pic) {

        pDialog.setTitle("Uploading Results");
        showDialog();
        Call<UploadScholarResult> uploadScholarResult = Api_Client.getInstance().uploadScholarResult(studentId, scholarId, profile_pic);
        uploadScholarResult.enqueue(new Callback<UploadScholarResult>() {
            @Override
            public void onResponse(Call<UploadScholarResult> call, Response<UploadScholarResult> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    getSchResultDeatils();
                    Toast.makeText(ScholarshipDetailsActivity.this, "Results Uploaded Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    hideDialog();
                    Toast.makeText(ScholarshipDetailsActivity.this, "Not uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadScholarResult> call, Throwable t) {
                hideDialog();
                Toast.makeText(ScholarshipDetailsActivity.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getSchResultDeatils() {
        pDialog.setMessage("Loading Scholarship Results...");
        showDialog();
        Call<ScholarshipResultModel> allNotificationModelCall = Api_Client.getInstance().getAllScholarResult(studentId, scholarId);
        allNotificationModelCall.enqueue(new Callback<ScholarshipResultModel>() {
            @Override
            public void onResponse(Call<ScholarshipResultModel> call, Response<ScholarshipResultModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    ScholarshipResultModel allNotificationModel = response.body();
                    ArrayList<SchResultModelDetails> schResultModelDetailsList = (ArrayList<SchResultModelDetails>) allNotificationModel.getSchResultModelDetailsList();
                    if (!schResultModelDetailsList.isEmpty() && schResultModelDetailsList.size() > 0) {
                        tv_scholar_result.setVisibility(View.VISIBLE);
                        rv_result.setVisibility(View.VISIBLE);
                        ScholarshipResultAdapter analysisAdapter = new ScholarshipResultAdapter(getApplication(), schResultModelDetailsList);
                        rv_result.setAdapter(analysisAdapter);
                        rv_result.setHasFixedSize(true);
                        rv_result.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.HORIZONTAL, false));
                    } else {

                        tv_scholar_result.setVisibility(View.GONE);
                        rv_result.setVisibility(View.GONE);
                    }


                } else {
                    hideDialog();
                    Toast.makeText(ScholarshipDetailsActivity.this, "No Scholarship Results Found", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ScholarshipResultModel> call, Throwable t) {
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


}
