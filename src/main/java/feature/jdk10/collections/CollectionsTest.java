package feature.jdk10.collections;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TODO，description of CollectionsTest
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/27 01:08
 */
public class CollectionsTest {
    public static void main(String[] args) {
        testCollectionCopyOf();
    }

    public static void testCollectionCopyOf() {
        List<String> list = IntStream.rangeClosed(1, 10).mapToObj(i -> "num" + i).collect(Collectors.toList());
        // 集合增加了一个copyOf函数，同样返回不可修改的集合
        List<String> newList = List.copyOf(list);
        try {
            newList.add("not allowed");
        } catch (UnsupportedOperationException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
