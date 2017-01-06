package com.bubblezombie.game.EventSystem.Events;

import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

public class BubbleConnectedEvent extends BaseEvent implements Event {
    private Bubble connectedBubble;

    public Bubble getConnectedBubble() {
        return connectedBubble;
    }

    public BubbleConnectedEvent(Bubble bubble) {
        super(EventType.BUBBLE_CONNECTED_TO_MESH);

        this.connectedBubble = bubble;
    }
}
