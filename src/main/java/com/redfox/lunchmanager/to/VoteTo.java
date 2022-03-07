package com.redfox.lunchmanager.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo extends BaseTo {

    private final LocalDate voteDate;

    @ConstructorProperties({"id", "voteDate"})
    public VoteTo(Integer id, LocalDate voteDate) {
        super(id);
        this.voteDate = voteDate;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(id, voteTo.id) && voteDate.equals(voteTo.voteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voteDate);
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", voteDate=" + voteDate +
                '}';
    }
}
