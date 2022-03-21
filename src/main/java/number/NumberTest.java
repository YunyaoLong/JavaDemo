package number;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO，description of NumberTest
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2021/12/6 5:13 PM
 */
public class NumberTest {
    public static void main(String[] args) {
        Map<String, Object> src = new HashMap<>();
        src.put("hahaha", Long.MAX_VALUE % (1L << 40));
        // src.put("hahaha", 36028797018963967);
        String jsonString = JSON.toJSONString(src);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Object hahaha = jsonObject.get("hahaha");
        System.out.println(hahaha.getClass().getSimpleName());
        System.out.println(hahaha);

        Long aLong = Long.valueOf(new String(Integer.valueOf(987654321).toString().getBytes(StandardCharsets.UTF_8)));
        System.out.println(aLong);

        System.out.println(Long.parseLong("1234"));

        long longVal = 9876543210L;
        Long longValObj = Long.valueOf(longVal);
        System.out.println(longValObj.intValue());
        System.out.printf("longVal: %d%n", longVal);

        System.out.println(Double.valueOf(longValObj.toString()));

        Object obj = 1;
        System.out.println(obj.getClass().getName());
        System.out.println(obj instanceof Number);

        Arrays.stream(TestObj.class.getDeclaredFields()).map(Field::getType).map(Class::getName).forEach(System.out::println);

        double number = 9876543210.0f;
        System.out.println((int)number);
        System.out.println(Double.valueOf(number).intValue());
        long longNUmber = 9876543210L;
        System.out.println((int)longNUmber);
        System.out.println(Long.valueOf(longNUmber).intValue());
        System.out.println(Long.MAX_VALUE);
        double doubleNumber = -9223372036854775899.0f;
        System.out.println(rangeVerify(doubleNumber, Long.class));
    }

    private static Object rangeVerify(Object value, Class<?> targetType) {
        // 如果是基本类型或者不是Number类型，直接返回
        if (!(value instanceof Number)) {
            return value;
        }

        if (!NUM_IS_DECIMAL_CACHE.containsKey(targetType)) {
            return value;
        }
        // 转换成小数可以不用考虑精度转换异常
        if (NUM_IS_DECIMAL_CACHE.get(targetType)) {
            if (Float.class.equals(targetType) || float.class.equals(targetType)) {
                return ((Number)value).floatValue();
            } else if (Double.class.equals(targetType) || double.class.equals(targetType)) {
                return ((Number)value).doubleValue();
            } else {
                throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
            }
        }
        // 转换成整数
        if (!NUM_IS_DECIMAL_CACHE.get(targetType)) {
            if (!NUM_IS_DECIMAL_CACHE.get(value.getClass())) {
                // 如果是整数转整数不能降低精度
                if (NUM_BITS_CACHE.get(targetType) < NUM_BITS_CACHE.get(value.getClass())) {
                    throw new RuntimeException(value.getClass().getName() + " can not cast to target type " + targetType.getName());
                }
            }
        }
        double doubleValue = ((Number)value).doubleValue();
        if (Short.class.equals(targetType) || short.class.equals(targetType)) {
            if (doubleValue > Short.MAX_VALUE || doubleValue < Short.MIN_VALUE) {
                throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
            }
            return ((Number)value).shortValue();
        } else if (Byte.class.equals(targetType) || byte.class.equals(targetType)) {
            if (doubleValue > Byte.MAX_VALUE || doubleValue < Byte.MIN_VALUE) {
                throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
            }
            return ((Number)value).byteValue();
        } else if (Integer.class.equals(targetType) || int.class.equals(targetType)) {
            if (doubleValue > Integer.MAX_VALUE || doubleValue < Integer.MIN_VALUE) {
                throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
            }
            return ((Number)value).intValue();
        } else if (Long.class.equals(targetType) || long.class.equals(targetType)) {
            if (doubleValue > Long.MAX_VALUE || doubleValue < Long.MIN_VALUE) {
                throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
            }
            return ((Number)value).longValue();
        } else {
            throw new RuntimeException(value.getClass().getName() + "(" + value + ") can not cast to target type " + targetType.getName());
        }
    }

    private static final Map<Class<?>, Integer> NUM_BITS_CACHE = new HashMap<>();

    private static final Map<Class<?>, Boolean> NUM_IS_DECIMAL_CACHE = new HashMap<>();

    static {
        // 缓存精度
        NUM_BITS_CACHE.put(Byte.class, Byte.SIZE);
        NUM_BITS_CACHE.put(Short.class, Short.SIZE);
        NUM_BITS_CACHE.put(Integer.class, Integer.SIZE);
        NUM_BITS_CACHE.put(Long.class, Long.SIZE);
        NUM_BITS_CACHE.put(Float.class, Float.SIZE);
        NUM_BITS_CACHE.put(Double.class, Double.SIZE);
        NUM_BITS_CACHE.put(byte.class, Integer.SIZE);
        NUM_BITS_CACHE.put(short.class, Integer.SIZE);
        NUM_BITS_CACHE.put(int.class, Integer.SIZE);
        NUM_BITS_CACHE.put(long.class, Integer.SIZE);
        NUM_BITS_CACHE.put(float.class, Integer.SIZE);
        NUM_BITS_CACHE.put(double.class, Integer.SIZE);
        // 缓存是否是小数
        NUM_IS_DECIMAL_CACHE.put(Byte.class, false);
        NUM_IS_DECIMAL_CACHE.put(Short.class, false);
        NUM_IS_DECIMAL_CACHE.put(Integer.class, false);
        NUM_IS_DECIMAL_CACHE.put(Long.class, false);
        NUM_IS_DECIMAL_CACHE.put(Float.class, true);
        NUM_IS_DECIMAL_CACHE.put(Double.class, true);
        NUM_IS_DECIMAL_CACHE.put(byte.class, false);
        NUM_IS_DECIMAL_CACHE.put(short.class, false);
        NUM_IS_DECIMAL_CACHE.put(int.class, false);
        NUM_IS_DECIMAL_CACHE.put(long.class, false);
        NUM_IS_DECIMAL_CACHE.put(float.class, true);
        NUM_IS_DECIMAL_CACHE.put(double.class, true);

    }
    class TestObj {
        int a;
        Integer b;
    }
}
