package com.haims.pojo;

public class MangerUrl {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MangerUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    private String name;
    private String url;
}
