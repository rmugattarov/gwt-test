package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Created by rmugattarov on 19.04.2016.
 */
public class SuggestBoxWidget extends Composite {
    public SuggestBoxWidget() {
        VerticalPanel verticalPanel = new VerticalPanel();
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        oracle.add("123");
        oracle.add("234");
        oracle.add("345");
        SuggestBox suggestBox = new SuggestBox(oracle);
        suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            @Override
            public void onSelection(SelectionEvent<SuggestOracle.Suggestion> selectionEvent) {
                log("selectionEvent : " + selectionEvent.getSelectedItem().getReplacementString());
            }
        });
        suggestBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> valueChangeEvent) {
                log("valueChangeEvent : " + valueChangeEvent.getValue());
            }
        });
        verticalPanel.add(suggestBox);
        initWidget(verticalPanel);
    }

    public static native void log(String msg) /*-{
        $wnd.console.log(msg);
    }-*/;
}