package com.bubblezombie.game.EventSystem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class EventManagerTests {
    private EventManager eventManager;

    @Before
    public void setUp() {
        eventManager = new EventManagerImpl();
    }

    @Test
    public void updateNoEventDuplicates() {
        Consumer<Event> listenerMock = getListenerMock();
        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.queueEvent(eventStub);
        eventManager.update();

        eventManager.update();

        verify(listenerMock, times(1)).accept(eventStub);
    }

    @Test
    public void addListenerInterestedEvent() {
        Consumer<Event> listenerMock = getListenerMock();
        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.queueEvent(eventStub);
        eventManager.update();

        verify(listenerMock).accept(eventStub);
    }

    @Test
    public void addListenerUninterestedEvent() {
        Consumer<Event> listenerMock = getListenerMock();
        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.ALL_ENEMIES_KILLED);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.queueEvent(eventStub);
        eventManager.update();

        verify(listenerMock, never()).accept(any(Event.class));
    }

    @Test
    public void removeListener() {
        Consumer<Event> listenerMock = getListenerMock();
        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.removeListener(listenerMock);
        eventManager.queueEvent(eventStub);
        eventManager.update();

        verify(listenerMock, never()).accept(any(Event.class));
    }

    @Test
    public void removeListenerDoesntExistNoException() {
        Consumer<Event> listenerMock = getListenerMock();

        eventManager.removeListener(listenerMock);
    }

    @Test
    public void removeAllListeners() {
        Consumer<Event> listenerMock1 = getListenerMock();
        Consumer<Event> listenerMock2 = getListenerMock();

        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock1, EventType.COMBO);
        eventManager.addListener(listenerMock2, EventType.COMBO);
        eventManager.removeAllListeners();

        eventManager.queueEvent(eventStub);
        eventManager.update();

        verify(listenerMock1, never()).accept(any(Event.class));
        verify(listenerMock2, never()).accept(any(Event.class));
    }

    @Test
    public void queueEventOrder() {
        Consumer<Event> listenerMock = getListenerMock();

        Event eventStub1 = mock(Event.class);
        when(eventStub1.getType()).thenReturn(EventType.COMBO);

        Event eventStub2 = mock(Event.class);
        when(eventStub2.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock, EventType.COMBO);
        eventManager.queueEvent(eventStub1);
        eventManager.queueEvent(eventStub2);
        eventManager.update();

        InOrder firstThenSecondEvent = Mockito.inOrder(listenerMock);

        firstThenSecondEvent.verify(listenerMock).accept(eventStub1);
        firstThenSecondEvent.verify(listenerMock).accept(eventStub2);
    }

    @Test
    public void queueEventMultipleListeners() {
        Consumer<Event> listenerMock1 = getListenerMock();
        Consumer<Event> listenerMock2 = getListenerMock();

        Event eventStub = mock(Event.class);
        when(eventStub.getType()).thenReturn(EventType.COMBO);

        eventManager.addListener(listenerMock1, EventType.COMBO);
        eventManager.addListener(listenerMock2, EventType.COMBO);

        eventManager.queueEvent(eventStub);
        eventManager.update();

        verify(listenerMock1).accept(eventStub);
        verify(listenerMock2).accept(eventStub);
    }

    @Test
    public void queueEventInListenerCallback() {
        Event comboEventStub = mock(Event.class);
        when(comboEventStub.getType()).thenReturn(EventType.COMBO);

        Event enemiesKilledEventStub = mock(Event.class);
        when(enemiesKilledEventStub.getType()).thenReturn(EventType.ALL_ENEMIES_KILLED);

        Consumer<Event> listenerMock1 = getListenerMock();
        doAnswer(AdditionalAnswers.answerVoid(
                (event) -> eventManager.queueEvent(enemiesKilledEventStub)))
                .when(listenerMock1).accept(any(Event.class));

        Consumer<Event> listenerMock2 = getListenerMock();

        eventManager.addListener(listenerMock1, EventType.COMBO);
        eventManager.addListener(listenerMock2, EventType.ALL_ENEMIES_KILLED);
        eventManager.queueEvent(comboEventStub);
        eventManager.update();

        verify(listenerMock1).accept(comboEventStub);
        verify(listenerMock2, never()).accept(enemiesKilledEventStub);
    }

    @Test
    public void queueEventInListenerCallbackNextUpdate() {
        Event eventStub1 = mock(Event.class);
        when(eventStub1.getType()).thenReturn(EventType.COMBO);

        Event eventStub2 = mock(Event.class);
        when(eventStub2.getType()).thenReturn(EventType.ALL_ENEMIES_KILLED);

        Consumer<Event> listenerMock1 = getListenerMock();
        doAnswer(AdditionalAnswers.answerVoid(
                (event) -> eventManager.queueEvent(eventStub2)))
                .when(listenerMock1).accept(any(Event.class));

        Consumer<Event> listenerMock2 = getListenerMock();

        eventManager.addListener(listenerMock1, EventType.COMBO);
        eventManager.addListener(listenerMock2, EventType.ALL_ENEMIES_KILLED);
        eventManager.update();

        verify(listenerMock2, never()).accept(eventStub2);

        eventManager.update();

        verify(listenerMock2, never()).accept(eventStub2);
    }


    @SuppressWarnings("unchecked")
    private Consumer<Event> getListenerMock() {
        return mock(Consumer.class);
    }
}