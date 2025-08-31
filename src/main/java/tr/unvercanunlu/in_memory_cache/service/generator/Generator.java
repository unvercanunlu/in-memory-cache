package tr.unvercanunlu.in_memory_cache.service.generator;

@FunctionalInterface
public interface Generator<T> {

  T generate();

}
