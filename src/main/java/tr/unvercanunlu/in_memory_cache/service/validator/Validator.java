package tr.unvercanunlu.in_memory_cache.service.validator;

@FunctionalInterface
public interface Validator<K> {

  boolean isValid(K value);

}
