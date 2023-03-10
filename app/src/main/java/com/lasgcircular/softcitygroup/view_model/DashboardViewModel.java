package com.lasgcircular.softcitygroup.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lasgcircular.softcitygroup.model.DashboardModel;
import com.lasgcircular.softcitygroup.remote.RemoteRepository;
import com.lasgcircular.softcitygroup.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<DashboardModel>> dashboard = new MutableLiveData<>();
    public LiveData<ResponseWrapper<DashboardModel>> _dashboard = dashboard;

    public void getDashboardData(String token){

        dashboard.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .dashboard(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DashboardModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            dashboard.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(DashboardModel model) {
                        dashboard.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

