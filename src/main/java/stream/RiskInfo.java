package stream;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO，description of RiskInfo
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/1/11 8:14 PM
 */
public class RiskInfo implements Serializable {

    public static void main(String[] args) {

    }

    public static final String DEFAULT_CLIENT_ID = "0";

    public static final Long DEFAULT_GAME_ID = 0L;

    public static final Integer DEFAULT_CHILD_OPERATION = 0;

    private static final long serialVersionUID = 23231442314124L;

    private String loginName;
    private Integer childOperation;
    private String clientId;
    private Integer uid;
    private String mobile;
    private String targetClientId;
    private String realClientId;// 用于评估操作风险

    private String password;
    private String registerType;
    private Long registerTime;
    private String display;
    private boolean registered;

    private String accountId;
    private String version;
    private String nbiDatagram;
    private Integer nbiSceneId;//宙斯盾场景Id

    // 强验手机版本
    // 用来保存请求的clientInfo字符串参数，原因是其他地方有各种各样的clientInfoDto，他们的构造又依赖请求参数的clientInfo，
    // 但不一定都会有传递，其实是不太有扩展性的
    private String clientInfoStr;

    public String getClientInfoStr() {
        return clientInfoStr;
    }

    public void setClientInfoStr(String clientInfoStr) {
        this.clientInfoStr = clientInfoStr;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private RiskInfo(String loginName, String clientId, String registerType, Long registerTime) {
        this.loginName = loginName;
        this.clientId = clientId;
        this.registerTime = registerTime;
        this.registerType = registerType;
    }

    public String getLoginName() {
        return loginName;
    }

    public Integer getChildOperation() {
        return childOperation;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getUid() {
        if (uid == null) {
            setUid();
        }
        return uid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getTargetClientId() {
        return targetClientId;
    }

    public String getRealClientId() {
        return realClientId;
    }

    public void setRealClientId(String realClientId) {
        this.realClientId = realClientId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getVersion() {
        return version;
    }

    public String getNbiDatagram() {
        return nbiDatagram;
    }

    private void setUid() {
        if (!StringUtils.isEmpty(loginName)) {
            try {
                uid = 98765;
            } catch (Exception e) {

                uid = -1;
            }

        }
    }

    public static class RiskInfoBuilder {

        private String loginName;
        private String clientId;
        private String registerType;
        private Long registerTime;

        public RiskInfoBuilder setLoginName(String loginName) {
            this.loginName = loginName;
            return this;
        }

        public RiskInfoBuilder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public RiskInfoBuilder setRegisterType(String registerType) {
            this.registerType = registerType;
            return this;
        }

        public RiskInfoBuilder setRegisterTime(Long registerTime) {
            this.registerTime = registerTime;
            return this;
        }

        public RiskInfo build() {
            return new RiskInfo(loginName, clientId, registerType, registerTime);
        }

    }

    public String getPassword() {
        return password;
    }

    public String getRegisterType() {
        return registerType;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public String getDisplay() {
        return display;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isRegistered() {
        return registered;
    }

    public Integer getNbiSceneId() {
        return nbiSceneId;
    }

    @Override
    public String toString() {
        return "RiskInfo{" + "loginName='" + loginName + '\'' + ", operation=" + ", childOperation=" + childOperation + ", clientId='" + clientId + '\'' + ", clientInfo="
            + ", uid=" + uid + ", longUid=" +  ", mobile='" + mobile + '\'' + ", targetClientId='" + targetClientId + '\'' + ", realClientId='" + realClientId + '\''
            + ", password='" + password + '\'' + ", registerType='" + registerType + '\'' + ", registerTime=" + registerTime + ", display='" + display + '\'' + '}';
    }
}

