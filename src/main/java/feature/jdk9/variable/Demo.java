package feature.jdk9.variable;

import java.util.Arrays;

/**
 * TODO，description of Demo
 *
 * @author yunyaolang
 * @version V1.0
 * @since 2023/5/26 15:46
 */
public class Demo {
    public int count = 1;
    protected long sum = 100;
    private String name = "init";
    public int[] arrayData = new int[] {3, 5, 7};

    @Override
    public String toString() {
        return "Demo{" + "name='" + name + '\'' + ", count=" + count + ", sum=" + sum + ", data=" + Arrays.toString(arrayData) + '}';
    }
}
