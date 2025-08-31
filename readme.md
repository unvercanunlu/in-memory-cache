# In-Memory Cache (Java)

A simple, fast, and generic in-memory key-value cache written in Java 21. Supports `Integer`, `String`, and `UUID` keys with validation, preprocessing, and key generation.

## Features

* Generic `Cache<K, V>` interface
* Thread-safe (`ConcurrentHashMap`)
* Built-in key validation (`Validator`)
* Key preprocessing (`Preprocessor`, e.g., normalize strings)
* Auto key generation (`Generator`)
* Configurable limits in `AppConfig`
* Java 21, Maven, Lombok, SLF4J, Logback

## Example Usage

```java
Cache<String, String> cache = new TextKeyInMemoryCacheImpl<>();
String key = cache.generateKey();
cache.store(key, "hello");
Optional<String> value = cache.retrieve(key);
// value.get() => "hello"
```

## Config

Edit constants in `AppConfig.java` (number ranges, text length, generation limits).