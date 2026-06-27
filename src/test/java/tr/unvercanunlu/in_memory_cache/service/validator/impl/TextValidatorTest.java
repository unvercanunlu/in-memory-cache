package tr.unvercanunlu.in_memory_cache.service.validator.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TextValidatorTest {

  private final TextValidator validator = new TextValidator();

  @Test
  void acceptsTrimmedTextWithinConfiguredLength() {
    assertTrue(validator.isValid("a"));
    assertTrue(validator.isValid(" abc "));
    assertTrue(validator.isValid("abcdefghijklmnopqrst"));
    assertTrue(validator.isValid("123"));
  }

  @Test
  void rejectsNullBlankAndTooLongText() {
    assertFalse(validator.isValid(null));
    assertFalse(validator.isValid(""));
    assertFalse(validator.isValid("   "));
    assertFalse(validator.isValid("abcdefghijklmnopqrstu"));
  }
}
