package tr.unvercanunlu.in_memory_cache.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfig {

  // integer
  public static final int NUMBER_MIN = 1;
  public static final int NUMBER_MAX = 1_000_000;

  // string
  public static final int TEXT_LENGTH_MIN = 1;
  public static final int TEXT_LENGTH_MAX = 20;

  // random string key
  public static final int GENERATED_TEXT_LENGTH_MAX = 8;
  public static final int GENERATED_TEXT_LENGTH_MIN = 1;
  public static final boolean GENERATED_TEXT_WITH_DIGITS = true;

  // key generation try count
  public static final int GENERATION_MAX_TRIES = 20;

}
