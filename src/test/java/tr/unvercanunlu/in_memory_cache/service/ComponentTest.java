package tr.unvercanunlu.in_memory_cache.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ComponentTest {

  @Test
  void exposesReusableGeneratorsValidatorsAndPreprocessors() {
    assertNotNull(Component.TEXT_GENERATOR);
    assertNotNull(Component.NUMBER_GENERATOR);
    assertNotNull(Component.UUID_GENERATOR);
    assertNotNull(Component.TEXT_VALIDATOR);
    assertNotNull(Component.NUMBER_VALIDATOR);
    assertNotNull(Component.TEXT_PREPROCESSOR);
  }

  @Test
  void componentWiringProducesValidGeneratedKeys() {
    String textKey = Component.TEXT_GENERATOR.generate();
    Integer numberKey = Component.NUMBER_GENERATOR.generate();

    assertTrue(Component.TEXT_VALIDATOR.isValid(textKey));
    assertTrue(Component.NUMBER_VALIDATOR.isValid(numberKey));
    assertTrue(Component.TEXT_VALIDATOR.isValid(Component.TEXT_PREPROCESSOR.preprocess(" " + textKey + " ")));
  }
}
