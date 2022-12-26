package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.HistoryModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HistoryViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<HistoryModel>> history = new MutableLiveData<>();
    public LiveData<ResponseWrapper<HistoryModel>> _history = history;

    public void getHistoryData(String token){

        history.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .history(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HistoryModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        history.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(HistoryModel model) {
                        history.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}


