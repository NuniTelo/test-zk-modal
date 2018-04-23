package zk.springboot.MainPageController;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jfree.text.TextBox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import zk.springboot.API.ApiService;
import zk.springboot.FilterArgs;
import zk.springboot.Models.AssetMainModel;

import java.util.List;
import java.util.Map;

public class MainPageController extends SelectorComposer<Component> {

//
//    @Listen("onClick = #orderBtn")
//    public void showModal(Event e) {
//        //create a window programmatically and use it as a modal dialog.
//        Window window = (Window) Executions.getCurrent().createComponentsDirectly("/modal_dialog.zul", null, null);
//        window.doModal();
//    }


}
