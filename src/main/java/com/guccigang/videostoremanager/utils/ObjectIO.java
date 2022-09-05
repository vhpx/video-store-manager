package com.guccigang.videostoremanager.utils;

import java.util.ArrayList;

import com.guccigang.videostoremanager.core.Manager;

public class ObjectIO<O> {
    private final Manager<O> manager;
    private final ObjectUtils<O> utils;

    private final String objectsType;
    private String fileName;

    public ObjectIO(Manager<O> manager, ObjectUtils<O> utils, String objectsType) throws IllegalArgumentException {
        if (manager == null)
            throw new IllegalArgumentException("manager cannot be null");

        if (utils == null)
            throw new IllegalArgumentException("utils cannot be null");

        if (objectsType == null)
            throw new IllegalArgumentException("objectsType cannot be null");

        this.manager = manager;
        this.utils = utils;
        this.objectsType = objectsType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void checkFileName() throws IllegalArgumentException {
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("fileName cannot be null or empty");
    }

    public void load() {
        final String action = "load";

        try {
            // Check if the file name is set
            checkFileName();

            var lines = IOHelper.readFile(fileName);

            for (var line : lines) {
                var data = utils.parse(line);
                manager.add(data);
            }

            int amount = lines.size();
            printLoadedMessage(amount, objectsType, fileName);
        } catch (Exception e) {
            printErrorMessage(e, action, objectsType, fileName);
        }
    }

    public void save() {
        final String action = "save";

        try {
            // Check if the file name is set
            checkFileName();

            var items = manager.getAll();
            var lines = new ArrayList<String>();

            for (var i : items) {
                String line = utils.serialize(i);
                lines.add(line);
            }

            IOHelper.createFile(fileName, lines);

            int amount = items.size();
            printSavedMessage(amount, objectsType, fileName);
        } catch (Exception e) {
            printErrorMessage(e, action, objectsType, fileName);
        }
    }

    protected void printInfoMessage(String action, int n, String objectsType, String fileName) {
        String formattedAction = action.substring(0, 1).toUpperCase() + action.substring(1);
        System.out
                .println("Info: " + formattedAction + " " + n + " " + objectsType + " from \"" + fileName + "\" file.");
    }

    protected void printLoadedMessage(int n, String objectsType, String fileName) {
        printInfoMessage("loaded", n, objectsType, fileName);
    }

    protected void printSavedMessage(int n, String objectsType, String fileName) {
        printInfoMessage("saved", n, objectsType, fileName);
    }

    protected void printErrorMessage(Exception e, String action, String objectsType, String fileName) {
        String formattedAction = action.toLowerCase();
        System.out.println(
                "Error: Failed to " + formattedAction + " " + objectsType + " from \"" + fileName + "\" file.");
        System.out.println(e.getMessage());
    }
}