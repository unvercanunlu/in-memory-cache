# In-Memory Cache (Java)

A simple, fast, and generic in-memory key-value cache written in Java 21. Supports `Integer`, `String`, and `UUID` keys with validation and key
generation.

## Features

- Generic `Cache<K, V>` interface
- Thread-safe (`ConcurrentHashMap`)
- Built-in key validation
- Key normalization for strings
- Auto key generation (random int, string, UUID)
- Java 21, Maven, SLF4J, Lombok, JUnit, Mockito
- Code coverage with JaCoCo

## Example Usage

```java
Cache<String, String> cache = new StringKeyInMemoryCacheImpl<>();
String key = cache.generateKey();
cache.put(key, "hello");
Optional<String> value = cache.retrieve(key);
// value.get() => "hello"
````

## Run Tests

```bash
mvn test
```

## Code Coverage

```bash
mvn verify
# Open: target/site/jacoco/index.html
```

## Config

Edit settings in `AppConfig.java`:
