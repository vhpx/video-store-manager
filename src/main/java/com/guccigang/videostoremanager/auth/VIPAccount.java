package com.guccigang.videostoremanager.auth;

public class VIPAccount extends Account {
    private int points = 0;

    public VIPAccount(int points) {
        this.points = points;
    }

    public VIPAccount(String id, String username, String password, int points) {
        super(id, username, password);
        this.points = points;
    }

    public VIPAccount(String id, String username, String password, String address, String phone, String name,
            String role, int points) {
        super(id, username, password, address, phone, name, "VIP");
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
