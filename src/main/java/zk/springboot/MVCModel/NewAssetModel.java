package zk.springboot.MVCModel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.*;
import zk.springboot.API.ApiService;
import zk.springboot.EmailContacts;
import zk.springboot.FilterArgs;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.CategoryModel.SpecificPropertiesModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class NewAssetModel extends SelectorComposer<Component> {


    @Wire
    private Combobox spinerCategories;

    @Wire
    private Button btn;

    private List<SpecificPropertiesModel> assetMainModelStatusProperties;

    @Wire
    private Grid gridModel;

    @Override
    public void doAfterCompose(Component comp) throws Exception{
        super.doAfterCompose(comp);

        //Here we can have the spinner so we can select from our categories.
        List<CategoryModel> categoryModels = ApiService.getCategories(FilterArgs.BASE_API_CATEGORIES);
        for(CategoryModel spinerItem: categoryModels) {
            spinerCategories.appendItem(spinerItem.getCategoryName());
        }

        //We take the main model list from the API so we can use it for rendering
        CategoryModel assetMainModelStatus = ApiService.getAssetMainModelStatus(FilterArgs.MAIN_CATEGORY_STATUS);

        assetMainModelStatusProperties = assetMainModelStatus.getProperties();

        fillTheGridWithProperties(assetMainModelStatusProperties);

        if(spinerCategories.getValue().equals("")|| spinerCategories.getValue()==null){
            btn.setDisabled(true);
        }
//
//        Row row = new Row();
//        row.appendChild(new Label("test"));
//        row.appendChild(new Textbox());
//        gridModel.getRows().appendChild(row);

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

//        ApiService.postCategory();

    }

    @Listen("onSelect = #spinerCategories")
    public void onSpinerCategories() throws Exception {
        try {
            Clients.showBusy("Loading Model....");
            //here we select the spinner
            String selectedSpinner = spinerCategories.getValue();

            gridModel.getRows().getChildren().clear();

            btn.setDisabled(false);


            //fetch properties from the API and save to a object so we can use it later
            CategoryModel categoryModel = ApiService.getSingleCategory(FilterArgs.URL_GET_BY_CATEGORY + spinerCategories.getValue());


            //we add to the grid once again
            fillTheGridWithProperties(assetMainModelStatusProperties);


            System.out.println(categoryModel.getId());

            //since we have a list of objects we can construct in this way
            List<SpecificPropertiesModel> properties = categoryModel.getProperties();


            //now its time to add the dynamic fields
            fillTheGridWithProperties(properties);


            System.out.println(spinerCategories.getValue());

            Clients.clearBusy();
        }
        catch (Exception e) {
            Clients.clearBusy();
            Clients.alert("ERROR! PLEASE SELECT ONE CATEGORY");
            //TODO PASTRO FUSHAT QE NUK JANE TEK TE PERGJITHSHMET
            btn.setDisabled(true);
        }
    }

    public void fillTheGridWithProperties(List<SpecificPropertiesModel> properties){
        for(SpecificPropertiesModel itemStatus : properties){
            if(itemStatus.isRequired()) {
                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Textbox value = new Textbox();
                    value.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else if(itemStatus.getPropertyName().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    value.setConstraint(FilterArgs.PROPERTY_NUMBER);
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Chosenbox chosenbox = new Chosenbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    value.setStyle("width:100%");
                    chosenbox.setStyle("width:100%");
                    ListModelList<String> contactsModel = new ListModelList<String>(EmailContacts.getContacts());
                    chosenbox.setModel(contactsModel);
                    chosenbox.setCreatable(true);
                    chosenbox.setName(itemStatus.getPropertyName()+"choosenbox");
                    chosenbox.setId(itemStatus.getPropertyName()+"choosenbox");
                    chosenbox.setCreateMessage("Add to list: ");
                    chosenbox.setEmptyMessage("No data to list.You can add some");
                    chosenbox.addEventListener("onSearch", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {

                           System.out.println(event.getData());
//                           System.out.println(chosenbox.getFellow(itemStatus.getPropertyName()+"choosenbox").getVa);
//                            Map<String, Object> data = (Map<String, Object>)event.getData();
//                            String _val = (String) data.get("_val");
//////                            System.out.println(event.getData().toString());
////                            String _val = (String) data.get("_val");
////                            System.out.println(_val);
//
//                            System.out.println(_val);
                        }

                        });
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(chosenbox);
                    gridModel.getRows().appendChild(itemRow);
                }
            }else {

                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else if(itemStatus.getPropertyName().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
                else {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }

            }
        }
    }


}
