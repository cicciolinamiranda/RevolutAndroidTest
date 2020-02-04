package com.revolut.androidexam.dagger;

import com.revolut.androidexam.model.api.RateAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public RateAPI providesAPI() {
        return new RateAPI.Factory().makeService();
    }
}
