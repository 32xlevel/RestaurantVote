package ru.s32xlevel.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes WHERE r.id=:id")
    Optional<Restaurant> findById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r where r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes ORDER BY r.name")
    List<Restaurant> findAll();
}
