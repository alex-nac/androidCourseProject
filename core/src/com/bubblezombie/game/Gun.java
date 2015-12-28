package com.bubblezombie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.bubblezombie.game.Bubbles.Bomb;
import com.bubblezombie.game.Bubbles.Bubble;
import com.bubblezombie.game.Bubbles.BubbleType;
import com.bubblezombie.game.Bubbles.Bullet;
import com.bubblezombie.game.Bubbles.ColorBomb;
import com.bubblezombie.game.Bubbles.FreezeBomb;
import com.bubblezombie.game.Bubbles.SimpleBubble;
import com.bubblezombie.game.EventSystem.GameEvent;
import com.bubblezombie.game.EventSystem.IncorrentGameEventDataException;
import com.bubblezombie.game.Util.GameConfig;
import com.bubblezombie.game.Util.Scene2dSprite;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Alex on 20/12/15.
 * <p/>
 * Class for SWAT gun-machine
 * We always have 2 bullet - one in a basket
 * and one in the gun
 */

public class Gun extends Actor {

    private static final String RES_GUN_DOWN = "game/gun_down.png";
    private static final String RES_GUN_TOP = "game/gun_top.png";

    //private static final int GUN_LENGTH = 34;
    private static final int SHOOTING_VEL = 300;
    private static final float BULLET_DIAMETR = 27.0f;
    private static final float SHOOTING_DELAY = 0.2f;
    private static final int VIEW_X = BubbleZombieGame.width / 2;
    private static final int VIEW_Y = -13;
    private static final int GUN_X = -4;
    private static final int GUN_Y = 32;
    private static final int TOP_GUN_X = 27;
    private static final int TOP_GUN_Y = -7;


    private Group _view = new Group();
    private Scene2dSprite _gun;
    private Group _bulletPlace = new Group();
    private Body _gunBody;
    private float _angle = 0;               //angle of gun's rotation
    private GameConfig _cfg;
    private World _space;
    private Boolean _canShoot = true;
    private Timer _canShootTimer;           //timer for delay in shooting
    private Boolean _repeatBulletsEnabled;
    private BubbleMesh _mesh;

    //2 bullets
    private Bubble _nextBullet;
    private Bubble _basketBullet;

    public Group getView() {
        return _view;
    }

    public void setCanShoot(Boolean value) {
        _canShoot = value;
        // TODO: здесь какая-то херня - это нужно чекнуть
        startCanShootTimer();
    }

    public Gun(GameConfig cfg, World space, Boolean repeateBulletsEnabled, BubbleMesh mesh) {
        BubbleZombieGame.INSTANCE.assetManager.load(RES_GUN_DOWN, Texture.class);
        BubbleZombieGame.INSTANCE.assetManager.load(RES_GUN_TOP, Texture.class);
        BubbleZombieGame.INSTANCE.assetManager.finishLoading();

        _mesh = mesh;
        _repeatBulletsEnabled = repeateBulletsEnabled;
        _space = space;

        _gun = new Scene2dSprite(BubbleZombieGame.INSTANCE.assetManager.get(RES_GUN_DOWN, Texture.class));
        _gun.addActor(_bulletPlace);
        Image topGun = new Image(BubbleZombieGame.INSTANCE.assetManager.get(RES_GUN_TOP, Texture.class));
        topGun.setPosition(TOP_GUN_X, TOP_GUN_Y);
        _gun.addActor(topGun);
        _gun.setX(GUN_X);
        _gun.setY(GUN_Y);
        _gun.setAnchorPoint(20, 20);
        _view.addActor(_gun);
        _cfg = cfg;
        _view.setPosition(VIEW_X, VIEW_Y);

        // body
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.KinematicBody;
        def.position.set(_view.getX() + _gun.getX() + 20, _view.getY() + _gun.getY() + 10);
        _gunBody = _space.createBody(def);

        // shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(_gun.getWidth() * 2, _gun.getHeight() / 4, new Vector2(0, _gun.getHeight() / 8), 0);

        // fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        _gunBody.createFixture(fixtureDef);

        //bullet in a basket
        //PutBullet();

        //bullet in a gun
        _nextBullet = GetNextBullet();
        _nextBullet.setSpace(_space);
        _nextBullet.getView().setAlpha(0);
        _bulletPlace.addActor(_nextBullet.getView());
        float scale = BULLET_DIAMETR / Bubble.DIAMETR;
        _nextBullet.getView().setScale(scale);
        _nextBullet.setX(-4);
        _nextBullet.getView().addAction(fadeIn(0.4f));

        // pause shooting timer
        _canShootTimer = new Timer();
        try {
            ///?????
            GameEvent event = new GameEvent(GameEvent.Type.SHOOT, _nextBullet);
            event.setTarget(this);
            ///?????
            notify(event, false);
        }
        catch (IncorrentGameEventDataException e) {
            Gdx.app.log("Gun", e.getMessage());
        }
    }

    // rotate gun
    public void setGunRotation(float degrees) {
        _gun.setRotation(degrees);
        _gunBody.setTransform(_gunBody.getPosition().x, _gunBody.getPosition().y, degrees * (float)Math.PI / 180f);

        try {
            ///?????
            GameEvent event = new GameEvent(GameEvent.Type.MOVED, (int)(_angle * 180 / MathUtils.PI));
            event.setTarget(this);
            ///?????
            notify(event, false);
        }
        catch (IncorrentGameEventDataException e) {
            Gdx.app.log("Gun", e.getMessage());
        }
    }

    public void Shoot() {
        if (!_canShoot) return;
        if (CheckForTouchingMesh()) {
            //Main.SM.PlaySound(new block_snd());
            return;
        }

        //Main.SM.PlaySound(new shot_01_snd());

        //shooting the bubble which is in a gun
        final Bubble bullet = _nextBullet;
        bullet.getView().addAction(sequence(
                moveTo(80 + bullet.getView().getWidth() / 2, bullet.getView().getY(), (80 + bullet.getView().getWidth() / 2) / SHOOTING_VEL),
                new Action() {
                    public boolean act(float delta) {
                        if (CheckForTouchingMesh()) {
                            bullet.Delete();
                            return true;
                        }

                        _view.getParent().addActor(bullet.getView());
                        _view.getParent().addActor(bullet.getEffects());

                        bullet.setSpace(_space);

                        bullet.setPosition(_gun.localToParentCoordinates(bullet.getPosition()));
                        bullet.setVelocity(new Vector2(SHOOTING_VEL * MathUtils.cos(-_angle), -SHOOTING_VEL * MathUtils.sin(-_angle)));
                        bullet.getView().addAction(scaleTo(1.0f, 1.0f, 0.1f)); //scale it to normal size

                        return true;
                    }
                }));

        if (bullet.isDead()) return;
        bullet.StartLifeTimer();
        bullet.setIsBullet(true);

        //moving basket bullet
        _nextBullet = _basketBullet;
        _nextBullet.getView().addAction(moveTo(_nextBullet.getView().getX(), 0, 0.3f)); // move it right

        //dispatching event about new bubble
        try {
            notify(new GameEvent(GameEvent.Type.SHOOT, _nextBullet), false);
        }
        catch (IncorrentGameEventDataException e) {
            Gdx.app.log("Gun", e.getMessage());
        }

        //and add new bullet to the basket
        PutBullet();

        //set delay
        startCanShootTimer();
    }

    // after shooting a bullet user should wait some time to shoot another one
    private void startCanShootTimer() {
        _canShootTimer.schedule(new Timer.Task() {
            @Override
            public void run() {
                _canShoot = true;
            }
        }, SHOOTING_DELAY);
    }

    private Bubble GetNextBullet() {
        Bubble bullet;

        if (Math.random() * 100 <= _cfg.superBulletPercent) {
            int n = (int) (Math.random() * 100);
            if (n < _cfg.bombPercent) {
                bullet = new Bomb();
            } else if (n > _cfg.bombPercent && n < _cfg.bombPercent + _cfg.freezeBombPercent) {
                bullet = new FreezeBomb();
            } else if (n > _cfg.bombPercent + _cfg.freezeBombPercent) {
                bullet = new ColorBomb();
            } else bullet = new Bullet();
        } else {
            bullet = new Bullet();
        }

        // checking if there are two bubbles with the same type or with the same color
        if (_nextBullet != null && !_repeatBulletsEnabled) {
            if (bullet.getType() == _nextBullet.getType()) {
                if (bullet.getType() == BubbleType.BOMB || bullet.getType() == BubbleType.FREEZE_BOMB)
                    return GetNextBullet();
                if (bullet.getType() == BubbleType.COLOR_BOMB &&
                        ((ColorBomb) bullet).getColor() == ((ColorBomb) _nextBullet).getColor())
                    return GetNextBullet();
            }
        }

        if (bullet instanceof SimpleBubble &&
                _mesh.GetRemainingBubblesByColor(((SimpleBubble) bullet).getColor()) <= 0)
            return GetNextBullet();

        return bullet;
    }

    // putting new bullet to the basket
    private void PutBullet() {
        _basketBullet = GetNextBullet();
        _basketBullet.setSpace(_space);
//        _basketBullet.getView().setAlpha(0);

        _bulletPlace.addActor(_basketBullet.getView());
        float scale = BULLET_DIAMETR / Bubble.DIAMETR;
        _basketBullet.getView().setScale(scale);
        _basketBullet.setX(-4);
        _basketBullet.setY(-28);

        _basketBullet.getView().addAction(fadeIn(0.4f));
    }

    private Boolean CheckForTouchingMesh() {
        // TODO: mesh collision
        /*
        var meshTouched:Boolean = false;
        _gunBody.arbiters.foreach(function (obj:Arbiter):void {
            if ((obj.body2.userData.ref is Bubble && (obj.body2.userData.ref as Bubble).isConnected) || (obj.body1.userData.ref is Bubble&& (obj.body1.userData.ref as Bubble).isConnected))
            meshTouched = true;
        });

        return meshTouched;
        */
        return false;
    }
}
