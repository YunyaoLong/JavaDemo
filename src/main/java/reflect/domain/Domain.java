package reflect.domain;

/**
 * TODO，description of Domain
 *
 * @author yunyaolang
 * @version V1.0
 * @date 2022/4/27 10:34
 */
public class Domain {
    private Integer intField;

    private String strField;

    public Domain(Integer intField, String strField) {
        this.intField = intField;
        this.strField = strField;
    }

    public Domain() {
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public String getStrField() {
        return strField;
    }

    public void setStrField(String strField) {
        this.strField = strField;
    }
}
