package zk.springboot.ViewModels;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.MainModelStatus.AssetMainModelStatus;
import zk.springboot.Models.MainModelStatus.Properties;

import java.util.ArrayList;
import java.util.List;

public class AddAssetViewModel {

    List<CategoryModel> categoryModels;

    List<String> listToDisplay;

    AssetMainModelStatus assetMainModelStatus;

    List<Properties> assetMainModelStatusProperties;



    @Init
    public void init() throws Exception {

        categoryModels = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        listToDisplay = new ArrayList<>();
        assetMainModelStatus = ApiService.getAssetMainModelStatus(FilterArgs.MAIN_CATEGORY_STATUS);
        assetMainModelStatusProperties = assetMainModelStatus.getProperties();
        for (Properties items : assetMainModelStatusProperties){
            listToDisplay.add(items.getPropertyName());
        }

    }

    @Command("selectedCategory")
    public void selectedCategory(@ContextParam(ContextType.COMPONENT)Combobox combobox){
        System.out.println(combobox.getValue());

    }

    @Command("click")
    public void click(@ContextParam(ContextType.COMPONENT)Component grid){
        System.out.println(grid.getFellows());
    }

    public List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    public List<String> getListToDisplay() {
        return listToDisplay;
    }

    public void setListToDisplay(List<String> listToDisplay) {
        this.listToDisplay = listToDisplay;
    }
}
