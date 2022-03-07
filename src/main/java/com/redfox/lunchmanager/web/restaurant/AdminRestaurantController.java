package com.redfox.lunchmanager.web.restaurant;

import com.redfox.lunchmanager.View;
import com.redfox.lunchmanager.to.RestaurantTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/admin/restaurants";

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant, @PathVariable int id) {
        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant) {
        var created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping("/{id}/with-dishes")
    public RestaurantTo getWithDishesBy(@RequestParam @Nullable LocalDate date, @PathVariable int id) {
        return super.getWithDishesBy(Objects.isNull(date) ? LocalDate.now() : date, id);
    }
}
