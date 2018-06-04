package ru.s32xlevel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "votes_date_time_idx")} )
public class Vote extends AbstractBaseEntity {

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Integer userId;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateTime;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Integer id, Integer userId, LocalDateTime dateTime, Restaurant restaurant) {
        super(id);
        this.userId = userId;
        this.dateTime = dateTime;
        this.restaurant = restaurant;
    }

    public Vote(Integer userId, LocalDateTime dateTime) {
        this.userId = userId;
        this.dateTime = dateTime;
    }

    public Vote(Vote v) {
        this(v.getId(), v.getUserId(), v.getDateTime(), v.getRestaurant());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
