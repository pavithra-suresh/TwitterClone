package com.example.twitterclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GQAWkgApYDAV7Am86VgkcszhxwaQnzeZvycuFIlC")
                // if defined
                .clientKey("TkHJFMz5lcOKXizzW0Q9mJ6EhDAnyIZGZe3F4jKx")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }

}
