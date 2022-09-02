package core;

import java.util.ArrayList;

import utils.ObjectIO;

public class Manager<T> {
    private ArrayList<T> objects;
    private ObjectIO<T> objectIO;

    public Manager() {
        this.objects = new ArrayList<>();
    }

    public void setIO(ObjectIO<T> objectIO, String fileName) {
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

    public void add(T object) {
        objects.add(object);
    }

    public void remove(T object) {
        objects.remove(object);
    }

    public ArrayList<T> getAll() {
        return objects;
    }

    public ArrayList<T> getCopy() {
        return new ArrayList<>(objects);
    }

    public void clear() {
        objects.clear();
    }

    public int size() {
        return objects.size();
    }
}
