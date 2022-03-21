package stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * TODO，description of StreamSerialized
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/1/10 3:58 PM
 */
public class StreamSerialized {
    public static void main(String[] args) {

        System.out.println(Integer.numberOfLeadingZeros(-1));
        System.out.println(Integer.numberOfLeadingZeros(-125));
        System.out.println(Integer.numberOfLeadingZeros(-124));
        System.out.println(Integer.numberOfLeadingZeros(1));
        System.out.println(Integer.numberOfLeadingZeros(125));
        System.out.println(Integer.numberOfLeadingZeros(124));
        System.out.println(Integer.toBinaryString(125));

        System.out.println("  Hello world!  ".trim());

    }

    private static byte[] toByteArray(Object mqMessage) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut = null;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(mqMessage);
            sOut.flush();
            byte[] bytes = out.toByteArray();
            return bytes;
        } catch (IOException e) {
            System.out.println(String.format("toByteArray error. e=%s", e.getMessage()));
        } finally {
            if (sOut != null) {
                try {
                    sOut.close();
                } catch (IOException e) {
                    System.out.println(String.format("toByteArray error when sOut.close(). e=%s", e.getMessage()));
                }
            }
        }
        return null;
    }

    private static Object toObject(byte[] data) {

        if (data == null) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream sIn = null;
        try {
            sIn = new ObjectInputStream(in);
            return sIn.readObject();
        } catch (Exception e) {
            System.out.println(String.format("toObject error. e=%s", e.getMessage()));
            e.printStackTrace();
        } finally {
            if (sIn != null) {
                try {
                    sIn.close();
                } catch (IOException e) {
                    System.out.println(String.format("toObject error when sIn.close(). e=%s", e.getMessage()));
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public interface MQMessage extends Serializable {
    }

    public static class AccountLoginEventDto implements MQMessage {

        private static final long serialVersionUID = 7247912686080432532L;

        private Long uid;

        private String password;

        public AccountLoginEventDto(Long uid, String password) {
            this.uid = uid;
            this.password = password;
        }

        public Long getUid() {
            return uid;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "AccountLoginEventDto [uid=" + uid + ", password=" + password + "]";
        }

    }
}
