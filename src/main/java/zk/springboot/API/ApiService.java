package zk.springboot.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.junit.Test;
import org.zkoss.json.JSONObject;
import zk.springboot.FilterArgs;
import zk.springboot.Models.AssetMainModel;
import zk.springboot.Models.CategoryModel.CategoryModel;
import zk.springboot.Models.CategoryModel.SpecificPropertiesModel;
import java.io.IOException;
import java.util.*;

public class ApiService {

    //CLIENT
    private static OkHttpClient client = new OkHttpClient();
    //REQUEST BUILDER
    private static Request request;

    //RESPONSE BUILDER PASS URL HERE
    public static Response buildByUrl(String url) throws IOException {
        request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    //GET DATA
    public static List<AssetMainModel> getData(String url,String page) throws Exception {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Response response = client.newCall(request).execute();
//        String data =  response.body().toString();
//
//        List<AssetMainModelStatus> assetMainModel = new Gson().fromJson(response.body().charStream(), (Type) AssetMainModelStatus.class);

//        AssetMainModelStatus[] assetMainModelslist = new Gson().fromJson()
        //BUILD RESPONSE BY URL
//        long startTime = System.currentTimeMillis();
//        List<AssetMainModelStatus> assetList = new ArrayList<>();
        Gson gson;
        Response response;
//        int page = 1;
        GsonBuilder gsonBuilder;
//        int size = 0;
//        int previewSize;
//        boolean same = false;
//        boolean firstRequest  = true;
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
//        do {
//
//            if(!same) {
                response = buildByUrl(url + "?page=" + page);
//
//                if(firstRequest) {
//                    firstRequest = false;
        List<AssetMainModel> assetList = gson.fromJson(response.body().charStream(), new TypeToken<List<AssetMainModel>>(){}.getType());

//                    size = assetList.size();
//
//                }
//                else {
//                    assetList.addAll(gson.fromJson(response.body().charStream(), new TypeToken<List<AssetMainModelStatus>>() {
//                    }.getType()));
//                    previewSize  = size;
//                    size = assetList.size();
//                    if(size==previewSize){
//                        same=true;
//                    }
//                }
//                page++;
//            }
//                else {
//                    break;
//                }
//        } while (buildByUrl(url + "?page=" + page).isSuccessful());
//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        System.out.println(elapsedTime);

        return assetList;
    }

    //GET CATEGORIES
    public static List<CategoryModel> getCategories(String url) throws Exception {
        Response response = buildByUrl(url);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        List<CategoryModel> categoryList = gson.fromJson(response.body().charStream(), new TypeToken<List<CategoryModel>>() {
        }.getType());
        return categoryList;
    }

    //GET A SINGE CATEGORY
    public static CategoryModel getSingleCategory(String url) throws Exception{
        Response response = buildByUrl(url);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        CategoryModel selectedCategory = gson.fromJson(response.body().charStream(), CategoryModel.class);
        return selectedCategory;
    }

    public static CategoryModel getAssetMainModelStatus(String url) throws Exception{
        Response response = buildByUrl(url);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        CategoryModel assetMainModelStatus = gson.fromJson(response.body().charStream(), CategoryModel.class);

        return assetMainModelStatus;
    }

//    public static List<AssetMainModelStatus> getByAssetCondition(String url){
//
//    }
    //FROM HERE WE CAN CREATE A NEW CATEGORY
    //TODO ARRAY AS A PARAMETER OR MAP
    public static void postCategory() throws IOException {
        //we will use JSONObject (GSON) to create the json structure
        JSONObject jsonObject = new JSONObject();

        //we create the JSON structure here
        try {
            jsonObject.put(FilterArgs.CATEGORY_NAME, "postTest");
            jsonObject.put(FilterArgs.CATEGORY_PARENT, "parent");

            //this is hard-coded
            List<SpecificPropertiesModel> properties = new ArrayList<>() ;
            SpecificPropertiesModel specificPropertiesModel = new SpecificPropertiesModel("test2","test2",true,"tesst");
            properties.add(specificPropertiesModel);

            //for every element to the list we can create a map and then can add that map to the list
             List<Map<String, Object>> properties2 = new ArrayList<>();
             Map<String,Object> map = new HashMap<>();

            //if we have an array here we can construct
             map.put(FilterArgs.PROPERTY_NAME,specificPropertiesModel.getPropertyName());
             map.put(FilterArgs.PROPERTY_TYPE,specificPropertiesModel.getPropertyType());

//            Map<String,Object> ma2p = new HashMap<>();
//
//            //another map here...list to map
//            ma2p.put("propertyName","testtestnuni");
//            ma2p.put("propertyType","testtestnuni");
//            properties2.add(ma2p);
            //we add to the list
            properties2.add(map);
//
            //this is the final part where we can put out map so we can create an array of objects("properties")
            jsonObject.put(FilterArgs.CATEGORY_PROPERTIES, properties2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //we tell the service that the Media Type will be in JSON format.
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        //we create the request body, so we can send later to the API.
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        //we create the request to the provided api-endpoint
        Request request = new Request.Builder()
                .url(FilterArgs.CREATE_NEW_CATEGORY)
                .post(body)
                .build();

        //this is the response that we will get from the service
        Response response = null;

        try {
            response = client.newCall(request).execute();
            //here we can do anything we want with the responese
            String resStr = response.body().string();
        } catch (IOException e) {
            //if is null then we cant do much,but display an message
            e.printStackTrace();
        }

    }

}
