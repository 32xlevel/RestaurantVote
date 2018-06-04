package ru.s32xlevel.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.s32xlevel.AuthorizedUser;
import ru.s32xlevel.model.Vote;
import ru.s32xlevel.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(VoteRestController.REST_VOTE_URL)
public class VoteRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_VOTE_URL = "/vote";

    @Autowired
    private VoteService voteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(@RequestParam(value = "date", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return voteService.getAll(date);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllToRestaurant(@PathVariable("id") int restaurantId,
                                         @RequestParam(value = "date", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return voteService.getAllToRestaurant(date, restaurantId);
    }

    @GetMapping(value = "/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAllToRestaurantHistory(@PathVariable("id") int restaurantId,
                                                @RequestParam(value = "date", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                @RequestParam(value = "date", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return voteService.getAllToRestaurantHistory(startDate, endDate, restaurantId);
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getBetween(@RequestParam(value = "startDate", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                 @RequestParam(value = "endDate", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return voteService.getAllBetween(startDate, endDate);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Vote> vote(@PathVariable("id") int restaurantId, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote v = voteService.vote(restaurantId, authorizedUser.getId(), LocalTime.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_VOTE_URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(v);
    }

    @GetMapping(value = "/is/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@RequestParam(value = "date", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                    @PathVariable("id") int id) {
        return voteService.get(id, date);
    }
}
