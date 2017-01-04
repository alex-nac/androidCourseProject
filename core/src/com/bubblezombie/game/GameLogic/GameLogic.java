package com.bubblezombie.game.GameLogic;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.bubblezombie.game.GameObjects.GameObject;

public interface GameLogic {

    enum GameState {
        LOSE, WON, UNDEF
    }

    void init();

    void render(float delta);

    void Update(float delta);

    void pause();

    void resume();


    void AddGameObject(GameObject gameObject);

    void RemoveGameObject(GameObject gameObject);

    void AddContactListener(ContactListener contactListener);

    void RemoveContactListener(ContactListener contactListener);

    void AddTween(Tween tween);
}
