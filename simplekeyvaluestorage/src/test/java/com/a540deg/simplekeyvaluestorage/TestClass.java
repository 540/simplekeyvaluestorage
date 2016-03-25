package com.a540deg.simplekeyvaluestorage;

class TestClass {

    private String text;
    private int number;
    private boolean bool;

    private TestClass(String text, int number, boolean bool) {
        this.text = text;
        this.number = number;
        this.bool = bool;
    }

    public static TestClass build1() {
        return new TestClass("text", 9, true);
    }

    public static TestClass build2() {
        return new TestClass("text2", 8, false);
    }

    @Override
    public String toString() {
        return "{\"text\":\"" + text + "\""
                + ", \"number\":" + number
                + ", \"bool\":" + bool + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestClass that = (TestClass) o;

        if (bool != that.bool) {
            return false;
        }
        if (number != that.number) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (bool ? 1 : 0);
        return result;
    }
}
