package ru.s32xlevel.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_idx")})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @OrderBy("name")
    private List<Dish> menu;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName());
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
