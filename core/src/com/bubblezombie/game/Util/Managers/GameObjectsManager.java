package com.bubblezombie.game.Util.Managers;

import com.bubblezombie.game.GameObjects.GameObject;

import java.util.ArrayList;

/**
 * Created by Alex on 24/01/16.
 * Class for safety adding and deleting game objects
 */
public class GameObjectsManager {
    private static final int GAME_OBJECTS_START_CAPACITY = 20;

    private ArrayList<GameObject> _gameObjects = new ArrayList<GameObject>(GAME_OBJECTS_START_CAPACITY);
    private ArrayList<GameObject> _objectsToAdd = new ArrayList<GameObject>(GAME_OBJECTS_START_CAPACITY);
    private ArrayList<GameObject> _objectsToDelete = new ArrayList<GameObject>(GAME_OBJECTS_START_CAPACITY);

    public void Update() {
        // safely add object before game update is started
        for (GameObject obj: _objectsToAdd)
            _gameObjects.add(obj);
        _objectsToAdd.clear();

        // update game objects
        for (GameObject obj : _gameObjects) obj.Update();

        // safely delete object after game update is ended
        for (GameObject obj: _objectsToDelete) {
            _gameObjects.remove(obj);
            obj.Delete();
        }
        _objectsToDelete.clear();
    }

    public void Pause() {
        for (GameObject obj : _gameObjects)
            obj.Pause();
    }

    public void Resume() {
        for (GameObject obj : _gameObjects)
            obj.Resume();
    }

    public void Dispose() {
        for (GameObject obj : _gameObjects)
            obj.Delete();
        _gameObjects.clear();
        _objectsToDelete.clear();
        _objectsToAdd.clear();
    }

    public void AddGameObject(GameObject obj) {
        _objectsToAdd.add(obj);
    }

    public void RemoveGameObject(GameObject obj) {
        if (!_objectsToDelete.contains(obj))
            _objectsToDelete.add(obj);
    }
}
