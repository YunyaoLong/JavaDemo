package feature.jdk9.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试集合的新接口(of函数)
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 11:21
 */
public class CollectionsTest {
    public static void main(String[] args) {
        System.out.println("================= testSetOf() =================");
        testSetOf();
        System.out.println("================ testListOf() =================");
        testListOf();
        System.out.println("============== testCollectionOf() =============");
        testCollectionOf();
    }

    private static void testSetOf() {
        Set<String> newFuncOfSet;
        newFuncOfSet = Set.of("1", "2", "3", "4");
        // 构造出来的Set是ImmutableCollections，只读
        System.out.println(newFuncOfSet);
        System.out.println(newFuncOfSet.contains("1"));
        System.out.println(newFuncOfSet.containsAll(List.of("1", "2", "1")));

        try {
            // 默认的Set.of构造方法禁止出现重复元素
            newFuncOfSet = Set.of("1", "2", "3", "4", "1");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            // 默认的Set.of构造方法禁止插入元素，参见{@link ImmutableCollections.AbstractImmutableCollection}
            newFuncOfSet.add("5");
        } catch (UnsupportedOperationException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void testListOf() {
        List<String> newFuncOfList;
        newFuncOfList = List.of("1", "2", "3", "4");
        // 构造出来的Set是ImmutableCollections，只读
        System.out.println(newFuncOfList);
        System.out.println(newFuncOfList.contains("1"));
        System.out.println(newFuncOfList.containsAll(List.of("1", "2", "1")));

        try {
            // 默认的List.of构造方法禁止出现重复元素
            newFuncOfList = List.of("1", "2", "3", "4", "1");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        try {
            // 默认的List.of构造方法禁止变更元素
            newFuncOfList.add("5");
        } catch (UnsupportedOperationException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void testCollectionOf() {
        Map<String, Integer> newFuncOfMap;
        newFuncOfMap = Map.of("key1", 1, "key2", 2);
        System.out.println(newFuncOfMap);

        try {
            // 默认的Map.of构造方法禁止出现重复key
            newFuncOfMap = Map.of("key1", 1, "key2", 2, "key1", 3);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
