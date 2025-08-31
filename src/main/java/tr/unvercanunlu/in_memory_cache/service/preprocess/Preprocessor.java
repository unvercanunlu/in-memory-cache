package tr.unvercanunlu.in_memory_cache.service.preprocess;

@FunctionalInterface
public interface Preprocessor<T> {

  T preprocess(T raw);

}
