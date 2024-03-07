package com.lecturedekhoelearn.in.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.lecturedekhoelearn.in.util.HashGenerationUtils;
import com.payu.base.models.ErrorResponse;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lecturedekhoelearn.in.Constant;
import com.lecturedekhoelearn.in.R;
import com.lecturedekhoelearn.in.api.Api_Client;
import com.lecturedekhoelearn.in.model.PaymentDetailModel;
import com.payu.base.models.PayUPaymentParams;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;


import java.security.Key;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartPaymentActivity extends AppCompatActivity {

  //  PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
  PayUPaymentParams  paymentParam = null;

  PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();

    String TAG = "mainActivity", txnid = "txt12378", amount = "20", phone = "8284804227",
            prodname = "BlueApp Course", firstname = "Rakesh", email = "rakeshjaiswal680@gmail.com",
            merchantId = "5279796", merchantkey = "bGYpLA";//"bGYpLA";  //   first test key only


    String random_string;
    String packageId;
    String StudentId;
    String afterwalletuse;
    String WalletAmount;
    String CouponCode;
    String name;
    String mobile_number;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ProgressDialog pDialog;
    char[] chars1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpayment);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Please Wait..");
        pDialog.setCancelable(false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        preferences = getApplicationContext().getSharedPreferences(Constant.myprif, MODE_PRIVATE);
        editor = preferences.edit();
        StudentId = preferences.getString("studentId", "");
        packageId = preferences.getString("packageId", "");
        afterwalletuse = preferences.getString("afterwalletuse", "");
        WalletAmount = preferences.getString("WalletAmount", "");
        CouponCode = preferences.getString("CouponCode", "");
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        mobile_number = preferences.getString("mobile", "");
        chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        random_string = sb1.toString();


        startpay(afterwalletuse, random_string, mobile_number, "Lecture Dekho", name, email);
    }

    public void startpay(String amount, String txnid, String phone, String prodname, String firstname, String email) {
        HashMap<String, Object> additionalParams = new HashMap<>();

    /*   String vasForMobileSdkHash = HashGenerationUtils.generateHashFromSDK(
                "bGYpLA|"+PayUCheckoutProConstants.CP_VAS_FOR_MOBILE_SDK+"|"+PayUCheckoutProConstants.CP_DEFAULT+"|","Ig8cQWhg",null
        );
        String paymenRelatedDetailsHash = HashGenerationUtils.generateHashFromSDK(
                "bGYpLA|"+PayUCheckoutProConstants.CP_VAS_FOR_MOBILE_SDK+"|"+PayUCheckoutProConstants.CP_DEFAULT+"|","Ig8cQWhg",null
        );
*/
       // additionalParams.put(PayUCheckoutProConstants.CP_VAS_FOR_MOBILE_SDK, vasForMobileSdkHash);
       // additionalParams.put(PayUCheckoutProConstants.CP_PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK,paymenRelatedDetailsHash);

        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "udf1");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "udf2");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "udf3");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "udf4");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, "udf5");
        builder.setAmount(amount)                          // Payment amount
                .setTransactionId(random_string)                     // Transaction ID
                .setPhone(phone)                   // User Phone number
                .setProductInfo(prodname)                   // Product Name or description
                .setFirstName(firstname)                              // User First name
                .setEmail(email)              // User Email ID
                .setSurl( "https://payu.herokuapp.com/success")     // Success URL (surl)
                .setFurl("https://payu.herokuapp.com/failure")     //Failure URL (furl)
                .setKey(merchantkey)
                .setIsProduction(true)// Merchant key
                .setAdditionalParams(additionalParams)
                .setUserCredential("lecturedekho@gmail.com");

        try {
            paymentParam = builder.build();
            // generateHashFromServer(paymentParam );
            //getHashkey();
            getPayment(paymentParam);


        } catch (Exception e) {
            Log.e(TAG, " error s " + e.toString());
        }
    }

    void getPayment(PayUPaymentParams payUPaymentParams) {

        PayUCheckoutPro.open(this, payUPaymentParams,getCheckoutProConfig() ,new PayUCheckoutProListener() {

            @Override
                    public void onPaymentSuccess(Object response) {
                        //Cast response object to HashMap
                       // HashMap<String, Object> result = (HashMap<String, Object>) response;
                        //String payuResponse = (String) result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        //String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
                        insertPaymentDetails();
                    }

                    @Override
                    public void onPaymentFailure(Object response) {
                        //Cast response object to HashMap
                        HashMap<String, Object> result = (HashMap<String, Object>) response;
                        String payuResponse = (String) result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);

                    }

                    @Override
                    public void onPaymentCancel(boolean isTxnInitiated) {
                       if(isTxnInitiated){
                           //Cast response object to HashMap

                       }
                    }

                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        String hash="";
                        //String salt="Ig8cQWhg";
                        String errorMessage = errorResponse.getErrorMessage();
                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
                        //For setting webview properties, if any. Check Customized Integration section for more details on this
                       // insertPaymentDetails();
            }

            @Override
            public void generateHash(HashMap<String, String> valueMap, PayUHashGenerationListener hashGenerationListener) {
                String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
                String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);
                if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                    //Generate Hash from your backend here
                    String salt = "Ig8cQWhg";
                   // if (valueMap.containsKey(PayUCheckoutProConstants.CP_POST_SALT))
                      //  salt = salt + "" + (valueMap.get(PayUCheckoutProConstants.CP_POST_SALT));


                    String hash = null;
                    if (hashName.equalsIgnoreCase(PayUCheckoutProConstants.CP_LOOKUP_API_HASH)) {
                        //Calculate HmacSHA1 HASH for calculating Lookup API Hash
                        ///Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.

                        hash = calculateHmacSHA1Hash(hashData, "bGYpLA");
                    } else {

                        //Calculate SHA-512 Hash here
                        hash = calculateHash(hashData + salt);
                    }

                    HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put(hashName, hash);
                    hashGenerationListener.onHashGenerated(dataMap);
                }
            }
                }
        );

    }


    private String calculateHash(String hashString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            return getHexString(mdbytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getHexString(byte[] array) {
        StringBuilder hash = new StringBuilder();
        for (byte hashByte : array) {
            hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return hash.toString();
    }


    private String calculateHmacSHA1Hash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = getHexString(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private PayUCheckoutProConfig getCheckoutProConfig() {
        PayUCheckoutProConfig checkoutProConfig = new PayUCheckoutProConfig();
       // checkoutProConfig.setPaymentModesOrder(getCheckoutOrderList());
        //checkoutProConfig.setOfferDetails(getOfferDetailsList());
        // uncomment below code for performing enforcement
//        checkoutProConfig.setEnforcePaymentList(getEnforcePaymentList());
       /* checkoutProConfig.setShowCbToolbar(switchHideCbToolBar.isChecked());
        checkoutProConfig.setAutoSelectOtp(binding.switchAutoSelectOtp.isChecked());
        checkoutProConfig.setAutoApprove(binding.switchAutoApprove.isChecked());
        checkoutProConfig.setSurePayCount(Integer.parseInt(binding.etSurePayCount.getText().toString()));
        checkoutProConfig.setShowExitConfirmationOnPaymentScreen(!binding.switchDiableCBDialog.isChecked());
        checkoutProConfig.setShowExitConfirmationOnCheckoutScreen(!binding.switchDiableUiDialog.isChecked());
      */
        checkoutProConfig.setMerchantName("Lecture Dekho");
        checkoutProConfig.setMerchantLogo(R.mipmap.ic_launcher);
        checkoutProConfig.setWaitingTime(30000);
        checkoutProConfig.setMerchantResponseTimeout(30000);
       // checkoutProConfig.setCustomNoteDetails(getCustomeNoteList());

        return checkoutProConfig;
    }

    public void getHashkey() {
        ServiceWrapper service = new ServiceWrapper(null);
        Call<String> call = service.newHashCall(merchantkey, random_string, afterwalletuse, "Lecture Dekho",
                name, email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "hash res " + response.body());
                String merchantHash = response.body();
                if (merchantHash.isEmpty() || merchantHash.equals("")) {
                    Toast.makeText(StartPaymentActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "hash empty");
                } else {
                    // mPaymentParams.setMerchantHash(merchantHash);
                   // paymentParam.setMerchantHash(merchantHash);
                    // Invoke the following function to open the checkout page.
                    // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this,-1, true);
                   // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this, R.style.AppTheme_default, true);

                  //  getPayment(paymentParam,merchantHash);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "hash error " + t.toString());
            }
        });
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// PayUMoneySdk: Success -- payuResponse{"id":225642,"mode":"CC","status":"success","unmappedstatus":"captured","key":"9yrcMzso","txnid":"223013","transaction_fee":"20.00","amount":"20.00","cardCategory":"domestic","discount":"0.00","addedon":"2018-12-31 09:09:43","productinfo":"a2z shop","firstname":"kamal","email":"kamal.bunkar07@gmail.com","phone":"9144040888","hash":"b22172fcc0ab6dbc0a52925ebbd0297cca6793328a8dd1e61ef510b9545d9c851600fdbdc985960f803412c49e4faa56968b3e70c67fe62eaed7cecacdfdb5b3","field1":"562178","field2":"823386","field3":"2061","field4":"MC","field5":"167227964249","field6":"00","field7":"0","field8":"3DS","field9":" Verification of Secure Hash Failed: E700 -- Approved -- Transaction Successful -- Unable to be determined--E000","payment_source":"payu","PG_TYPE":"AXISPG","bank_ref_no":"562178","ibibo_code":"VISA","error_code":"E000","Error_Message":"No Error","name_on_card":"payu","card_no":"401200XXXXXX1112","is_seamless":1,"surl":"https://www.payumoney.com/sandbox/payment/postBackParam.do","furl":"https://www.payumoney.com/sandbox/payment/postBackParam.do"}
//PayUMoneySdk: Success -- merchantResponse438104
// on successfull txn
        //  request code 10000 resultcode -1
        //tran {"status":0,"message":"payment status for :438104","result":{"postBackParamId":292490,"mihpayid":"225642","paymentId":438104,"mode":"CC","status":"success","unmappedstatus":"captured","key":"9yrcMzso","txnid":"txt12345","amount":"20.00","additionalCharges":"","addedon":"2018-12-31 09:09:43","createdOn":1546227592000,"productinfo":"a2z shop","firstname":"kamal","lastname":"","address1":"","address2":"","city":"","state":"","country":"","zipcode":"","email":"kamal.bunkar07@gmail.com","phone":"9144040888","udf1":"","udf2":"","udf3":"","udf4":"","udf5":"","udf6":"","udf7":"","udf8":"","udf9":"","udf10":"","hash":"0e285d3a1166a1c51b72670ecfc8569645b133611988ad0b9c03df4bf73e6adcca799a3844cd279e934fed7325abc6c7b45b9c57bb15047eb9607fff41b5960e","field1":"562178","field2":"823386","field3":"2061","field4":"MC","field5":"167227964249","field6":"00","field7":"0","field8":"3DS","field9":" Verification of Secure Hash Failed: E700 -- Approved -- Transaction Successful -- Unable to be determined--E000","bank_ref_num":"562178","bankcode":"VISA","error":"E000","error_Message":"No Error","cardToken":"","offer_key":"","offer_type":"","offer_availed":"","pg_ref_no":"","offer_failure_reason":"","name_on_card":"payu","cardnum":"401200XXXXXX1112","cardhash":"This field is no longer supported in postback params.","card_type":"","card_merchant_param":null,"version":"","postUrl":"https:\/\/www.payumoney.com\/mobileapp\/payumoney\/success.php","calledStatus":false,"additional_param":"","amount_split":"{\"PAYU\":\"20.0\"}","discount":"0.00","net_amount_debit":"20","fetchAPI":null,"paisa_mecode":"","meCode":"{\"vpc_Merchant\":\"TESTIBIBOWEB\"}","payuMoneyId":"438104","encryptedPaymentId":null,"id":null,"surl":null,"furl":null,"baseUrl":null,"retryCount":0,"merchantid":null,"payment_source":null,"pg_TYPE":"AXISPG"},"errorCode":null,"responseCode":null}---438104

        // Result Code is -1 send from Payumoney activity
       // Log.e("StartPaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                   InsertPaymentDetails();
                   Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                } else {
                    //Failure Transaction
                    Toast.makeText(this, "Payment Failure", Toast.LENGTH_SHORT).show();
                }
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
               // Log.e(TAG, "tran " + payuResponse + "---" + merchantResponse);
            } /* else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }}}*/



    private void insertPaymentDetails() {
        showDialog();
        Call<PaymentDetailModel> paymentDetailModelCall = Api_Client.getInstance().InsertPaymentDetails(StudentId, random_string, packageId, afterwalletuse, email, mobile_number, "", "", "",WalletAmount);
        paymentDetailModelCall.enqueue(new Callback<PaymentDetailModel>() {
            @Override
            public void onResponse(Call<PaymentDetailModel> call, Response<PaymentDetailModel> response) {
                hideDialog();
                Intent i = new Intent(StartPaymentActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            @Override
            public void onFailure(Call<PaymentDetailModel> call, Throwable t) {
                hideDialog();
              //  Toast.makeText(StartPaymentActivity.this, "Payment Failure", Toast.LENGTH_SHORT).show();
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
