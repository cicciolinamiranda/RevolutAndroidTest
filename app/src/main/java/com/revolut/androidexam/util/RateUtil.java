package com.revolut.androidexam.util;

import com.mynameismidori.currencypicker.ExtendedCurrency;

public final class RateUtil {

    private RateUtil() {}

    public static int getFlagResByISO(String iso){
        for(ExtendedCurrency currency: ExtendedCurrency.getAllCurrencies())
            if(currency.getCode().equals(iso))
                return currency.getFlag();
        return -1;
    }

    public static String getCurrencyNameResByISO(String iso){
        for(ExtendedCurrency currency: ExtendedCurrency.getAllCurrencies())
            if(currency.getCode().equals(iso))
                return currency.getName();
        return "";
    }

}
