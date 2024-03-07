package com.lecturedekhoelearn.in.login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.activity.MainActivity;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.async.ServerConnector;
import com.lecturedekhoelearn.in.model.CityModel;
import com.lecturedekhoelearn.in.model.CityModelDetails;
import com.lecturedekhoelearn.in.model.CountryDetails;
import com.lecturedekhoelearn.in.model.CoutnryModel;
import com.lecturedekhoelearn.in.model.RegisterModel;
import com.lecturedekhoelearn.in.model.RegisterModelDetails;
import com.lecturedekhoelearn.in.model.StateDetailsModel;
import com.lecturedekhoelearn.in.model.StateModel;
import com.lecturedekhoelearn.in.model.ValidReffrealModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lecturedekhoelearn.in.login.SignupOtpActivity.str_phone;


public class SignupActivity extends AppCompatActivity {

    TextView already_account,et_dob,editCountry, editState, editCity;
    EditText et_name, et_pass, et_mail, editPincode, editAddresss, editrefferal;
    Button button;
    RadioGroup Rgender;
    String str_levelId, str_type_id, str_classId, str_name, str_mail, str_password, straddress;
    String strCountry, strState, strCity, strPincode;
    String gender, Mssage;
    private ProgressDialog pDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String referral;
    String userId;
    String deviceid;
    ImageView btn_reffreal;
    AutoCompleteTextView act_country, act_state, act_city;
    private DatePicker datePicker;
    Calendar calendar;
    int year, month, day;
    DatePickerDialog picker;
    ArrayList<CountryDetails> countryDetailsArrayList;
    ArrayList<StateDetailsModel> stateDetailsModelArrayList;
    ArrayList<CityModelDetails> cityModelDetailsArrayList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();

        picker = new DatePickerDialog(SignupActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        et_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);

        str_levelId = preferences.getString("level_id", "");
        str_type_id = preferences.getString("category_id", "");
        str_classId = preferences.getString("class_id", "");
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        already_account = findViewById(R.id.already_account);
        et_name = (EditText) findViewById(R.id.editName);
        et_pass = (EditText) findViewById(R.id.editpass);
        et_mail = (EditText) findViewById(R.id.editemail);
        editAddresss = (EditText) findViewById(R.id.editAddresss);
        editrefferal = (EditText) findViewById(R.id.editrefferal);
        editCountry =  findViewById(R.id.editCountry);
        editState =  findViewById(R.id.editState);
        editCity =  findViewById(R.id.editCity);
        editPincode = (EditText) findViewById(R.id.editPincode);
        btn_reffreal = (ImageView) findViewById(R.id.btn_reffreal);
        et_dob=findViewById(R.id.editdob);
        button = (Button) findViewById(R.id.btn_register);
        Rgender = findViewById(R.id.radio_group);
        act_country = findViewById(R.id.act_country);
        act_state = findViewById(R.id.act_state);
        act_city = findViewById(R.id.act_city);

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }
        });

        editPincode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if(cs.length()>5){
                    showDialog();
                    getGeodetails(cs.toString());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        // getAllCountry();

        deviceid = Settings.Secure.getString(getApplication().getContentResolver(), Settings.Secure.ANDROID_ID);

        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        et_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_pass.getTransformationMethod().getClass().getSimpleName().equals("PasswordTransformationMethod")) {
                    et_pass.setTransformationMethod(new SingleLineTransformationMethod());
                } else {
                    et_pass.setTransformationMethod(new PasswordTransformationMethod());
                }

                et_pass.setSelection(et_pass.getText().length());
            }
        });


        Rgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton Id which User select
                RadioButton rb = (RadioButton) findViewById(checkedId);
                gender = rb.getText().toString();

                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_mail = et_mail.getText().toString().trim();
                str_password = et_pass.getText().toString().trim();
                str_name = et_name.getText().toString().trim();
                straddress = editAddresss.getText().toString().trim();
                referral = editrefferal.getText().toString().trim();
                strCountry = editCountry.getText().toString().trim();
                strState = editState.getText().toString().trim();
                strCity = editCity.getText().toString().trim();
                strPincode = editPincode.getText().toString().trim();

                if (gender == null) {
                    Toast.makeText(SignupActivity.this, "Please Select your gender ", Toast.LENGTH_SHORT).show();

                } else if (str_password.length() <= 6 && str_password != null) {
                    et_pass.setError("Password must be 7 digit");
                    Toast.makeText(SignupActivity.this, "Password must be 7 digit", Toast.LENGTH_SHORT).show();

                } else if (straddress.length() < 3 && straddress != null) {
                    editAddresss.setError("Please enter your Address");
                    Toast.makeText(SignupActivity.this, "Please enter your Address", Toast.LENGTH_SHORT).show();

                } else if (isEmail(et_mail) == false) {
                    et_mail.setError("Please enter your email");
                    Toast.makeText(SignupActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();

                } else if (str_name.trim().equals("")) {
                    et_name.setError("Please enter your name");
                    Toast.makeText(SignupActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();

                }
                else if (strPincode != null && strPincode.length() != 6) {
                    editPincode.setError("Please fill City Pin-code");
                    Toast.makeText(SignupActivity.this, "Please fill City Pin-code", Toast.LENGTH_SHORT).show();

                } else {
                    getRegister();

                }
            }


        });

        btn_reffreal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referral = editrefferal.getText().toString().trim();

                if (referral.trim().equals("")) {

                    editrefferal.setError("Please fill Referral");

                } else {

                    ValidReffreal();
                }
            }
        });
    }

    public void getRegister() {
        pDialog.setMessage("Registering...");
        showDialog();
        Call<RegisterModel> userCall = Api_Client.getInstance().getRegister(str_levelId, str_type_id, str_classId, str_name, str_mail, str_phone, str_password, straddress, gender, referral,strCountry, strState, strCity, strPincode, deviceid,et_dob.getText().toString());
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {

                if (response.body().getStatus().equals(0)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    userId = response.body().getUserId();
                    RegisterModelDetails registerModelDetails = response.body().getRegisterModelDetails();
                    editor.putString("studentId", userId).apply();
                    editor.putString("name", registerModelDetails.getName()).apply();
                    editor.putString("email", registerModelDetails.getEmail()).apply();
                    editor.putString("class_id", registerModelDetails.getClass_id()).apply();
                    editor.putString("refer", registerModelDetails.getRefer_code()).apply();
                    editor.putString("mobile", registerModelDetails.getMobile()).apply();
                    editor.putString("loginType", "Student").apply();

                    showRegister();

                } else if (response.body().getStatus().equals(1)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();
                } else if (response.body().getStatus().equals(2)) {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();
                } else {
                    hideDialog();
                    Mssage = response.body().getMsg();
                    showRegisterUnsucess();

                }

            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
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

    boolean isEmail(EditText editText) {
        CharSequence email = editText.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    private void showRegister() {
        final android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(SignupActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(SignupActivity.this);
        }
        builder.setTitle("Register Successful")
                .setMessage(Mssage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logoutIntent = new Intent(SignupActivity.this, MainActivity.class);
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                        overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                })
                .setIcon(R.drawable.ic_done_all_black_24dp)
                .show();
    }


    private void showRegisterUnsucess() {
        final android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(SignupActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(SignupActivity.this);
        }
        builder.setTitle("Register Unsuccessful ")
                .setMessage(Mssage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void ValidReffreal() {
        pDialog.setMessage("Checking...");
        showDialog();
        Call<ValidReffrealModel> userCall = Api_Client.getInstance().CheckReffrreal(referral);
        userCall.enqueue(new Callback<ValidReffrealModel>() {
            @Override
            public void onResponse(Call<ValidReffrealModel> call, Response<ValidReffrealModel> response) {

                if (response.body().getStatus().equals(1)) {
                    hideDialog();

                    Toast.makeText(SignupActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    hideDialog();
                    Toast.makeText(SignupActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValidReffrealModel> call, Throwable t) {
                hideDialog();
            }
        });

    }


    private void getAllCountry() {

        Call<CoutnryModel> subjectModelCall = Api_Client.getInstance().getCountry();
        subjectModelCall.enqueue(new Callback<CoutnryModel>() {
            @Override
            public void onResponse(@NotNull Call<CoutnryModel> call, @NotNull Response<CoutnryModel> response) {
                assert response.body() != null;
                if (Objects.requireNonNull(response.body()).getStatus().equals(1)) {
                    CoutnryModel subjectModel = response.body();
                    countryDetailsArrayList = (ArrayList<CountryDetails>) subjectModel.getCountryDetails();

                    ArrayList<String> countryName = new ArrayList<String>();
                    if (!countryDetailsArrayList.isEmpty() && countryDetailsArrayList.size() > 0) {
                        for (int i = 0; i < countryDetailsArrayList.size(); i++) {
                            countryName.add(countryDetailsArrayList.get(i).getCountry());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this, R.layout.layout_country, R.id.text_view_list_item, countryName);
                        act_country.setAdapter(adapter);

                        act_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String itemName = adapter.getItem(i);
                                for (CountryDetails pojo : countryDetailsArrayList) {
                                    if (pojo.getCountry().equals(itemName)) {
                                        String id = pojo.getId(); // This is the correct ID
                                        strCountry=pojo.getCountry();
                                     //   Toast.makeText(SignupActivity.this, id, Toast.LENGTH_SHORT).show();
                                        getAllState(id);
                                        break; // No need to keep looping once you found it.
                                    }
                                }
                            }

                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<CoutnryModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


    private void getAllState(String Id) {

        Call<StateModel> stateModelCall = Api_Client.getInstance().getAllStates(Id);
        stateModelCall.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                if (response.body().getStatus().equals(1)) {
                    StateModel stateModel = response.body();
                    stateDetailsModelArrayList = (ArrayList<StateDetailsModel>) stateModel.getStateDetailsModelts();

                    ArrayList<String> stateName = new ArrayList<String>();
                    if (!stateDetailsModelArrayList.isEmpty() && stateDetailsModelArrayList.size() > 0) {
                        for (int i = 0; i < stateDetailsModelArrayList.size(); i++) {
                            stateName.add(stateDetailsModelArrayList.get(i).getState());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this, R.layout.layout_state, R.id.text_l_state, stateName);
                        act_state.setAdapter(adapter);


                        act_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                String itemName = adapter.getItem(i);
                                for (StateDetailsModel pojo : stateDetailsModelArrayList) {
                                    if (pojo.getState().equals(itemName)) {
                                        String id = pojo.getId(); // This is the correct ID
                                        strState=pojo.getState();
                                     //   Toast.makeText(SignupActivity.this, id, Toast.LENGTH_SHORT).show();
                                        getAllcity(id);
                                        break; // No need to keep looping once you found it.
                                    }
                                }


                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


    private void getAllcity(String Id) {

        Call<CityModel> stateModelCall = Api_Client.getInstance().getAllCities(Id);
        stateModelCall.enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                if (response.body().getStatus().equals(1)) {
                    CityModel stateModel = response.body();
                    cityModelDetailsArrayList = (ArrayList<CityModelDetails>) stateModel.getCityModelDetails();

                    ArrayList<String> stateName = new ArrayList<String>();
                    if (!cityModelDetailsArrayList.isEmpty() && stateDetailsModelArrayList.size() > 0) {
                        for (int i = 0; i < cityModelDetailsArrayList.size(); i++) {
                            stateName.add(cityModelDetailsArrayList.get(i).getCity());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this, R.layout.layout_state, R.id.text_l_state, stateName);
                        act_city.setAdapter(adapter);


                        act_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                String itemName = adapter.getItem(i);
                                for (CityModelDetails pojo : cityModelDetailsArrayList) {
                                    if (pojo.getCity().equals(itemName)) {
                                        String id = pojo.getId(); // This is the correct ID

                                        strCity=pojo.getCity();
                                       // Toast.makeText(SignupActivity.this, id, Toast.LENGTH_SHORT).show();
                                        break; // No need to keep looping once you found it.
                                    }
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    private void getGeodetails(String ss) {
        String urlParameters = "https://postalpincode.in/api/pincode/"+ss;
        final String status = "";
        try {
            urlParameters = "";
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ServerConnector loginConnection = new ServerConnector(urlParameters);
        loginConnection.setDataDowmloadListner(new ServerConnector.onAsyncTaskComplete() {
            @Override
            public void OnResponse(String response) {
                hideDialog();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    JSONObject jsonObject1=jsonarray.getJSONObject(0);
                    String Code = jsonObject1.getString("Status");

                    if (Code.equals("Success")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("PostOffice");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            editCity.setText(jsonObject.getString("District"));
                            editCountry.setText(jsonObject.getString("Country"));
                            editState.setText(jsonObject.getString("State"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loginConnection.execute("https://api.postalpincode.in/pincode/"+ss);
        loginConnection.setIsget(true);
    }




}


