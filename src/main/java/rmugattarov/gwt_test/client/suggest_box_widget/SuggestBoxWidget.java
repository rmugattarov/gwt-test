package rmugattarov.gwt_test.client.suggest_box_widget;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.*;
import rmugattarov.gwt_test.client.events.SuggestSelectEvent;

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
    private final Button selectButton = new Button("Select");
    private final SimpleEventBus eventBus;
    private String value;

    private static class MyPopupPanel extends PopupPanel {
        @Override
        public void show() {
            if (getWidget() != null) {
                super.show();
            }
        }
    }

    public SuggestBoxWidget(final SimpleEventBus eventBus, final Collection<String> inputDefaultValues) {
        this.eventBus = eventBus;
        defaultValues = filterInputValues(inputDefaultValues);
        lowerCaseDefaultValues = getLowerCaseDefaultValues();
        final HorizontalPanel horizontalPanel = new HorizontalPanel();

        eventBus.addHandler(SuggestSelectEvent.TYPE, new SuggestSelectEvent.SuggestSelectEventHandler() {
            @Override
            public void onSuggestSelectEvent(SuggestSelectEvent event) {
                log("SuggestSelectEvent value : " + event.getValue());
                if (Strings.isNullOrEmpty(event.getValue())) {
                    selectButton.setEnabled(false);
                } else {
                    selectButton.setEnabled(true);
                }
            }
        });
        selectButton.setEnabled(false);

        textBox.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent focusEvent) {
                showPopupSuggestions();
            }
        });

        textBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent keyUpEvent) {
                String newValue = textBox.getValue().toLowerCase();
                int valueIndex = lowerCaseDefaultValues.indexOf(newValue);
                if (valueIndex > 0) {
                    value = defaultValues.get(valueIndex);
                } else {
                    value = null;
                }
                eventBus.fireEvent(new SuggestSelectEvent(value));
                log("value : " + value);
                showPopupSuggestions();
            }
        });

        popupPanel.addHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent blurEvent) {
                log("popup blur");
            }
        }, BlurEvent.getType());

        horizontalPanel.add(textBox);
        horizontalPanel.add(selectButton);
        initWidget(horizontalPanel);
    }

    private void showPopupSuggestions() {
        popupPanel.hide();
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
                value = label.getText();
                log("value : " + value);
                eventBus.fireEvent(new SuggestSelectEvent(value));
                textBox.setText(value);
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