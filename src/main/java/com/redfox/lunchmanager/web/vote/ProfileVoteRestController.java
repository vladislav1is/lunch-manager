package com.redfox.lunchmanager.web.vote;

import com.redfox.lunchmanager.to.VoteTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController extends AbstractVoteController {

    protected static final String REST_URL = "/rest/restaurants/{restaurantId}/votes";

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        super.delete(id, restaurantId);
    }

    @Override
    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }

    @Override
    @GetMapping("/by")
    public VoteTo getByDate(@RequestParam LocalDate localDate) {
        return super.getByDate(localDate);
    }

    @Override
    @GetMapping
    public List<VoteTo> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @Override
    @GetMapping("/filter")
    public List<VoteTo> getBetween(@RequestParam @Nullable LocalDate startDate,
                                   @RequestParam @Nullable LocalDate endDate,
                                   @PathVariable int restaurantId) {
        return super.getBetween(startDate, endDate, restaurantId);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo, @PathVariable int id, @PathVariable int restaurantId) {
        super.update(voteTo, id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> createWithLocation(@RequestBody VoteTo vote, @PathVariable int restaurantId) {
        var created = super.create(vote, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}