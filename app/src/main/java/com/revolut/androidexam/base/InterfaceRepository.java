package com.revolut.androidexam.base;

import android.database.Observable;

import com.revolut.androidexam.dto.Rate;

import java.util.List;

public interface InterfaceRepository {

    Observable<List<Rate>> updateRates(String base, Double amount);

}
