package tr.unvercanunlu.in_memory_cache.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class TextUtilTest {

  @Test
  void normalizeTrimsAndLowercasesWithLocaleRoot() {
    Locale previous = Locale.getDefault();

    try {
      Locale.setDefault(Locale.forLanguageTag("tr-TR"));

      assertEquals("id", TextUtil.normalize(" ID "));
    } finally {
      Locale.setDefault(previous);
    }
  }

  @Test
  void normalizeReturnsNullForNullText() {
    assertNull(TextUtil.normalize(null));
  }

  @Test
  void normalizeKeepsInternalWhitespace() {
    assertEquals("hello world", TextUtil.normalize("  Hello World  "));
  }

  @Test
  void normalizeFoldsTurkishDottedCapitalIToPlainLowercaseI() {
    // İ (U+0130) must map to plain 'i', not 'i' + combining dot above (U+0307),
    // so that store("İD") and retrieve("ID") resolve to the same cache key.
    assertEquals("id", TextUtil.normalize("İD"));
    assertEquals(TextUtil.normalize("İD"), TextUtil.normalize("ID"));
  }

  @Test
  void randomTextGenerateReturnsExactLengthWhenMinAndMaxAreEqual() {
    String value = TextUtil.randomTextGenerate(6, 6, true);

    assertEquals(6, value.length());
    assertTrue(value.matches("[a-z0-9]+"));
  }

  @Test
  void randomTextGenerateCanReturnEmptyTextWhenZeroLengthIsAllowed() {
    assertEquals("", TextUtil.randomTextGenerate(0, 0, true));
  }

  @Test
  void randomTextGenerateThrowsWhenMinLengthIsGreaterThanMaxLength() {
    assertThrows(IllegalArgumentException.class, () -> TextUtil.randomTextGenerate(5, 4, true));
  }

  @Test
  void randomTextGenerateHonorsLengthAndDigitFlagWhenDigitsAreAllowed() {
    for (int index = 0; index < 1_000; index++) {
      String value = TextUtil.randomTextGenerate(4, 8, true);

      assertTrue(value.length() >= 4);
      assertTrue(value.length() <= 8);
      assertTrue(value.matches("[a-z0-9]+"));
    }
  }

  @Test
  void randomTextGenerateWithDigitsDisabledStillReturnsLettersOnly() {
    assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
      for (int index = 0; index < 1_000; index++) {
        String value = TextUtil.randomTextGenerate(4, 8, false);

        assertTrue(value.length() >= 4);
        assertTrue(value.length() <= 8);
        assertTrue(value.matches("[a-z]+"));
      }
    });
  }
}
