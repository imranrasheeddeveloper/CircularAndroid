package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.DashboardModel;
import com.circular.circular.model.report_data.ReportDataModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReportDataViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<ReportDataModel>> report = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ReportDataModel>> _report = report;

    public void report_data(String token, JsonObject object){

        report.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .report_data(token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportDataModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            report.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(ReportDataModel model) {
                        report.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

