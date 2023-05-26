package feature.jdk9.variable;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 线程安全的对Object中filed值替换的操作类{@link VarHandle}，用于替代{@link java.util.concurrent.atomic}和{@link sun.misc.Unsafe}中的类。
 * 参考：<a href="https://segmentfault.com/a/1190000013544841">java9系列(七)Variable Handles</a>
 * </pre>
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 14:52
 */
public class VariableHandlesTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        // 测试public字段的替换
        testSetPublicField();
        // 测试protect字段的替换
        testSetProtectedField();
        // 测试私有字段的替换
        testSetPrivateField();
        // 测试数组的替换
        testSetArray();
        // 测试安全性
        testThreadSafety();
    }

    /**
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void testSetPublicField() throws NoSuchFieldException, IllegalAccessException {
        Demo instance = new Demo();
        VarHandle countHandle = MethodHandles.lookup().in(Demo.class).findVarHandle(Demo.class, "count", int.class);
        countHandle.set(instance, 99);
        System.out.println(instance);
    }

    public static void testSetProtectedField() throws NoSuchFieldException, IllegalAccessException {
        Demo instance = new Demo();
        VarHandle countHandle = MethodHandles.lookup().in(Demo.class).findVarHandle(Demo.class, "sum", long.class);
        countHandle.set(instance, 99999);
        System.out.println(instance);
    }

    public static void testSetPrivateField() throws NoSuchFieldException, IllegalAccessException {
        Demo instance = new Demo();
        VarHandle countHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup()).findVarHandle(Demo.class, "name", String.class);
        countHandle.set(instance, "hello world");
        System.out.println(instance);
    }

    public static void testSetArray() {
        Demo instance = new Demo();
        VarHandle arrayVarHandle = MethodHandles.arrayElementVarHandle(int[].class);
        arrayVarHandle.compareAndSet(instance.arrayData, 0, 3, 100);
        arrayVarHandle.compareAndSet(instance.arrayData, 1, 5, 300);
        arrayVarHandle.compareAndSet(instance.arrayData, 2, 10, 500);
        System.out.println(instance);
    }

    public static void testThreadSafety() throws InterruptedException {
        Demo instance = new Demo();

        ExecutorService es = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            es.execute(() -> {
                VarHandle countHandle;
                try {
                    countHandle = MethodHandles.lookup().in(Demo.class).findVarHandle(Demo.class, "sum", long.class);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                countHandle.getAndAdd(instance, 1);
            });
        }
        es.shutdown();
        boolean termination = es.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(instance);
    }
}
