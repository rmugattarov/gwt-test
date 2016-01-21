package rmugattarov.gwt_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class EntryPointClass implements EntryPoint {
    public void onModuleLoad() {
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new Label("Bazinga!"));
        verticalPanel.add(new Label("Hello!"));
        RootPanel.get().add(verticalPanel);
    }
}
