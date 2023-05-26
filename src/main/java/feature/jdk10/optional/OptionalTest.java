package feature.jdk10.optional;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Optional.orElseThrow()
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/27 01:00
 */
public class OptionalTest {
    public static void main(String[] args) {
        testOrElseThrow();
    }

    public static void testOrElseThrow() {
        // 引入的var只是为了简化代码，注意var只能用于局部变量。它不能用于类成员变量，方法参数等。
        var data = List.of("a", "b", "c");
        var optional = data.stream().filter(s -> s.startsWith("z")).findAny();
        try {
            var res = optional.orElseThrow();
            System.out.println(res);
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
