package tr.unvercanunlu.in_memory_cache.service.cache;

public interface ValidValueCache<V> {

  boolean isValueValid(V value);

}
