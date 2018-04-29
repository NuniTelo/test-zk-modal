package zk.springboot.Models.MainModelStatus;

public class Properties {


    private String propertyName;

    private String propertyType;

    private Boolean required;

    public Properties(String propertyName, String propertyType, Boolean required) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.required = required;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
