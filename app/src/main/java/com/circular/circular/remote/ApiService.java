package com.circular.circular.remote;


import com.circular.circular.model.DashboardModel;
import com.circular.circular.model.GetAppContentModel;
import com.circular.circular.model.HistoryModel;
import com.circular.circular.model.LoginModel;
import com.circular.circular.model.PasswordUpdateModel;
import com.circular.circular.model.RegisterModel;
import com.circular.circular.model.ResetModel;
import com.circular.circular.model.create_preference.CreatePreferencesModel;
import com.circular.circular.model.data_points.DataPointsModel;
import com.circular.circular.model.notifications.NotificationModel;
import com.circular.circular.model.notifications.NotificationReadModel;
import com.circular.circular.model.report_data.ReportDataModel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface ApiService {


    @FormUrlEncoded
    @POST("auth/login")
    Observable<LoginModel> loginUser(@Field("email") String email,
                                     @Field("password") String password,
                                     @Field("device_key") String device_key);

    @FormUrlEncoded
    @POST("auth/signup")
    Observable<RegisterModel> registerUser(@Field("name") String firstname,
                                           @Field("last_name") String lastname,
                                           @Field("email") String email,
                                           @Field("phone") String phone,
                                           @Field("password") String pass,
                                           @Field("device_key") String device_key);


    @FormUrlEncoded
    @POST("auth/update_password")
    Observable<PasswordUpdateModel> updatePassword(@Header("Authorization") String token,
                                                   @Field("current_password") String current_password,
                                                   @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("auth/reset")
    Observable<ResetModel> reset(@Field("email") String email);

    @GET("dashboard")
    Observable<DashboardModel> getHomeData(@Header("Authorization") String token);

    @GET("data/history")
    Observable<HistoryModel> getHistoryData(@Header("Authorization") String token);

    @GET("content/about_us")
    Observable<GetAppContentModel> getAboutData();

    @GET("content/terms")
    Observable<GetAppContentModel> getTermsData();


    @Multipart
    @POST("profile/update")
    Observable<LoginModel> update(@Header("Authorization") String token,
                                  @Part("name") String firstname,
                                  @Part("last_name") String lastname,
                                  @Part("phone") String phone,
                                  @Part MultipartBody.Part filePart,
                                     @Part("email") String email);

    @GET("reporting/data_points")
    Observable<DataPointsModel> getDataPoints(@Header("Authorization") String token);


    @FormUrlEncoded
    @POST("reporting/data_preference/create")
    Observable<CreatePreferencesModel> createPreference(@Header("Authorization") String token,
                                                        @Field("name") String user_name,
                                                        @Field("data_point_ids[]") List<Integer> selected_data_points);


    @POST("report/data")
    Observable<ReportDataModel> report(@Header("Authorization") String token,
                                       @Body JsonObject object);

    @GET("notifications")
    Observable<NotificationModel> get_notifications(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("notification")
    Observable<NotificationReadModel> read_notification(@Header("Authorization") String token,
                                                        @Field("notification_id") int notification_id);
}
