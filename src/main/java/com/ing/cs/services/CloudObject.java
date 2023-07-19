package com.ing.cs.services;


public final class CloudObject {
    private final String name;
    private final String prefix;
    private final long size;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public CloudObject(String prefix, String name, long size) {
        this.prefix = prefix;
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return "CloudObject[" +
                "prefix=" + prefix + ", " +
                "name=" + name + ", " +
                "size=" + size +']';
    }


    public String getPrefix() {
        return prefix;
    }

}
