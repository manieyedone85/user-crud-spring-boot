package com.spring.tutorial.user.persistence.enumeration;

public enum Gender {
    Male("Male"), Female("Female"), Others("Others");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }
}
