package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATION_MAX_TRIES;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import tr.unvercanunlu.in_memory_cache.service.cache.Cache;
import tr.unvercanunlu.in_memory_cache.service.cache.KeyGeneratable;
import tr.unvercanunlu.in_memory_cache.service.cache.KeyValidatable;
import tr.unvercanunlu.in_memory_cache.service.cache.ValueValidatable;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;
import tr.unvercanunlu.in_memory_cache.service.preprocess.Preprocessor;
import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

@Slf4j
public abstract class BaseInMemoryCacheImpl<K, V>
    implements Cache<K, V>, KeyGeneratable<K>, KeyValidatable<K>, ValueValidatable<V> {

  protected final ConcurrentMap<K, V> pairs = new ConcurrentHashMap<>();

  // generators
  protected final Generator<K> keyGenerator;

  // validators
  protected final Validator<K> keyValidator;
  protected final Validator<V> valueValidator;

  // preprocessor
  protected final Preprocessor<K> keyPreprocessor;

  // default
  protected BaseInMemoryCacheImpl(Generator<K> keyGenerator) {
    this.keyGenerator = keyGenerator;
    this.keyValidator = Objects::nonNull;
    this.valueValidator = Objects::nonNull;
    this.keyPreprocessor = (value -> value);
  }

  // custom
  protected BaseInMemoryCacheImpl(
      Generator<K> keyGenerator,
      Validator<K> keyValidator,
      Validator<V> valueValidator,
      Preprocessor<K> keyPreprocessor
  ) {
    this.keyGenerator = keyGenerator;
    this.keyValidator = keyValidator;
    this.valueValidator = valueValidator;
    this.keyPreprocessor = keyPreprocessor;
  }

  @Override
  public K store(K key, V value) {
    K processedKey = keyPreprocessor.preprocess(key);

    if (!isKeyValid(processedKey)) {
      handleInvalidKey(key);
    }

    if (!isValueValid(value)) {
      handleInvalidValue(value);
    }

    if (pairs.putIfAbsent(processedKey, value) != null) {
      handleDuplicateKey(key);
    }

    log.debug("Stored: key={}, value={}", processedKey, value);

    return processedKey;
  }

  @Override
  public Optional<V> retrieve(K key) {
    K processedKey = keyPreprocessor.preprocess(key);

    if (!isKeyValid(processedKey)) {
      handleInvalidKey(key);
    }

    V result = pairs.get(processedKey);

    if (result != null) {
      log.debug("Retrieved: key={}, value={}", processedKey, result);
    } else {
      log.debug("Cache miss for key={}", processedKey);
    }

    return Optional.ofNullable(result);
  }

  @Override
  public void evict(K key) {
    K processedKey = keyPreprocessor.preprocess(key);

    if (!isKeyValid(processedKey)) {
      handleInvalidKey(key);
    }

    V removed = pairs.remove(processedKey);

    if (removed != null) {
      log.debug("Evicted: key={}, value={}", processedKey, removed);
    } else {
      log.debug("Evict attempted but key={} was not present", processedKey);
    }
  }

  @Override
  public boolean containsKey(K key) {
    K processedKey = keyPreprocessor.preprocess(key);

    return pairs.containsKey(processedKey);
  }

  @Override
  public void clear() {
    int oldSize = pairs.size();

    pairs.clear();

    log.debug("Cleared cache, previous size={}", oldSize);
  }

  @Override
  public int size() {
    return pairs.size();
  }

  @Override
  public boolean isKeyValid(K key) {
    return keyValidator.isValid(key);
  }

  @Override
  public boolean isValueValid(V value) {
    return valueValidator.isValid(value);
  }

  @Override
  public K generateKey() {
    K key;
    int tryCount = 0;

    do {
      if (tryCount >= GENERATION_MAX_TRIES) {
        handleMaxTries(tryCount);
      }

      key = keyGenerator.generate();
      tryCount++;
    } while (containsKey(key));

    log.debug("Generated key={} after {} tries", key, tryCount);

    return key;
  }

  private void handleInvalidKey(K key) throws IllegalArgumentException {
    String parameter = Objects.toString(key, "null");
    log.warn("Invalid key: key={}", parameter);
    throw new IllegalArgumentException("Invalid key: key=%s".formatted(parameter));
  }

  private void handleInvalidValue(V value) throws IllegalArgumentException {
    String parameter = Objects.toString(value, "null");
    log.warn("Invalid value: value={}", parameter);
    throw new IllegalArgumentException("Invalid value: value=%s".formatted(parameter));
  }

  private void handleMaxTries(int tryCount) throws RuntimeException {
    log.error("Max tries to generate key exceeded after {} attempts!", tryCount);
    throw new RuntimeException("Max tries to generate key exceeded: tries=%d".formatted(tryCount));
  }

  private void handleDuplicateKey(K key) throws IllegalArgumentException {
    String parameter = Objects.toString(key, "null");
    log.warn("Duplicate key: key={}", parameter);
    throw new IllegalArgumentException("Duplicate key: key=%s".formatted(parameter));
  }

}
