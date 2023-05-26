package feature.jdk9.flow;

import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * <pre>
 * Java9默认实现的{@link SubmissionPublisher}中提供的响应式流的消息发送类。
 * 参考资料：
 * <a href="https://www.cnblogs.com/IcanFixIt/p/7245377.html">Java 9 揭秘（17. Reactive Streams）</a>
 * <a href="https://cloud.tencent.com/developer/article/1899402">(juc系列)flow响应式流接口及submissionpublisher实现</a>
 * </pre>
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 17:28
 */
public class SubmissionPublisherTester {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        //Register Subscriber
        MySubscriber<String> subscriber = new MySubscriber<>();
        publisher.subscribe(subscriber);
        //Publish items
        System.out.println("Publishing Items...");
        String[] items = {"1", "x", "2", "x", "3", "x", "4", "x", "5", "x", "6", "x", "7", "x", "8", "x"};
        Arrays.stream(items).forEach(publisher::submit);
        publisher.close();
        Thread.sleep(20000);
    }
}
class MySubscriber<T> implements Flow.Subscriber<T> {
    private Flow.Subscription subscription;
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("Subscribe");
        this.subscription = subscription;
        subscription.request(2); //这里要使用Long.MAX_VALUE就会被认为获取无穷的数据。
    }
    @Override
    public void onNext(T item) {
        System.out.println("Got : " + item);
        subscription.request(2); //这里也可以使用Long.MAX_VALUE
    }
    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}