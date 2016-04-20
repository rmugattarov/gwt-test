package rmugattarov.gwt_test.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by rmugattarov on 20.04.2016.
 */
public class SuggestSelectEvent extends GwtEvent<SuggestSelectEvent.SuggestSelectEventHandler> {
    public static GwtEvent.Type<SuggestSelectEventHandler> TYPE = new GwtEvent.Type<SuggestSelectEventHandler>();
    private final String value;

    public SuggestSelectEvent(String value) {
        this.value = value;
    }

    @Override
    public Type<SuggestSelectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SuggestSelectEventHandler suggestSelectEventHandler) {
        suggestSelectEventHandler.onSuggestSelectEvent(this);
    }

    public String getValue() {
        return value;
    }

    public interface SuggestSelectEventHandler extends EventHandler {
        void onSuggestSelectEvent(SuggestSelectEvent event);
    }
}
