/**
 * TODO，description of RsaUtil
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/3/2 11:33 AM
 */

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA加解密工具类
 *
 * @time 2020-07-23
 */
public class RsaUtil {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateK);
            signature.update(data);
            return new String(Base64.getEncoder().encode(signature.sign()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String privateKey =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoAePj1sBKGguusFBWER1eQLQjQtVgr7U7dobpxH/sGIOHsy0BRwxjz2PXFpK/GLIeWMmlXVVkNwgyi6cNS2Ba7sObHuYvX9euoChxEDsIvcwSv7cQjl25jgyl+5o/Yb6enA23XcjOgiXgXW8PJhDi2xPWmUVzjgCzciYsfjRjRljWZVjOZK+fY5DLbk5Hg3YyycKddb3JBHxGsfu/KdtJOFb8uEDrvr1xIPGzx3TcmqYclszXeiEYPdMUtVAeWiMqDZF0YjDpLXmsZK19A9rj4ONxSshaMXCNVsOFc6F+sPIkysh6vD+VzkriQYBParS1MZvWfSp5udEo6+SOQTHdAgMBAAECggEAcNekKvmoeZz4z8IYk1Y3dYDb/Pr8uk4J2gCIKScUDI2Cenh79+myhq9bDbJZqgR6TiuwdGM14XOdC+YEs8c21pybwOqGDLfA88caoqYWII7qLIWb/YDBrsmb8TgGqB+MmpeSFuDk6N89dW8MEw6sNNKUhoS3wx9TlTNMNIInyaz1ZT2IJxOpGDB4sYu17veKsRVp5Y2Lu9IQyAmUBkomiO/+lXJEc9Lds4MH0EvCBupog/hDB/tmwkV7Es5yBWww/Mhqbf3L9fEGuUqAjbbLe6ilpOTNr7upZI2gf3zy3MR+F+n3fFRAabWv1Weq2w5tyLRk59QXIkMgDBOAVyD8QQKBgQDW+zFHkxKTr6yqDSa4AorSg7AsMvGuaVTjNHiHTogLNuC3YoXPJkUH9S6G2CvRsU2y2Hd6o6l3FILIP+iygtz0rX9D0CYiY45jZAnUQLsIkNaSqwnhBI9IZqRATC3v45wE2xm+ZP3U+dQX1jONADUQJXGlRvDEHJR1OctR8UlcKQKBgQDIED/vYVV7NjRazad+G0xaokUJnCC97tZW3Q3k5SUVEGKVTMufIlWhZOl/+R6tWsFIAAm9GG6JHFSAe7cycjgEhpL72IF2eaKYjgVkXxoFSptLwJMfxJdSPqQPXc9x4LxM+oy9mvxB4yOQZWBh0Sxy7zuQFcJ+K/RRh0hrj+velQKBgQCQfaRRhvglax2R7eToKE1F0FzIWDU4FHOJgerAhnfJkcjXeNCWuhILly7+Uqyg85pVakhjlTyBXLfJ/6pW0L3RdgrjeTMoSeGZy9MPZbJPv4l4apzpMDddYh9jV9BIO6LRYLGc1iQE0SVMIBvl2Q3RHFmVzGhmZw8g+wc8u/+igQKBgCGnGhoaNVbUd1oO3e3Iib91LEfBi4BnfIaACKZIJUS1zN9wW5AZJm5Pmzd7oD1o1rpVx02XnoBR06UJKnfB3spkGLVkZeKX81HWEfIgTTA2rIjBi/38A7hB8TbkgNCXDRWlz6bVOzmaC5FufBf4J5HtzAcgoea3XhGYwAXX38VBAoGAYcodTFEKeeS+3dRuVFqVeL4nWadTIPaF+qRqR7CCrBdqhTfXi7lNYj//guMgS3sEmwyI/ECe8Fzld9egz16O+xgWq2VnL784RwkeZUaqW9mZ9KXcrQIonXz/bPiAWfuN91io3xnBIpTQjuqFalOSHufFNbuV4VflHVwDXs6JB5U=";

        String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqAHj49bAShoLrrBQVhEdXkC0I0LVYK+1O3aG6cR/7BiDh7MtAUcMY89j1xaSvxiyHljJpV1VZDcIMounDUtgWu7Dmx7mL1/XrqAocRA7CL3MEr+3EI5duY4MpfuaP2G+npwNt13IzoIl4F1vDyYQ4tsT1plFc44As3ImLH40Y0ZY1mVYzmSvn2OQy25OR4N2MsnCnXW9yQR8RrH7vynbSThW/LhA6769cSDxs8d03JqmHJbM13ohGD3TFLVQHlojKg2RdGIw6S15rGStfQPa4+DjcUrIWjFwjVbDhXOhfrDyJMrIerw/lc5K4kGAT2q0tTGb1n0qebnRKOvkjkEx3QIDAQAB";

        String appKey = "k2Pa70v03qM0m42J8unv2W0XqHhCmtQC";

        String timeStamp = String.valueOf(System.currentTimeMillis());

        String mess = String.valueOf((int)(Math.random() * 99999999));

        String content =
            "data=zpgKjZpjpaDpY2tANSzlhhvThGqXLVhfWMVDlNIOYwJVY3OYy0+QO5Y2BhRHE7Dg0q0gfkhCufdTVfPS/mVRhmyn56kpFyK++Aq5axVvCzAZ2GYSRYcQQvgAM8h9ioLvHQ5hlJ9Uk3rbBBvzv8Um2QMijHYjcg5dbvxKHm6FyjB8xAhvGiUhV/KgJeziAnASlUUWG3S4J6Pc5AC31oDY9SAPASZyU3K2oS5uJOtTp6eS3M0wc4LQX8DIQdf6p+l0JkghNH0cgLy1RYMQdkytlifX67vOXF+o"
                + "&mess=" + mess + "&timestamp=" + timeStamp + "&key=" + appKey;

        System.out.println("content = " + content);

        String sign = sign((content).getBytes(), privateKey);

        System.out.println("mess = " + mess);

        System.out.println("timeStamp = " + timeStamp);

        System.out.println("sign = " + sign);

        assert sign != null;
        System.out.println("鉴权结果：" + verify((content).getBytes(), publicKey, sign));
    }
}