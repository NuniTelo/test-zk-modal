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
import zk.springboot.Models.MainModelStatus.AssetMainModelStatus;
import zk.springboot.Models.MainModelStatus.Properties;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Filter;


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

    AssetMainModelStatus assetMainModelStatus;

    List<Properties> assetMainModelStatusProperties;

    @Wire
    Grid gridModel;


    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);

        //Here we can have the spinner so we can select from our categories.
        categoryModels = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        for(CategoryModel spinerItem: categoryModels) {
            spinerCategories.appendItem(spinerItem.getCategoryName());
        }

        //We take the main model list from the API so we can use it for rendering
        assetMainModelStatus = ApiService.getAssetMainModelStatus(FilterArgs.MAIN_CATEGORY_STATUS);

        assetMainModelStatusProperties = assetMainModelStatus.getProperties();

        addToGridStaticFields(assetMainModelStatusProperties);


//
//        Row row = new Row();
//        row.appendChild(new Label("test"));
//        row.appendChild(new Textbox());
//        gridModel.getRows().appendChild(row);


    }

    public void addToGridStaticFields(List<Properties> properties){
        for(Properties itemStatus : properties){
            if(itemStatus.getRequired()) {
                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else if(itemStatus.getPropertyName().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setConstraint(FilterArgs.PROPERTY_NUMBER);
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
            }else {

                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else if(itemStatus.getPropertyName().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }

            }
        }
    }

    @Listen("onClick = #btn")
    public void clickBtn() throws IOException {

//            System.out.println(Arrays.toString(new Collection[]{staticFieldsDiv.getFellows()}));

//            System.out.println(staticFieldsDiv.getFellow("serialTextBox").setVisible(false));

//        staticFieldsDiv.getChildren().clear();


        //we clear the data if something has been typed
        //or when we want to take some data from the dynamic fields
//        Iterator itr = dynamicFieldsDiv.getFellows().iterator();
//        while (itr.hasNext()) {
//            Object element = itr.next();
//            if (element instanceof Textbox) {
//
//                //this will igonore the field for the spriner one
//                if(!((Textbox) element).getId().equals("spinerCategories")){
//                ((Textbox) element).setValue("nuuniiii");
//                }
//            }
//        }

        //NEEDS TO HAVE AN ID SO WE CAN DO SOMETHING
        Iterator iterator = gridModel.getFellows().iterator();
        while(iterator.hasNext()){
            Object el = iterator.next();
            if(el instanceof  Textbox){
                ((Textbox) el).setValue("nuuniiii");
            }
        }

        ApiService.postCategory();

    }
    
    @Listen("onSelect = #spinerCategories")
    public void onSpinerCategories() throws Exception {
        Clients.showBusy("Loading Model....");
        //here we select the spinner
        String selectedSpinner = spinerCategories.getValue();

        //if categorie spinner changes then we clear the dynamic fields
        dynamicFieldsDiv.getChildren().clear();

        gridModel.getRows().getChildren().clear();


        //fetch properties from the API and save to a object so we can use it later
        CategoryModel categoryModel = ApiService.getSingleCategory(FilterArgs.URL_GET_BY_CATEGORY + spinerCategories.getValue());


        //we add to the grid once again
        addToGridStaticFields(assetMainModelStatusProperties);


        System.out.println(categoryModel.getId());

        //since we have a list of objects we can construct in this way
        List<SpecificPropertiesModel> properties = categoryModel.getProperties();


        //This is the method that we will use so we can add components to the page
        addComponentsToPageDynamically(properties);

        //now its time to add the dynamic fields
        addComponentsToGridDynamically(properties);


        System.out.println(spinerCategories.getValue());

        Clients.clearBusy();
    }

    public void addComponentsToPageDynamically(List<SpecificPropertiesModel> properties){

        for(SpecificPropertiesModel modelProperties : properties){
            //here we filter the data if we want to
            if(modelProperties.getPropertyType().toLowerCase().equals("string")){
                Label labelString = new Label(modelProperties.getPropertyName());
                Textbox data = new Textbox();
                //here we make the textbox all required
                data.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                data.setId(modelProperties.getPropertyName());
                data.setStyle("");
                dynamicFieldsDiv.appendChild(labelString);
                dynamicFieldsDiv.appendChild(data);

//                dynamicFieldsDiv.appendChild(new Label(model.getPropertyName()));
//                dynamicFieldsDiv.appendChild(new Textbox());
            }
            else if (modelProperties.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                Label labelNumber = new Label(modelProperties.getPropertyName());
                Textbox numberFiled = new Textbox();
                numberFiled.setConstraint(FilterArgs.CONSTRAIN_ONLY_NUMBER);
                numberFiled.setId(modelProperties.getPropertyName());

                dynamicFieldsDiv.appendChild(labelNumber);
                dynamicFieldsDiv.appendChild(numberFiled);
            }

            else if(modelProperties.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_ARRAY)){
                Label labelArray = new Label(modelProperties.getPropertyName());
                Textbox arrayField = new Textbox();
                arrayField.setId(modelProperties.getPropertyName());
                arrayField.setConstraint("no empty");
                //multiline
                arrayField.setMultiline(true);


                dynamicFieldsDiv.appendChild(labelArray);
                dynamicFieldsDiv.appendChild(arrayField);
            }

        }
//        dynamicFieldsDiv.getChildren().add(new Space());
//        dynamicFieldsDiv.getChildren().add(new Space());
//        dynamicFieldsDiv.getChildren().add(new Vbox());

    }

    public void addComponentsToGridDynamically(List<SpecificPropertiesModel> properties){

        for(SpecificPropertiesModel itemDynamicProperties : properties){

                    if(itemDynamicProperties.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                        Row itemRow = new Row();
                        Label label = new Label(itemDynamicProperties.getPropertyName());
                        Textbox value = new Textbox();
                        value.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                        value.setStyle("width:100%");
                        itemRow.appendChild(label);
                        itemRow.appendChild(value);
                        gridModel.getRows().appendChild(itemRow);
                    }
                    else if(itemDynamicProperties.getPropertyName().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                        Row itemRow = new Row();
                        Label label = new Label(itemDynamicProperties.getPropertyName());
                        Textbox value = new Textbox();
                        value.setConstraint(FilterArgs.PROPERTY_NUMBER);
                        value.setStyle("width:100%");
                        itemRow.appendChild(label);
                        itemRow.appendChild(value);
                        gridModel.getRows().appendChild(itemRow);
                    }
                    else {
                        Row itemRow = new Row();
                        Label label = new Label(itemDynamicProperties.getPropertyName());
                        Textbox value = new Textbox();
                        value.setStyle("width:100%");
                        itemRow.appendChild(label);
                        itemRow.appendChild(value);
                        gridModel.getRows().appendChild(itemRow);
                    }

                }
        }
    }
