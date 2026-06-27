package tr.unvercanunlu.in_memory_cache.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtil {

  private static final Pattern COMBINING_MARKS = Pattern.compile("\\p{M}+");

  public static String normalize(String text) {
    if (text == null) {
      return null;
    }

    // NFD decomposition + combining mark removal ensures consistent normalization across all scripts.
    // For Turkish: İ (U+0130) → i + combining dot → i (cache key consistency).
    // For all scripts: combining marks (diacritics, tone marks) are stripped.
    String decomposed = Normalizer.normalize(text.trim(), Normalizer.Form.NFD);
    return COMBINING_MARKS.matcher(decomposed).replaceAll("").toLowerCase(Locale.ROOT);
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
