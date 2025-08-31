package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import tr.unvercanunlu.in_memory_cache.service.generator.impl.IntegerGenerator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.IntegerKeyValidator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.StandardValidator;

public class IntegerKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<Integer, V> {

  public IntegerKeyInMemoryCacheImpl() {
    super(new IntegerGenerator(), new IntegerKeyValidator(), new StandardValidator<>());
  }

}
