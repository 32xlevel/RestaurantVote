package ru.s32xlevel.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.s32xlevel.model.User;
import ru.s32xlevel.repository.UserRepository;
import ru.s32xlevel.repository.impl.CrudUserRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(new Sort(Sort.Direction.ASC, "name", "email"));
    }
}
