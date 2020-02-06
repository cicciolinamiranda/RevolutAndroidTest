package com.revolut.androidexam.presenter;

import android.content.Context;

import com.revolut.androidexam.base.BasePresenter;
import com.revolut.androidexam.base.LatestRateRule;
import com.revolut.androidexam.model.repository.LocalStorage;
import com.revolut.androidexam.model.repository.Repository;
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

    }

    @Override
    public void getLatestRate() {

    }
}
