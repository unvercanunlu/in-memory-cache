package tr.unvercanunlu.in_memory_cache.service.generator.impl;

import java.util.UUID;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;

public class UuidGenerator implements Generator<UUID> {

  @Override
  public UUID generate() {
    return UUID.randomUUID();
  }

}
