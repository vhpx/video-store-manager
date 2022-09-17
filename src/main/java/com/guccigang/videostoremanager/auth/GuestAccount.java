package com.guccigang.videostoremanager.auth;

public class GuestAccount extends Account {
    public GuestAccount(String id, String username, String password, String address, String phone, String name, String role, int points) {
        super(id, username, password, address, phone, name, role, points);
    }
}
