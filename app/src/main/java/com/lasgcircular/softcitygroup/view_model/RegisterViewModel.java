package com.lasgcircular.softcitygroup.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lasgcircular.softcitygroup.model.RegisterModel;
import com.lasgcircular.softcitygroup.remote.RemoteRepository;
import com.lasgcircular.softcitygroup.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<RegisterModel>> register = new MutableLiveData<>();
    public LiveData<ResponseWrapper<RegisterModel>> _register = register;

    public void register(String firstname, String lastname ,String email,String phone,String pass,String device_key) {

        register.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .register(firstname,lastname,email,phone,pass,device_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            register.setValue(new ResponseWrapper<>(
                                    false,
                                     body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(RegisterModel model) {
                        register.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

