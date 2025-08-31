package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

public abstract class BaseValidator<K> implements Validator<K> {

  @Override
  public boolean isValid(K value) {
    return (value != null);
  }

}
