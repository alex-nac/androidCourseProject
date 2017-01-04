package com.bubblezombie.game.EventSystem;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

public class EventManagerImpl implements EventManager {
    private static int EVENT_MANAGER_NUM_QUEUES = 2;

    private Queue<Event>[] eventsQueues;
    private int activeQueueIndex = 0;

    private Map<EventType, List<Consumer<Event>>> eventListeners;

    public EventManagerImpl() {

    }

    @Override
    public void update() {
        Queue<Event> activeQueue = eventsQueues[activeQueueIndex];

        while (!activeQueue.isEmpty()) {
            Event event = activeQueue.poll();

            EventType type = event.getType();

            for (Consumer<Event> eventListener : eventListeners.get(type)) {
                eventListener.accept(event);
            }
        }
    }

    @Override
    public void enqueEvent(Event event) {

    }

    @Override
    public void addListener(Consumer<Event> callback, EventType type) {

    }

    @Override
    public void removeListener(Consumer<Event> callback) {

    }

    @Override
    public void removeAllListeners() {
        eventListeners.clear();
    }
}
