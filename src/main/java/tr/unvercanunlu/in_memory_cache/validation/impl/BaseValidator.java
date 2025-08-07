package tr.unvercanunlu.in_memory_cache.validation.impl;

import tr.unvercanunlu.in_memory_cache.validation.Validator;

public abstract class BaseValidator<K> implements Validator<K> {

  @Override
  public boolean isInvalid(K value) {
    return (value == null);
  }

}
