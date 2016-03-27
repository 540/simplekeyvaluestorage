package com.quinientoscuarenta.simplekeyvaluestorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SimpleKeyValueStorageTest {

    public static final String TEST_KEY = "key";
    public static final String ANOTHER_TEST_KEY = "another_key";
    public static final String UNAVAILABLE_KEY = "unavailable_key";

    private SharedPreferences defaultSharedPreferences;

    private SimpleKeyValueStorage simpleKeyValueStorage;

    @Before
    public void setUp() throws Exception {
        defaultSharedPreferences = RuntimeEnvironment.application
                .getSharedPreferences("skvs_default_prefs", Context.MODE_PRIVATE);

        simpleKeyValueStorage = SimpleKeyValueStorage.initDefault(RuntimeEnvironment.application);
    }

    @Test
    public void returnsStoredObject() throws Exception {
        TestClass testObject = TestClass.build1();
        storeTestObjectIntoDefaultSharedPrefs(testObject);

        TestClass storedObject = simpleKeyValueStorage.get(TEST_KEY, TestClass.class);

        assertThat(storedObject, equalTo(testObject));
    }

    @Test
    public void returnsAnotherStoredObject() throws Exception {
        TestClass testObject = TestClass.build2();
        storeTestObjectIntoDefaultSharedPrefs(testObject);

        TestClass storedObject = simpleKeyValueStorage.get(TEST_KEY, TestClass.class);

        assertThat(storedObject, equalTo(testObject));
    }

    @Test
    public void returnsNullForUnavailableKey() throws Exception {
        assertNull(simpleKeyValueStorage.get(UNAVAILABLE_KEY, TestClass.class));
    }

    @Test
    public void savesAnotherClassObject() throws Exception {
        Locale locale = Locale.ENGLISH;

        simpleKeyValueStorage.set(ANOTHER_TEST_KEY, locale);

        assertThat(simpleKeyValueStorage.get(ANOTHER_TEST_KEY, Locale.class), equalTo(locale));
    }

    @Test
    public void setValueIsSet() throws Exception {
        TestClass testObject = TestClass.build1();

        simpleKeyValueStorage.set(TEST_KEY, testObject);

        assertThat(simpleKeyValueStorage.isSet(TEST_KEY), is(true));
    }

    @Test
    public void unavailableKeyIsNotSet() throws Exception {
        assertThat(simpleKeyValueStorage.isSet(UNAVAILABLE_KEY), is(false));
    }

    @Test
    public void deletesValue() throws Exception {
        TestClass testObject = TestClass.build1();
        simpleKeyValueStorage.set(TEST_KEY, testObject);

        simpleKeyValueStorage.delete(TEST_KEY);

        assertThat(simpleKeyValueStorage.isSet(TEST_KEY), is(false));
    }

    @Test
    public void clearsAllValues() throws Exception {
        simpleKeyValueStorage.set(TEST_KEY, TestClass.build1());
        simpleKeyValueStorage.set(ANOTHER_TEST_KEY, TestClass.build2());

        simpleKeyValueStorage.clear();

        assertThat(simpleKeyValueStorage.isSet(TEST_KEY), is(false));
        assertThat(simpleKeyValueStorage.isSet(ANOTHER_TEST_KEY), is(false));
    }

    @Test
    public void savesList() throws Exception {
        List<TestClass> testObjects = Arrays.asList(TestClass.build1(), TestClass.build2());

        simpleKeyValueStorage.set(TEST_KEY, testObjects);

        assertThat(simpleKeyValueStorage.getList(TEST_KEY, TestClass[].class),
                equalTo(testObjects));
    }

    @Test
    public void returnsEmptyListForUnavailableListKey() throws Exception {
        List<TestClass> list = simpleKeyValueStorage.getList(UNAVAILABLE_KEY, TestClass[].class);

        assertThat(list, is(EMPTY_LIST));
    }

    @Test
    public void initializesWithCustomName() {
        SimpleKeyValueStorage customNameKeyValueStorage = SimpleKeyValueStorage.builder()
                .withName("custom_name").init(RuntimeEnvironment.application);
        storeTestObjectIntoDefaultSharedPrefs(TestClass.build1());

        TestClass storedObject = customNameKeyValueStorage.get(TEST_KEY, TestClass.class);

        assertThat(storedObject, nullValue());
    }

    private void storeTestObjectIntoDefaultSharedPrefs(TestClass testObject) {
        defaultSharedPreferences.edit().putString(TEST_KEY, testObject.toString()).apply();
    }

    @After
    public void tearDown() throws Exception {
        defaultSharedPreferences.edit().clear().apply();
    }
}
