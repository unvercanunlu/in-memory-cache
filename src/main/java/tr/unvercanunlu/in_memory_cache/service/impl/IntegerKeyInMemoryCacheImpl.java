package tr.unvercanunlu.in_memory_cache.service.impl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import tr.unvercanunlu.in_memory_cache.config.AppConfig;
import tr.unvercanunlu.in_memory_cache.validation.impl.IntegerKeyValidator;

public class IntegerKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<Integer, V> {

  public IntegerKeyInMemoryCacheImpl() {
    super(new IntegerKeyValidator());
  }

  @Override
  protected Supplier<Integer> getKeyGenerator() {
    return () -> ThreadLocalRandom.current()
        .nextInt(AppConfig.INTEGER_KEY_MIN, Integer.MAX_VALUE);
  }

}
