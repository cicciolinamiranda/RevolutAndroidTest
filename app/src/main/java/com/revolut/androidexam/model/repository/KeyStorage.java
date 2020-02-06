package com.revolut.androidexam.model.repository;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class KeyStorage {

    private static final String CURRENCY_KEY = "CURRENCY_KEY";
    private static final String MAIN_CURRENCY_KEY = "MAIN_CURRENCY_KEY";

    static String getRateKey() {
        return CURRENCY_KEY;
    }

    static String getMainRateKey() {
        return MAIN_CURRENCY_KEY;
    }

}
