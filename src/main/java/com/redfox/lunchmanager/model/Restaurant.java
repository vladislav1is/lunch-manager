package com.redfox.lunchmanager.model;

public class Restaurant extends AbstractNamedEntity {

    public Restaurant(String title) {
        this(null, title);
    }

    public Restaurant(Integer id, String title) {
        super(id, title);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + name + '\'' +
                '}';
    }
}
