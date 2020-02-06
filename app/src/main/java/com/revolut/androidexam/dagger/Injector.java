package com.revolut.androidexam.dagger;

import com.revolut.androidexam.RevolutApplication;
import com.revolut.androidexam.model.repository.Repository;
import com.revolut.androidexam.presenter.LatestRatePresenter;
import com.revolut.androidexam.service.SchedulerService;
import com.revolut.androidexam.views.activities.MainActivity;
import com.revolut.androidexam.views.adapter.LatestRateAdapter;

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

    void inject(LatestRatePresenter latestRatePresenter);

    void inject(Repository repository);

    void inject(LatestRateAdapter latestRateAdapter);

    void inject(SchedulerService schedulerService);

}
