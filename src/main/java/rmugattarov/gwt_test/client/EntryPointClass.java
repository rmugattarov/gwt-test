package rmugattarov.gwt_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import rmugattarov.gwt_test.shared.MyRemoteService;
import rmugattarov.gwt_test.shared.MyRemoteServiceAsync;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class EntryPointClass implements EntryPoint {

    private Label label;
    private Button button;
    private MyRemoteServiceAsync service = GWT.create(MyRemoteService.class);

    public void onModuleLoad() {
        label = new Label("Bazinga!");
        button = new Button("Hello!");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                service.sayHello(new AsyncCallback<String>() {
                    public void onFailure(Throwable throwable) {
                        Window.alert("onFailure");
                    }

                    public void onSuccess(String s) {
                        label.setText(s);
                    }
                });

            }
        });

        VerticalPanel verticalPanel = new VerticalPanel();

        verticalPanel.add(label);
        verticalPanel.add(button);
        verticalPanel.addHandler(new ChangeHandler() {
            public void onChange(ChangeEvent changeEvent) {
                Window.alert("verticalPanel onChange");
            }
        }, ChangeEvent.getType());
        RootPanel.get().add(verticalPanel);
        button.fireEvent(new ChangeEvent() {
        });
    }
}
