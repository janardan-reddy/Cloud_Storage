package com.ing.cs.services;


public final class CloudObject {
    private final String name;
    private final String prefix;
    private final String contentType;
    private final long size;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }


    public String getPrefix() {
        return prefix;
    }

    public String getContentType() {
        return contentType;
    }

    public CloudObject(String prefix, String name, String contentType, long size) {
        this.prefix = prefix;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    @Override
    public String toString() {
        return "CloudObject[" +
                "prefix=" + prefix + ", " +
                "name=" + name + ", " +
                "size=" + size +']';
    }

}
