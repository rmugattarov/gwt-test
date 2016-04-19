package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by rmugattarov on 19.04.2016.
 */
public class SuggestBoxWidget extends Composite {

    private static class MyPopupPanel extends PopupPanel {
        public MyPopupPanel(final TextBox textBox, List<String> elements) {
            super(true);
            setPopupPosition(textBox.getAbsoluteLeft(), textBox.getAbsoluteTop() + textBox.getOffsetHeight());
            if (elements != null && !elements.isEmpty()) {
                LinkedHashSet<String> validElements = new LinkedHashSet<String>();
                for (String element : elements) {
                    if (!Strings.isNullOrEmpty(element)) {
                        validElements.add(element);
                    }
                }
                if (!validElements.isEmpty()) {
                    VerticalPanel verticalPanel = new VerticalPanel();
                    for (String validElement : validElements) {
                        final Label popupListElement = new Label(validElement);
                        popupListElement.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent clickEvent) {
                                textBox.setText(popupListElement.getText());
                                hide();
                            }
                        });
                        verticalPanel.add(popupListElement);
                    }
                    setWidget(verticalPanel);
                }
            }
        }

        @Override
        public void show() {
            if (getWidget() != null) {
                super.show();
            }
        }
    }

    public SuggestBoxWidget() {
        VerticalPanel verticalPanel = new VerticalPanel();
        final TextBox textBox = new TextBox();
        textBox.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent focusEvent) {
                MyPopupPanel popupPanel = new MyPopupPanel(textBox, Arrays.asList("123", "234", "345"));
                popupPanel.show();
            }
        });
        verticalPanel.add(textBox);
        initWidget(verticalPanel);
    }


    public static native void log(String msg) /*-{
        $wnd.console.log(msg);
    }-*/;
}