package tr.unvercanunlu.in_memory_cache.service.cache;

public interface ValidKeyCache<K> {

  boolean isKeyValid(K key);

}
