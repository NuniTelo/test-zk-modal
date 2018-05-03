package zk.springboot.ViewModels;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.CategoryModel.SpecificPropertiesModel;

import java.util.*;

public class AddAssetViewModel {

    List<CategoryModel> categoryModels;

    CategoryModel assetMainModelStatus;

    ListModelList<SpecificPropertiesModel> assetMainModelStatusProperties;

    ListModelList<String> types;

    private ListModelList<String> tags = new ListModelList<>();

    private ListModelList<String> labelsId = new ListModelList<>();


    Map<String,Object> o = new HashMap<>();


    ListModelList<String> l = new ListModelList<>();
    Map<String,ListModelList<String>> d = new HashMap<>();

Map<String,String> mapNuni = new HashMap<>();
ListModelList<String> listNuni = new ListModelList<>();

//    ListModelList<String> modelList = new ListModelList<>();

        Multimap<String, String> multimap = ArrayListMultimap.create();

    Map<String,ListModelList<String>> modelListMap = new HashMap<>();



    @Init
    public void init() throws Exception {

        //first we fetch our categories names
        categoryModels = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        //here we fetch the data for the main attributes
        assetMainModelStatus = ApiService.getAssetMainModelStatus(FilterArgs.MAIN_CATEGORY_STATUS);
        //we connvert only the properties to a listModelList
        assetMainModelStatusProperties = new ListModelList<>(assetMainModelStatus.getProperties());
        //we save the types to a listTestNuni so we can indentify
        types = new ListModelList<>();
        for(SpecificPropertiesModel model : assetMainModelStatusProperties){
        types.add(model.getPropertyType());
        }

        for (SpecificPropertiesModel mo : assetMainModelStatusProperties){
            labelsId.add(mo.getPropertyName());

            o.put(mo.getPropertyName(),new ListModelList<>());
        }


    }

    @Command("selectedCategory")
    public void selectedCategory(@ContextParam(ContextType.COMPONENT)Combobox combobox){

        System.out.println(combobox.getValue());

    }

    @Command("click")
    public void click(@ContextParam(ContextType.COMPONENT)Component grid){
        Iterator iterator = grid.getFellows().iterator();
        while(iterator.hasNext()){
            Object el = iterator.next();
            if(el instanceof Textbox){
                ((Textbox) el).setValue("nuuniiii");
            }
        }
        System.out.println(grid.getFellows());
    }

    ListModelList<String> da = new ListModelList<>();

    @Command("newContact")
    public void addNewTag(@BindingParam("contact") String contact,
                          @ContextParam(ContextType.COMPONENT) Component component,
                          @ContextParam(ContextType.TRIGGER_EVENT)Event event){
//        dataList.clear();
//        dataList.add(contact);
//        data.put(component.getId(),dataList);
////        dataList.clear();
//        System.out.println(component.getId());
//
////        System.out.println(component.getFellows());
////        tags.add(contact);
//        data.addToSelection(contact);
//
//        data.forEach((key, value) -> System.out.println("KEY : " + key + " Value: " + value));
//        System.out.println(component.getId());
//        if(component.getId().equals("tags")){
//            if()
//            component
//            tags.addToSelection(contact);
//        }
//        else {
//            tags.clear();
//        }

//ListModelList<String> ll = new ListModelList<>(Arrays.asList("test1","test2","test3"));
//
//        ListModelList<String> ll2 = new ListModelList<>(Arrays.asList("tt","tt2","tt3"));
//
//if(component.getId().equals("tags")){
//    ((Chosenbox) component).setModel(ll);
//        }
//        else {
//    ((Chosenbox) component).setModel(ll2);
//        }
//
//for(String s : labelsId) {
//    if (component.getId().equals(s)) {
//
//        l.add(contact);
//        l.addToSelection(contact);
//        d.put(component.getId(), l);
//
////        tags.add(contact);
////        tags.addToSelection(contact);
//
//        for (Map.Entry<String, ListModelList<String>> entry : d.entrySet()) {
//            if (entry.getKey().equals(component.getId())) {
//                ((Chosenbox) component).setModel(entry.getValue());
////                System.out.println("KEY : " + entry.getKey() + " VALUE " + entry.getValue());
//            }
////        ((Chosenbox) component).setModel(d.get(component.getId()));
//
//
//        }
//
//    }
//}
//
//
//        for (Map.Entry<String, ListModelList<String>> entry : d.entrySet()) {
//
//                ((Chosenbox) component).setModel(entry.getValue());
//                System.out.println("KEY : " + entry.getKey() + " VALUE " + entry.getValue());}
//        l.clear();
        mapNuni.put(component.getId(),contact);

        multimap.put(component.getId(),contact);
        System.out.println(multimap.size());





//        for (Map.Entry<String, String> entry : multimap.ent) {
//            if (entry.getKey().equals(component.getId())) {
//                modelList.add(entry.getValue());
//                modelList.addToSelection(entry.getValue());
//                System.out.println("KEY : " + entry.getKey() + " VALUE " + entry.getValue());
//            }
//
//        }
//        ListModelList<String> modelList = new ListModelList<>();
//        for(int i=0; i < multimap.size(); i++) {
//                modelList.add(String.valueOf(multimap.get(component.getId())));
//                modelList.addToSelection(String.valueOf(multimap.get(component.getId())));
//        }
//        for (Map.Entry<String, String> entry : multimap.ent) {
//            if (entry.getKey().equals(component.getId())) {
//                modelList.add(entry.getValue());
//                modelList.addToSelection(entry.getValue());
//                System.out.println("KEY : " + entry.getKey() + " VALUE " + entry.getValue());
//            }
//
//        }

//            ((Chosenbox) component).setModel(modelList);

            listTestNuni.add(contact);
            listTestNuni.addToSelection(contact);

    }

    ListModelList<String> listTestNuni;
    @Command("createNewModelList")
    public void createNewModelList (@ContextParam(ContextType.COMPONENT) Component component)
    {

       listTestNuni = new ListModelList<>();
        ((Chosenbox) component).setModel(listTestNuni);
    }

    public ListModelList<String> getListTestNuni() {
        return listTestNuni;
    }

    public void setListTestNuni(ListModelList<String> listTestNuni) {
        this.listTestNuni = listTestNuni;
    }

    public Map<String, ListModelList<String>> getModelListMap() {
        return modelListMap;
    }

    public void setModelListMap(Map<String, ListModelList<String>> modelListMap) {
        this.modelListMap = modelListMap;
    }

    public List<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(List<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    public CategoryModel getAssetMainModelStatus() {
        return assetMainModelStatus;
    }

    public void setAssetMainModelStatus(CategoryModel assetMainModelStatus) {
        this.assetMainModelStatus = assetMainModelStatus;
    }

    public ListModelList<SpecificPropertiesModel> getAssetMainModelStatusProperties() {
        return assetMainModelStatusProperties;
    }

    public void setAssetMainModelStatusProperties(ListModelList<SpecificPropertiesModel> assetMainModelStatusProperties) {
        this.assetMainModelStatusProperties = assetMainModelStatusProperties;
    }

    public ListModelList<String> getTypes() {
        return types;
    }

    public void setTypes(ListModelList<String> types) {
        this.types = types;
    }

    public ListModelList<String> getTags() {
        return tags;
    }

    public void setTags(ListModelList<String> tags) {
        this.tags = tags;
    }


}
