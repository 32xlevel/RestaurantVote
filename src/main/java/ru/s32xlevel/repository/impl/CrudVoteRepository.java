package ru.s32xlevel.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Override
    Vote save(Vote vote);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.userId=:userId and v.date=:date")
    Vote get(@Param("date") LocalDate dateTime, @Param("userId") int userId);

    @Query("SELECT DISTINCT v from Vote v LEFT JOIN FETCH v.restaurant WHERE v.date>=:startDate AND v.date<=:endDate")
    List<Vote> findAllByDateTimeBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.date=:date")
    List<Vote> findAllByDateTime(@Param("date") LocalDate dateTime);

    @Query("SELECT DISTINCT v from Vote v LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id=:rId and v.date=:date")
    List<Vote> findAllByDateTimeAndRestaurantId(@Param("date") LocalDate dateTime, @Param("rId") int restaurantId);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id=:rId and v.date>=:startDate and v.date<=:endDate")
    List<Vote> findAllToRestaurantHistory(int restaurantId, LocalDate startDate, LocalDate endDate);

    Optional<Vote> findByIdAndRestaurantId(Integer id, Integer restaurantId);
}
