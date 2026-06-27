package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;
import tr.unvercanunlu.in_memory_cache.service.preprocess.Preprocessor;
import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

class BaseInMemoryCacheImplTest {

  @Test
  void storeReturnsProcessedKeyAndStoresValue() {
    TestCache cache = new TestCache(() -> "generated");

    String storedKey = cache.store(" key ", "value");

    assertEquals("key", storedKey);
    assertEquals("value", cache.retrieve("key").orElseThrow());
    assertEquals(1, cache.size());
  }

  @Test
  void storeThrowsWhenProcessedKeyIsInvalid() {
    TestCache cache = new TestCache(() -> "generated");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.store("   ", "value")
    );

    assertEquals("Invalid key: key=   ", exception.getMessage());
  }

  @Test
  void storeThrowsWhenValueIsInvalid() {
    TestCache cache = new TestCache(() -> "generated");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.store("key", "   ")
    );

    assertEquals("Invalid value: value=   ", exception.getMessage());

    IllegalArgumentException nullValueException = assertThrows(
        IllegalArgumentException.class,
        () -> cache.store("key", null)
    );

    assertEquals("Invalid value: value=null", nullValueException.getMessage());
  }

  @Test
  void storeThrowsWhenProcessedKeyAlreadyExists() {
    TestCache cache = new TestCache(() -> "generated");
    cache.store(" key ", "first");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.store("key", "second")
    );

    assertEquals("Duplicate key: key=key", exception.getMessage());
    assertEquals("first", cache.retrieve("key").orElseThrow());
  }

  @Test
  void retrieveReturnsValueWhenKeyExists() {
    TestCache cache = new TestCache(() -> "generated");
    cache.store("key", "value");

    assertEquals("value", cache.retrieve(" key ").orElseThrow());
  }

  @Test
  void retrieveReturnsEmptyWhenKeyDoesNotExist() {
    TestCache cache = new TestCache(() -> "generated");

    assertTrue(cache.retrieve("missing").isEmpty());
  }

  @Test
  void retrieveThrowsWhenProcessedKeyIsInvalid() {
    TestCache cache = new TestCache(() -> "generated");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.retrieve("   ")
    );

    assertEquals("Invalid key: key=   ", exception.getMessage());
  }

  @Test
  void evictRemovesExistingKey() {
    TestCache cache = new TestCache(() -> "generated");
    cache.store("key", "value");

    cache.evict(" key ");

    assertFalse(cache.containsKey("key"));
    assertEquals(0, cache.size());
  }

  @Test
  void evictDoesNothingWhenValidKeyDoesNotExist() {
    TestCache cache = new TestCache(() -> "generated");

    cache.evict("missing");

    assertEquals(0, cache.size());
  }

  @Test
  void evictThrowsWhenProcessedKeyIsInvalid() {
    TestCache cache = new TestCache(() -> "generated");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.evict("   ")
    );

    assertEquals("Invalid key: key=   ", exception.getMessage());
  }

  @Test
  void containsKeyReturnsTrueWhenProcessedKeyExists() {
    TestCache cache = new TestCache(() -> "generated");
    cache.store("key", "value");

    assertTrue(cache.containsKey(" key "));
  }

  @Test
  void containsKeyReturnsFalseWhenProcessedKeyDoesNotExist() {
    TestCache cache = new TestCache(() -> "generated");

    assertFalse(cache.containsKey("missing"));
  }

  @Test
  void containsKeyThrowsWhenProcessedKeyIsInvalid() {
    TestCache cache = new TestCache(() -> "generated");

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> cache.containsKey("   ")
    );

    assertEquals("Invalid key: key=   ", exception.getMessage());
  }

  @Test
  void clearRemovesAllEntries() {
    TestCache cache = new TestCache(() -> "generated");
    cache.store("one", "1");
    cache.store("two", "2");

    cache.clear();

    assertEquals(0, cache.size());
    assertTrue(cache.retrieve("one").isEmpty());
    assertTrue(cache.retrieve("two").isEmpty());
  }

  @Test
  void isKeyValidAndIsValueValidDelegateToValidators() {
    TestCache cache = new TestCache(() -> "generated");

    assertTrue(cache.isKeyValid("key"));
    assertFalse(cache.isKeyValid("   "));
    assertTrue(cache.isValueValid("value"));
    assertFalse(cache.isValueValid("   "));
  }

  @Test
  void defaultConstructorUsesNonNullValidationAndIdentityPreprocessor() {
    DefaultConstructorCache cache = new DefaultConstructorCache(() -> "generated");

    assertEquals(" key ", cache.store(" key ", "value"));
    assertEquals("value", cache.retrieve(" key ").orElseThrow());
    assertThrows(IllegalArgumentException.class, () -> cache.store(null, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store("key", null));
  }

  @Test
  void generateKeyReturnsFirstAbsentGeneratedKey() {
    TestCache cache = new TestCache(() -> "free");

    assertEquals("free", cache.generateKey());
  }

  @Test
  void generateKeyRetriesCollisionsUntilItFindsAbsentKey() {
    SequenceGenerator generator = new SequenceGenerator("taken", "taken", "free");
    TestCache cache = new TestCache(generator);
    cache.store("taken", "value");

    String generated = cache.generateKey();

    assertEquals("free", generated);
    assertEquals(3, generator.callCount());
  }

  @Test
  void generateKeyThrowsAfterMaxTriesWhenAllGeneratedKeysCollide() {
    TestCache cache = new TestCache(() -> "same");
    cache.store("same", "value");

    RuntimeException exception = assertThrows(RuntimeException.class, cache::generateKey);

    assertEquals("Max tries to generate key exceeded: tries=20", exception.getMessage());
  }

  @Test
  void generateKeyThrowsWhenGeneratorProducesInvalidKey() {
    TestCache cache = new TestCache(() -> null);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, cache::generateKey);

    assertEquals("Invalid key: key=null", exception.getMessage());
  }

  private static final class TestCache extends BaseInMemoryCacheImpl<String, String> {

    private TestCache(Generator<String> keyGenerator) {
      super(keyGenerator, validText(), validText(), trim());
    }

    private static Validator<String> validText() {
      return value -> value != null && !value.isBlank();
    }

    private static Preprocessor<String> trim() {
      return value -> value == null ? null : value.trim();
    }
  }

  private static final class DefaultConstructorCache extends BaseInMemoryCacheImpl<String, String> {

    private DefaultConstructorCache(Generator<String> keyGenerator) {
      super(keyGenerator);
    }
  }

  private static final class SequenceGenerator implements Generator<String> {

    private final String[] values;
    private final AtomicInteger index = new AtomicInteger();

    private SequenceGenerator(String... values) {
      this.values = values;
    }

    @Override
    public String generate() {
      int currentIndex = index.getAndIncrement();
      return values[Math.min(currentIndex, values.length - 1)];
    }

    private int callCount() {
      return index.get();
    }
  }
}
