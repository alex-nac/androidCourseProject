package com.bubblezombie.game.EventSystem.Events;


import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

import java.util.List;

public class ComboEvent extends BaseEvent implements Event {
    private List<Bubble> killedBubbles;

    public List<Bubble> getKilledBubbles() {
        return killedBubbles;
    }

    public ComboEvent(List<Bubble> killedBubbles) {
        super(EventType.COMBO);

        this.killedBubbles = killedBubbles;
    }
}
