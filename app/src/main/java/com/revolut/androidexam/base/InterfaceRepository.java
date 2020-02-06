package com.revolut.androidexam.base;

import com.revolut.androidexam.model.dto.RateDTO;

import java.util.List;

import rx.Observable;

public interface InterfaceRepository {

    Observable<List<RateDTO>> updateRates(String base, Double amount);

}
