package com.revolut.androidexam.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.presenter.LatestRatePresenter;
import com.revolut.androidexam.util.Logger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.michaelrocks.paranoid.Obfuscate;
import rx.Observable;
import rx.Subscription;

@Obfuscate
public class SchedulerService extends Service {

    private static Logger LOGGER = Logger.build(SchedulerService.class);

    public static final long SCHEDULE_MS = 1;
    private Subscription subscription;

    @Inject
    LatestRatePresenter presenter;

    @Inject
    public SchedulerService() {
        RevolutApplication.getInjector().inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LOGGER.log("SchedulerService onCreate");
        super.onCreate();
        subscription = getPeriodicObservable().subscribe((t)->presenter.getLatestRate());
    }

    @Override
    public void onDestroy() {
        LOGGER.log("onDestroy");
        if(subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        super.onDestroy();
    }

    public Observable<Long> getPeriodicObservable(){
        return Observable.interval(SCHEDULE_MS, TimeUnit.SECONDS);
    }

}
