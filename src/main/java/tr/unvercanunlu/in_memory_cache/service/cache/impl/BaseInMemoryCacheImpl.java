package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.RANDOM_KEY_GENERATION_MAX_TRIES;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import tr.unvercanunlu.in_memory_cache.service.cache.Cache;
import tr.unvercanunlu.in_memory_cache.service.cache.KeyGeneratableCache;
import tr.unvercanunlu.in_memory_cache.service.cache.ValidKeyCache;
import tr.unvercanunlu.in_memory_cache.service.cache.ValidValueCache;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;
import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

@Slf4j
public abstract class BaseInMemoryCacheImpl<K, V> implements Cache<K, V>, KeyGeneratableCache<K>, ValidKeyCache<K>, ValidValueCache<V> {

  protected final ConcurrentMap<K, V> pairs = new ConcurrentHashMap<>();

  protected final Generator<K> keyGenerator;

  protected final Validator<K> keyValidator;
  protected final Validator<V> valueValidator;

  protected BaseInMemoryCacheImpl(
      Generator<K> keyGenerator,
      Validator<K> keyValidator,
      Validator<V> valueValidator
  ) {
    this.keyGenerator = keyGenerator;
    this.keyValidator = keyValidator;
    this.valueValidator = valueValidator;
  }

  @Override
  public K store(K key, V value) {
    K processedKey = preprocessKey(key);

    if (!isKeyValid(processedKey)) {
      String message = "Key invalid: key=%s".formatted(Objects.toString(key, "null"));
      log.error(message);
      throw new IllegalArgumentException(message);
    }

    if (!isValueValid(value)) {
      String message = "Value invalid: value=%s".formatted(Objects.toString(value, "null"));
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

    if (!isKeyValid(processedKey)) {
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

    if (!isKeyValid(processedKey)) {
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
      if (tryCount >= RANDOM_KEY_GENERATION_MAX_TRIES) {
        String message = "Max tries to generate key exceeded after %d attempts!".formatted(tryCount);
        log.error(message);
        throw new RuntimeException(message);
      }
      Supplier<K> keyGenerator = getKeyGenerator();
      key = keyGenerator.get();
      tryCount++;
    } while (checkExists(key));

    return key;
  }

  @Override
  public boolean isKeyValid(K key) {
    return getKeyValidator()
        .test(key);
  }

  @Override
  public boolean isValueValid(V value) {
    return getValueValidator()
        .test(value);
  }

  protected Supplier<K> getKeyGenerator() {
    return keyGenerator::generate;
  }

  protected Predicate<K> getKeyValidator() {
    return keyValidator::isValid;
  }

  protected Predicate<V> getValueValidator() {
    return valueValidator::isValid;
  }

  protected K preprocessKey(K key) {
    return key;
  }

}
