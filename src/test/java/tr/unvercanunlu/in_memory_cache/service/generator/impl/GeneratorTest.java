package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATED_TEXT_LENGTH_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATED_TEXT_LENGTH_MIN;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MIN;

import org.junit.jupiter.api.Test;

class GeneratorTest {

  @Test
  void textGeneratorProducesConfiguredLengthLowercaseAlphaNumericText() {
    TextGenerator generator = new TextGenerator();

    for (int index = 0; index < 1_000; index++) {
      String value = generator.generate();

      assertTrue(value.length() >= GENERATED_TEXT_LENGTH_MIN);
      assertTrue(value.length() <= GENERATED_TEXT_LENGTH_MAX);
      assertTrue(value.matches("[a-z0-9]+"));
    }
  }

  @Test
  void numberGeneratorProducesConfiguredRangeValues() {
    NumberGenerator generator = new NumberGenerator();

    for (int index = 0; index < 1_000; index++) {
      Integer value = generator.generate();

      assertTrue(value >= NUMBER_MIN);
      assertTrue(value <= NUMBER_MAX);
    }
  }

  @Test
  void uuidGeneratorProducesNonNullValue() {
    UuidGenerator generator = new UuidGenerator();

    assertNotNull(generator.generate());
  }

  @Test
  void generatorsProduceValuesAcceptedByDefaultValidators() {
    TextGenerator textGenerator = new TextGenerator();
    NumberGenerator numberGenerator = new NumberGenerator();

    assertTrue(new tr.unvercanunlu.in_memory_cache.service.validator.impl.TextValidator()
        .isValid(textGenerator.generate()));
    assertTrue(new tr.unvercanunlu.in_memory_cache.service.validator.impl.NumberValidator()
        .isValid(numberGenerator.generate()));
  }
}
