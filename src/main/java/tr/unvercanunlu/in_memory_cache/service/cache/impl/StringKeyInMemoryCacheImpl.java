package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import tr.unvercanunlu.in_memory_cache.service.generator.impl.StringGenerator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.StandardValidator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.StringKeyValidator;

public class StringKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<String, V> {

  public StringKeyInMemoryCacheImpl() {
    super(new StringGenerator(), new StringKeyValidator(), new StandardValidator<>());
  }

}
