package com.example.android.jwttest.utils;

import android.app.Application;

import com.auth0.lock.Lock;
import com.auth0.lock.LockProvider;

/**
 * Created by Android on 5/27/2016.
 * 
 * I really don't know why this is being used , wrote on Github
 */
public class JwtTestApplication extends Application implements LockProvider {

    private Lock lock;

    public void onCreate() {
        super.onCreate();
        lock = new Lock.Builder()
                .loadFromApplication(this)
                        /** Other configuration goes here */
                .closable(true)
                .build();
    }

    @Override
    public Lock getLock() {
        return lock;
        // Commenting this line to review changes in pul request/ commnd of git
    }
}
