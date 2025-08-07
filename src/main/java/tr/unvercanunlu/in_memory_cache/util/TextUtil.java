package tr.unvercanunlu.in_memory_cache.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtil {

  public static String normalize(String text) {
    if (text == null) {
      return null;
    }

    return text.trim().toLowerCase();
  }

  public static String randomTextGenerate(int minLength, int maxLength, boolean containsDigit) {
    Random random = ThreadLocalRandom.current();

    StringBuilder builder = new StringBuilder();

    int length = random.nextInt(minLength, (maxLength + 1));

    while (builder.length() < length) {
      boolean isCharacter = random.nextBoolean();

      if (isCharacter) {
        char randomCharacter = (char) ('a' + random.nextInt(('z' - 'a') + 1));
        builder.append(randomCharacter);
      } else if (containsDigit) {
        int randomDigit = random.nextInt(0, 10);
        builder.append(randomDigit);
      }
    }

    return builder.toString();
  }

}
