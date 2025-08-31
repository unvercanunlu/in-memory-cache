package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.INTEGER_KEY_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.INTEGER_KEY_MIN;

import java.util.concurrent.ThreadLocalRandom;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;

public class IntegerGenerator implements Generator<Integer> {

  @Override
  public Integer generate() {
    return ThreadLocalRandom.current()
        .nextInt(INTEGER_KEY_MIN, INTEGER_KEY_MAX);
  }

}
