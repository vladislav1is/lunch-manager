package com.redfox.lunchmanager.util;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.to.VoteTo;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Votes {

    public static int EMPTY = 0;
    public static LocalTime deadline = LocalTime.of(11, 0);

    public Votes() {
    }

    public static void setDeadline(LocalTime deadline) {
        Votes.deadline = deadline;
    }

    public static boolean canVoteAfter(LocalTime voteTime) {
        return LocalTime.now().isAfter(voteTime);
    }

    public static VoteTo convertToDto(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate());
    }

    public static Vote convertToEntity(VoteTo voteTo) {
        return new Vote(voteTo.getId(), null, null, voteTo.getVoteDate());
    }

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream()
                .map(Votes::convertToDto)
                .toList();
    }

    public static List<VoteTo> getTos(Vote... votes) {
        return Arrays.stream(votes)
                .map(Votes::convertToDto)
                .toList();
    }
}
