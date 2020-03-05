package cn.net.health.tools.design.single.lazy;


/**
 * @author xiyou
 * 1. 相对于SimpleLazySingleton多了一个synchronized 线程安全了
 * 2.  但是效率太差了，不管班长对象有没有被创建好，后面每个线程并发走到这，无用等待太多了
 */
public class SyncLazySingleton {

    private static SyncLazySingleton SYNC_LAZY_INSTANCE = null;

    private SyncLazySingleton() {
    }

    public synchronized static SyncLazySingleton getMonitor() {
        if (SYNC_LAZY_INSTANCE == null) {
            SYNC_LAZY_INSTANCE = new SyncLazySingleton();
        }
        return SYNC_LAZY_INSTANCE;
    }
}