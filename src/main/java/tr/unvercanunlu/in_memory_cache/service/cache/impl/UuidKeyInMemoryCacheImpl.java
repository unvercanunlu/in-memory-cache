package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import java.util.UUID;
import tr.unvercanunlu.in_memory_cache.service.generator.impl.UuidGenerator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.StandardValidator;

public class UuidKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<UUID, V> {

  public UuidKeyInMemoryCacheImpl() {
    super(new UuidGenerator(), new StandardValidator<>(), new StandardValidator<>());
  }

}
