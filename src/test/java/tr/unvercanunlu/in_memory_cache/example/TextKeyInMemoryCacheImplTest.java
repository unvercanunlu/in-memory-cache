package tr.unvercanunlu.in_memory_cache.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class TextKeyInMemoryCacheImplTest {

  @Test
  void storeNormalizesKeyAndReturnsProcessedKey() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    String storedKey = cache.store("  HeLLo  ", "world");

    assertEquals("hello", storedKey);
    assertEquals("world", cache.retrieve("HELLO").orElseThrow());
    assertTrue(cache.containsKey(" hello "));
  }

  @Test
  void retrieveReturnsEmptyWhenKeyIsAbsent() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    assertTrue(cache.retrieve("missing").isEmpty());
    assertFalse(cache.containsKey("missing"));
  }

  @Test
  void storeRejectsNullBlankAndTooLongKeys() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    assertThrows(IllegalArgumentException.class, () -> cache.store(null, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store("   ", "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store("abcdefghijklmnopqrstu", "value"));
  }

  @Test
  void keyBasedOperationsRejectInvalidKeysConsistently() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    assertThrows(IllegalArgumentException.class, () -> cache.retrieve(null));
    assertThrows(IllegalArgumentException.class, () -> cache.retrieve("   "));
    assertThrows(IllegalArgumentException.class, () -> cache.evict(null));
    assertThrows(IllegalArgumentException.class, () -> cache.evict("   "));
    assertThrows(IllegalArgumentException.class, () -> cache.containsKey(null));
    assertThrows(IllegalArgumentException.class, () -> cache.containsKey("   "));
  }

  @Test
  void storeRejectsNullValue() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    assertThrows(IllegalArgumentException.class, () -> cache.store("key", null));
  }

  @Test
  void generatedTextKeyCanBeStoredAndReadBack() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();
    String key = cache.generateKey();

    cache.store(key, "value");

    assertTrue(cache.isKeyValid(key));
    assertEquals("value", cache.retrieve(key).orElseThrow());
  }

  @Test
  void storeRejectsDuplicateAfterNormalization() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

    cache.store("KEY", "first");

    assertThrows(IllegalArgumentException.class, () -> cache.store(" key ", "second"));
    assertEquals("first", cache.retrieve("key").orElseThrow());
  }

  @Test
  void evictRemovesExistingKeyAndIgnoresMissingValidKey() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();
    cache.store("key", "value");

    cache.evict(" KEY ");
    cache.evict("missing");

    assertFalse(cache.containsKey("key"));
    assertEquals(0, cache.size());
  }

  @Test
  void clearRemovesAllEntries() {
    TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();
    cache.store("one", "1");
    cache.store("two", "2");

    cache.clear();

    assertEquals(0, cache.size());
    assertTrue(cache.retrieve("one").isEmpty());
    assertTrue(cache.retrieve("two").isEmpty());
  }

  @Test
  void normalizationIsStableUnderTurkishDefaultLocale() {
    Locale previous = Locale.getDefault();

    try {
      Locale.setDefault(Locale.forLanguageTag("tr-TR"));
      TextKeyInMemoryCacheImpl<String> cache = new TextKeyInMemoryCacheImpl<>();

      cache.store("ID", "value");

      assertEquals("value", cache.retrieve("id").orElseThrow());
    } finally {
      Locale.setDefault(previous);
    }
  }
}
