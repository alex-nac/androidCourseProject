package com.bubblezombie.game.EventSystem.Events;

import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

public class GunRotatedEvent extends BaseEvent implements Event {
    private int angle;

    public int getAngle() {
        return angle;
    }

    public GunRotatedEvent(int angle) {
        super(EventType.GUN_ROTATED);

        this.angle = angle;
    }
}
