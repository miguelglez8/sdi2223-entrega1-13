package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import com.uniovi.sdimywallapop.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

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
                          @RequestParam(required = false) String searchText){
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Page<Offer> offers;
        if (searchText != null && !searchText.isEmpty()) {
            offers = offersService.searchOffersByTitle(pageable, searchText);
        } else {
            offers = offersService.getOffers(pageable);
        }
        model.addAttribute("offerList", offers.getContent());
        model.addAttribute("page", offers);
        model.addAttribute("user", user);
        model.addAttribute("searchText", searchText);

        return "offer/list";
    }

    @RequestMapping("/offer/myList")
    public String getMyList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Page<Offer> offers;
        offers = offersService.getOffersForUser(pageable, user);
        model.addAttribute("offerList", offers.getContent());
        model.addAttribute("page", offers);
        return "offer/myList";
    }

    @RequestMapping("/offer/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Offer> offers = offersService.getOffers(pageable);
        model.addAttribute("offerList", offers.getContent());
        return "offer/list :: tableOffers";
    }

    @RequestMapping("/offer/listBuy")
    public String getListBuy(Model model, Principal principal){
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        model.addAttribute("offerListB", offersService.getOffersByUserId(user.getId()));
        return "offer/listBuy";
    }

    @RequestMapping("/offer/list/updateBuy")
    public String updateListB(Model model, Principal principal) {
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        model.addAttribute("offerListB", offersService.getOffersByUserId(user.getId()));
        return "offer/listBuy :: tableOffersB";
    }

    @RequestMapping(value = "/offer/add")
    public String getOffer(Model model) {
        model.addAttribute("offerList", offersService.getOffers());
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value = "/offer/buy/{id}", method = RequestMethod.GET)
    public String buyOffer(Model model, Pageable pageable, @PathVariable Long id, Principal principal){
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Offer offer = offersService.searchById(id);
        Page<Offer> offers = offersService.getOffers(pageable);
        model.addAttribute("offerList", offers.getContent());
        model.addAttribute("user", user);
        model.addAttribute("page", offers);
        List<String> errors = offersService.validateOffer(offer, user);
        model.addAttribute("errors", errors);
        if (errors.size() > 0) {
            return "offer/list";
        }
        usersService.decrementMoney(user, offer.getPrice());
        offersService.soldOffer(offer, user);
        return "redirect:/offer/list";
    }

    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(Principal principal, Model model, @Validated Offer offer, BindingResult result) {
        offerFormValidator.validate(offer, result);
        if (result.hasErrors()) {
            model.addAttribute("offerList", offersService.getOffers());
            return "offer/add";
        }
        offer.setCreationDate(new Date());
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        offer.setUser(user);
        offersService.addOffer(offer);
        return "redirect:/offer/myList";
    }

    @RequestMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id){
        offersService.deleteOffer(id);
        return "redirect:/offer/myList";
    }
}
