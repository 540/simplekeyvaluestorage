package com.quinientoscuarenta.simplekeyvaluestorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleKeyValueStorage {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SimpleKeyValueStorage(Context context, String prefsName) {
        sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /**
     * Get value for key. If the key does not exist null will be returned.
     *
     * @param key      The name of the preference to retrieve.
     * @param classRef The Class reference of the value is being retrieved.
     * @return Returns the preference value if it exists, or null. Throws
     * ClassCastException if there is a preference with this name that is not
     * a of the Class type indicated by classRef.
     */
    public <T> T get(String key, Class<T> classRef) {
        if (sharedPreferences.contains(key)) {
            try {
                return gson.fromJson(sharedPreferences.getString(key, null), classRef);
            } catch (JsonSyntaxException exception) {
                throw new ClassCastException("Unable to cast to " + classRef);
            }
        } else {
            return null;
        }
    }

    /**
     * Get List value for key. If the key does not exist null will be returned.
     *
     * @param key      The name of the preference to retrieve.
     * @param classRef The array Class reference of the value is being retrieved.
     * @return Returns the preference array value if it exists, or null. Throws
     * ClassCastException if there is a preference with this name that is not
     * a of the Class type indicated by classRef.
     */
    public <T> List<T> getList(String key, Class<T[]> classRef) {
        if (sharedPreferences.contains(key)) {
            try {
                T[] arr = gson.fromJson(sharedPreferences.getString(key, null), classRef);
                return Arrays.asList(arr);
            } catch (JsonSyntaxException exception) {
                throw new ClassCastException("Unable to cast to " + classRef);
            }
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Set value for key. If the key already exists value will be modified.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void set(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(value));
        editor.apply();
    }

    /**
     * Check if the preference has an stored value.
     *
     * @param key The name of the preference to check
     * @return true if the preference exists or false if not.
     */
    public boolean isSet(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * Delete the preference value.
     *
     * @param key The name of the preference to delete.
     */
    public void delete(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    /**
     * Remove <em>all</em> values from the preferences.
     */
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * Create a SimpleKeyValueStorage instance with the default name: skvs_default_prefs
     *
     * @param context Application or Activity Context needed to access SharedPreferences.
     * @return A SimleKeyValueStorage instance.
     */
    public static SimpleKeyValueStorage initDefault(Context context) {
        return new SimpleKeyValueStorageBuilder().init(context);
    }

    /**
     * Return a SimpleKeyValueStorage builder. Builder that helps to create SimpleKeyValueStorage
     * instances.
     *
     * @return A SimpleKeyValueStorageBuilder instance.
     */
    public static SimpleKeyValueStorageBuilder builder() {
        return new SimpleKeyValueStorageBuilder();
    }
}
