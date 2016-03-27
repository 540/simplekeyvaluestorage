# SimpleKeyValueStorage

[![Build Status](https://travis-ci.org/540/SimpleKeyValueStorage.svg?branch=master)](https://travis-ci.org/540/SimpleKeyValueStorage)
[![Download](https://api.bintray.com/packages/gorkma/maven/simplekeyvaluestorage/images/download.svg) ](https://bintray.com/gorkma/maven/simplekeyvaluestorage/_latestVersion)

--------

A simple Shared preference wrapper to store POJO objects in Android, serialized with gson.

Library Objectives 
------------------

Tired of having to manually serialize/unserialize objects content into shared preferences this library uses 
gson to automate object serialization and make painless object storage into shared preferences. Objects are stored
json encoded.

Installation
------------

Grab via Gradle:
```groovy
compile 'com.quinientoscuarenta:simplekeyvaluestorage:1.0.0'
```
or Maven:
```xml
<dependency>
  <groupId>com.quinientoscuarenta</groupId>
  <artifactId>simplekeyvaluestorage</artifactId>
  <version>1.0.0</version>
</dependency>
```

Usage
-----

#### Initialize:
You can initialize it with default name 'skvs_default_prefs'
```java
SimpleKeyValueStorage.initDefault(context);
```
Or with custom name
```java
SimpleKeyValueStorage.builder().withName('custom_name').init(context);
```
You will have to provide a context, it can be application or activity context.

#### Use:
Set method accepts any type such as list, object, primitive...
```java
Locale locale = Locale.ENGLISH;
simpleKeyValueStorage.set("key", locale);
```

Get or get list and specify the content type to retrieve the saved value
```java
Locale savedLocale = simpleKeyValueStorage.get("key", Locale.class);

List<String> savedStrings = simpleKeyValueStorage.getList("key", String[].class);
```

Delete value
```java
simpleKeyValueStorage.delete("key");
```

Check data availability
```java
boolean available = simpleKeyValueStorage.isSet("key");
```

Clear all storage
```java
simpleKeyValueStorage.clear();
```

Proguard
--------
```
#Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
```

License
-------
<pre>
Copyright 2015 Gorka Moreno Asín

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
