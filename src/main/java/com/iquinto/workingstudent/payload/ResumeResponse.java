package com.iquinto.workingstudent.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeResponse {
    private String id;
    private String name;
    private String alias;
    private String description;
    private String url;
    private String type;
    private long size;

    public ResumeResponse() {
    }

    public ResumeResponse(String id, String name, String alias, String description, String url, String type, long size) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}