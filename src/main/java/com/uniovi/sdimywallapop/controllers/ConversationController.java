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

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.GET)
    public String startConversation(Model model, @PathVariable Long id, Principal principal){
        String email = principal.getName();
        Offer offer = offersService.searchById(id);
        User buyer = usersService.getUserByEmail(email);
        User seller = offer.getUser();
        Conversation conversation = conversationService.searchByBuyerAndOffer(email, id);

        if (buyer.getEmail().equals(seller.getEmail()))
            return "redirect:/offer/list";

        Conversation newConversation;
        if (conversation == null) {
            newConversation = new Conversation(seller, buyer, offer);
            conversationService.addConversation(newConversation);
            model.addAttribute("conversation", newConversation);
        }
        else {
            model.addAttribute("conversation", conversation);
        }

        model.addAttribute("buyer", buyer);
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

        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("offer", offer);

        model.addAttribute("message", new Message());

        return "conversation/conversation";
    }

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.POST)
    public String sendMessage(Model model, @ModelAttribute Message message, @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Offer offer = offersService.searchById(id);
        message.setUser(user);
        Conversation conversation = conversationService.searchByUserAndOffer(user, offer);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/start/" + id;
    }

    @RequestMapping(value = "/conversation/resume/{id}", method = RequestMethod.POST)
    public String sendMessageResume(Model model, @ModelAttribute Message message, @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Conversation conversation = conversationService.searchById(id);
        message.setUser(user);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/resume/" + id;
    }

    @RequestMapping("/conversation/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(required = false) String searchText){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Conversation> conversations = conversationService.searchConversationsTakingPartBy(pageable, user);

        model.addAttribute("conversationList", conversations.getContent());
        model.addAttribute("page", conversations);
        model.addAttribute("user", user);
        model.addAttribute("searchText", searchText);

        return "conversation/list";
    }

    @RequestMapping("/conversation/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Conversation> conversations = conversationService.getConversations(pageable);
        model.addAttribute("conversationList", conversations.getContent());
        model.addAttribute("user", user);
        return "conversation/list :: tableConversations";
    }

    @RequestMapping(value = "/conversation/update")
    public String getList(Model model, @ModelAttribute Conversation conversation, Principal principal){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Conversation updatedConversation = conversationService.searchById(conversation.getId());

        model.addAttribute("conversation", updatedConversation);
        model.addAttribute("offer", updatedConversation.getOffer());
        model.addAttribute("user", user);

        return "conversation/conversation :: main-container";
    }
}
