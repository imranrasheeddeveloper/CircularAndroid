package com.lasgcircular.softcitygroup.remote;


import com.lasgcircular.softcitygroup.model.DashboardModel;
import com.lasgcircular.softcitygroup.model.GetAppContentModel;
import com.lasgcircular.softcitygroup.model.HistoryModel;
import com.lasgcircular.softcitygroup.model.LoginModel;
import com.lasgcircular.softcitygroup.model.PasswordUpdateModel;
import com.lasgcircular.softcitygroup.model.RegisterModel;
import com.lasgcircular.softcitygroup.model.ResetModel;
import com.lasgcircular.softcitygroup.model.create_preference.CreatePreferencesModel;
import com.lasgcircular.softcitygroup.model.data_points.DataPointsModel;
import com.lasgcircular.softcitygroup.model.notifications.NotificationModel;
import com.lasgcircular.softcitygroup.model.notifications.NotificationReadModel;
import com.lasgcircular.softcitygroup.model.report_data.ReportDataModel;
import com.lasgcircular.softcitygroup.utils.RetrofitInstanceProvider;
import com.google.gson.JsonObject;


import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * this class takes ApiService to call api methods
 * that are declared in ApiService interface
 */
public class RemoteRepository {

    private static final String TAG = "RemoteRepository";

    //for providing singleton instance
    private static RemoteRepository _instance = null;
    private final ApiService mService;

    private RemoteRepository(ApiService service) {
        mService = service;
    }

    public static RemoteRepository getInstance() {
        if (_instance == null) {
            return _instance = new RemoteRepository(
                    RetrofitInstanceProvider.getInstance().getApiService());
        } else {
            return _instance;
        }
    }

    public Observable<LoginModel> login(String email , String password , String device_key) {

        if (mService != null) {
            return mService.loginUser(email,password,device_key);
        }
        return null;
    }

    public Observable<RegisterModel> register(String firstname, String lastname, String email, String phone, String pass,String device_key) {
        if (mService != null) {
            return mService.registerUser(firstname,lastname,email,phone,pass,device_key);
        }
        return null;
    }

    public Observable<ResetModel> resetEmail(String email){
        if (mService != null) {
            return mService.reset(email);
        }
        return null;
    }

    public Observable<DashboardModel> dashboard(String token){
        if (mService != null) {
            return mService.getHomeData(token);
        }
        return null;
    }

    public Observable<HistoryModel> history(String token){
        if (mService != null) {
            return mService.getHistoryData(token);
        }
        return null;
    }

    public Observable<PasswordUpdateModel> update_password(String token,String previous , String current) {

        if (mService != null) {
            return mService.updatePassword(token,previous,current);
        }
        return null;
    }

    public Observable<GetAppContentModel> about() {

        if (mService != null) {
            return mService.getAboutData();
        }
        return null;
    }

    public Observable<GetAppContentModel> terms() {

        if (mService != null) {
            return mService.getTermsData();
        }
        return null;
    }

    public Observable<LoginModel> updateUserData(String token,String first_name , String last_name, String phone, MultipartBody.Part part, String email) {

        if (mService != null) {
            return mService.update(token,first_name,last_name,phone,part,email);
        }
        return null;
    }

    public Observable<DataPointsModel> dataPoints(String token){
        if (mService != null) {
            return mService.getDataPoints(token);
        }
        return null;
    }

    public Observable<CreatePreferencesModel> preferences(String token, String name, List<Integer> selected_data_points){
        if (mService != null) {
            return mService.createPreference(token,name,selected_data_points);
        }
        return null;
    }

    public Observable<ReportDataModel> report_data(String token, JsonObject object) {
        if (mService != null) {
            return mService.report(token,object);
        }
        return null;
    }

    public Observable<NotificationModel> notifications(String token) {
        if (mService != null) {
            return mService.get_notifications(token);
        }
        return null;
    }

    public Observable<NotificationReadModel> read_notification(String token, int id) {
        if (mService != null) {
            return mService.read_notification(token,id);
        }
        return null;
    }
}
