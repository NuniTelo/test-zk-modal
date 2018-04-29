package zk.springboot.ViewModels;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.event.PagingEvent;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.AssetMainModel;
import zk.springboot.Models.CategoryModel.CategoryModel;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ListViewModel {
    private ListModelList<AssetMainModel> assetList;
    private AssetMainModel selectedAsset;
    private List<zk.springboot.Models.CategoryModel.CategoryModel> categoryList;
    private zk.springboot.Models.CategoryModel.CategoryModel selectedCategory;
    private List<String> assetCondition;
    private String selectedAssetCondition;
    private List<AssetMainModel> assetsCopy;

    @Init
    public void init() throws Exception {
        Clients.showBusy(FilterArgs.SHOW_BUSY_MESSAGE);
        categoryList = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        assetList = new ListModelList<>(ApiService.getData(FilterArgs.BASE_API_URL ,"1"));
        assetCondition = FilterArgs.ASSET_CONDITION;
        assetsCopy = assetList;
        selectedAsset = assetsCopy.get(0);
        Clients.clearBusy();
    }

    //TODO IT WILL SELECT AN ITEM AND WILL DISPALY SPECIFIC PROPERTIES ABOUT THAT ITEM
    @Command("listItemSpepcific")
    @NotifyChange("selectedAsset")
    public void selectedAsset() {
        System.out.println(selectedAsset.getBrand());
//        Window window = (Window)Executions.getCurrent().createComponents("testt.zul", null, null);
//        window.setClosable(true);
//        window.doModal();

//        Executions.sendRedirect("testt.zul");

//        Messagebox.show(String.valueOf(selectedAsset.getAssetHistory().get(0).getActionId()));

    }

    //THIS WILL OPEN A MODAL DIALOG TO SHOW SPECIFICES FOR AN ASSET
    @Command("ok")
    public void ok(@ContextParam(ContextType.EXECUTION) Execution exec) {
//        System.out.println(Executions.getCurrent().getDesktop().getRequestPath());
//        System.out.println(Executions.getCurrent().getNativeRequest());

//        exec.setAttribute("path", ((ExecutionCtrl)exec).getCurrentPageDefinition().getRequestPath());

//        Window window = (Window)Executions.createComponents("/zul/testt.zul", null, null);
//        window.setBorder(true);
//        window.setClosable(true);
//        window.setTitle("Title");
//        window.doModal();
        Window win = (Window) Executions.createComponents("~./zul/testt.zul", null, null);
        win.setBorder(true);
        win.setClosable(true);
        win.setTitle("Title");
        win.doOverlapped();
        win.doModal();
    }

    //label CHECKBOX
    @Command("checkboxclicked")
    public void checkboxclicked(@BindingParam("check") Checkbox checkbox) {
        System.out.println(checkbox.isChecked());
        System.out.println(checkbox.getLabel());
    }

    //FOR SELECTED CATEGORY
    @Command("selectedCategory")
    @NotifyChange("assetList")
    public void selectedCategory() throws Exception {
        Clients.showBusy(FilterArgs.SHOW_BUSY_MESSAGE);
//        assetList = ApiService.getData(FilterArgs.URL_GET_BY_CATEGORY + selectedCategory.getCategoryName());
        System.out.println(selectedCategory.getCategoryName());
        Clients.clearBusy();
    }

    //CLEAR BUTTON
    @Command("clearAndGetAll")
    @NotifyChange("assetList")
    public void clearAndGetAll() throws Exception {
        Clients.showBusy(FilterArgs.SHOW_BUSY_MESSAGE);
//        assetList = ApiService.getData(FilterArgs.BASE_API_URL);
        Clients.clearBusy();
    }

    @Command
    @NotifyChange("assetList")
    public void changePage(@ContextParam(ContextType.TRIGGER_EVENT)PagingEvent event) throws Exception {
        System.out.println(String.valueOf(event.getActivePage()));
        assetList = new ListModelList<>(ApiService.getData(FilterArgs.BASE_API_URL , String.valueOf(event.getActivePage()+1)));
    }


    @Command("searchList")
    @NotifyChange("categoryList")
    public List<CategoryModel> searchList(@BindingParam("searchBox")Textbox searchBox) throws Exception {
        List<zk.springboot.Models.CategoryModel.CategoryModel> categorySearch;
        categorySearch=ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        if(searchBox.getValue().isEmpty() || searchBox.getValue() == null || searchBox.getValue().equals("")){
            categoryList = categorySearch;
            return categoryList;
        }
        else {
            System.out.println(searchBox.getValue());
            categoryList.clear();
            for(zk.springboot.Models.CategoryModel.CategoryModel word: categorySearch){
                if(word.getCategoryName().toLowerCase().contains(searchBox.getValue().toLowerCase())){
                    categoryList.add(word);
                }
            }
            return categoryList;

        }

    }


    @Command("addAsset")
    public void addAssetPage(){
        Executions.sendRedirect("addAsset.zul");
    }


    //THIS IS HOW WE CAN FETCH DATA FROM MVVM
    @Command("addCategory")
    public void getData(@ContextParam(ContextType.COMPONENT) Component getValues,
                        @ContextParam(ContextType.VIEW)Component view){
        System.out.println("just some random fck tesr");
        System.out.println(view.getChildren());

        System.out.println(getValues.getFellows());
    }


    //here we filter the data to display
    @Command("selectedAssetCondition")
    @NotifyChange({"selectedAssetCondition", "assetList"})
    public List<AssetMainModel> selectedAssetCondition(){
//        System.out.println(selectedAssetCondition);
//        if(selectedAssetCondition.equals("NONE")){
//            assetList = assetsCopy;
//            return assetList    ;
//        }
//        else {
//            assetList = assetsCopy;
//            assetList = assetList.stream()
//                    .filter(line -> line.getAssetCondition().equals(selectedAssetCondition))
//                    .collect(Collectors.toList());

            return assetList;
//        }
    }

    public String getSelectedAssetCondition() {
        return selectedAssetCondition;
    }

    public void setSelectedAssetCondition(String selectedAssetCondition) {
        this.selectedAssetCondition = selectedAssetCondition;
    }

    public List<String> getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(List<String> assetCondition) {
        this.assetCondition = assetCondition;
    }

    public zk.springboot.Models.CategoryModel.CategoryModel getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(zk.springboot.Models.CategoryModel.CategoryModel selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<zk.springboot.Models.CategoryModel.CategoryModel> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<zk.springboot.Models.CategoryModel.CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    public ListModelList<AssetMainModel> getAssetList() {
        return assetList;
    }

    public void setAssetList(ListModelList<AssetMainModel> assetList) {
        this.assetList = assetList;
    }

    public AssetMainModel getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(AssetMainModel selectedAsset) {
        this.selectedAsset = selectedAsset;
    }


}
