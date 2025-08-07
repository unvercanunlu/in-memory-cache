package tr.unvercanunlu.in_memory_cache.service.impl;

import java.util.function.Supplier;
import tr.unvercanunlu.in_memory_cache.config.AppConfig;
import tr.unvercanunlu.in_memory_cache.util.TextUtil;
import tr.unvercanunlu.in_memory_cache.validation.impl.StringKeyValidator;

public class StringKeyInMemoryCacheImpl<V> extends BaseInMemoryCacheImpl<String, V> {

  public StringKeyInMemoryCacheImpl() {
    super(new StringKeyValidator());
  }

  @Override
  public String preprocessKey(String key) {
    return TextUtil.normalize(key);
  }

  @Override
  protected Supplier<String> getKeyGenerator() {
    return () -> TextUtil.randomTextGenerate(
        AppConfig.RANDOM_STRING_KEY_LENGTH_MIN,
        AppConfig.RANDOM_STRING_KEY_LENGTH_MAX,
        AppConfig.RANDOM_STRING_KEY_WITH_DIGITS
    );
  }

}
