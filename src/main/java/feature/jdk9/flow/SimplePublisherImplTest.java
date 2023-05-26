package feature.jdk9.flow;

import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.stream.IntStream;

/**
 * <pre>
 * Flow标准接口。用于满足响应式发布订阅流（Reactive Streams publish-subscribe framework）。
 * 参见<a href="https://openjdk.org/jeps/266">JEP 266: More Concurrency Updates</a>
 * </pre>
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 16:53
 */
public class SimplePublisherImplTest {
    public static void main(String[] args) {
        new PublisherTester(10).subscribe(new SubscriberTester());
    }
}

class PublisherTester implements Flow.Publisher<Integer> {
    private final Iterator<Integer> iterator;

    PublisherTester(int count) {
        this.iterator = IntStream.rangeClosed(1, count).iterator();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        iterator.forEachRemaining(subscriber::onNext);
        subscriber.onComplete();
    }
}

class SubscriberTester implements Flow.Subscriber<Integer> {
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // 这个接口由Flow.Publisher在订阅接口中主动调用，在当前demo中不会别调用到
        System.out.println("subscribe: " + System.identityHashCode(this));
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("item = [" + item + "]");
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
        System.out.println("complete");
    }
}