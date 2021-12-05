package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.to.VoteTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public class Votes {

    public Votes() {
    }

    public static boolean canRevoteBefore(int hour, int minute) {
        return LocalTime.now().isBefore(LocalTime.of(hour, minute));
    }

    public static VoteTo convertToDto(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRegistered());
    }

    public static Vote convertToEntity(VoteTo voteTo) {
        return new Vote(voteTo.getId(), null, null, voteTo.getRegistered());
    }

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream()
                .map(Votes::convertToDto)
                .toList();
    }
}
