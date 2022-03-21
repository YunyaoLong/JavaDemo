package number;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * TODO，description of Number2Test
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/3/14 10:56 AM
 */
public class Number2Test {

    static HashSet<Character> potentialUidChar = new HashSet<>(Arrays.asList('-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    static HashSet<Character> potentialUiListChar = new HashSet<>(Arrays.asList('-', ',', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

    static int CHECK_RANGE_MIN = (int)(1L << 31);

    /**
     * 降精为Integer.MIN_VALUE + 10_0000_0000
     */
    static int CHECK_RANGE_MAX = (int)((1L << 31) + 10_0000_0000);

    public static void main(String[] args) {
        System.out.println("1L << 32: " + (1L << 32));
        System.out.println("CHECK_RANGE_MIN: " + CHECK_RANGE_MIN);
        System.out.println("CHECK_RANGE_MAX: " + CHECK_RANGE_MAX);
        System.out.println("Integer.MIN_VALUE: " + Integer.MIN_VALUE);
        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println((int)2200001000L);

        System.out.println(JSON.toJSONString(1));

        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("uid", -2147482648);
        String jsonStr = jsonObject.toJSONString();
        System.out.println(jsonStr);

        int index = jsonStr.indexOf("uid");
        if (index != -1) {
            System.out.println(index);
        }
        // 越过":两个字符
        int uidNumberIndexBegin = index + "uid".length() + 2;
        int uidNumberIndexEnd = uidNumberIndexBegin;
        while (uidNumberIndexEnd < jsonStr.length() && potentialUidChar.contains(jsonStr.charAt(uidNumberIndexEnd))) {
            ++uidNumberIndexEnd;
        }
        if (uidNumberIndexEnd != uidNumberIndexBegin) {
            String uidStr = jsonStr.substring(uidNumberIndexBegin, uidNumberIndexEnd).trim();
            System.out.println("uidStr: " + uidStr);
        }

    }
}
