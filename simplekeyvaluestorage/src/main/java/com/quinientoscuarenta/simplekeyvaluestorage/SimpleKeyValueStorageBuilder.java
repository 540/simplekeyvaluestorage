package com.quinientoscuarenta.simplekeyvaluestorage;

import android.content.Context;

public class SimpleKeyValueStorageBuilder {

    private static final String DEFAULT_PREFS_NAME = "skvs_default_prefs";

    private String prefsName = DEFAULT_PREFS_NAME;

    /**
     * Set the name for the SharedPreferences object SimpleKeyValueStorage will be built with.
     *
     * @param name Desired preferences file. If a preferences file by this name
     *             does not exist, it will be created.
     * @return SimpleKeyValueStorageBuilder.
     */
    public SimpleKeyValueStorageBuilder withName(String name) {
        SimpleKeyValueStorageBuilder builder = new SimpleKeyValueStorageBuilder();
        builder.prefsName = name;

        return builder;
    }

    /**
     * Will create a new instance of SimpleKeyValueStorage.
     *
     * @param context Application or Activity Context needed to access SharedPreferences.
     * @return A SimpleKeyValueStorageBuilder instance.
     */
    public SimpleKeyValueStorage init(Context context) {
        return new SimpleKeyValueStorage(context, prefsName);
    }
}
