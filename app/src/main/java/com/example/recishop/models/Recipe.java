package com.example.recishop.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Recipe")
public class Recipe extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_CHEF = "chef";

    public String getName(){
        return getString(KEY_NAME);
    }

    public void setName(String name){
        put(KEY_NAME, name);
    }

    public ParseUser getChef(){
        return getParseUser(KEY_CHEF);
    }

    public void setChef(ParseUser chef){
        put(KEY_CHEF, chef);
    }

}
