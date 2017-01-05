package com.bubblezombie.game.EventSystem;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class EventManagerTests {
    private EventManager eventManager;

    @Before
    public void setUp() {
        eventManager = mock(EventManager.class);
    }

    @Test
    public void eventToSubscribedListener() {
        Consumer<Event> listenerMock = getConsumerMock();
        Event eventMock = mock(Event.class);
        when(eventMock.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.enqueEvent(eventMock);
        eventManager.update();

        verify(listenerMock).accept(eventMock);
    }

    @Test
    public void eventToUninterestedListener() {
        Consumer<Event> listenerMock = getConsumerMock();
        Event eventMock = mock(Event.class);
        when(eventMock.getType()).thenReturn(EventType.ALL_ENEMIES_KILLED);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.enqueEvent(eventMock);
        eventManager.update();

        verify(listenerMock, never()).accept(any());
    }

    @Test
    public void eventToRemovedListener() {
        Consumer<Event> listenerMock = getConsumerMock();
        Event eventMock = mock(Event.class);
        when(eventMock.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.removeListener(listenerMock);
        eventManager.enqueEvent(eventMock);
        eventManager.update();

        verify(listenerMock, never()).accept(eventMock);
    }

    @Test
    public void eventToManySubscribedListeners() {
        Consumer<Event> listenerMock1 = getConsumerMock();
        Consumer<Event> listenerMock2 = getConsumerMock();
        Consumer<Event> listenerMock3 = getConsumerMock();

        Event eventMock = mock(Event.class);
        when(eventMock.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock1, EventType.COMBO);
        eventManager.addListener(listenerMock2, EventType.COMBO);
        eventManager.addListener(listenerMock3, EventType.COMBO);

        eventManager.enqueEvent(eventMock);
        eventManager.update();

        verify(listenerMock1).accept(eventMock);
        verify(listenerMock2).accept(eventMock);
        verify(listenerMock3).accept(eventMock);
    }

    @Test
    public void manyEventsToOneListener() {
        Consumer<Event> listenerMock = getConsumerMock();

        Event eventMock1 = mock(Event.class);
        when(eventMock1.getType()).thenReturn(EventType.COMBO);

        Event eventMock2 = mock(Event.class);
        when(eventMock2.getType()).thenReturn(EventType.GUN_ROTATED);

        Event eventMock3 = mock(Event.class);
        when(eventMock3.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.enqueEvent(eventMock1);
        eventManager.enqueEvent(eventMock2);
        eventManager.enqueEvent(eventMock3);
        eventManager.update();

        verify(listenerMock).accept(eventMock1);
        verify(listenerMock, never()).accept(eventMock2);
        verify(listenerMock).accept(eventMock3);
    }

    @Test
    public void eventQueueInListenerCallback() {
        Event eventMock1 = mock(Event.class);
        when(eventMock1.getType()).thenReturn(EventType.COMBO);

        Event eventMock2 = mock(Event.class);
        when(eventMock2.getType()).thenReturn(EventType.COMBO);

        Consumer<Event> listener = (e) -> eventManager.enqueEvent(eventMock2);

        eventManager.addListener(listener, EventType.COMBO);
        eventManager.update();

        verify(listener).accept(eventMock1);
        verify(listener, never()).accept(eventMock2);
    }


    @SuppressWarnings("unchecked")
    private Consumer<Event> getConsumerMock() {
        return mock(Consumer.class);
    }
}