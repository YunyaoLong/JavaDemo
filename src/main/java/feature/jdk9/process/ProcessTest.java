package feature.jdk9.process;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * <pre>
 * 测试Java9的进程控制函数
 * 参考：<a href="https://segmentfault.com/a/1190000013496056">java9系列(四)Process API更新</a>
 * </pre>
 *
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 11:52
 */
public class ProcessTest {
    public static void main(String[] args) throws IOException {
        System.out.println("============== testProcessHandle() =============");
        testProcessHandle();

        System.out.println("============== testControlProcess() =============");
        testControlProcess();
    }
    public static void testProcessHandle() {
        // 打印当前进程的信息
        final ProcessHandle processHandle = ProcessHandle.current();
        final ProcessHandle.Info info = processHandle.info();
        System.out.println("Process info =>");
        System.out.format("PID: %s%n", processHandle.pid());
        info.arguments().ifPresent(args -> System.out.format("Arguments: %s%n", Arrays.toString(args)));
        info.command().ifPresent(command -> System.out.format("Command: %s%n", command));
        info.commandLine().ifPresent(commandLine -> System.out.format("Command line: %s%n", commandLine));
        info.startInstant().ifPresent(startInstant -> System.out.format("Start time: %s%n", startInstant));
        info.totalCpuDuration().ifPresent(cpuDuration -> System.out.format("CPU time: %s%n", cpuDuration));
        info.user().ifPresent(user -> System.out.format("User: %s%n", user));
    }

    /**
     * 尝试在Java代码中执行top命令
     *
     * @throws IOException 进程执行过程中的io异常
     */
    public static void testControlProcess() throws IOException {
        final ProcessBuilder processBuilder = new ProcessBuilder("top")
            .inheritIO();
        // 执行top这个command命令，同时返回这个进程的handler句柄
        ProcessHandle processHandle = processBuilder.start().toHandle();

        // 申请一个倒计时锁存器，防止进程还未打印完成就被kill
        final CountDownLatch latch = new CountDownLatch(1);

        // 说明进程结束时，执行的步骤
        processHandle.onExit().whenCompleteAsync((handle, throwable) -> {
            if (throwable == null) {
                // 打印"top"命令行执行的进程pid
                System.out.println(handle.pid());
            } else {
                throwable.printStackTrace();
            }
            latch.countDown();
        });

        // Main函数的现成控制器，和"top"命令一起技师，防止拉起的进程结束的比Main晚
        final Thread shutdownThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            // 等待top命令进程结束之后，尝试销毁进程
            if (processHandle.supportsNormalTermination()) {
                processHandle.destroy();
            } else {
                processHandle.destroyForcibly();
            }
        });
        shutdownThread.start();
        try {
            shutdownThread.join();
            latch.await();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
