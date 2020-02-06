package com.revolut.androidexam.presenter;

import android.content.Context;
import android.content.Intent;

import com.revolut.androidexam.base.BasePresenter;
import com.revolut.androidexam.base.LatestRateRule;
import com.revolut.androidexam.model.repository.LocalStorage;
import com.revolut.androidexam.model.repository.Repository;
import com.revolut.androidexam.service.SchedulerService;
import com.revolut.androidexam.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.michaelrocks.paranoid.Obfuscate;
import rx.Subscription;

@Obfuscate
@Singleton
public class LatestRatePresenter extends BasePresenter<LatestRateRule.ILatestRateView> implements LatestRateRule.ILatestRatePresenter {

    private static Logger LOGGER = Logger.build(LatestRatePresenter.class);

    private Subscription subscription;

    @Inject
    Repository repository;
    @Inject
    Context context;
    @Inject
    LocalStorage storage;

    @Override
    public void getLatestRate(String code, Double amount) {
        LOGGER.log("LatestRatePresenter getLastUpdatedCurrency");
        subscription = repository.updateRates(code, amount)
                .subscribe(
                        list -> {
                            if (!list.isEmpty())
                                getMvpView().onRateUpdated(list);
                            else
                                getMvpView().onNoDataAvailable();
                        }, LOGGER::error
                );
    }

    @Override
    public void getLatestRate() {
        LOGGER.log("LatestRatePresenter getLatestRate");

        getLatestRate(storage.getMainRate().name(), storage.getMainRate().value());
    }

    @Override
    public void attachView(LatestRateRule.ILatestRateView view) {
        LOGGER.log("LatestRatePresenter attachView");
        super.attachView(view);
        getLatestRate();
        context.startService(new Intent(context, SchedulerService.class));
    }

    @Override
    public void detachView() {
        LOGGER.log("LatestRatePresenter detachView");
        context.stopService(new Intent(context, SchedulerService.class));
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.detachView();
    }
}
