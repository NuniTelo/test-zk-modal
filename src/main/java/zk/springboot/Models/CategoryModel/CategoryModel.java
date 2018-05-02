package zk.springboot.Models.CategoryModel;

import java.util.List;


public class CategoryModel {

    private String id;

    //this is going to be subcategory name
    private String categoryName;

    //this is going to be the main level for a particular category
    private String parent;

    //if true -> required and if false not required
    private String required;


    private List<SpecificPropertiesModel> properties;


    public CategoryModel() {
    }

    public CategoryModel(String id, String categoryName, String parent, String required, List<SpecificPropertiesModel> properties) {
        this.id = id;
        this.categoryName = categoryName;
        this.parent = parent;
        this.required = required;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public List<SpecificPropertiesModel> getProperties() {
        return properties;
    }

    public void setProperties(List<SpecificPropertiesModel> properties) {
        this.properties = properties;
    }
}


