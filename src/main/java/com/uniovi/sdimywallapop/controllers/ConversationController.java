package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Message;
import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.ConversationService;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        // User buyer = usersService.getUserByDni(dni);
        User seller = offer.getUser();
        Conversation conversation = conversationService.searchByBuyerAndOffer(dni, id);

        Conversation newConversation;
        if (conversation == null) {
            // newConversation = new Conversation(seller, buyer, offer);
            //conversationService.addConversation(newConversation);
            //model.addAttribute("conversation", newConversation);
            //System.out.println("is null " + newConversation.getId());
        }
        else {
            model.addAttribute("conversation", conversation);
            System.out.println("is NOT null " + conversation.getId());
        }

        // model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("offer", offer);

        model.addAttribute("message", new Message());

        return "conversation/conversation";
    }

    @RequestMapping(value = "/conversation/resume/{id}", method = RequestMethod.GET)
    public String resumeConversation(Model model, @PathVariable Long id, Principal principal){
        Conversation conversation = conversationService.searchById(id);
        User buyer = conversation.getBuyer();
        User seller = conversation.getSeller();
        Offer offer = conversation.getOffer();

        model.addAttribute("conversation", conversation);
        System.out.println("is NOT null " + conversation.getId());

        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("offer", offer);

        model.addAttribute("message", new Message());

        return "conversation/conversation";
    }

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.POST)
    public String sendMessage(Model model, @ModelAttribute Message message, @PathVariable Long id, Principal principal) {
        String dni = principal.getName();
        // User user = usersService.getUserByDni(dni);
        Offer offer = offersService.searchById(id);
        //message.setUser(user);
        //Conversation conversation = conversationService.searchByUserAndOffer(user, offer);
        //message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/start/" + id;
    }

    @RequestMapping(value = "/conversation/resume/{id}", method = RequestMethod.POST)
    public String sendMessageResume(Model model, @ModelAttribute Message message, @PathVariable Long id, Principal principal) {
        String dni = principal.getName();
        // User user = usersService.getUserByDni(dni);
        Conversation conversation = conversationService.searchById(id);
        //message.setUser(user);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/resume/" + id;
    }

    @RequestMapping("/conversation/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Conversation> conversations = conversationService.getConversations(pageable);
        model.addAttribute("conversationList", conversations.getContent());
        return "conversation/list :: tableConversations";
    }

    @RequestMapping("/conversation/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(required = false) String searchText){
        String dni = principal.getName();
        // User user = usersService.getUserByDni(dni);
        //Page<Conversation> conversations = conversationService.searchConversationsTakingPartBy(pageable, user);

        //model.addAttribute("offerList", conversations.getContent());
        //model.addAttribute("page", conversations);
        // model.addAttribute("user", user);
        model.addAttribute("searchText", searchText);

        return "conversation/list";
    }
}
