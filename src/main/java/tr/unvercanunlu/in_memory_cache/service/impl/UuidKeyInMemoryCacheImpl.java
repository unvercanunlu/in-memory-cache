package tr.unvercanunlu.in_memory_cache.service.impl;

import java.util.UUID;
import java.util.function.Supplier;
import tr.unvercanunlu.in_memory_cache.validation.impl.UuidValidator;

public class UuidKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<UUID, V> {

  public UuidKeyInMemoryCacheImpl() {
    super(new UuidValidator());
  }

  @Override
  protected Supplier<UUID> getKeyGenerator() {
    return UUID::randomUUID;
  }

}
