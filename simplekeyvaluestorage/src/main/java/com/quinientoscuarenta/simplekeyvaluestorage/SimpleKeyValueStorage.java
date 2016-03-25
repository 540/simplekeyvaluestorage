package com.quinientoscuarenta.simplekeyvaluestorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleKeyValueStorage {

    public static final String SHARED_PREFS_NAME = "prefs_name";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SimpleKeyValueStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public <T> T get(String key, Class<T> classRef) {
        if (sharedPreferences.contains(key)) {
            return gson.fromJson(sharedPreferences.getString(key, null), classRef);
        } else {
            return null;
        }
    }

    public <T> List<T> getList(String key, Class<T[]> classRef) {
        if (sharedPreferences.contains(key)) {
            T[] arr = gson.fromJson(sharedPreferences.getString(key, null), classRef);
            return Arrays.asList(arr);
        } else {
            return Collections.emptyList();
        }
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(value));
        editor.apply();
    }

    public boolean isSet(String key) {
        return sharedPreferences.contains(key);
    }

    public void delete(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
