package rmugattarov.gwt_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import rmugattarov.gwt_test.client.suggest_box_widget.SuggestBoxWidget;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class EntryPointClass implements EntryPoint {
    public void onModuleLoad() {
        RootPanel.get().add(new SuggestBoxWidget());
    }
}
