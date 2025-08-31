package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATED_TEXT_LENGTH_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATED_TEXT_LENGTH_MIN;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.GENERATED_TEXT_WITH_DIGITS;
import static tr.unvercanunlu.in_memory_cache.util.TextUtil.randomTextGenerate;

import tr.unvercanunlu.in_memory_cache.service.generator.Generator;

public class TextGenerator implements Generator<String> {

  @Override
  public String generate() {
    return randomTextGenerate(
        GENERATED_TEXT_LENGTH_MIN,
        GENERATED_TEXT_LENGTH_MAX,
        GENERATED_TEXT_WITH_DIGITS
    );
  }

}
