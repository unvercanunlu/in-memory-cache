package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MAX;
import static tr.unvercanunlu.in_memory_cache.config.AppConfig.NUMBER_MIN;

import java.util.concurrent.ThreadLocalRandom;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;

public class NumberGenerator implements Generator<Integer> {

  @Override
  public Integer generate() {
    return ThreadLocalRandom.current()
        .nextInt(NUMBER_MIN, NUMBER_MAX);
  }

}
