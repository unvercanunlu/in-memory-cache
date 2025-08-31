package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.RANDOM_STRING_KEY_LENGTH_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.RANDOM_STRING_KEY_LENGTH_MIN;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.RANDOM_STRING_KEY_WITH_DIGITS;
import static tr.unvercanunlu.in_memory_cache.util.TextUtil.randomTextGenerate;

import tr.unvercanunlu.in_memory_cache.service.generator.Generator;

public class StringGenerator implements Generator<String> {

  @Override
  public String generate() {
    return randomTextGenerate(
        RANDOM_STRING_KEY_LENGTH_MIN,
        RANDOM_STRING_KEY_LENGTH_MAX,
        RANDOM_STRING_KEY_WITH_DIGITS
    );
  }

}
