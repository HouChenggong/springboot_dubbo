```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiyou
 * springIOC实现单例模式
 * 懒汉式实现，容器式单例
 */
public class ContainerSingleton {
    private ContainerSingleton() {

    }

    private static Map<String, Object> ioc = new ConcurrentHashMap<>(8);

    public static Object getInstance(String className) {
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object o = null;
                try {
                    o = Class.forName(className).newInstance();
                    ioc.put(className, o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return o;
            } else {
                return ioc.get(className);
            }
        }
    }


}
```