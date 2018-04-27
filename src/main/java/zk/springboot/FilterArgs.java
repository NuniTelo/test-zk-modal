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





}
