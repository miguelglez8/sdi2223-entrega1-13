package com.uniovi.sdimywallapop.repositories;

import com.uniovi.sdimywallapop.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);
}
