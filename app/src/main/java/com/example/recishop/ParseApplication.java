package com.example.recishop;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Recipe.class);
        ParseObject.registerSubclass(Ingredient.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wkPHJkGtOKyFXfC5IsHVM5YRAm6NkNXkWrxz431y")
                .clientKey("le6mjQExVbw8PdXTFjqSf5prPUQ5okmytdRPgsd4")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
