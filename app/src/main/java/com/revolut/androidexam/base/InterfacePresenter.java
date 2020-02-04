package com.revolut.androidexam.base;

public interface InterfacePresenter<T extends InterfaceView> {

    void attachView(T t);

    void detachView();

}
