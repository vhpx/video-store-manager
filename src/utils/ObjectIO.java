package utils;

// abstract class with type parameter T
abstract class ObjectIO<T> {
    protected T manager;

    public ObjectIO(T manager) throws IllegalArgumentException {
        if (manager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        }

        this.manager = manager;
    }

    public abstract void loadData(String fileName);

    public abstract void saveData(String fileName);
}