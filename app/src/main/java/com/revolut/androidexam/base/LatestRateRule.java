package com.revolut.androidexam.base;

import com.revolut.androidexam.model.dto.RateDTO;

import java.util.List;

public interface LatestRateRule {

    interface ILatestRateView extends InterfaceView {
        void onRateUpdated(List<RateDTO> list);
        void onNoDataAvailable();
    }

    interface ILatestRatePresenter {
        void getLatestRate(String code, Double amount);
        void getLatestRate();
    }

}
