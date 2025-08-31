package tr.unvercanunlu.in_memory_cache.service.cache.impl;

import static tr.unvercanunlu.in_memory_cache.service.Component.NUMBER_GENERATOR;
import static tr.unvercanunlu.in_memory_cache.service.Component.NUMBER_VALIDATOR;

import java.util.Objects;

public class NumberKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<Integer, V> {

  public NumberKeyInMemoryCacheImpl() {
    super(NUMBER_GENERATOR, NUMBER_VALIDATOR, Objects::nonNull, (value -> value));
  }

}
