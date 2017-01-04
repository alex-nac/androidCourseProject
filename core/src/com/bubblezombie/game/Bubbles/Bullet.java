package com.bubblezombie.game.Bubbles;

import com.badlogic.gdx.graphics.Texture;
import com.bubblezombie.game.GameLogic.BubbleZombieGameLogic;
import com.bubblezombie.game.GameObjects.BubbleDeleter;
import com.bubblezombie.game.GameObjects.BubbleMesh;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.EventSystem.GameEvent;
import com.bubblezombie.game.EventSystem.IncorrentGameEventDataException;
import com.bubblezombie.game.Screen.GameScreen;
import com.bubblezombie.game.Util.BFS;
import com.bubblezombie.game.Util.CoreClasses.SpriteActor;

import java.util.ArrayList;

/**
 * Created by Alex on 24/12/15.
 */
public class Bullet extends SimpleBubble {

    private static final String blue = "game/bubbles/bomb_normal_blue.png";
    private static final String green = "game/bubbles/bomb_normal_green.png";
    private static final String pink = "game/bubbles/bomb_normal_pink.png";
    private static final String red = "game/bubbles/bomb_normal_red.png";
    private static final String violett = "game/bubbles/bomb_normal_violett.png";
    private static final String yellow = "game/bubbles/bomb_normal_yellow.png";

    public Bullet() {
        this(com.bubblezombie.game.Enums.BubbleColor.NONE);
    }

    public Bullet(com.bubblezombie.game.Enums.BubbleColor color) {
        super(color);
        setColor(_color);
    }

    @Override
    public void setColor(com.bubblezombie.game.Enums.BubbleColor newColor) {
        super.setColor(newColor);
        setBulletView();
    }

    private void setBulletView() {
        SpriteActor sprite;

        sprite = getBubbleImage();

        setScale( DIAMETR / sprite.getWidth());

        sprite.setWidth(getScale() * sprite.getWidth());
        sprite.setHeight(getScale() * sprite.getHeight());

        setView(sprite);
    }

    public SpriteActor getBubbleImage() {
        if (!BubbleZombieGame.INSTANCE.assetManager.isLoaded(pink)) {
            // TODO: this is bad
            BubbleZombieGame.INSTANCE.assetManager.load(blue, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(green, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(pink, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(red, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(violett, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.load(yellow, Texture.class);
            BubbleZombieGame.INSTANCE.assetManager.finishLoading();
        }

        SpriteActor sprite = null;

        switch (getBubbleColor()) {
            case PINK:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(pink, Texture.class));
                break;
            case YELLOW:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(yellow, Texture.class));
                break;
            case RED:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(red, Texture.class));
                break;
            case GREEN:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(green, Texture.class));
                break;
            case BLUE:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(blue, Texture.class));
                break;
            case VIOLETT:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(violett, Texture.class));
                break;
            case UBER_BLACK:
                sprite = new SpriteActor(BubbleZombieGame.INSTANCE.assetManager.get(violett, Texture.class));
                break;
        }
        return sprite;
    }


    @Override
    protected void onConnected(BubbleMesh mesh) {
        super.onConnected(mesh);

        //Main.SM.PlaySound(new bomb_touch_snd());
        ArrayList<Bubble> deletedBubbles = BFS.getSameColorBubbles(getMeshPosition(), false);

        if(deletedBubbles.size() >= 3) {
            deletedBubbles = BFS.getSameColorBubbles(getMeshPosition(), true);

            for (Bubble bbl: deletedBubbles)
                mesh.DisconnectBubble(bbl);

            deletedBubbles.addAll(BFS.getUnrootedBubbles());
            for (Bubble bbl :deletedBubbles) mesh.DisconnectBubble(bbl);

            try {
                GameEvent event = new GameEvent(GameEvent.Type.COMBO, deletedBubbles);
                event.setTarget(mesh);
                mesh.notify(event, false);
            } catch (IncorrentGameEventDataException e) {
                e.printStackTrace();
            }

            // start deleter
            BubbleZombieGameLogic.instance.AddGameObject(new BubbleDeleter(getPosition(), deletedBubbles, _body.getWorld()));
        }
    }

//    @Override
//    public void Delete(boolean withPlane) {
//        //useless
//        Main.SM.PlaySound(new shot_04_snd());
//        var expl:Animation = new Animation(new bomb_expl_mc(), 0.9 * scale);
//        expl.x = view.x;
//        expl.y = view.y;
//        if (_mesh) _mesh.AddEffect(expl);
//        else view.parent.addChild(expl);
//
//        super.Delete(withPlane);
//    }
}
