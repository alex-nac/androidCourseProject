package com.bubblezombie.game.Entities;

import java.util.Map;

public interface Entity {
    void Update(int deltaMs);
    void Destroy();

    void AddComponent(EntityComponent component);
    EntityComponent GetComponent(int id);
    EntityComponent GetComponent(String name);
    Map<Integer, EntityComponent> GetComponents();
}
