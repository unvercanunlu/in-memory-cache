package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MIN;

import org.junit.jupiter.api.Test;

class NumberValidatorTest {

  private final NumberValidator validator = new NumberValidator();

  @Test
  void acceptsConfiguredBounds() {
    assertTrue(validator.isValid(NUMBER_MIN));
    assertTrue(validator.isValid(NUMBER_MAX));
  }

  @Test
  void rejectsNullAndValuesOutsideConfiguredBounds() {
    assertFalse(validator.isValid(null));
    assertFalse(validator.isValid(NUMBER_MIN - 1));
    assertFalse(validator.isValid(NUMBER_MAX + 1));
    assertFalse(validator.isValid(Integer.MIN_VALUE));
    assertFalse(validator.isValid(Integer.MAX_VALUE));
  }
}
