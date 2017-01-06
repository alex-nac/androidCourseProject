package com.bubblezombie.game.EventSystem;

import java.util.function.Consumer;

public interface EventManager {
    void update();

    void addListener(Consumer<Event> callback, EventType type);
    void removeListener(Consumer<Event> callback);
    void removeAllListeners();

    void queueEvent(Event event);
}
