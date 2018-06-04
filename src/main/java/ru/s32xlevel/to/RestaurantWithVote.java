package ru.s32xlevel.to;

import ru.s32xlevel.model.Dish;
import ru.s32xlevel.model.Restaurant;

import java.util.List;

public class RestaurantWithVote {
    private int id;

    private String name;

    private List<Dish> menu;

    private int vote;

    public RestaurantWithVote(Restaurant restaurant, List<Dish> menu, int vote) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.menu = menu;
        this.vote = vote;
    }

    public RestaurantWithVote(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.vote = restaurant.getVotes() == null || restaurant.getVotes().size() == 0 ? 0 :
                restaurant.getVotes().size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
