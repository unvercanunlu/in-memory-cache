package tr.unvercanunlu.in_memory_cache.validation;

public interface Validator<K> {

  boolean isInvalid(K value);

}
