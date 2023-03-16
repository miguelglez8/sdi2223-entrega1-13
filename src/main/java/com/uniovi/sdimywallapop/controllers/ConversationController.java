package com.uniovi.sdimywallapop.controllers;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Message;
import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.services.ConversationService;
import com.uniovi.sdimywallapop.services.OffersService;
import com.uniovi.sdimywallapop.services.UsersService;
import com.uniovi.sdimywallapop.validators.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OffersService offersService;

    @Autowired
    private MessageValidator messageValidator;

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
            model.addAttribute("tableMessages", newConversation.getMessages());
        }
        else {
            model.addAttribute("conversation", conversation);
            model.addAttribute("tableMessages", conversation.getMessages());
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
        model.addAttribute("tableMessages", conversation.getMessages());

        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        model.addAttribute("offer", offer);

        model.addAttribute("message", new Message());

        return "conversation/conversation";
    }

    @RequestMapping(value = "/conversation/start/{id}", method = RequestMethod.POST)
    public String sendMessage(Model model, @ModelAttribute @Validated Message message, @PathVariable Long id, Principal principal, BindingResult result) {
        messageValidator.validate(message, result);
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Offer offer = offersService.searchById(id);
        Conversation conversation = conversationService.searchByUserAndOffer(user, offer);
        if (result.hasErrors()) {
            model.addAttribute("conversation", conversation);
            model.addAttribute("tableMessages", conversation.getMessages());

            model.addAttribute("offer", offer);
            return "conversation/conversation";
        }
        message.setUser(user);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/start/" + id;
    }

    @RequestMapping(value = "/conversation/resume/{id}", method = RequestMethod.POST)
    public String sendMessageResume(Model model, @ModelAttribute @Validated Message message, @PathVariable Long id, Principal principal, BindingResult result) {
        messageValidator.validate(message, result);
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Conversation conversation = conversationService.searchById(id);
        if (result.hasErrors()) {
            model.addAttribute("conversation", conversation);
            model.addAttribute("tableMessages", conversation.getMessages());

            model.addAttribute("offer", conversation.getOffer());
            return "conversation/conversation";
        }
        message.setUser(user);
        message.setConversation(conversation);
        conversationService.addMessage(message);
        return "redirect:/conversation/resume/" + id;
    }

    @RequestMapping("/conversation/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Conversation> conversations = conversationService.searchConversationsTakingPartBy(pageable, user);

        model.addAttribute("conversationList", conversations.getContent());
        model.addAttribute("page", conversations);
        model.addAttribute("user", user);

        return "conversation/list";
    }

    @RequestMapping("/conversation/remove/{id}")
    public String deleteConversation(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        List<Long> conversations = conversationService.searchConversationsTakingPartBy(user);

        if(conversations.contains(id)){
            conversationService.deleteConversation(id);
        }
        return "redirect:/conversation/list";
    }

    @RequestMapping("/conversation/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Conversation> conversations = conversationService.getConversations(pageable);
        model.addAttribute("conversationList", conversations.getContent());
        return "conversation/list :: tableConversations";
    }

    @RequestMapping(value = "/conversation/start/update/{id}")
    public String getList(Model model,  @PathVariable Long id){
        Conversation conversation = conversationService.searchById(id);
        model.addAttribute("tableMessages", conversation.getMessages());
        return "conversation/conversation :: tableMessages";
    }
}
