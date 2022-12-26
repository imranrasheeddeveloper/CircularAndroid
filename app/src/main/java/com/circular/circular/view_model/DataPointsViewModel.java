package com.circular.circular.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.circular.circular.model.create_preference.CreatePreferencesModel;
import com.circular.circular.model.data_points.DataPointsModel;
import com.circular.circular.remote.RemoteRepository;
import com.circular.circular.remote.ResponseWrapper;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataPointsViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<DataPointsModel>> data_points = new MutableLiveData<>();
    public LiveData<ResponseWrapper<DataPointsModel>> _data_points = data_points;

    private final MutableLiveData<ResponseWrapper<CreatePreferencesModel>> create_pref = new MutableLiveData<>();
    public LiveData<ResponseWrapper<CreatePreferencesModel>> _create_pref = create_pref;

    public void getPoints(String token) {

        data_points.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .dataPoints(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataPointsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        data_points.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(DataPointsModel model) {
                        data_points.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void createPreferences(String token, String name, List<Integer> selected_data_points) {

        create_pref.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .preferences(token,name,selected_data_points)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CreatePreferencesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        create_pref.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(CreatePreferencesModel model) {
                        create_pref.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}