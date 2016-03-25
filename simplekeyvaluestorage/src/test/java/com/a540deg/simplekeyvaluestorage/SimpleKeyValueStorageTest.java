package com.a540deg.simplekeyvaluestorage;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SimpleKeyValueStorageTest {

    public static final String CLASS_TEST_PREFS_KEY = "user_session";
    public static final String ANOTHER_CLASS_TEST_PREFS_KEY = "another_class";
    public static final String UNAVAILABLE_TEST_PREFS_KEY = "unavailable_key";

    private SharedPreferences sharedPreferences;
    private SimpleKeyValueStorage serializedSimpleKeyValueStorage;

    @Before
    public void setUp() throws Exception {
        sharedPreferences = RuntimeEnvironment.application.getSharedPreferences(
                SimpleKeyValueStorage.SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        serializedSimpleKeyValueStorage =
                new SimpleKeyValueStorage(RuntimeEnvironment.application);
    }

    @Test
    public void testThatReturnsPersistedClass() throws Exception {
        TestClass testClass = TestClass.build1();
        serializeAnotherPersistedClassIntoSharedPrefs(testClass);

        TestClass serializedPersistedClass = serializedSimpleKeyValueStorage.get(
                CLASS_TEST_PREFS_KEY, TestClass.class);

        assertThat(serializedPersistedClass, equalTo(testClass));
    }

    @Test
    public void testThatReturnsAnotherPersistedClass() throws Exception {
        TestClass testClass = TestClass.build2();
        serializeAnotherPersistedClassIntoSharedPrefs(testClass);

        TestClass serializedPersistedClass = serializedSimpleKeyValueStorage.get(
                CLASS_TEST_PREFS_KEY, TestClass.class);

        assertThat(serializedPersistedClass, equalTo(testClass));
    }

    @Test
    public void testThatReturnsNullForInexistentKey() throws Exception {
        assertNull(serializedSimpleKeyValueStorage
                .get(UNAVAILABLE_TEST_PREFS_KEY, TestClass.class));
    }

    @Test
    public void testThatSavesAnotherClass() throws Exception {
        TestClass testClass = TestClass.build1();

        serializedSimpleKeyValueStorage.set(CLASS_TEST_PREFS_KEY, testClass);

        assertThat(
                serializedSimpleKeyValueStorage.get(CLASS_TEST_PREFS_KEY, TestClass.class),
                equalTo(testClass)
        );
    }

    @Test
    public void testThatSetValueIsSet() throws Exception {
        TestClass testClass = TestClass.build1();

        serializedSimpleKeyValueStorage.set(CLASS_TEST_PREFS_KEY, testClass);

        assertThat(serializedSimpleKeyValueStorage.isSet(CLASS_TEST_PREFS_KEY), is(true));
    }

    @Test
    public void testThatUnavailableKeyIsNotSeted() throws Exception {
        assertThat(serializedSimpleKeyValueStorage.isSet(UNAVAILABLE_TEST_PREFS_KEY), is(false));
    }

    @Test
    public void testThatDeletesValue() throws Exception {
        TestClass testClass = TestClass.build1();
        serializedSimpleKeyValueStorage.set(CLASS_TEST_PREFS_KEY, testClass);

        serializedSimpleKeyValueStorage.delete(CLASS_TEST_PREFS_KEY);

        assertThat(serializedSimpleKeyValueStorage.isSet(CLASS_TEST_PREFS_KEY), is(false));
    }

    @Test
    public void testThatClearsAllValues() throws Exception {
        serializedSimpleKeyValueStorage.set(CLASS_TEST_PREFS_KEY, TestClass.build1());
        serializedSimpleKeyValueStorage.set(ANOTHER_CLASS_TEST_PREFS_KEY, TestClass.build2());

        serializedSimpleKeyValueStorage.clear();

        assertThat(serializedSimpleKeyValueStorage.isSet(CLASS_TEST_PREFS_KEY), is(false));
        assertThat(serializedSimpleKeyValueStorage.isSet(ANOTHER_CLASS_TEST_PREFS_KEY), is(false));
    }

    @Test
    public void testThatSavesListClass() throws Exception {
        List<TestClass> testClasses = Arrays.asList(TestClass.build1(), TestClass.build2());

        serializedSimpleKeyValueStorage.set(CLASS_TEST_PREFS_KEY, testClasses);

        assertThat(serializedSimpleKeyValueStorage.getList(CLASS_TEST_PREFS_KEY,
                TestClass[].class), equalTo(testClasses));
    }

    private void serializeAnotherPersistedClassIntoSharedPrefs(TestClass persistedClass) {
        sharedPreferences.edit().putString(CLASS_TEST_PREFS_KEY, persistedClass.toString()).apply();
    }

    @After
    public void tearDown() throws Exception {
        sharedPreferences.edit().clear().apply();
    }
}
