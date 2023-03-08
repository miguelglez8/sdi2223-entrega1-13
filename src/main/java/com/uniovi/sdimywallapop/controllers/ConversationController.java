package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Message;
import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.ConversationService;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OffersService offersService;

    // TODO: Validador

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.GET)
    public String startConversation(Model model, @PathVariable Long id, Principal principal){
        String dni = principal.getName();
        Offer offer = offersService.searchById(id);
        User buyer = usersService.getUserByDni(dni);
        User seller = offer.getUser();
        Conversation conversation = conversationService.searchByBuyerSellerAndOffer(dni, seller.getDni(), id);
        Conversation newConversation;
        if (conversation == null) {
            newConversation = new Conversation(seller, buyer, offer);
            conversationService.addConversation(newConversation);
            model.addAttribute("conversation", newConversation);
            conversation = newConversation;
        }
        else
            model.addAttribute("conversation", conversation);

        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("offer", offer);

        if (buyer.getDni().equals(dni))
            model.addAttribute("message", new Message());
        else
            model.addAttribute("message", new Message());

        return "offer/conversation";
    }

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.POST)
    public String sendMessage(Model model, @ModelAttribute Message message, @PathVariable Long id, Principal principal) {
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Offer offer = offersService.searchById(id);
        message.setUser(user);
        Conversation conversation = conversationService.searchByUserAndOffer(user, offer);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/start/" + id;
    }
}
