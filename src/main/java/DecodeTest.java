import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO，description of DecodeTest
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/3/15 23:20
 */
public class DecodeTest {

    public static void main(String[] args) {
        String conf = "uid = -2147483648 : -1147483648, 2200000000:2300000000\n" + "login_name = -2147483648:-1147483648,2200000000:2300000000\n" + "user_id = ";
        // 首先切割换行符
        Map<String, Map<Long, Long>> collect = Arrays.stream(conf.split("[\n\r]")).map(String::trim)
            // 切割第一层key和value值（对应uid的范围/login_name的范围）
            .map(eachConf -> eachConf.split("="))
            .collect(Collectors.toMap(eachConfSplit -> eachConfSplit.length > 0 ? eachConfSplit[0].trim() : "",
                eachConfSplit -> eachConfSplit.length < 2 ? new HashMap<>(2) :
                // 切割第二层key和value值（对应每一个具体的范围
                Arrays.stream(eachConfSplit[1].split(",")).map(String::trim).filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toMap(((pair) -> new Long(pair.split(":")[0].trim())), (pair) -> new Long(pair.split(":")[1].trim())))));

        System.out.println(collect);

        List<BusiBasicInfo> busiBasicInfoList = new ArrayList<>();
        busiBasicInfoList.add(new BusiBasicInfo("9game", 10));
        busiBasicInfoList.add(new BusiBasicInfo("9game", 8));
        busiBasicInfoList.add(new BusiBasicInfo("9game", 3));
        busiBasicInfoList.add(new BusiBasicInfo("9game", 10));
        busiBasicInfoList.add(new BusiBasicInfo("9game", 8));
        busiBasicInfoList.add(new BusiBasicInfo("linxi", 6));
        busiBasicInfoList.add(new BusiBasicInfo("linxi", 99));
        busiBasicInfoList.add(new BusiBasicInfo("linxi", 6));
        Map<Integer, String> clintIdToBusId = new HashMap<>();
        Map<Integer, Long> clientIdCount = new HashMap<>();
        Map<String, Long> busIdCount = new HashMap<>();
        busiBasicInfoList.forEach(basicInfo -> {
            clientIdCount.compute(basicInfo.getClientId(), (k, v) -> v == null ? 1 : v + 1);
            clintIdToBusId.compute(basicInfo.getClientId(), (k, v) -> basicInfo.getBusId());
        });
        System.out.println(clientIdCount);
        clientIdCount.forEach((clientK, clientV) -> busIdCount.compute(clintIdToBusId.getOrDefault(clientK, ""), (busK, busV) -> busV == null ? clientV : busV + clientV));
        System.out.println(busIdCount);
    }

    static class BusiBasicInfo {
        String busId;
        Integer clientId;

        public String getBusId() {
            return busId;
        }

        public void setBusId(String busId) {
            this.busId = busId;
        }

        public Integer getClientId() {
            return clientId;
        }

        public void setClientId(Integer clientId) {
            this.clientId = clientId;
        }

        public BusiBasicInfo(String busId, Integer clientId) {
            this.busId = busId;
            this.clientId = clientId;
        }
    }
}
