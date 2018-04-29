package zk.springboot.Models.MainModelStatus;

import java.util.List;

public class AssetMainModelStatus {


    private String id;

    private String categoryName;

    private String parent;

    private List<Properties> properties;

    public AssetMainModelStatus(String id, String categoryName, String parent, List<Properties> properties) {
        this.id = id;
        this.categoryName = categoryName;
        this.parent = parent;
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

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }
}
