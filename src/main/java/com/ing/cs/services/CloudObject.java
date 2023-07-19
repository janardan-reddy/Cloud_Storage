package com.ing.cs.services;

import java.io.InputStream;
import java.util.Objects;

public final class CloudObject {
    private String name;
    private long size;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public CloudObject(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return "CloudObject[" +
                "name=" + name + ", " +
                "size=" + size +']';
    }


}
