package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.DashboardModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<DashboardModel>> dashboard = new MutableLiveData<>();
    public LiveData<ResponseWrapper<DashboardModel>> _dashboard = dashboard;

    public void getDashboardData(String token){

        dashboard.setValue(
                new ResponseWrapper<>(
                        true, "", null
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
                        dashboard.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(DashboardModel model) {
                        dashboard.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}

