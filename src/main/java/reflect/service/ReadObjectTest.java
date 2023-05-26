package reflect.service;

import reflect.domain.Domain;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * TODO，description of ReadObjectTest
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/4/27 10:33
 */
public class ReadObjectTest {
    public static void main(String[] args) {
        CloneableDomain cloneableDomain = new CloneableDomain();
        CloneableDomain cloneableDomain1 = cloneableDomain.clone();

        System.out.println(cloneableDomain1 == cloneableDomain);
        System.out.println(cloneableDomain1.domain == cloneableDomain.domain);

        System.out.println(null instanceof String);

        Domain domain = new Domain(10, "20");
        List<String> filedList = Arrays.asList("intField", "strField");
        try {
            for (String fieldName : filedList) {
                getValue(domain, fieldName);
            }
        } catch (NoSuchFieldException e) {
            System.out.println(e);
        }
    }

    private static void getValue(Domain domain, String filedName) throws NoSuchFieldException {
        Class<? extends Domain> domainClass = domain.getClass();
        Field intField = domainClass.getDeclaredField(filedName);
        intField.setAccessible(true);
        try {
            System.out.println(intField.get(domain));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        intField.setAccessible(false);
    }

    static class CloneableDomain implements Cloneable {
        private Domain domain = new Domain();

        @Override
        public CloneableDomain clone() {
            try {
                CloneableDomain clone = (CloneableDomain)super.clone();
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
