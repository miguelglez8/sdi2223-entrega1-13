package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import com.uniovi.sdimywallapop.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.LinkedList;

@Controller
public class OffersController {

    @Autowired
    private OffersService offersService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OfferFormValidator offerFormValidator;

    @RequestMapping("/offer/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(value="", required = false) String searchText){
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        if (searchText != null && !searchText.isEmpty()) {
            offers = offersService.searchOffersByTitle(pageable, searchText);
        } else {
            offers = offersService.getOffers(pageable);
        }
        model.addAttribute("offerList", offers.getContent());
        model.addAttribute("page", offers);
        model.addAttribute("user", user);

        return "offer/list";
    }

    @RequestMapping("/offer/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        Page<Offer> offers = offersService.getOffers(pageable);
        model.addAttribute("offerList", offers.getContent());
        return "offer/list :: tableOffers";
    }

    @RequestMapping(value = "/offer/add")
    public String getOffer(Model model) {
        model.addAttribute("offerList", offersService.getOffers());
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value = "/offer/buy/{id}", method = RequestMethod.GET)
    public String buyOffer(Model model, @PathVariable Long id, Principal principal){
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        Offer offer = offersService.searchById(id);
        usersService.decrementMoney(user, offer.getPrice());
        offersService.soldOffer(offer);
        return "redirect:/offer/list";
    }

    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(Model model, @Validated Offer offer, BindingResult result) {
        offerFormValidator.validate(offer, result);
        if (result.hasErrors()) {
            model.addAttribute("offerList", offersService.getOffers());
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
