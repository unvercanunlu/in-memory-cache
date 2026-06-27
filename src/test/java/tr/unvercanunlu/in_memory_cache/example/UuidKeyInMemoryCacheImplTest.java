package tr.unvercanunlu.in_memory_cache.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class UuidKeyInMemoryCacheImplTest {

  @Test
  void storesRetrievesAndEvictsUuidKeys() {
    UuidKeyInMemoryCacheImpl<String> cache = new UuidKeyInMemoryCacheImpl<>();
    UUID key = UUID.randomUUID();

    cache.store(key, "value");

    assertEquals("value", cache.retrieve(key).orElseThrow());
    assertTrue(cache.containsKey(key));

    cache.evict(key);

    assertTrue(cache.retrieve(key).isEmpty());
  }

  @Test
  void rejectsNullKeyAndValue() {
    UuidKeyInMemoryCacheImpl<String> cache = new UuidKeyInMemoryCacheImpl<>();

    assertThrows(IllegalArgumentException.class, () -> cache.store(null, "value"));
    assertThrows(IllegalArgumentException.class, () -> cache.store(UUID.randomUUID(), null));
    assertThrows(IllegalArgumentException.class, () -> cache.containsKey(null));
    assertThrows(IllegalArgumentException.class, () -> cache.retrieve(null));
    assertThrows(IllegalArgumentException.class, () -> cache.evict(null));
  }

  @Test
  void rejectsDuplicateUuidKey() {
    UuidKeyInMemoryCacheImpl<String> cache = new UuidKeyInMemoryCacheImpl<>();
    UUID key = UUID.randomUUID();

    cache.store(key, "first");

    assertThrows(IllegalArgumentException.class, () -> cache.store(key, "second"));
    assertEquals("first", cache.retrieve(key).orElseThrow());
  }

  @Test
  void generatedKeyIsNonNullAndValid() {
    UuidKeyInMemoryCacheImpl<String> cache = new UuidKeyInMemoryCacheImpl<>();
    UUID key = cache.generateKey();

    assertNotNull(key);
    assertTrue(cache.isKeyValid(key));
    assertFalse(cache.containsKey(key));
  }
}
