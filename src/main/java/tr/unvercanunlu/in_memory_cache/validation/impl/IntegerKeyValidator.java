package tr.unvercanunlu.in_memory_cache.validation.impl;

import tr.unvercanunlu.in_memory_cache.config.AppConfig;

public class IntegerKeyValidator extends BaseValidator<Integer> {

  @Override
  public boolean isInvalid(Integer value) {
    return super.isInvalid(value)
        || (value < AppConfig.INTEGER_KEY_MIN);
  }

}
