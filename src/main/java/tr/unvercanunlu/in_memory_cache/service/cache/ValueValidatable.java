package tr.unvercanunlu.in_memory_cache.service.cache;

@FunctionalInterface
public interface ValueValidatable<V> {

  boolean isValueValid(V value);

}
