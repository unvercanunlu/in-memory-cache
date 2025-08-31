package tr.unvercanunlu.in_memory_cache.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfig {

  // integer key
  public static final int INTEGER_KEY_MIN = 1;
  public static final int INTEGER_KEY_MAX = 1_000_000;

  // string key
  public static final int STRING_KEY_LENGTH_MIN = 1;
  public static final int STRING_KEY_LENGTH_MAX = 20;

  // random string key
  public static final int RANDOM_STRING_KEY_LENGTH_MAX = 8;
  public static final int RANDOM_STRING_KEY_LENGTH_MIN = 1;
  public static final boolean RANDOM_STRING_KEY_WITH_DIGITS = true;

  // key generation try count
  public static final int RANDOM_KEY_GENERATION_MAX_TRIES = 20;

}
