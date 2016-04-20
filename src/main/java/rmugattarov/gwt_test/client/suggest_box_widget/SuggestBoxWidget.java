package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.*;

/**
 * Created by rmugattarov on 19.04.2016.
 */
public class SuggestBoxWidget extends Composite {

    public static final int SCROLL_PANEL_MAX_VISIBLE_HEIGHT = 200;
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
                showPopupSuggestions();
            }
        });

        textBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                popupPanel.hide();
                showPopupSuggestions();
            }
        });

        popupPanel.addHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent blurEvent) {
                log("popup blur");
            }
        }, BlurEvent.getType());

        verticalPanel.add(textBox);
        initWidget(verticalPanel);
    }

    private void showPopupSuggestions() {
        if (Strings.isNullOrEmpty(textBox.getText())) {
            showDefaultSuggestions();
        } else {
            showSuggestions();
        }
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
        for (int i = 0; i < lowerCaseDefaultValues.size(); i++) {
            String lowerCaseDefaultValue = lowerCaseDefaultValues.get(i);
            if (lowerCaseDefaultValue.startsWith(userInput)) {
                suggestions.add(defaultValues.get(i));
                startsWithIndices.add(i);
            }
        }
        for (int i = 0; i < lowerCaseDefaultValues.size(); i++) {
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
                verticalPanel.add(createPopupListElement(suggestion));
            }
            showPopupPanel(verticalPanel);
        }
    }

    private Label createPopupListElement(String text) {
        final Label label = new Label(text);
        label.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                textBox.setText(label.getText());
                popupPanel.hide();
            }
        });
        return label;
    }

    private void showDefaultSuggestions() {
        VerticalPanel suggestions = new VerticalPanel();
        for (int i = 0; i < defaultValues.size(); i++) {
            suggestions.add(createPopupListElement(defaultValues.get(i)));
        }
        showPopupPanel(suggestions);
    }

    private void showPopupPanel(Widget widget) {
        popupPanel.setWidget(widget);
        popupPanel.setPopupPosition(textBox.getAbsoluteLeft(), textBox.getAbsoluteTop() + textBox.getOffsetHeight());
        popupPanel.show();
        int widgetHeight = widget.getOffsetHeight();
        if (widgetHeight > SCROLL_PANEL_MAX_VISIBLE_HEIGHT) {
            ScrollPanel scrollPanel = new ScrollPanel();
            scrollPanel.add(widget);
            scrollPanel.setHeight(SCROLL_PANEL_MAX_VISIBLE_HEIGHT + "px");
            scrollPanel.setWidth(textBox.getOffsetWidth() + "px");
            popupPanel.setWidget(scrollPanel);
        }
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