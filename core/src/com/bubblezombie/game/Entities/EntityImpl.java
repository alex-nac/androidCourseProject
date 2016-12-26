package com.bubblezombie.game.Entities;

import java.util.Map;

public class EntityImpl implements Entity {
    private int id;
    private EntityType type;

    private Map<Integer, EntityComponent> components;

    public EntityImpl(int id, EntityType type) {
        this.id = id;
        this.type = type;
    }


    @Override
    public void Update(int deltaMs) {

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void AddComponent(EntityComponent component) {

    }

    @Override
    public EntityComponent GetComponent(int id) {
        return null;
    }

    @Override
    public EntityComponent GetComponent(String name) {
        return null;
    }

    @Override
    public Map<Integer, EntityComponent> GetComponents() {
        return null;
    }
}
