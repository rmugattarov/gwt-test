package rmugattarov.gwt_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class EntryPointClass implements EntryPoint {

    private Label label;
    private Button button;

    public void onModuleLoad() {
        label = new Label("Bazinga!");
        button = new Button("Hello!");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                label.setText(label.getText() + "a");
            }
        });

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(label);
        verticalPanel.add(button);
        RootPanel.get().add(verticalPanel);
    }
}
