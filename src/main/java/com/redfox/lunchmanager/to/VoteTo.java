package com.redfox.lunchmanager.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo {

    private final LocalDate registered;

    @ConstructorProperties({"id", "registered"})
    public VoteTo(Integer id, LocalDate registered) {
        super(id);
        this.registered = registered;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(id, voteTo.id) && registered.equals(voteTo.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registered);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", registered=" + registered +
                '}';
    }
}
