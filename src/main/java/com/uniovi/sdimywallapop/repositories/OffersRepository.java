package com.uniovi.sdimywallapop.repositories;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {
    Page<Offer> findAllByUser(Pageable pageable, User user);

    Page<Offer> findAll(Pageable pageable);

    @Query("Select r FROM Offer r WHERE LOWER(r.title) LIKE LOWER(?1)")
    Page<Offer> searchByTitle(Pageable pageable, String searchText);

    List<Offer> findAllByComprador(Long id);
}
