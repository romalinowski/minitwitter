package com.example.romi.helloword.s08cl05_minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

//CL15
public class SharedPreferencesManager {

    private  static  final String  APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){ }

    private static SharedPreferences getSharedPreferences(){
        // El siguiente código es el que se encuentra en la documentacion.
        //   SharedPreferences sharedPref = context.getSharedPreferences(
        //    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //Yo lo modifico segun el curso-

        return MyApp.getContext().
                getSharedPreferences(APP_SETTINGS_FILE , Context.MODE_PRIVATE);

    }

    public static void setSomeStringValue( String dataLabel, String dataValue ){
        //CL15- c) 2-
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        // 3-
        // dataLabel la clave
        //dataValue el valor de la clave.
        editor.putString(dataLabel, dataValue);
        //3-
        editor.commit();
    }

    public static void setSomeBooleanValue( String dataLabel, boolean dataValue ){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.commit();
    }

    //15-1-f-1)
    public static String getSomeStringValue(String dataLabel){
        return getSharedPreferences().getString(dataLabel,null);
    }
    //15-1-f-2)
    public static Boolean getSomeBooleanValue(String dataLabel){
        return getSharedPreferences().getBoolean(dataLabel,false);
    }
}
