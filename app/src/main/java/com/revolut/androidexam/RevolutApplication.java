package com.revolut.androidexam;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.hawk.Hawk;
import com.revolut.androidexam.dagger.ContextModule;
import com.revolut.androidexam.dagger.DaggerInjector;
import com.revolut.androidexam.dagger.Injector;
import com.revolut.androidexam.util.Logger;
import com.revolut.androidexam.model.valuepair.AutoValueParser;

public class RevolutApplication extends Application {

    private Logger LOGGER = Logger.build(RevolutApplication.class);

    public static Injector getInjector() {
        return injector;
    }

    private static Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        LOGGER.log("RevolutApplication onCreate");
        injector = DaggerInjector
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);

        Hawk.init(this)
                .setParser(new AutoValueParser())
                .setLogInterceptor(LOGGER::log)
                .build();

    }

}
