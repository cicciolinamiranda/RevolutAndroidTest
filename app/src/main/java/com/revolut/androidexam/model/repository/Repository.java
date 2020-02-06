package com.revolut.androidexam.model.repository;

import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.base.InterfaceRepository;
import com.revolut.androidexam.model.api.RateAPI;
import com.revolut.androidexam.model.dto.RateDTO;
import com.revolut.androidexam.util.Mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class Repository implements InterfaceRepository {

    @Inject
    RateAPI currencyAPI;
    @Inject
    LocalStorage localStorage;

    @Inject
    public Repository() {
        RevolutApplication.getInjector().inject(this);
    }

    public Observable<List<RateDTO>> updateRates(String base, Double amount) {
        return currencyAPI.getRates(base, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(localStorage::saveCurrencies)
                .onErrorReturn(throwable -> localStorage.getCurrencies())
                .filter((remoteCurrencyDTO) -> !amount.equals(0.0))
                .map(Mappers::mapRemoteToLocal)
                .onErrorReturn(throwable -> new ArrayList<>());
    }

}
