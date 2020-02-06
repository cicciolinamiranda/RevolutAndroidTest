package com.revolut.androidexam.dagger;

import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.model.repository.Repository;
import com.revolut.androidexam.views.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ContextModule.class,
                ApiModule.class
        }
)
public interface Injector {

    void inject(RevolutApplication keeperApplication);

    void inject(MainActivity mainActivity);

//    void inject(LastCurrencyPresenter lastCurrencyPresenter);
//
    void inject(Repository repository);
//
//    void inject(LastCurrencyAdapter adapter);
//
//    void inject(PeriodicalService periodicalService);

}
