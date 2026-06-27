package tr.unvercanunlu.in_memory_cache.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import tr.unvercanunlu.in_memory_cache.service.cache.KeyGeneratable;
import tr.unvercanunlu.in_memory_cache.service.cache.KeyValidatable;
import tr.unvercanunlu.in_memory_cache.service.cache.ValueValidatable;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;
import tr.unvercanunlu.in_memory_cache.service.preprocess.Preprocessor;
import tr.unvercanunlu.in_memory_cache.service.validator.Validator;

class FunctionalInterfaceTest {

  @Test
  void generatorAndKeyGeneratableContractsCanBeImplementedWithLambdas() {
    Generator<String> generator = () -> "generated";
    KeyGeneratable<String> keyGeneratable = generator::generate;

    assertEquals("generated", generator.generate());
    assertEquals("generated", keyGeneratable.generateKey());
  }

  @Test
  void validatorContractsCanBeImplementedWithLambdas() {
    Validator<String> validator = value -> value != null && value.length() > 2;
    KeyValidatable<String> keyValidatable = validator::isValid;
    ValueValidatable<String> valueValidatable = validator::isValid;

    assertTrue(validator.isValid("abc"));
    assertTrue(keyValidatable.isKeyValid("abc"));
    assertTrue(valueValidatable.isValueValid("abc"));
    assertFalse(validator.isValid("a"));
  }

  @Test
  void preprocessorContractCanBeImplementedWithLambda() {
    Preprocessor<String> preprocessor = value -> value == null ? null : value.trim();

    assertEquals("key", preprocessor.preprocess(" key "));
  }
}
