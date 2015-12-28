package com.bubblezombie.game.EventSystem;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleColor;
import com.bubblezombie.game.Bubbles.BubbleType;
import com.bubblezombie.game.Bubbles.ColorBomb;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.Util.Scene2dSprite;

import java.util.ArrayList;

/**
 * Created by Alex on 28/12/15.
 * General event system
 * Long story short: I decided not to divide events in many classes
 * There is only one class handling all the game events
 */
public class GameEvent extends Event {

    private Type _type;

    // combo event data
    private ArrayList<Bubble> _killedBubbles;

    // gun events data
    private Scene2dSprite _bulletSprite;
    private BubbleType _bulletType;
    private BubbleColor _bulletColor = BubbleColor.NONE;
    private int _angle;

    // getters
    public Type getType() { return _type; }
    public ArrayList<Bubble> getKilledBubbles() { return _killedBubbles; };
    public Scene2dSprite getBulletSprite() { return _bulletSprite; }
    public BubbleType getBulletType() { return _bulletType; }
    public BubbleColor getBulletColor() { return _bulletColor; }
    public int getAngle() { return _angle; }

    // general
    public GameEvent(Type type) throws IncorrentGameEventDataException {
        if (type == Type.COMBO || type == Type.SHOOT || type == Type.MOVED)
            throw new IncorrentGameEventDataException("Not enough data provided for this kind of event");

        _type = type;
    }

    // combo
    public GameEvent(Type type, ArrayList<Bubble> killedBubbles) throws IncorrentGameEventDataException {
        if (type != Type.COMBO) throw new IncorrentGameEventDataException("Trying to create combo event without providing killed bubbles");

        _type = type;
        _killedBubbles = killedBubbles;
    }

    // gun
    public GameEvent(Type type, Bubble nextBullet) throws IncorrentGameEventDataException { // shoot
        if (type != Type.SHOOT)
            throw new IncorrentGameEventDataException("Trying to create gun shoot event without providing shooted bullet");
        _type = type;
        _bulletSprite = nextBullet.GetBubbleImage();
        _bulletType = nextBullet.getType();
        if (_bulletType == BubbleType.SIMPLE)
            _bulletColor = ((SimpleBubble)nextBullet).getColor();
        if (_bulletType == BubbleType.COLOR_BOMB)
            _bulletColor = ((ColorBomb)nextBullet).getColor();
    }

    public GameEvent(Type type, int angle) throws IncorrentGameEventDataException { // moved
        if (type != Type.MOVED)
            throw new IncorrentGameEventDataException("Trying to create gun moved event without providing angle");

        _type = type;
        _angle = angle;
    }

    // event types
    static public enum Type {
        // general
        ALL_ENEMIES_KILLED,

        // combo
        COMBO,

        // airplane
        END_FLY,
        END_LAST_WAVE,

        // mesh
        NEW_ROW,
        LAST_WAVE,

        // gun
        SHOOT,
        MOVED;
    }
}
