package tr.unvercanunlu.in_memory_cache.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AppConfigTest {

  @Test
  void numberRangeIsValid() {
    assertTrue(AppConfig.NUMBER_MIN <= AppConfig.NUMBER_MAX);
  }

  @Test
  void textValidationRangeContainsGeneratedTextRange() {
    assertTrue(AppConfig.TEXT_LENGTH_MIN <= AppConfig.GENERATED_TEXT_LENGTH_MIN);
    assertTrue(AppConfig.GENERATED_TEXT_LENGTH_MAX <= AppConfig.TEXT_LENGTH_MAX);
  }

  @Test
  void keyGenerationRetryLimitIsPositive() {
    assertTrue(AppConfig.GENERATION_MAX_TRIES > 0);
  }
}
