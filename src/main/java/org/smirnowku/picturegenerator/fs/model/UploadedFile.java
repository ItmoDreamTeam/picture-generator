package org.smirnowku.picturegenerator.fs.model;

public class UploadedFile extends BaseEntity {

    private String name;
    private String size;

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
