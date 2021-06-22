package com.haims.pojo;

public class Type {
    private String id;

    private String type;

    private String parentId;

    private Integer tLevel;

    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer gettLevel() {
        return tLevel;
    }

    public void settLevel(Integer tLevel) {
        this.tLevel = tLevel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}