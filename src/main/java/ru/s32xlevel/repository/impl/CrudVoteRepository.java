package ru.s32xlevel.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Override
    Vote save(Vote vote);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.userId=:userId and v.dateTime=:dateTime")
    Vote get(@Param("dateTime") LocalDateTime dateTime, @Param("userId") int userId);

    @Query("SELECT DISTINCT v from Vote v LEFT JOIN FETCH v.restaurant WHERE v.dateTime >= :startDate AND v.dateTime <= :endDate")
    List<Vote> findAllByDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.dateTime=:dateTime")
    List<Vote> findAllByDateTime(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT DISTINCT v from Vote v LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id=:rId and v.dateTime=:dateTime")
    List<Vote> findAllByDateTimeAndRestaurantId(@Param("dateTime") LocalDateTime dateTime, @Param("rId") int restaurantId);

    @Query("SELECT DISTINCT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.restaurant.id=:rId and v.dateTime>=:startDate and v.dateTime<=:endDate")
    List<Vote> findAllToRestaurantHistory(int restaurantId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<Vote> findByIdAndRestaurantId(Integer id, Integer restaurantId);
}
