package com.lasgcircular.softcitygroup.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lasgcircular.softcitygroup.model.LoginModel;
import com.lasgcircular.softcitygroup.model.PasswordUpdateModel;
import com.lasgcircular.softcitygroup.model.ResetModel;
import com.lasgcircular.softcitygroup.remote.RemoteRepository;
import com.lasgcircular.softcitygroup.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<LoginModel>> loginData = new MutableLiveData<>();
    public LiveData<ResponseWrapper<LoginModel>> _loginData = loginData;

    private final MutableLiveData<ResponseWrapper<ResetModel>> reset = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ResetModel>> _reset = reset;

    private final MutableLiveData<ResponseWrapper<PasswordUpdateModel>> update = new MutableLiveData<>();
    public LiveData<ResponseWrapper<PasswordUpdateModel>> _update = update;

    public void login(String email , String pass,String key) {

        loginData.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .login(email,pass , key)
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
                            loginData.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(LoginModel loginModel) {
                        loginData.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                loginModel
                        ));
                    }
                });
    }

    public void reset(String email) {

        reset.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .resetEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            reset.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(ResetModel model) {
                        reset.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }


    public void update(String token,String previous_password , String new_password) {

        update.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .update_password(token,previous_password,new_password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PasswordUpdateModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            update.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(PasswordUpdateModel model) {
                        update.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

