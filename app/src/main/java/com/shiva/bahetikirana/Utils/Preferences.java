package com.shiva.bahetikirana.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


//Temporary data storage class
public class Preferences {
   private SharedPreferences preferences;
  private  SharedPreferences.Editor editor;


    private static final String SHARED_PREF_KEY="BahetiKirana";
    private static final String PRODUCTLIST="productlist";

    private static Preferences preferencesInstance;

    @SuppressLint("CommitPrefEdits")
    private Preferences(Context context){

        preferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public static synchronized Preferences getSharedPrefInstance(Context context){
        if (preferencesInstance==null){
            preferencesInstance=new Preferences(context);
        }
        return preferencesInstance;

    }

    //Method required for Retrieving CustId stored
    public String getProductList() {
        return preferences.getString(PRODUCTLIST, null);
    }

    //Pin Status stored temporarily in savePinStatus method
    public void saveProductList( String productlist) {
        editor.putString(PRODUCTLIST, productlist);
        editor.apply();
        editor.commit();

    }

    //Global method used for passing key values as per contants declared
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();

    }

    //Retrieving key from stored key value pair and authenticate using key
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    //Clearing the stored prferences or session maintained uptil now on Logout
    public void clearPreferences() {

        editor.clear();
        editor.apply();
        editor.commit();

    }
}
