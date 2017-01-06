package com.bubblezombie.game.EventSystem;

import java.util.*;
import java.util.function.Consumer;

public class EventManagerImpl implements EventManager {

    private class QueuePair<T> {
        private Queue<T> activeQueue, passiveQueue;

        QueuePair() {
            activeQueue = new LinkedList<>();
            passiveQueue = new LinkedList<>();
        }

        Queue<T> getActiveQueue() {
            return activeQueue;
        }

        void swapQueues() {
            Queue<T> tmp;
            tmp = activeQueue;
            activeQueue = passiveQueue;
            passiveQueue = tmp;
        }
    }

    private static int EVENT_MANAGER_NUM_QUEUES = 2;

    private QueuePair<Event> eventQueuePair = new QueuePair<>();

    private Map<EventType, List<Consumer<Event>>> eventListeners = new HashMap<>();

    @Override
    public void update() {
        Queue<Event> activeQueue = eventQueuePair.getActiveQueue();
        eventQueuePair.swapQueues();

        while (!activeQueue.isEmpty()) {
            Event event = activeQueue.poll();

            EventType type = event.getType();

            List<Consumer<Event>> listeners = eventListeners.get(type);
            if (listeners != null) {
                listeners.forEach((l) -> l.accept(event));
            }
        }
    }

    @Override
    public void addListener(Consumer<Event> callback, EventType type) {
        eventListeners.putIfAbsent(type, new ArrayList<>());
        List<Consumer<Event>> consumersList = eventListeners.get(type);
        consumersList.add(callback);
    }

    @Override
    public void removeListener(Consumer<Event> callback) {
        eventListeners.forEach((type, listeners) -> listeners.remove(callback));
    }

    @Override
    public void removeAllListeners() {
        eventListeners.clear();
    }

    @Override
    public void queueEvent(Event event) {
        eventQueuePair.getActiveQueue().add(event);
    }
}
