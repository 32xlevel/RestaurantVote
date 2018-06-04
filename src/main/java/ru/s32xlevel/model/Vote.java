package ru.s32xlevel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_date_idx")} )
public class Vote extends AbstractBaseEntity {

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Column(name = "date", nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, Integer userId, LocalDate date, Restaurant restaurant) {
        super(id);
        this.userId = userId;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Vote(Integer userId, LocalDate dateTime) {
        this.userId = userId;
        this.date = dateTime;
    }

    public Vote(Vote v) {
        this(v.getId(), v.getUserId(), v.getDate(), v.getRestaurant());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dateTime) {
        this.date = dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
