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
import ru.s32xlevel.model.Restaurant;
import ru.s32xlevel.service.RestaurantService;
import ru.s32xlevel.to.RestaurantWithVote;
import ru.s32xlevel.util.RestaurantUtil;
import ru.s32xlevel.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.s32xlevel.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(RestaurantRestController.REST_RESTAURANT_URL)
public class RestaurantRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_RESTAURANT_URL = "/restaurant";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAll() {
        log.info("getAll restaurant");
        return RestaurantUtil.getAll(restaurantService.getAll());
    }

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllToDate(@RequestParam(value = "date", required = false)
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll restaurant for {} date", date);
        return RestaurantUtil.getAllWithoutMenu(restaurantService.getAll(), date);
    }

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllWithMenu(@RequestParam(value = "date", required = false)
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAll restaurant for {} date", date);
        return RestaurantUtil.getAllWithMenu(restaurantService.getAll(), date);
    }

    @GetMapping(value = "/date/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllWithMenuToDate(@RequestParam(value = "date", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAllWithMenu restaurant for {} date", date);
        return RestaurantUtil.getAllWithMenu(restaurantService.getAll(), date);
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllBetweenWithoutMenu(@RequestParam(value = "startDate", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam(value = "endDate", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("getAllBetweenWithoutMenu restaurant from {} to {} date", startDate, endDate);
        return RestaurantUtil.getAllWithoutMenu(restaurantService.getAll(), startDate, endDate);
    }

    @GetMapping(value = "/history/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllBetweenOfVotes(@RequestParam(value = "startDate", required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateVoice,
                                                         @RequestParam(value = "endDate", required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateVoice,
                                                         @RequestParam(value = "dateMenu", required = false)
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAllBetweenOfVotes restaurant from {} to {} date and menu for {} date", startDateVoice, endDateVoice, date);
        return RestaurantUtil.getAll(restaurantService.getAll(), startDateVoice, endDateVoice, date);
    }

    @GetMapping(value = "/history/menu/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVote> getAllBetweenOfVotesAndMenu(@RequestParam(value = "startDateVoice", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateVoice,
                                                                @RequestParam(value = "endDateVoice", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateVoice,
                                                                @RequestParam(value = "startDateMenu", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateMenu,
                                                                @RequestParam(value = "endDateMenu", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateMenu) {
        log.info("getAllBetweenOfVotesAndMenu restaurant votes from {} to {} date and menu from{} to {} date", startDateVoice, endDateVoice, startDateMenu, endDateMenu);
        return RestaurantUtil.getAll(restaurantService.getAll(), startDateVoice, endDateVoice, startDateMenu, endDateMenu);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> add(@Valid @RequestBody Restaurant restaurant) {
        log.info("add restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_RESTAURANT_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int restaurantId) {
        log.info("delete restaurant {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("update restaurant {}", restaurant);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantWithVote get(@PathVariable("id") int restaurantId) {
        log.info("get restaurant {}", restaurantId);
        return RestaurantUtil.get(restaurantService.get(restaurantId));
    }
}
