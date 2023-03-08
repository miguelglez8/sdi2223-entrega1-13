package com.uniovi.sdimywallapop.repositories;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import org.jboss.logging.Messages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    List<Conversation> findAllBySeller(User user);
    Page<Conversation> findAll(Pageable pageable);

    @Query("Select c from Conversation c where c.buyer.dni LIKE ?1 and c.seller.dni LIKE ?2 and c.offer.id = ?3")
    Conversation findByBuyerSellerAndOffer(String buyerDni, String sellerDni, Long id);

    @Query("Select c from Conversation c where c.buyer.dni LIKE ?1 and c.offer.id = ?2")
    Conversation searchByUserAndOffer(String dni, Long id);
}
