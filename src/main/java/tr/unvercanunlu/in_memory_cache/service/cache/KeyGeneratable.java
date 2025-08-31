package tr.unvercanunlu.in_memory_cache.service.cache;

@FunctionalInterface
public interface KeyGeneratable<K> {

  K generateKey();

}
