package zk.springboot.Models.CategoryModel;

import java.util.List;


public class CategoryModel {

    private String id;

    //this is going to be subcategory name
    private String categoryName;

    //this is going to be the main level for a particular category
    private String parent;

    private List<SpecificPropertiesModel> properties;

    public CategoryModel(String id, String categoryName, String parent, List<SpecificPropertiesModel> properties) {
        this.id = id;
        this.categoryName = categoryName;
        this.parent = parent;
        this.properties = properties;
    }

    public CategoryModel() {
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

    public List<SpecificPropertiesModel> getProperties() {
        return properties;
    }

    public void setProperties(List<SpecificPropertiesModel> properties) {
        this.properties = properties;
    }
}

