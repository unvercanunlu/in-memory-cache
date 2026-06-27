package tr.unvercanunlu.in_memory_cache.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MIN;

import org.junit.jupiter.api.Test;

class NumberKeyInMemoryCacheImplTest {

  @Test
  void acceptsConfiguredBoundaryKeys() {
    NumberKeyInMemoryCacheImpl<String> cache = new NumberKeyInMemoryCacheImpl<>();

    cache.store(NUMBER_MIN, "min");
    cache.store(NUMBER_MAX, "max");

    assertEquals("min", cache.retrieve(NUMBER_MIN).orElseThrow());
    assertEquals("max", cache.retrieve(NUMBER_MAX).orElseThrow());
    assertTrue(cache.containsKey(NUMBER_MIN));
    assertTrue(cache.containsKey(NUMBER_MAX));
  }

  @Test
  void rejectsNullAndOutOfRangeKeys() {
    NumberKeyInMemoryCacheImpl<String> cache = new NumberKeyInMemoryCacheImpl<>();

    assertThrows(IllegalArgumentException.class, () -> cache.store(null, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store(NUMBER_MIN - 1, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store(NUMBER_MAX + 1, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.containsKey(null));
    assertThrows(IllegalArgumentException.class, () -> cache.retrieve(NUMBER_MIN - 1));
    assertThrows(IllegalArgumentException.class, () -> cache.evict(NUMBER_MAX + 1));
  }

  @Test
  void rejectsDuplicateKeyAndNullValue() {
    NumberKeyInMemoryCacheImpl<String> cache = new NumberKeyInMemoryCacheImpl<>();

    cache.store(1, "one");

    assertThrows(IllegalArgumentException.class, () -> cache.store(1, "duplicate"));
    assertThrows(IllegalArgumentException.class, () -> cache.store(2, null));
    assertEquals("one", cache.retrieve(1).orElseThrow());
  }

  @Test
  void evictRemovesExistingNumberKeyAndLeavesMissingKeyAlone() {
    NumberKeyInMemoryCacheImpl<String> cache = new NumberKeyInMemoryCacheImpl<>();
    cache.store(1, "one");

    cache.evict(1);
    cache.evict(2);

    assertFalse(cache.containsKey(1));
    assertEquals(0, cache.size());
  }

  @Test
  void generatedKeyIsInsideAcceptedRange() {
    NumberKeyInMemoryCacheImpl<String> cache = new NumberKeyInMemoryCacheImpl<>();

    for (int index = 0; index < 1_000; index++) {
      Integer key = cache.generateKey();

      assertTrue(key >= NUMBER_MIN);
      assertTrue(key <= NUMBER_MAX);
      assertTrue(cache.isKeyValid(key));
    }
  }
}
