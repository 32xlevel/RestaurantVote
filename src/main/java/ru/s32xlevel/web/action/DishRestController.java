package ru.s32xlevel.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.s32xlevel.model.Dish;
import ru.s32xlevel.service.DishService;
import ru.s32xlevel.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.s32xlevel.util.ValidationUtil.assureIdConsistent;
import static ru.s32xlevel.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(DishRestController.REST_URL)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/menu";

    @Autowired
    private DishService dishService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll(@PathVariable("id") int restaurantId,
                             @RequestParam(value = "date", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        log.info("getAll dish for {} restaurant for {} date", restaurantId, date);
        return dishService.getAll(restaurantId, date);
    }

    @GetMapping(value = "/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getBetween(@PathVariable("id") int restaurantId,
                                 @RequestParam(value = "startDate", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                 @RequestParam(value = "endDate", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("getBetween dish for {} restaurant from {} to {} date", restaurantId, startDate, endDate);
        return dishService.getAllBetween(restaurantId, startDate, endDate);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable("id") int restaurantId) {
        checkNew(dish);
        Dish created = dishService.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping(value = "/{rId}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Dish dish, @PathVariable("id") int id, @PathVariable("rId") int restaurantId) {
        assureIdConsistent(dish, id);
        dishService.update(dish, restaurantId);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int dishId) {
        dishService.delete(dishId);
    }

    @GetMapping(value = "/is/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int dishId) {
        return dishService.get(dishId);
    }
}
