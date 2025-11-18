package tr.unvercanunlu.in_memory_cache.example;

import static tr.unvercanunlu.in_memory_cache.service.Component.TEXT_GENERATOR;
import static tr.unvercanunlu.in_memory_cache.service.Component.TEXT_PREPROCESSOR;
import static tr.unvercanunlu.in_memory_cache.service.Component.TEXT_VALIDATOR;

import java.util.Objects;
import tr.unvercanunlu.in_memory_cache.service.cache.impl.BaseInMemoryCacheImpl;

public class TextKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<String, V> {

  public TextKeyInMemoryCacheImpl() {
    super(TEXT_GENERATOR, TEXT_VALIDATOR, Objects::nonNull, TEXT_PREPROCESSOR);
  }

}
