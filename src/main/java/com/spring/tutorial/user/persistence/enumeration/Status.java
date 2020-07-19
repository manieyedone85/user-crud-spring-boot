package com.spring.tutorial.user.persistence.enumeration;

public enum Status {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE"), DELETED("DELETED");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
