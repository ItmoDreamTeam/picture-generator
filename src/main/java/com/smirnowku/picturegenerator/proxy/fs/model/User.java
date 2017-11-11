package com.smirnowku.picturegenerator.proxy.fs.model;

import java.util.List;

import static java.util.Comparator.comparing;

public class User extends BaseEntity {

    private String username;
    private List<UploadedFile> files;

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
        this.files.sort(comparing(UploadedFile::getCreated));
//        this.files.sort(comparing(UploadedFile::getCreated).reversed());
    }

    public String getUsername() {
        return username;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }
}
