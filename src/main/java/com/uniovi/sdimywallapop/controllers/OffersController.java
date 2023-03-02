package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import com.uniovi.sdimywallapop.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class OffersController {

    @Autowired
    private OffersService offersService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OfferFormValidator offerFormValidator;

    @RequestMapping("/offer/list")
    public String getList(Model model, Principal principal) {
//        String dni = principal.getName();
//        User user = usersService.getUserByDni(dni);
//        model.addAttribute("offerList", offersService.getOffersForUser(user) );
//        return "offer/list";
        model.addAttribute("offerList", offersService.getOffers());
        return "offer/list";
    }

    @RequestMapping(value = "/offer/add")
    public String getOffer(Model model) {
        model.addAttribute("offersList", offersService.getOffers());
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(Model model, @Validated Offer offer, BindingResult result) {
        offerFormValidator.validate(offer, result);
        if (result.hasErrors()) {
            model.addAttribute("offersList", offersService.getOffers());
            return "offer/add";
        }
        offersService.addOffer(offer);
        return "redirect:/offer/list";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id){
        offersService.deleteOffer(id);
        return "redirect:/offer/list";
    }
}
