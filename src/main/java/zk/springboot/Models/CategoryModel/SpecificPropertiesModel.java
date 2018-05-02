package zk.springboot.Models.CategoryModel;

public class SpecificPropertiesModel {

    private String propertyName;
    private String propertyType;
    private boolean required;
    private String instruction;

    public SpecificPropertiesModel(String propertyName, String propertyType, boolean required, String instruction) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.required = required;
        this.instruction = instruction;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}

