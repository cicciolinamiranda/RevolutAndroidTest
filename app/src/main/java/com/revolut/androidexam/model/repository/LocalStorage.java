package com.revolut.androidexam.model.repository;

import android.provider.Telephony;

import com.orhanobut.hawk.Hawk;
import com.revolut.androidexam.model.dto.RateDTO;
import com.revolut.androidexam.model.dto.RemoteRateDTO;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.revolut.androidexam.model.repository.KeyStorage.getMainRateKey;
import static com.revolut.androidexam.model.repository.KeyStorage.getRateKey;

@Singleton
public class LocalStorage {

    @Inject
    public LocalStorage() {
    }

    public RemoteRateDTO saveCurrencies(RemoteRateDTO remoteRateDTO) {
        Hawk.put(getRateKey(), remoteRateDTO);
        return remoteRateDTO;
    }

    public RemoteRateDTO getCurrencies() {
        return Hawk.get(getRateKey());
    }

    public RateDTO saveMainRate(RateDTO mainRateDTO){
        Hawk.put(getMainRateKey(), mainRateDTO);
        return mainRateDTO;
    }

    public RateDTO getMainRate(){
        return Hawk.get(getMainRateKey(), RateDTO.create("AUD", 100.0));
    }
    
}
