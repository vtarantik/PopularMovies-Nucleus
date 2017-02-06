package com.vtarantik.popularmovies.mvp.presenter;

import android.os.Bundle;
import android.util.Log;

import com.vtarantik.popularmovies.App;
import com.vtarantik.popularmovies.db.dao.MovieDao;
import com.vtarantik.popularmovies.interactor.IApiInteractor;
import com.vtarantik.popularmovies.mvp.view.IMainView;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vtarantik on 10.1.2017.
 */

public class MainPresenter extends RxPresenter<IMainView>{
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }


}
