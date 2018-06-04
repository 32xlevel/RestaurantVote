package ru.s32xlevel.repository.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.s32xlevel.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Override
    @Transactional
    User save(User user);

    @Override
    Optional<User> findById(Integer id);

    User getByEmail(String email);

    @Override
    List<User> findAll(Sort sort);

    @Transactional
    @Modifying
    @Query("DELETE from User u where u.id=:id")
    int delete(@Param("id") int id);
}
