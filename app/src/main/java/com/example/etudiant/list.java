package com.example.etudiant;

public class list {
    private String name;
    private String first;
    private String second;
    private String fird;

    public list(String name) {
        this.name = name;
    }

    public list(String name, String first) {
        this.name = name;
        this.first = first;
    }

    public list(String name, String first, String second, String fird) {
        this.name = name;
        this.first = first;
        this.second = second;
        this.fird = fird;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getFird() {
        return fird;
    }

    public void setFird(String fird) {
        this.fird = fird;
    }
}
