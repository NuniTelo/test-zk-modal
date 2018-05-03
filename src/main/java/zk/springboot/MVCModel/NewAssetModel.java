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
import zk.springboot.FilterArgs;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.CategoryModel.SpecificPropertiesModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class NewAssetModel extends SelectorComposer<Component> {


    @Wire
    private Combobox spinerCategories;

    @Wire
    private Button sendData;

    //Main Properties for the asset
    private List<SpecificPropertiesModel> assetMainModelStatusProperties;

    @Wire
    private Grid gridModel;

    private List<String> allId = new ArrayList<>();


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

        //fill the grid with main data
        fillTheGridWithProperties(assetMainModelStatusProperties);

        //TODO UNCOMENT
//        if(spinerCategories.getValue().equals("")|| spinerCategories.getValue()==null){
//            sendData.setDisabled(false);
//        }

//
//        Row row = new Row();
//        row.appendChild(new Label("test"));
//        row.appendChild(new Textbox());
//        gridModel.getRows().appendChild(row);

    }

    //WHEN WE SELECT A CATEGORY AND WE FILTER THE DATA
    @Listen("onSelect = #spinerCategories")
    public void onSpinerCategories() throws Exception {
        try {
            allId.clear();
            Clients.showBusy("Loading Model....");
            //here we select the spinner
            String selectedSpinner = spinerCategories.getValue();

            gridModel.getRows().getChildren().clear();

            //TODO UNCOMENT
           // sendData.setDisabled(false);


            //fetch properties from the API and save to a object so we can use it later
            CategoryModel categoryModel = ApiService.getSingleCategory(FilterArgs.URL_GET_BY_CATEGORY + spinerCategories.getValue());


            //we add to the grid once again with static properties
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
            //TODO UNCOMENT
//            Clients.alert("ERROR! PLEASE SELECT ONE CATEGORY");
//
//            sendData.setDisabled(true);
        }
    }

    //METHOD FOR DISPLAYING THE FIELDS TO THE GRID
    public void fillTheGridWithProperties(List<SpecificPropertiesModel> properties){
        for(SpecificPropertiesModel itemStatus : properties){
            //IF THE ITEM IS REQUIRED TO BE FILLED
            if(itemStatus.isRequired()) {

                //HERE WE CHECK IF THE ITEM IS STRING:
                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    //ROW WILL CONTAIN ALL DATA INSIDE
                    Row itemRow = new Row();
                    //WE HAVE THE LABEL NAME
                    Label label = new Label(itemStatus.getPropertyName());
                    //HERE WE HAVE THE INSTRUCTIONS
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    //NEW TEXTBOX FOR THE VALUE WE WILL ENTER
                    Textbox value = new Textbox();


                    //HERE WE SET THE ID FOR THE PROPERTY STRING
                    value.setId(itemStatus.getPropertyName().toLowerCase());

                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    //WE SAY THAT IS REQUIRED SO CONSTRAIN IT WILL NOT BE EMPTY
                    value.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                    //WE SET THE STYLE
                    value.setStyle("width:100%");
                    //APPEND THE CHILDREN TO ROWS
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    //FINALLY WE APPED TO THE GRID
                    gridModel.getRows().appendChild(itemRow);
                }

                //HERE WE CHECK IF THE TYPE IS NUMBER OR DOUBLE
                else if(itemStatus.getPropertyType().equals(FilterArgs.PROPERTY_NUMBER) || itemStatus.getPropertyType().equals(FilterArgs.PROPERTY_DOUBLE)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    //ENTRY ONLY NUMBER
                    value.setConstraint(FilterArgs.CONSTRAIN_ONLY_NUMBER);


                    //HERE WE SET THE ID FOR THE PROPERTY NUMBER OR DOUBLE
                    value.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    //APPEND TO THE GRID THE ROW
                    gridModel.getRows().appendChild(itemRow);
                }

                //CHECK IF ARRAY AND CREATE CHOSENBOX
                else if (itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_LIST) || itemStatus.getPropertyType().equals(FilterArgs.PROPERTY_ARRAY)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Chosenbox chosenbox = new Chosenbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    //SET THE STYLE
                    chosenbox.setStyle("width:100%");
                    //FOR EVERY CHOSENBOX WE SET A DIFFERENT MODEL
                    ListModelList<String> contactsModel = new ListModelList<String>();
                    chosenbox.setModel(contactsModel);
                    chosenbox.setCreatable(true);

                    // SET THE ID FOR CHOSENBOX
                    chosenbox.setId((itemStatus.getPropertyName()+FilterArgs.ID_FOR_CHOSENBOX).toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add((itemStatus.getPropertyName()+FilterArgs.ID_FOR_CHOSENBOX).toLowerCase());


                    //SET INFO FOR THE CHOSENBOX
                    chosenbox.setCreateMessage(FilterArgs.CHOSENBOX_MESSAGE);
                    chosenbox.setEmptyMessage(FilterArgs.CHOSENBOX_EMPTY);
//                    chosenbox.setAttribute("setConstraint" ,"no empty");
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    chosenbox.addEventListener("onSearch", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //this is the part where we can add data to a specific model
                            contactsModel.add(((InputEvent)event).getValue());
                            contactsModel.addToSelection(((InputEvent)event).getValue());
                        }
                        });
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(chosenbox);
                    //FINALLY WE ADD TO THE LIST
                    gridModel.getRows().appendChild(itemRow);
                }
                //DATE
                else if (itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_DATE)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Datebox datebox = new Datebox();
                    datebox.setFormat(FilterArgs.DATE_TYPE);
                    datebox.setStyle("width:100%");
                    datebox.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);

                    //SET THE ID FOR THE DATEBOX
                    datebox.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(datebox);
                    gridModel.getRows().appendChild(itemRow);
                }
                //CHECKBOX
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_MULTIGEOPOINT_BOOLEAN)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Checkbox checkbox = new Checkbox();
                    checkbox.setChecked(false);

                    //SET THE ID FOR THE DATEBOX
                    checkbox.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(checkbox);
                    gridModel.getRows().appendChild(itemRow);
                }
                //MULTIGEOPOINT
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_MULTI_GEO_POINT)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Chosenbox chosenbox = new Chosenbox();
                    ListModelList<String>listPoints = new ListModelList<>();
                    chosenbox.setCreateMessage(FilterArgs.CHOSENBOX_MESSAGE);
                    chosenbox.setEmptyMessage(FilterArgs.CHOSENBOX_EMPTY);
                    chosenbox.setStyle("width:100%");
                    chosenbox.setAttribute("setConstraint" ,"no empty");
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    chosenbox.setModel(listPoints);
                    chosenbox.addEventListener("onSearch", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //this is the part where we can add data to a specific model
                            listPoints.add(((InputEvent)event).getValue());
                            listPoints.addToSelection(((InputEvent)event).getValue());
                        }
                    });

                    //SET THE ID FOR THE MULTIGEOPOINT
                    chosenbox.setId(itemStatus.getPropertyName().toLowerCase()+FilterArgs.ID_FOR_CHOSENBOX);
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase()+FilterArgs.ID_FOR_CHOSENBOX);
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(chosenbox);
                    gridModel.getRows().appendChild(itemRow);
                }

                //SINGLE GEO-POINT
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_SINGLE_GEO_POINT)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Textbox value = new Textbox();
                    value.setConstraint(FilterArgs.CONSTRAIN_NO_EMPTY);
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    //SET THE ID FOR THE MULTIGEOPOINT
                    value.setStyle("width:100%");
                    value.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }
            }

            //IF THE ITEM IS NOT REQUIRED TO BE FILLED
            else if (!itemStatus.isRequired()){
                //CASE IF IS PROPERTY STRING
                if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_STRING)) {
                    //ROW WILL CONTAIN ALL DATA INSIDE
                    Row itemRow = new Row();
                    //WE HAVE THE LABEL NAME
                    Label label = new Label(itemStatus.getPropertyName());
                    //HERE WE HAVE THE INSTRUCTIONS
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    //NEW TEXTBOX FOR THE VALUE WE WILL ENTER
                    Textbox value = new Textbox();

                    //HERE WE SET THE ID FOR THE PROPERTY STRING
                    value.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());


                    //WE SET THE STYLE
                    value.setStyle("width:100%");
                    //APPEND THE CHILDREN TO ROWS
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    //FINALLY WE APPED TO THE GRID
                    gridModel.getRows().appendChild(itemRow);
                }

                //HERE WE CHECK IF THE TYPE IS NUMBER OR DOUBLE
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_NUMBER) || itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_DOUBLE)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Textbox value = new Textbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());


                    //HERE WE SET THE ID FOR THE PROPERTY NUMBER OR DOUBLE
                    value.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    value.setStyle("width:100%");
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    //APPEND TO THE GRID THE ROW
                    gridModel.getRows().appendChild(itemRow);
                }
                //LIST/ARRAY
                else if (itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_LIST) || itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_ARRAY)) {
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Chosenbox chosenbox = new Chosenbox();
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    //SET THE STYLE
                    chosenbox.setStyle("width:100%");
                    //FOR EVERY CHOSENBOX WE SET A DIFFERENT MODEL
                    ListModelList<String> tagsListModel = new ListModelList<String>();
                    chosenbox.setModel(tagsListModel);
                    chosenbox.setCreatable(true);

                    //WE CAN SET THE INFO FOR CHOSENBOX
                    chosenbox.setId(itemStatus.getPropertyName()+FilterArgs.ID_FOR_CHOSENBOX);
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName()+FilterArgs.ID_FOR_CHOSENBOX);

                    chosenbox.setCreateMessage(FilterArgs.CHOSENBOX_MESSAGE);
                    chosenbox.setEmptyMessage(FilterArgs.CHOSENBOX_EMPTY);
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    chosenbox.addEventListener("onSearch", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //this is the part where we can add data to a specific model
                            tagsListModel.add(((InputEvent)event).getValue());
                            tagsListModel.addToSelection(((InputEvent)event).getValue());
                        }
                    });
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(chosenbox);
                    //FINALLY WE ADD TO THE LIST
                    gridModel.getRows().appendChild(itemRow);
                }
                //DATE
                else if (itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_DATE)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Datebox datebox = new Datebox();
                    datebox.setFormat(FilterArgs.DATE_TYPE);
                    datebox.setStyle("width:100%");

                    //SET THE ID FOR THE DATEBOX
                    datebox.setId(itemStatus.getPropertyType().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyType().toLowerCase());

                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(datebox);
                    gridModel.getRows().appendChild(itemRow);

                }
                //CHECKBOX
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_MULTIGEOPOINT_BOOLEAN)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Checkbox checkbox = new Checkbox();
                    checkbox.setChecked(false);

                    //SET THE ID FOR THE DATEBOX
                    checkbox.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());

                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(checkbox);
                    gridModel.getRows().appendChild(itemRow);
                }
                //MULTIGEOPOINT
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_MULTI_GEO_POINT)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Chosenbox chosenbox = new Chosenbox();
                    ListModelList<String>listPoints = new ListModelList<>();
                    chosenbox.setCreateMessage(FilterArgs.CHOSENBOX_MESSAGE);
                    chosenbox.setEmptyMessage(FilterArgs.CHOSENBOX_EMPTY);
                    chosenbox.setStyle("width:100%");
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    chosenbox.setModel(listPoints);
                    chosenbox.addEventListener("onSearch", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //this is the part where we can add data to a specific model
                            listPoints.add(((InputEvent)event).getValue());
                            listPoints.addToSelection(((InputEvent)event).getValue());
                        }
                    });

                    //SET THE ID FOR THE MULTIGEOPOINT
                    chosenbox.setId(itemStatus.getPropertyName().toLowerCase()+FilterArgs.ID_FOR_CHOSENBOX);
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase()+FilterArgs.ID_FOR_CHOSENBOX);
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(chosenbox);
                    gridModel.getRows().appendChild(itemRow);
                }

                //SINGLE GEO-POINT
                else if(itemStatus.getPropertyType().toLowerCase().equals(FilterArgs.PROPERTY_SINGLE_GEO_POINT)){
                    Row itemRow = new Row();
                    Label label = new Label(itemStatus.getPropertyName());
                    Label itemInstruction = new Label(itemStatus.getInstruction());
                    Textbox value = new Textbox();
                    //THE EVENT WHERE WE TAKE THE DATA AND ADD TO A LIST
                    //SET THE ID FOR THE MULTIGEOPOINT
                    value.setStyle("width:100%");
                    value.setId(itemStatus.getPropertyName().toLowerCase());
                    //ADD ALL THE DATA TO OUR ARRAY
                    allId.add(itemStatus.getPropertyName().toLowerCase());
                    itemRow.appendChild(label);
                    itemRow.appendChild(itemInstruction);
                    itemRow.appendChild(value);
                    gridModel.getRows().appendChild(itemRow);
                }

            }
        }
    }

    //HOW WE CREATE THE ASSET

    @Listen("onClick = #sendData")
    public void clickBtn() throws IOException {
        //FIRST WE FETCH ALL THE DATA FROM THE GRID BASED ON ID
        Iterator iterator = gridModel.getFellows().iterator();
        //THEN WE SAY THAT IF HAS NEXT WE CHECK FOR EVERY FIELD THAT WE NEED
        while(iterator.hasNext()){
                Object el = iterator.next();
                //HERE WE BREDHIM NE TE GJITHA ATO QE KEMI MARRE
                    for(String gridRowId : allId ){
                        if(el instanceof  Textbox){
                          if(((Textbox) el).getId().equals(gridRowId)) {
                          System.out.println("ALL TEXTBOX: " + gridRowId);
                    }
//                    ((Textbox) el).setValue("nuuniiii");
                }
                else if (el instanceof Chosenbox){
                            if (((Chosenbox) el).getId().equals(gridRowId)){
                                System.out.println("ALL CHOSEN:" + gridRowId);
                            }
                        }
            }
        }

    }


}
