package com.handson.jonnatas.architecturepatternswithjava;

public class FakeSession {
    public boolean commited = false;

    public void commit() {
        commited = true;
    }
}
