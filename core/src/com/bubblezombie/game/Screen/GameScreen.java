package com.bubblezombie.game.Screen;


import com.badlogic.gdx.Screen;
import com.bubblezombie.game.BubbleZombieGame;
import com.bubblezombie.game.GameLogic.BubbleZombieGameLogic;


public class GameScreen implements Screen {
    BubbleZombieGameLogic gameLogic;

    GameScreen(BubbleZombieGame game, int lvlNum) {
        gameLogic = new BubbleZombieGameLogic(game, lvlNum);
    }

    @Override
    public void show() {
        gameLogic.init();
    }

    @Override
    public void render(float delta) {
        gameLogic.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        gameLogic.pause();
    }

    @Override
    public void resume() {
        gameLogic.resume();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameLogic.dispose();
    }
}