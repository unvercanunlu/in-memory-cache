package tr.unvercanunlu.in_memory_cache.service;

import java.util.Optional;

public interface Cache<K, V> {

  K put(K key, V value);

  Optional<V> retrieve(K key);

  void remove(K key);

  boolean checkExists(K key);

  void clear();

  K generateKey();

}
