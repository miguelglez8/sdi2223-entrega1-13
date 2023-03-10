package com.uniovi.sdimywallapop.services;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.repositories.OffersRepository;
import com.uniovi.sdimywallapop.validators.OfferBuyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    public Page<Offer> getOffers(Pageable pageable) {
        Page<Offer> offers = offersRepository.findAll(pageable);
        return offers;
    }

    public Offer getOffer(Long id) {
        return offersRepository.findById(id).get();
    }

    public void addOffer(Offer offer) {
        offersRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
    }

    public Page<Offer> getOffersForUser(Pageable pageable, User user) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        if (user.getRole().equals("ROLE_USER")) {
            offers = offersRepository.findAllByUser(pageable, user);
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            offers = getOffers(pageable);
        }
        return offers;
    }

    public Page<Offer> searchOffersByTitle(Pageable pageable, String searchText) {
        Page<Offer> offers = new PageImpl<>(new LinkedList<Offer>());
        searchText = "%"+searchText+"%";
        offers = offersRepository.searchByTitle(pageable, searchText);
        return offers;
    }

    public void soldOffer(Offer offer, Long id) {
        offer.setSold(true);
        offer.setComprador(id);
        addOffer(offer);
    }

    public Offer searchById(Long id) {
        return offersRepository.findById(id).get();
    }

    public List<Offer> getOffersByUserId(Long id) {
        return offersRepository.findAllByComprador(id);
    }

    public List<String> validateOffer(Offer offer, User user) {
        return new OfferBuyValidator().validate(offer, user);
    }
}
