package tr.unvercanunlu.in_memory_cache.example;

import static tr.unvercanunlu.in_memory_cache.service.Component.UUID_GENERATOR;

import java.util.UUID;
import tr.unvercanunlu.in_memory_cache.service.cache.impl.BaseInMemoryCacheImpl;

public class UuidKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<UUID, V> {

  public UuidKeyInMemoryCacheImpl() {
    super(UUID_GENERATOR);
  }

}
