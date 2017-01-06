package com.bubblezombie.game.EventSystem.Events;

import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.EventSystem.Event;
import com.bubblezombie.game.EventSystem.EventType;

public class GunShootEvent extends BaseEvent implements Event {
    private Bubble nextBullet;
    private Bubble nowShotBullet;

    public GunShootEvent(Bubble nextBullet, Bubble nowShotBullet) {
        super(EventType.GUN_SHOOT);

        this.nextBullet = nextBullet;
        this.nowShotBullet = nowShotBullet;
    }
}
