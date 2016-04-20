package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by rmugattarov on 19.04.2016.
 */
public class SuggestBoxWidget extends Composite {

    private static class MyPopupPanel extends PopupPanel {
        @Override
        public void show() {
            if (getWidget() != null) {
                super.show();
            }
        }
    }

    public SuggestBoxWidget(final Collection<String> inputDefaultValues) {
        final List<String> defaultValues = filterInputValues(inputDefaultValues);
        final VerticalPanel verticalPanel = new VerticalPanel();
        final TextBox textBox = new TextBox();
        final MyPopupPanel popupPanel = new MyPopupPanel();

        textBox.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent focusEvent) {
                if (Strings.isNullOrEmpty(textBox.getText())) {
                    showDefaultSuggestions(textBox, defaultValues, popupPanel);
                }
            }
        });

        textBox.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent blurEvent) {
                popupPanel.hide();
            }
        });

        textBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                popupPanel.hide();
                if (Strings.isNullOrEmpty(textBox.getText())) {
                    showDefaultSuggestions(textBox, defaultValues, popupPanel);
                }
            }
        });
        verticalPanel.add(textBox);
        initWidget(verticalPanel);
    }

    private void showDefaultSuggestions(TextBox textBox, List<String> defaultValues, MyPopupPanel popupPanel) {
        VerticalPanel suggestions = new VerticalPanel();
        for (int i = 0; (i < 10) && (i < defaultValues.size()); i++) {
            suggestions.add(new Label(defaultValues.get(i)));
        }
        popupPanel.setWidget(suggestions);
        popupPanel.setPopupPosition(textBox.getAbsoluteLeft(), textBox.getAbsoluteTop() + textBox.getOffsetHeight());
        popupPanel.show();
    }

    private List<String> filterInputValues(Collection<String> inputDefaultValues) {
        TreeSet<String> result = new TreeSet<String>();
        if (inputDefaultValues != null) {
            for (String inputDefaultValue : inputDefaultValues) {
                if (!Strings.isNullOrEmpty(inputDefaultValue)) {
                    result.add(inputDefaultValue);
                }
            }
        }
        return new ArrayList<String>(result);
    }


    public static native void log(String msg) /*-{
        $wnd.console.log(msg);
    }-*/;
}