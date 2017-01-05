package com.bubblezombie.game.EventSystem.Events;

import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

public class BaseEvent implements Event {
    private EventType type;

    public BaseEvent(EventType type) {
        this.type = type;
    }

    @Override
    public EventType getType() {
        return type;
    }
}
