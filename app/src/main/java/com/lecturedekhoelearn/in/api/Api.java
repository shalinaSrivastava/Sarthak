package com.lecturedekhoelearn.in.api;

import com.lecturedekhoelearn.in.model.ActiveVideoModel;
import com.lecturedekhoelearn.in.model.AllNotificationModel;
import com.lecturedekhoelearn.in.model.AllVideosModel;
import com.lecturedekhoelearn.in.model.Allnews;
import com.lecturedekhoelearn.in.model.BookMarkModel;
import com.lecturedekhoelearn.in.model.BookmarkDetailsModel;
import com.lecturedekhoelearn.in.model.BuyPackageModel;
import com.lecturedekhoelearn.in.model.CategoryModel;
import com.lecturedekhoelearn.in.model.ChangedMobileNumber;
import com.lecturedekhoelearn.in.model.CityModel;
import com.lecturedekhoelearn.in.model.ClassModel;
import com.lecturedekhoelearn.in.model.CoutnryModel;
import com.lecturedekhoelearn.in.model.DailyQuizModel;
import com.lecturedekhoelearn.in.model.DailyQuizSubmitModel;
import com.lecturedekhoelearn.in.model.EditProfileModel;
import com.lecturedekhoelearn.in.model.FirebaseModel;
import com.lecturedekhoelearn.in.model.FirstTimeTestModel;
import com.lecturedekhoelearn.in.model.FreePackagesModel;
import com.lecturedekhoelearn.in.model.LevelTypeModel;
import com.lecturedekhoelearn.in.model.LoginUserModel;
import com.lecturedekhoelearn.in.model.MotivationalVideos;
import com.lecturedekhoelearn.in.model.MyPurchasedTestSeriesModel;
import com.lecturedekhoelearn.in.model.PackageCoupon;
import com.lecturedekhoelearn.in.model.PackageVideoModel;
import com.lecturedekhoelearn.in.model.PaymentDetailModel;
import com.lecturedekhoelearn.in.model.PaymentHistroyModel;
import com.lecturedekhoelearn.in.model.RegisterModel;
import com.lecturedekhoelearn.in.model.ResultSubmitModel;
import com.lecturedekhoelearn.in.model.ScholarShipModel;
import com.lecturedekhoelearn.in.model.ScholarshipResultModel;
import com.lecturedekhoelearn.in.model.SearchModel;
import com.lecturedekhoelearn.in.model.StateModel;
import com.lecturedekhoelearn.in.model.StuAnalysisAnByParentModel;
import com.lecturedekhoelearn.in.model.StudentAnalysisModel;
import com.lecturedekhoelearn.in.model.StudentBuyPacModel;
import com.lecturedekhoelearn.in.model.SubjectModel;
import com.lecturedekhoelearn.in.model.SubjectTeacherModel;
import com.lecturedekhoelearn.in.model.TeacherListModel;
import com.lecturedekhoelearn.in.model.TeacherModel.StudentListModel;
import com.lecturedekhoelearn.in.model.TeacherModel.TeacherLoginModel;
import com.lecturedekhoelearn.in.model.TeacherRatingModel;
import com.lecturedekhoelearn.in.model.TestInsideVideoModel;
import com.lecturedekhoelearn.in.model.Testj;
import com.lecturedekhoelearn.in.model.TopicModel;
import com.lecturedekhoelearn.in.model.TopicWiseVideoModel;
import com.lecturedekhoelearn.in.model.UpdateProfileModel;
import com.lecturedekhoelearn.in.model.UploadScholarResult;
import com.lecturedekhoelearn.in.model.UserProfileModel;
import com.lecturedekhoelearn.in.model.ValidReffrealModel;
import com.lecturedekhoelearn.in.model.VideoBookmarkModel;
import com.lecturedekhoelearn.in.model.VideoDeailsBookModel;
import com.lecturedekhoelearn.in.model.WalletModel;
import com.lecturedekhoelearn.in.model.parentModel.ParentLoginModel;
import com.lecturedekhoelearn.in.model.parentModel.ParentRegModel;
import com.lecturedekhoelearn.in.model.queryModel.AllQuery;
import com.lecturedekhoelearn.in.model.queryModel.Query;
import com.lecturedekhoelearn.in.model.queryModel.QueryDescussion;
import com.lecturedekhoelearn.in.model.queryModel.QueryReply;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    //----------------------------------------login Api---------------------------------------------


    //---------------------------------------for Student--------------------------------------------
    @FormUrlEncoded
    @POST("login")
    Call<LoginUserModel> getLoginUser(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("device_id") String device_id);


    //---------------------------------------For Teacher--------------------------------------------

    @FormUrlEncoded
    @POST("teacher_login")
    Call<TeacherLoginModel> getTeacherUser(@Field("email") String email,
                                           @Field("password") String password);


    //---------------------------------------For ParentApi--------------------------------------------


    @FormUrlEncoded
    @POST("parent_login")
    Call<ParentLoginModel> getParentUser(@Field("email") String email,
                                         @Field("password") String password);

    //----------------------------------------login Api---------------------------------------------


    //-----------------------------------Register Api-----------------------------------------------

    @GET("level_type")
    Call<LevelTypeModel> getLevelType();


    @FormUrlEncoded
    @POST("category_type")
    Call<CategoryModel> getCategoryType(@Field("level_id") String level_id);


    @FormUrlEncoded
    @POST("classes")
    Call<ClassModel> getClasses(@Field("category_id") String CategoryId);


    @FormUrlEncoded
    @POST("registers")
    Call<RegisterModel> getRegister(@Field("level_id") String level_Id,
                                    @Field("type_id") String type_id,
                                    @Field("class_id") String class_id,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("mobile") String mobile,
                                    @Field("password") String password,
                                    @Field("address") String address,
                                    @Field("gender") String gender,
                                    @Field("refer_by") String referby,
                                    @Field("country") String country,
                                    @Field("state") String state,
                                    @Field("city") String city,
                                    @Field("pincode") String pincode,
                                    @Field("device_id") String device_id,
                                    @Field("dob") String dob);


    @FormUrlEncoded
    @POST("parent_register")
    Call<ParentRegModel> getParentRegister(@Field("level_id") String level_Id,
                                           @Field("category_id") String type_id,
                                           @Field("student_email") String student_email,
                                           @Field("name") String name,
                                           @Field("email") String email,
                                           @Field("mobile") String mobile,
                                           @Field("password") String password);


    //-----------------------------------Register Api-----------------------------------------------


    //---------------------------------Dashboard Api------------------------------------------------

    //Get Free video Api
    @FormUrlEncoded
    @POST("freevideo_in_series")
    Call<AllVideosModel> getAllVideos(@Field("course_id") String courseID);


    //Get News  Api
    @GET("news")
    Call<Allnews> getNews();

    //Notification
    @GET("offline_notification")
    Call<AllNotificationModel> getNotification();


    //-----------------------------------------Query apis-------------------------------------------
    @FormUrlEncoded
    @POST("storequerys")
    Call<Query> getQuery(@Field("student_id") String user_id,
                         @Field("title") String title,
                         @Field("teacher_id") String teacher_id,
                         @Field("subject_id") String subject_id,
                         @Field("desc") String des,
                         @Field("document") String document);

    @FormUrlEncoded
    @POST("showquerys")
    Call<AllQuery> getAllQuery(@Field("student_id") String id, @Field("teacher_id") String teacher_id);


    @FormUrlEncoded
    @POST("showqueryreplys")
    Call<QueryDescussion> getQueryDec(@Field("query_id") String query_id);

    @FormUrlEncoded
    @POST("replyquerys")
    Call<QueryReply> getDiscussion(@Field("student_id") String user_id,
                                   @Field("query_id") String query_id,
                                   @Field("reply") String descus);


    //----------------------------------Query apis--------------------------------------------------


    @FormUrlEncoded
    @POST("userprofile")
    Call<UserProfileModel> getProfile(@Field("student_id") String student_id);


    @FormUrlEncoded
    @POST("class_wise_subject")
    Call<SubjectModel> getSubject(@Field("class_id") String classId);

    @FormUrlEncoded
    @POST("subject_wise_topics")
    Call<TopicModel> getAllTopics(@Field("class_id") String classId,
                                  @Field("subject_id") String subject_id);


    @FormUrlEncoded
    @POST("packages")
    Call<BuyPackageModel> forBuyAllPacakge(@Field("student_id") String student_id,
                                           @Field("class_id") String classID);

    @FormUrlEncoded
    @POST("packages")
    Call<MyPurchasedTestSeriesModel> getMyTestSeries(@Field("student_id") String student_id,
                                                     @Field("class_id") String classID);


    //Signup time get Otp
    @FormUrlEncoded
    @POST("signup_otp")
    Call<ChangedMobileNumber> getOtp(@Field("mobile") String mobileNumber,@Field("token") String token);

    //Signup time get Otp
    @FormUrlEncoded
    @POST("child_register")
    Call<ChangedMobileNumber> getchild(@Field("mobile") String mobileNumber);


    //Signup time get Otp
    @FormUrlEncoded
    @POST("signup_pchild")
    Call<ChangedMobileNumber> getOtp(@Field("child_mobile") String mobileNumber);


    //Signup time get Otp
    @FormUrlEncoded
    @POST("otp")
    Call<ChangedMobileNumber> getForgotOtp(@Field("mobile") String mobileNumber,@Field("token") String token);

    //Change Password APi by User
    @FormUrlEncoded
    @POST("forget_password")
    Call<ChangedMobileNumber> updateMobileNumber(@Field("mobile") String mobileNumber,
                                                 @Field("password") String passowrd,
                                                 @Field("device_id") String DeviceID);

    // Dashboard api
    @GET("motivational")
    Call<MotivationalVideos> getMotivationalVideo();


    // Dashboard api

    //teacher list shows api
    @FormUrlEncoded
    @POST("show_teacher")
    Call<TeacherListModel> getTeacherList(@Field("class_id") String class_id);


    //teacher Rate us
    @FormUrlEncoded
    @POST("teacher_rate_us")
    Call<TeacherRatingModel> updateRateUS(@Field("teacher_id") String teacher_id,
                                          @Field("rate_us") String rate_us,
                                          @Field("student_id") String student_id);


    //Daily quiz Api
    @FormUrlEncoded
    @POST("daily_gk_quiz")
    Call<DailyQuizModel> getDailyQuiz(@Field("class_id") String class_id,
                                      @Field("student_id") String student_id,
                                      @Field("current_date") String current_date);

    //Daily quizz submittion Api
    @FormUrlEncoded
    @POST("daily_quiz_submit")
    Call<DailyQuizSubmitModel> DailyQuizSubmit(@Field("student_id") String student_id,
                                               @Field("question_id") String question_id,
                                               @Field("answer_a") String answer_a,
                                               @Field("answer_b") String answer_b,
                                               @Field("answer_c") String answer_c,
                                               @Field("answer_d") String answer_d,
                                               @Field("time_taken") String time_taken);

    //Test inside video
    @FormUrlEncoded
    @POST("test_in_video")
    Call<TestInsideVideoModel> getTestWithVideoID(@Field("video_id") String video_id);

    @FormUrlEncoded
    @POST("mcq_test")
    Call<TestInsideVideoModel> getTestmcq_test(@Field("topic_id") String video_id);


    //Video  inside package
    @FormUrlEncoded
    @POST("video_in_package")
    Call<PackageVideoModel> getVideoInsidePackage(@Field("package_id") String package_id);


    @FormUrlEncoded
    @POST("wallet")
    Call<WalletModel> getWalletMoneydata(@Field("student_id") String student_id);


    @FormUrlEncoded
    @POST("topic_wise_free_video")
    Call<TopicWiseVideoModel> getTopicWiseVideos(@Field("class_id") String class_id, @Field("topic_id") String topic_id, @Field("subject_id") String subject_id, @Field("student_id") String student_id);


    @FormUrlEncoded
    @POST("student_list_for_teacher")
    Call<StudentListModel> getStudentList(@Field("class_id") String class_id);


    //Test section
    @FormUrlEncoded
    @POST("first_test_analysis_entrys")
    Call<FirstTimeTestModel> sendStartTestAnalysis(@Field("series_id") String series_id,
                                                   @Field("test_id") String test_id,
                                                   @Field("student_id") String Student_id,
                                                   @Field("total_questions") String total_question);

    //TEST Api
    @FormUrlEncoded
    @POST("question_in_tests")
    Call<Testj> getQuestionLoad(@Field("test_id") String test_id);

    //TestAnalysis
    @FormUrlEncoded
    @POST("student_test_analysiss")
    Call<StudentAnalysisModel> getStudentAnalysis(@Field("test_series_id") String testSeriesId,
                                                  @Field("test_id") String testID,
                                                  @Field("student_id") String StudentId);

    @FormUrlEncoded
    @POST("test_results")
    Call<ResultSubmitModel> submitTestResult(@Field("test_session_id") String session_id,
                                             @Field("student_id") String studentID,
                                             @Field("test_series_id") String testSeriesId,
                                             @Field("test_id") String testID,
                                             @Field("question_id") String question_id,
                                             @Field("answer_a") String ans_a,
                                             @Field("answer_b") String ans_b,
                                             @Field("answer_c") String ans_c,
                                             @Field("answer_d") String ans_d,
                                             @Field("total_time_taken") String totalTimeTaken,
                                             @Field("time_taken") String time_taken);


    //question Bookmark api
    @FormUrlEncoded
    @POST("test_bookmarks")
    Call<BookMarkModel> insertBookMark(@Field("student_id") String studentId,
                                       @Field("test_id") String test_id,
                                       @Field("question_id") String questionId);


    //Show question Bookmark api
    @FormUrlEncoded
    @POST("view_question_bookmark")
    Call<BookmarkDetailsModel> getAllBookmarkApi(@Field("student_id") String studentId);


    //video bookmark api
    @FormUrlEncoded
    @POST("video_bookmarks")
    Call<VideoBookmarkModel> insetVideoBookmark(@Field("student_id") String studentId,
                                                @Field("video_id") String videoID);

    //Show video bookmark api
    @FormUrlEncoded
    @POST("view_video_bookmark")
    Call<VideoDeailsBookModel> getBookmarkVideoList(@Field("student_id") String studentId);

    //Show video bookmark api
    @FormUrlEncoded
    @POST("dashboard_active_video")
    Call<ActiveVideoModel> getActiveVideo(@Field("student_id") String studentId);


    //Show video bookmark api
    @FormUrlEncoded
    @POST("editProfile")
    Call<EditProfileModel> EditProfile(@Field("student_id") String studentId, @Field("name") String name, @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("free_packages")
    Call<FreePackagesModel> getFreePACKAGES(@Field("student_id") String studentId, @Field("class_id") String class_id);


    @FormUrlEncoded
    @POST("payment_coupon")
    Call<PackageCoupon> applyCoupon(@Field("coupon") String coupon);

    // Notification section
    @FormUrlEncoded
    @POST("insert_firebase_token")
    Call<FirebaseModel> updatetoken(@Field("class_id") String courseId,
                                    @Field("student_id") String StudentId,
                                    @Field("token_id") String tokenID,
                                    @Field("device_id") String deviceID);


    @FormUrlEncoded
    @POST("valid_refer")
    Call<ValidReffrealModel> CheckReffrreal(@Field("refer_by") String reffreal);

    @FormUrlEncoded
    @POST("payment_history")
    Call<PaymentHistroyModel> getOrderHistory(@Field("student_id") String student_id);


    @FormUrlEncoded
    @POST("subject_wise_teacher")
    Call<SubjectTeacherModel> getSubjectTeacherList(@Field("subject_id") String subject_id);


    @FormUrlEncoded
    @POST("update_profile_pic")
    Call<UpdateProfileModel> updateProfile(@Field("student_id") String student_id, @Field("profile_pic") String file);


    @FormUrlEncoded
    @POST("profile_pic")
    Call<UpdateProfileModel> CheckProfileImage(@Field("student_id") String student_id);


    @FormUrlEncoded
    @POST("global_search")
    Call<SearchModel> SearchCAll(@Field("search") String serachKey, @Field("class_id") String classId);


    @GET("schoolarship")
    Call<ScholarShipModel> getScholarship();

    @FormUrlEncoded
    @POST("store_result_schoolarship")
    Call<UploadScholarResult> uploadScholarResult(@Field("student_id") String student_id, @Field("scholar_id") String scholar_id, @Field("result") String result);


    @GET("country")
    Call<CoutnryModel> getCountry();


    @FormUrlEncoded
    @POST("country_wise_state")
    Call<StateModel> getAllStates(@Field("country_id") String country_id);


    @FormUrlEncoded
    @POST("state_wise_city")
    Call<CityModel> getAllCities(@Field("state_id") String country_id);


    @FormUrlEncoded
    @POST("show_schoolarship_data")
    Call<ScholarshipResultModel> getAllScholarResult(@Field("student_id") String student_id, @Field("schoolar_id") String schoolar_id);


    @FormUrlEncoded
    @POST("payment_detail")
    Call<PaymentDetailModel> InsertPaymentDetails(@Field("student_id") String student_id,
                                                  @Field("txnid") String txnid,
                                                  @Field("series_id") String series_id,
                                                  @Field("amount") String amount,
                                                  @Field("email") String email,
                                                  @Field("phone") String phone,
                                                  @Field("country") String country,
                                                  @Field("state") String state,
                                                  @Field("address1") String address1,
                                                  @Field("wallet") String wallet);
    @FormUrlEncoded
    @POST("student_purchase_detail_view_by_parent")
    Call<StudentBuyPacModel>  getStudentByPackages(@Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("test_analysis_view_by_parent")
    Call<StuAnalysisAnByParentModel> getStudentAnalysis(@Field("student_id") String student_id);

}