package ru.s32xlevel.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Dish;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Override
    Dish save(Dish dish);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    Optional<Dish> findById(Integer id);

    @Query("SELECT DISTINCT d FROM Dish d LEFT JOIN FETCH d.restaurant WHERE d.restaurant.id=:rId and d.date=:date ORDER BY d.name")
    List<Dish> findAllByRestaurantIdAndDate(@Param("rId") Integer restaurantId, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT d FROM Dish d LEFT JOIN FETCH d.restaurant WHERE d.restaurant.id=:rId AND d.date >= :startDate AND d.date <= :endDate")
    List<Dish> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("rId") int id);
}
