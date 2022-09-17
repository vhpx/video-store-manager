package com.guccigang.videostoremanager.core;

public class Entity {
    private String id;

    public Entity(String id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception {
        this.id = id;
    }
}
