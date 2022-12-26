package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.notifications.NotificationModel;
import com.circular.circular.model.notifications.NotificationReadModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<NotificationModel>> notification = new MutableLiveData<>();
    public LiveData<ResponseWrapper<NotificationModel>> _notification = notification;

    private final MutableLiveData<ResponseWrapper<NotificationReadModel>> notification_status = new MutableLiveData<>();
    public LiveData<ResponseWrapper<NotificationReadModel>> _notification_status = notification_status;

    public void getNotification(String token){

        notification.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .notifications(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        notification.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(NotificationModel model) {
                        notification.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void readNotification(String token,int notification_id){

        notification_status.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .read_notification(token,notification_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationReadModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        notification_status.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(NotificationReadModel model) {
                        notification_status.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}

