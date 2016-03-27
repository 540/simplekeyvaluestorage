package com.quinientoscuarenta.simplekeyvaluestorage;

import android.content.Context;

public class SimpleKeyValueStorageBuilder {

    private static final String DEFAULT_PREFS_NAME = "skvs_default_prefs";

    private String prefsName = DEFAULT_PREFS_NAME;

    public static SimpleKeyValueStorageBuilder withName(String name) {
        SimpleKeyValueStorageBuilder builder = new SimpleKeyValueStorageBuilder();
        builder.prefsName = name;

        return builder;
    }

    public SimpleKeyValueStorage init(Context context) {
        return new SimpleKeyValueStorage(context, prefsName);
    }
}
