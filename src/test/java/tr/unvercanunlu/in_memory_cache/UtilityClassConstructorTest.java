package tr.unvercanunlu.in_memory_cache;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import tr.unvercanunlu.in_memory_cache.config.AppConfig;
import tr.unvercanunlu.in_memory_cache.service.Component;
import tr.unvercanunlu.in_memory_cache.util.TextUtil;

class UtilityClassConstructorTest {

  @Test
  void utilityClassConstructorsArePrivateAndInstantiableByReflection() throws Exception {
    assertPrivateConstructor(AppConfig.class);
    assertPrivateConstructor(Component.class);
    assertPrivateConstructor(TextUtil.class);
  }

  private static void assertPrivateConstructor(Class<?> type) throws Exception {
    Constructor<?> constructor = type.getDeclaredConstructor();

    assertTrue(Modifier.isPrivate(constructor.getModifiers()));

    constructor.setAccessible(true);

    assertNotNull(constructor.newInstance());
  }
}
