package tr.unvercanunlu.in_memory_cache.service.cache;

@FunctionalInterface
public interface KeyValidatable<K> {

  boolean isKeyValid(K key);

}
