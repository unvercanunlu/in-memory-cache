package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.TEXT_LENGTH_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.TEXT_LENGTH_MIN;

import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

public class TextValidator implements Validator<String> {

  @Override
  public boolean isValid(String value) {
    if (value == null) {
      return false;
    }

    String trimmed = value.trim();
    int length = trimmed.length();

    return (length >= TEXT_LENGTH_MIN) && (length <= TEXT_LENGTH_MAX);
  }

}
