package tr.unvercanunlu.in_memory_cache.validation.impl;

import tr.unvercanunlu.in_memory_cache.config.AppConfig;

public class StringKeyValidator extends BaseValidator<String> {

  @Override
  public boolean isInvalid(String value) {
    if (super.isInvalid(value)) {
      return true;
    }

    String trimmed = value.trim();
    int length = trimmed.length();

    return (length < AppConfig.STRING_KEY_LENGTH_MIN)
        || (length > AppConfig.STRING_KEY_LENGTH_MAX);
  }

}
