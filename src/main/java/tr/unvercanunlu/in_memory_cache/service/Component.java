package tr.unvercanunlu.in_memory_cache.service;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tr.unvercanunlu.in_memory_cache.service.generator.Generator;
import tr.unvercanunlu.in_memory_cache.service.generator.impl.NumberGenerator;
import tr.unvercanunlu.in_memory_cache.service.generator.impl.TextGenerator;
import tr.unvercanunlu.in_memory_cache.service.generator.impl.UuidGenerator;
import tr.unvercanunlu.in_memory_cache.service.preprocess.Preprocessor;
import tr.unvercanunlu.in_memory_cache.service.preprocess.impl.TextPreprocessor;
import tr.unvercanunlu.in_memory_cache.service.validator.Validator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.NumberValidator;
import tr.unvercanunlu.in_memory_cache.service.validator.impl.TextValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Component {

  // generators
  public static final Generator<String> TEXT_GENERATOR = new TextGenerator();
  public static final Generator<Integer> NUMBER_GENERATOR = new NumberGenerator();
  public static final Generator<UUID> UUID_GENERATOR = new UuidGenerator();

  // validators
  public static final Validator<String> TEXT_VALIDATOR = new TextValidator();
  public static final Validator<Integer> NUMBER_VALIDATOR = new NumberValidator();

  // preprocessors
  public static final Preprocessor<String> TEXT_PREPROCESSOR = new TextPreprocessor();

}
