package rmugattarov.gwt_test.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;
import rmugattarov.gwt_test.client.suggest_box_widget.SuggestBoxWidget;

import java.util.Arrays;

/**
 * Created by rmugattarov on 21.01.2016.
 */
public class EntryPointClass implements EntryPoint {
    public void onModuleLoad() {
        SimpleEventBus eventBus = GWT.create(SimpleEventBus.class);
        RootPanel.get().add(new SuggestBoxWidget(eventBus, Arrays.asList("Aaa", "AAa", "123456", "12345", "1234", "123", "234", "345", "456", "567", "678", "789", "890", "901", "012", "098", "987", "876")));
    }
}
