package com.quinientoscuarenta.simplekeyvaluestorage.sample;

import java.util.Locale;

public class Content {

    private final String text;
    private final int number;

    public Content(String text, int number) {
        this.text = text;
        this.number = number;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "Text: %s\nNumber: %d", text, number);
    }
}
