package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import static tr.unvercanunlu.in_memory_cache.service.Component.UUID_GENERATOR;

import java.util.UUID;

public class UuidKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<UUID, V> {

  public UuidKeyInMemoryCacheImpl() {
    super(UUID_GENERATOR);
  }

}
