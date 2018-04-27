package zk.springboot.Models.CategoryModel;

public class SpecificPropertiesModel {

    String propertyName;
    String propertyType;

    public SpecificPropertiesModel(String propertyName, String propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public SpecificPropertiesModel() {
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
}
