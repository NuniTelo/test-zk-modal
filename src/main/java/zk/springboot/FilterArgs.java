package zk.springboot;

import java.util.Arrays;
import java.util.List;

public class FilterArgs {

    public static String BASE_API_URL = "http://street-asset-manager-api.herokuapp.com/v1/all";

    public static String BASE_API_CATEGORIES = "http://street-asset-manager-api.herokuapp.com/v1/categories";

    public static String SHOW_BUSY_MESSAGE = "LOADING DATA...";

    public static String GET_ASSET_BY_CATEGORY = "http://street-asset-manager-api.herokuapp.com/v1/all/search?category=";

    public static String URL_GET_BY_CATEGORY = "http://street-asset-manager-api.herokuapp.com/v1/categories/category=";

    public static List<String> ASSET_CONDITION = Arrays.asList("GOOD","POOR","BAD","NONE");

    public static String TYPE_TEXTBOX_OR_ARRAY = "string";

    public static String TYPE_NUMBER = "number";

    public static String MAIN_CATEGORY_STATUS = "http://street-asset-manager-api.herokuapp.com/v1/all/status";

    public static String CREATE_NEW_CATEGORY = "http://street-asset-manager-api.herokuapp.com/v1/categories/create";

    public static String PROPERTY_NAME = "propertyName";

    public static String PROPERTY_TYPE = "propertyType";

    public static String CATEGORY_NAME = "categoryName";

    public static String CATEGORY_PARENT = "parent";

    public static String CATEGORY_PROPERTIES = "properties";

    public static String PROPERTY_STRING = "string";

    public static String PROPERTY_NUMBER = "number";

    public static String PROPERTY_ARRAY = "array";

    public static String PROPERTY_DOUBLE = "double";

    public static String PROPERTY_MULTIGEOPOINT_BOOLEAN = "boolean";

    public static String PROPERTY_MULTI_GEO_POINT = "geojsonmultipoint";

    public static String PROPERTY_SINGLE_GEO_POINT = "geojsonpoint";

    public static String PROPERTY_DATE = "date";

    public static String PROPERTY_LIST = "list";

    public static String CONSTRAIN_NO_EMPTY = "no empty: This field is required!";

    public static String CONSTRAIN_ONLY_NUMBER = "/^[0-9]+$/: Please enter a number";

    public static String ID_FOR_CHOSENBOX = "choosenbox";

    public static String CHOSENBOX_MESSAGE = "Add to list: ";

    public static String CHOSENBOX_EMPTY = "No data to list.";

    public static String DATE_TYPE = "yyyy/MM/dd";



}
