import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * TODO，description of TripleDes
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/3/2 11:14 AM
 */
public class TripleDes {
    public static byte[] tripleDesEncrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return tripleDesEncrypt(content, key, icv);
    }

    protected static byte[] tripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(key, "DESede");
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(content);
    }

    public static byte[] tripleDesDecrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return tripleDesDecrypt(content, key, icv);
    }

    protected static byte[] tripleDesDecrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(key, "DESede");
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(content);
    }


/*    public static void main(String[] args) throws Exception {
        byte[] content = ("\"{\"order_id\": \"201609010016562012987\",\"dealer_id\":\n" + "\"2736123\",\"broker_id\": \"xinjingji\",\"real_name\": \"张三\",\"card_no\":\n"
            + "\"6228880199872220\",\"phone_no\": \"13488795491\",\"id_card\":\n" + "\"123532612312312321\",\"pay\": \"100000.00\"}\"").getBytes(StandardCharsets.UTF_8);
        byte[] key = "123456788765432112345678".getBytes("utf-8");
        byte[] enc = tripleDesEncrypt(content, key);
        byte[] enc64 = Base64.getEncoder().encode(enc);
        System.out.println("encrypt: " + new String(enc64));
        byte[] dec64 = Base64.getDecoder().decode(enc64);
        byte[] dec = tripleDesDecrypt(dec64, key);
        System.out.println("decrypt: " + new String(dec));
    }*/



    public static void main(String[] args) {
        String thirdDesKey = "svE7tdP6LYl666W53X1pf8ep";
        JSONObject data = new JSONObject();
        data.put("order_id", UUID.randomUUID().toString());
        data.put("dealer_id", "28056708");
        data.put("broker_id", "27532644");
        data.put("real_name", "姚云龙");
        data.put("card_no", "15919163939");
        data.put("id_card", "431228199607291617");
        data.put("phone_no", "15919163939");
        data.put("pay", "0.01");
        byte[] encryptedData = null;
        try {
            encryptedData = tripleDesEncrypt(JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8), thirdDesKey.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert encryptedData != null;
        System.out.println(new String(Base64.getEncoder().encode(encryptedData)));
    }
}
