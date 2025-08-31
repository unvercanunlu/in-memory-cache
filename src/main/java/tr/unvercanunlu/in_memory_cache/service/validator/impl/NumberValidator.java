package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MIN;

import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

public class NumberValidator implements Validator<Integer> {

  @Override
  public boolean isValid(Integer value) {
    return (value != null)
        && (value >= NUMBER_MIN) && (value <= NUMBER_MAX);
  }

}
