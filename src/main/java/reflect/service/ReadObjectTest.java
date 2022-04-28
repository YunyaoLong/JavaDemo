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
}
