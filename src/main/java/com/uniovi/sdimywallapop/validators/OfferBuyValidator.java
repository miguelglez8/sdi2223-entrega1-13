package com.uniovi.sdimywallapop.validators;

import com.uniovi.sdimywallapop.entities.Offer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OfferBuyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        if (offer.getUser().getMoney() < offer.getPrice()) {
            errors.rejectValue("price", "Error.offer.price.minus");
        }
        if (offer.isSold()) {
            errors.rejectValue("sold", "Error.offer.sold");
        }


    }

}
