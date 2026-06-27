package tr.unvercanunlu.in_memory_cache.service.preprocess.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class TextPreprocessorTest {

  private final TextPreprocessor preprocessor = new TextPreprocessor();

  @Test
  void normalizesTextKeys() {
    assertEquals("key", preprocessor.preprocess("  KEY  "));
  }

  @Test
  void preservesNullForValidatorToRejectLater() {
    assertNull(preprocessor.preprocess(null));
  }
}
