package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.*;

/**
 * Created by rmugattarov on 19.04.2016.
 */
public class SuggestBoxWidget extends Composite {

    private final List<String> defaultValues;
    private final List<String> lowerCaseDefaultValues;
    private final TextBox textBox = new TextBox();
    private final MyPopupPanel popupPanel = new MyPopupPanel();

    private static class MyPopupPanel extends PopupPanel {
        @Override
        public void show() {
            if (getWidget() != null) {
                super.show();
            }
        }
    }

    public SuggestBoxWidget(final Collection<String> inputDefaultValues) {
        defaultValues = filterInputValues(inputDefaultValues);
        lowerCaseDefaultValues = getLowerCaseDefaultValues();
        final VerticalPanel verticalPanel = new VerticalPanel();

        textBox.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent focusEvent) {
                if (Strings.isNullOrEmpty(textBox.getText())) {
                    showDefaultSuggestions();
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
                    showDefaultSuggestions();
                } else {
                    showSuggestions();
                }
            }
        });
        verticalPanel.add(textBox);
        initWidget(verticalPanel);
    }

    private List<String> getLowerCaseDefaultValues() {
        List<String> result = new ArrayList<String>();
        for (String defaultValue : defaultValues) {
            result.add(defaultValue.toLowerCase());
        }
        return result;
    }

    private void showSuggestions() {
        String userInput = textBox.getText().toLowerCase();
        List<String> suggestions = new ArrayList<String>();
        Set<Integer> startsWithIndices = new HashSet<Integer>();
        for (int i = 0; (suggestions.size() < 10) && (i < lowerCaseDefaultValues.size()); i++) {
            String lowerCaseDefaultValue = lowerCaseDefaultValues.get(i);
            if (lowerCaseDefaultValue.startsWith(userInput)) {
                suggestions.add(defaultValues.get(i));
                startsWithIndices.add(i);
            }
        }
        for (int i = 0; (suggestions.size() < 10) && (i < lowerCaseDefaultValues.size()); i++) {
            if (startsWithIndices.contains(i)) {
                continue;
            }
            String lowerCaseDefaultValue = lowerCaseDefaultValues.get(i);
            if (lowerCaseDefaultValue.contains(userInput)) {
                suggestions.add(defaultValues.get(i));
            }
        }
        if (suggestions.size() > 0) {
            VerticalPanel verticalPanel = new VerticalPanel();
            for (String suggestion : suggestions) {
                verticalPanel.add(new Label(suggestion));
            }
            showPopupPanel(verticalPanel);
        }
    }

    private void showDefaultSuggestions() {
        VerticalPanel suggestions = new VerticalPanel();
        for (int i = 0; (i < 10) && (i < defaultValues.size()); i++) {
            suggestions.add(new Label(defaultValues.get(i)));
        }
        showPopupPanel(suggestions);
    }

    private void showPopupPanel(VerticalPanel widget) {
        popupPanel.setWidget(widget);
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