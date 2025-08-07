package tr.unvercanunlu.in_memory_cache.service.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import tr.unvercanunlu.in_memory_cache.config.AppConfig;
import tr.unvercanunlu.in_memory_cache.service.Cache;
import tr.unvercanunlu.in_memory_cache.validation.Validator;

@Slf4j
public abstract class BaseInMemoryCacheImpl<K, V> implements Cache<K, V> {

  protected final ConcurrentMap<K, V> pairs = new ConcurrentHashMap<>();

  protected final Validator<K> keyValidator;

  protected BaseInMemoryCacheImpl(Validator<K> keyValidator) {
    this.keyValidator = keyValidator;
  }

  @Override
  public K store(K key, V value) {
    K processedKey = preprocessKey(key);

    if (keyValidator.isInvalid(processedKey)) {
      String message = "Key invalid: key=%s".formatted(Objects.toString(key, "null"));
      log.error(message);
      throw new IllegalArgumentException(message);
    }

    if (checkExists(processedKey)) {
      throw new IllegalArgumentException("Duplicate key=%s".formatted(Objects.toString(key, "null")));
    }

    pairs.put(processedKey, value);

    return processedKey;
  }

  @Override
  public Optional<V> retrieve(K key) {
    K processedKey = preprocessKey(key);

    if (keyValidator.isInvalid(processedKey)) {
      String message = "Key invalid: key=%s".formatted(Objects.toString(key, "null"));
      log.error(message);
      throw new IllegalArgumentException(message);
    }

    return Optional.ofNullable(
        pairs.get(processedKey)
    );
  }

  @Override
  public void evict(K key) {
    K processedKey = preprocessKey(key);

    if (keyValidator.isInvalid(processedKey)) {
      String message = "Key invalid: key=%s".formatted(Objects.toString(key, "null"));
      log.error(message);
      throw new IllegalArgumentException(message);
    }

    pairs.remove(processedKey);
  }

  @Override
  public boolean checkExists(K key) {
    K processedKey = preprocessKey(key);

    return pairs.containsKey(processedKey);
  }

  @Override
  public void clear() {
    pairs.clear();
  }

  @Override
  public int size() {
    return pairs.size();
  }

  @Override
  public K generateKey() {
    K key;

    int tryCount = 0;

    do {
      if (tryCount > AppConfig.RANDOM_KEY_GENERATION_MAX_TRY) {
        String message = "Max tries to generate key exceeded after %d attempts!".formatted(tryCount);
        log.error(message);
        throw new RuntimeException(message);
      }

      Supplier<K> generator = getKeyGenerator();
      key = generator.get();

      tryCount++;
    } while (checkExists(key));

    return key;
  }

  public K preprocessKey(K key) {
    return key;
  }

  protected abstract Supplier<K> getKeyGenerator();

}
