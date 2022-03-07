package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.to.VoteTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController extends AbstractVoteController {

    protected static final String REST_URL = "/rest/votes";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> voteToday(@RequestParam int restaurantId) {
        var created = super.createToday(restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoteToday(@RequestParam int restaurantId) {
        super.updateToday(restaurantId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToday() {
        super.deleteToday();
    }

    @GetMapping("/by-date")
    public VoteTo getOwnByDate(@RequestParam LocalDate voteDate) {
        return super.getBy(voteDate);
    }

    @GetMapping
    public List<VoteTo> getAllOwn() {
        return super.getAll();
    }

    @Override
    @GetMapping("/count")
    public int countBy(@RequestParam LocalDate voteDate, @RequestParam int restaurantId) {
        return super.countBy(voteDate, restaurantId);
    }
}