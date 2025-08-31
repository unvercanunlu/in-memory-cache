package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.INTEGER_KEY_MIN;

public class IntegerKeyValidator extends BaseValidator<Integer> {

  @Override
  public boolean isValid(Integer value) {
    return super.isValid(value) && (value >= INTEGER_KEY_MIN);
  }

}
