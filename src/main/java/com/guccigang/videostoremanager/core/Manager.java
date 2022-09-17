package com.guccigang.videostoremanager.core;

import java.util.ArrayList;

import com.guccigang.videostoremanager.utils.ObjectIO;

public class Manager<M extends Entity> {
    private final ArrayList<M> objects;
    private ObjectIO<M> objectIO;

    public Manager() {
        this.objects = new ArrayList<>();
    }

    public void setIO(ObjectIO<M> objectIO, String fileName) {
        this.objectIO = objectIO;
        this.objectIO.setFileName(fileName);
    }

    public void save() {
        if (objectIO == null)
            throw new IllegalArgumentException("objectIO cannot be null");

        objectIO.save();
    }

    public void load() {
        if (objectIO == null)
            throw new IllegalArgumentException("objectIO cannot be null");

        objectIO.load();
    }

    public M get(String id) {
        for (var object : objects)
            if (object.getId().equals(id))
                return object;

        return null;
    }

    public void add(M object) {
        objects.add(object);
    }

    public void remove(M object) {
        objects.remove(object);
    }

    public ArrayList<M> getAll() {
        return objects;
    }

    public ArrayList<M> getCopy() {
        return new ArrayList<>(objects);
    }

    public void clear() {
        objects.clear();
    }

    public int size() {
        return objects.size();
    }
}
