package feature.jdk10.variable;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * var，本质上还是类型推导。
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/27 01:58
 */
public class VariableTest {
    public static void main(String[] args) {
        testVariable();
    }

    private static void testVariable() {
        // OK: both declare variables of type PriorityQueue<Item>
        PriorityQueue<String> itemQueue0 = new PriorityQueue<>();
        var itemQueue1 = new PriorityQueue<String>();

        // DANGEROUS: infers as PriorityQueue<Object>
        var itemQueue2 = new PriorityQueue<>();
        itemQueue2.add("123");
        // 这一行虽然编译不会报错，但是因为String和Integer无法比较，因此会出现运行时错误
        try {
            itemQueue2.add(456);
            System.out.println("itemQueue2( PriorityQueue<Object> ): " + itemQueue2);
        } catch (ClassCastException exception) {
            System.out.println(exception.getMessage());
        }

        // DANGEROUS: infers as List<Object>
        var list = List.of();

        // OK: itemQueue infers as PriorityQueue<String>
        Comparator<String> comp = Comparator.comparing(String::toString);
        var itemQueue3 = new PriorityQueue<>(comp);
        itemQueue3.add("123");
        // 如果可以推导出来明确的泛型，则下方语句禁止执行（因为已经向下限定了String类型及其子类型）
        // itemQueue3.add(456);

        // OK: infers as List<BigInteger>
        var list2 = List.of(BigInteger.ZERO);
    }
}
