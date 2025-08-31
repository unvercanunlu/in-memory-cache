package tr.unvercanunlu.in_memory_cache.service.preprocess.impl;

import tr.unvercanunlu.in_memory_cache.service.preprocess.Preprocessor;
import tr.unvercanunlu.in_memory_cache.util.TextUtil;

public class TextPreprocessor implements Preprocessor<String> {

  @Override
  public String preprocess(String raw) {
    return TextUtil.normalize(raw);
  }

}
