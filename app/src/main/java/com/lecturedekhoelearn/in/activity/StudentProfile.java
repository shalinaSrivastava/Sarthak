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
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lecturedekhoelearn.in.BuildConfig;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.EditProfileModel;
import com.lecturedekhoelearn.in.model.UpdateProfileModel;
import com.lecturedekhoelearn.in.model.UserProfileModel;
import com.lecturedekhoelearn.in.model.UserProfileModelDetails;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.Constant.userProfilePath;

public class StudentProfile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String studentId;

    TextView tv_gender, tv_course, tv_email, student_email, student_name, tv_back;
    CircleImageView imageViewProfilePic;

    EditText tv_name, tv_mobile;
    private ProgressDialog pDialog;
    Button btn_edit, btn_update;
    String name, email, profile_pic_link, Category, mobile_number, gender;
    ImageView UploadImage;

    File mPhotoFile;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    private String  user_dp = "";

    FileCompressor mCompressor;
    String userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_keyboard_arrow_left_black_24dp, null);
                toolbar.setNavigationIcon(d);
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedPreferences = getApplication().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        studentId = sharedPreferences.getString("studentId", "");

        mCompressor = new FileCompressor(this);
        tv_name = findViewById(R.id.tv_name);
        tv_gender = findViewById(R.id.tv_gender);
        tv_course = findViewById(R.id.tv_course);
        tv_email = findViewById(R.id.tv_email);
        tv_mobile = findViewById(R.id.tv_mobile);
        student_email = findViewById(R.id.student_email);
        student_name = findViewById(R.id.student_name);
        tv_back = findViewById(R.id.tv_back);
        btn_edit = findViewById(R.id.btn_edit);
        btn_update = findViewById(R.id.btn_update);
        UploadImage = findViewById(R.id.UploadImage);
        imageViewProfilePic = findViewById(R.id.img_student);

        CheckedUpdatedImages();

        btn_edit.setVisibility(View.VISIBLE);
        tv_name.setEnabled(false);
        tv_mobile.setEnabled(false);


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getProfileData();


        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_update.setVisibility(View.VISIBLE);
                btn_edit.setVisibility(View.GONE);
                tv_name.setEnabled(true);
                tv_mobile.setEnabled(true);


            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.GONE);
                name = tv_name.getText().toString().trim();
                mobile_number = tv_mobile.getText().toString().trim();


                if (name.trim().equals("")) {
                    tv_name.setError("Please enter your name");
                } else if (mobile_number != null && mobile_number.length() != 10) {
                    tv_mobile.setError("Please enter your phone number");

                } else {

                    updateProfile();


                }
            }
        });


    }


    public void getProfileData() {
        pDialog.setTitle("Loading User Profile");
        showDialog();

        Call<UserProfileModel> userProfileCall = Api_Client.getInstance().getProfile(studentId);
        userProfileCall.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    UserProfileModel userProfileModel = response.body();
                    UserProfileModelDetails userProfileModelDetails = userProfileModel.getUserProfileModelDetails();
                    name = userProfileModelDetails.getName();
                    email = userProfileModelDetails.getEmail();
                    Category = userProfileModelDetails.getCategory();
                    mobile_number = userProfileModelDetails.getMobile();
                    gender = userProfileModelDetails.getClass_id();
                    profile_pic_link = userProfileModelDetails.getProfile_pic();

                    editor.putString("mobile_number",mobile_number).apply();
                    tv_name.setText(name);
                    tv_gender.setText(Category);
                    tv_course.setText(gender);
                    tv_email.setText(email);
                    tv_mobile.setText(mobile_number);
                    student_email.setText(email);
                    student_name.setText(name);


                } else {
                    hideDialog();

                    Toast.makeText(StudentProfile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void updateProfile() {
        pDialog.setTitle("Updating your profile");
        showDialog();

        Call<EditProfileModel> userProfileCall = Api_Client.getInstance().EditProfile(studentId, name, mobile_number);
        userProfileCall.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                if (response.body().getStatus().equals(0)) {
                    hideDialog();

                    Toast.makeText(StudentProfile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    getProfileData();
                    tv_name.setEnabled(false);
                    tv_mobile.setEnabled(false);

                } else {

                    hideDialog();
                    Toast.makeText(StudentProfile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {
                hideDialog();

            }
        });

    }


    public void updateImage(String studentId, String profile_pic) {
        pDialog.setTitle("Uploading User Profile");
        showDialog();

        Call<UpdateProfileModel> topicsCall = Api_Client.getInstance().updateProfile(studentId, profile_pic);
        topicsCall.enqueue(new Callback<UpdateProfileModel>() {

            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    UpdateProfileModel updateProfileModel = response.body();

                    userProfile = updateProfileModel.getProfile_pic();
                    Picasso.get().load(userProfilePath +userProfile).placeholder(R.drawable.profile_pic_place_holder)// Place holder image from drawable folder
                            .error(R.drawable.profile_pic_place_holder)
                            .into(imageViewProfilePic);

                    editor.putString("profilePic", userProfile).apply();
                    Toast.makeText(StudentProfile.this, "Profile Pic Uploaded Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    hideDialog();
                    Toast.makeText(StudentProfile.this, "Not uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                hideDialog();
                Toast.makeText(StudentProfile.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void CheckedUpdatedImages() {
        pDialog.setTitle("Loading User Profile");
        showDialog();

        Call<UpdateProfileModel> topicsCall = Api_Client.getInstance().CheckProfileImage(studentId);
        topicsCall.enqueue(new Callback<UpdateProfileModel>() {

            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    UpdateProfileModel updateProfileModel = response.body();

                    userProfile = updateProfileModel.getProfile_pic();

                    Picasso.get().load(userProfilePath + userProfile).placeholder(R.drawable.profile_pic_place_holder)// Place holder image from drawable folder
                            .error(R.drawable.profile_pic_place_holder)
                            .into(imageViewProfilePic);

                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                hideDialog();
                Toast.makeText(StudentProfile.this, "network failure: Please check your Internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }


    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfile.this);
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
                  //  Toast.makeText(this, user_dp, Toast.LENGTH_SHORT).show();
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




}
