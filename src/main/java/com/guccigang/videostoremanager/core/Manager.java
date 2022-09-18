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
        // Save the objects to the local storage
        // If the I/O helper is not set, then throw an exception
        if (objectIO == null)
            throw new IllegalArgumentException("objectIO cannot be null");

        objectIO.save();
    }

    public void load() {
        // Load the objects from the local storage
        // If the I/O helper is not set, then throw an exception
        if (objectIO == null)
            throw new IllegalArgumentException("objectIO cannot be null");

        objectIO.load();
    }

    public M get(String id) {
        // Loop through the object list to find the object
        // with the same id and then return
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
        // Get the object list via pointer
        return objects;
    }

    public ArrayList<M> getCopy() {
        // Get a copy of the object list
        return new ArrayList<>(objects);
    }

    public void clear() {
        // Clear the object list
        objects.clear();
    }

    public int size() {
        return objects.size();
    }
}
