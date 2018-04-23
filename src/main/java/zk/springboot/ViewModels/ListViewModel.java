package zk.springboot.ViewModels;

import org.apache.poi.util.SystemOutLogger;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.AssetMainModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListViewModel {
    private List<AssetMainModel> assetList;
    private AssetMainModel selectedAsset;

    @Init
    public void assetListModel() throws Exception {
        assetList = ApiService.getData(FilterArgs.BASE_API_URL);
    }


    public List<AssetMainModel> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<AssetMainModel> assetList) {
        this.assetList = assetList;
    }

    public AssetMainModel getSelectedAsset() {
        return selectedAsset;
    }

    public void setSelectedAsset(AssetMainModel selectedAsset) {
        this.selectedAsset = selectedAsset;
    }

    @Command("listItemSpepcific")
    @NotifyChange("selectedAsset")
    public void selectedAsset (){
        System.out.println(selectedAsset.getBrand());
//        Window window = (Window)Executions.getCurrent().createComponents("testt.zul", null, null);
//        window.setClosable(true);
//        window.doModal();

        Executions.sendRedirect("testt.zul");

//        Messagebox.show(String.valueOf(selectedAsset.getAssetHistory().get(0).getActionId()));

    }

    @Command("ok")
    public void ok() {
        System.out.println(Executions.getCurrent().getDesktop().getRequestPath());
        Window window = (Window)Executions.createComponents("/zul/index.zul", null, null);
        window.setBorder(true);
        window.setClosable(true);
        window.setTitle("Title");
        window.doModal();
    }
}
