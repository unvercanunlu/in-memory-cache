package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.STRING_KEY_LENGTH_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.STRING_KEY_LENGTH_MIN;

public class StringKeyValidator extends BaseValidator<String> {

  @Override
  public boolean isValid(String value) {
    if (!super.isValid(value)) {
      return false;
    }

    String trimmed = value.trim();
    int length = trimmed.length();

    return (length >= STRING_KEY_LENGTH_MIN) && (length <= STRING_KEY_LENGTH_MAX);
  }

}
