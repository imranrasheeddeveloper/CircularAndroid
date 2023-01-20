package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.LoginModel;
import com.circular.circular.model.RegisterModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<LoginModel>> update_user = new MutableLiveData<>();
    public LiveData<ResponseWrapper<LoginModel>> _update_user = update_user;

    public void updateUser(String token,
                           String firstname,
                           String lastname ,
                           String email,
                           String phone,
                           MultipartBody.Part part) {

        update_user.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .updateUserData(token,firstname,lastname,phone,part,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            update_user.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(LoginModel model) {
                        update_user.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }


}