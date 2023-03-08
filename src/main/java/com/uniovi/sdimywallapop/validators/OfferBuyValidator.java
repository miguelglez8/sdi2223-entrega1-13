package com.uniovi.sdimywallapop.validators;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
public class OfferBuyValidator {
    @Value("${Error.price.minus}")
    String msg1;

    @Value("${Error.offer.sold}")
    String msg2;

    @Value("${Error.offer.user}")
    String msg3;

    public List<String> validate(Offer offer, User user) {
        List<String> errores = new ArrayList<>();
        if (offer.getUser().getMoney() < offer.getPrice()) {
            errores.add(msg1);
        } if (offer.isSold()) {
            errores.add(msg2);
        } if (offer.getUser().getId() == user.getId()) {
            errores.add(msg3);
        }
        return errores;
    }
}
