package com.uniovi.sdimywallapop.validators;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;

import java.util.ArrayList;
import java.util.List;

public class OfferBuyValidator {
    public List<String> validate(Offer offer, User user) {
        List<String> errores = new ArrayList<>();
        if (offer.getUser().getMoney() < offer.getPrice()) {
            errores.add("Error.offer.price.minus");
        } if (offer.isSold()) {
            errores.add("Error.offer.sold");
        } if (offer.getUser().getId() == user.getId()) {
            errores.add("Error.offer.user");
        }
        return errores;
    }
}
