package tr.unvercanunlu.in_memory_cache.example;

import static tr.unvercanunlu.in_memory_cache.service.Component.NUMBER_GENERATOR;
import static tr.unvercanunlu.in_memory_cache.service.Component.NUMBER_VALIDATOR;

import java.util.Objects;
import tr.unvercanunlu.in_memory_cache.service.cache.impl.BaseInMemoryCacheImpl;

public class NumberKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<Integer, V> {

  public NumberKeyInMemoryCacheImpl() {
    super(NUMBER_GENERATOR, NUMBER_VALIDATOR, Objects::nonNull, (value -> value));
  }

}
