package com.uniovi.sdimywallapop.validators;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OfferFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        if (offer.getPrice() < 0) {
            errors.rejectValue("price", "Error.offer.price.negative");
        }
        if (offer.getTitle().length() < 3 || offer.getTitle().length() > 30) {
            errors.rejectValue("title", "Error.offer.title.lenght");
        }
        if (offer.getUser().getMoney() < offer.getPrice()) {
            errors.rejectValue("price", "Error.offer.price.minus");
        }
        if (offer.isSold()) {
            errors.rejectValue("sold", "Error.offer.sold");
        }
        if (offer.getUser().getId().equals(offer.getUser().getId())) {
            errors.rejectValue("user", "Error.offer.user");
        }

    }

}
