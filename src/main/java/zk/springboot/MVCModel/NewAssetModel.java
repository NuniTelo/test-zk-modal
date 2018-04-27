package zk.springboot.MVCModel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.CategoryModel.SpecificPropertiesModel;

import java.util.Iterator;
import java.util.List;

public class NewAssetModel extends SelectorComposer<Component> {

    @Wire
    Div dynamicFieldsDiv;

    @Wire
    Div staticFieldsDiv;

    @Wire
    Combobox spinerCategories;

    @Wire
    Button btn;

    List<CategoryModel> categoryModels;


    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);

        categoryModels = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        for(CategoryModel spinerItem: categoryModels) {
            spinerCategories.appendItem(spinerItem.getCategoryName());
        }


    }

    @Listen("onClick = #btn")
    public void clickBtn(){

//            System.out.println(Arrays.toString(new Collection[]{staticFieldsDiv.getFellows()}));

//            System.out.println(staticFieldsDiv.getFellow("serialTextBox").setVisible(false));

//        staticFieldsDiv.getChildren().clear();


        //we clear the data if something has been typed
        //or when we want to take some data from the dynamic fields
        Iterator itr = dynamicFieldsDiv.getFellows().iterator();
        while (itr.hasNext()) {
            Object element = itr.next();
            if (element instanceof Textbox) {
                ((Textbox) element).setValue("");
            }
        }






    }


    @Listen("onSelect = #spinerCategories")
    public void onSpinerCategories() throws Exception {
        Clients.showBusy("Loading Model....");
        //here we select the spinner
        String selectedSpinner = spinerCategories.getValue();



        //if categorie spinner changes then we clear the dynamic fields
        dynamicFieldsDiv.getChildren().clear();


        CategoryModel categoryModel = ApiService.getSingleCategory(FilterArgs.URL_GET_BY_CATEGORY + spinerCategories.getValue());

        System.out.println(categoryModel.getId());



        List<SpecificPropertiesModel> properties = categoryModel.getProperties();


        for(SpecificPropertiesModel model : properties){
            //here we filter the data if we want to
            if(model.getPropertyType().toLowerCase().equals("string")){
                Label label = new Label(model.getPropertyName());
                Textbox data = new Textbox();
                //here we make the textbox all required
                data.setConstraint("no empty");
                data.setId(model.getPropertyName());
                data.setStyle("");
                dynamicFieldsDiv.appendChild(label);
                dynamicFieldsDiv.appendChild(data);

//                dynamicFieldsDiv.appendChild(new Label(model.getPropertyName()));
//                dynamicFieldsDiv.appendChild(new Textbox());


            }
        }
        dynamicFieldsDiv.getChildren().add(new Space());
        dynamicFieldsDiv.getChildren().add(new Space());
        dynamicFieldsDiv.getChildren().add(new Vbox());
        System.out.println(spinerCategories.getValue());

        Clients.clearBusy();
    }
}
