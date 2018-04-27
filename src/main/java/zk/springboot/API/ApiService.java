package zk.springboot.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.data.domain.PageRequest;
import org.zkoss.xel.zel.ELXelExpression;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import zk.springboot.Models.AssetMainModel;
import zk.springboot.Models.CategoryModel.CategoryModel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//        List<AssetMainModel> assetMainModel = new Gson().fromJson(response.body().charStream(), (Type) AssetMainModel.class);

//        AssetMainModel[] assetMainModelslist = new Gson().fromJson()
        //BUILD RESPONSE BY URL
//        long startTime = System.currentTimeMillis();
//        List<AssetMainModel> assetList = new ArrayList<>();
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
//                    assetList.addAll(gson.fromJson(response.body().charStream(), new TypeToken<List<AssetMainModel>>() {
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

    public static CategoryModel getSingleCategory(String url) throws Exception{
        Response response = buildByUrl(url);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();
        CategoryModel selectedCategory = gson.fromJson(response.body().charStream(), CategoryModel.class);
        return selectedCategory;
    }



//    public static List<AssetMainModel> getByAssetCondition(String url){
//
//    }

}
