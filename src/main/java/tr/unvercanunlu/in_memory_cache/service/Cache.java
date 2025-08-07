package tr.unvercanunlu.in_memory_cache.service;

import java.util.Optional;

public interface Cache<K, V> {

  K store(K key, V value);

  Optional<V> retrieve(K key);

  void evict(K key);

  boolean checkExists(K key);

  void clear();

  int size();

  K generateKey();

}
