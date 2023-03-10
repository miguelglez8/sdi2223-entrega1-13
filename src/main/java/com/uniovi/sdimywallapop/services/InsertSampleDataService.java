package com.uniovi.sdimywallapop.services;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Message;
import com.uniovi.sdimywallapop.entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.uniovi.sdimywallapop.entities.User;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private ConversationService conversationService;
    @PostConstruct
    public void init() {
        User user1 = new User("99999990A", "Pedro", "Díaz", "UO282337@uniovi.es");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("99999991B", "Lucas", "Núñez", "UO281337@uniovi.es");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("99999992C", "María", "Rodríguez", "UO282117@uniovi.es");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("99999993D", "Marta", "Almonte", "UO182337@uniovi.es");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("99999977E", "Pelayo", "Valdes", "UO112337@uniovi.es");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);

        Set user1Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta 4", "Texto4", "Detalles4", 14, user1));
                add(new Offer("Oferta 5", "Texto5", "Detalles5", 14, user1));
                add(new Offer("Oferta 6", "Texto6", "Detalles6", 14, user1));
            }
        };
        Set user12ffers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta 1", "Texto1", "Detalles1", 24, user2));
                add(new Offer("Oferta 2", "Texto2", "Detalles2", 100, user2));
                add(new Offer("Oferta 3", "Texto3", "Detalles3", 200, user2));
            }
        };
        user1.setOffers(user1Offers);
        user2.setOffers(user12ffers);

        Offer offer1 = new Offer("Oferta 8", "Texto8", "Detalles8", 24, user2);
        Offer offer2 = new Offer("Oferta 9", "Texto9", "Detalles9", 150, user2);
        Offer offer3 = new Offer("Oferta 10", "Texto10", "Detalles10", 14, user2);
        Set user2Offers = new HashSet<Offer>() {
            {
                add(offer1);
                add(offer2);
                add(offer3);
            }
        };
        user2.setOffers(user2Offers);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);

        Conversation conversation = new Conversation(user2, user1, offer1);
        conversationService.addConversation(conversation);

        Message message1 = new Message("esto es un mensaje de prueba", user1, conversation);
        Message message2 = new Message("si se ve esto significa que todo es correcto", user1, conversation);
        Message message3 = new Message("lo que el comprador ha dicho", user2, conversation);
        conversationService.addMessage(message1);
        conversationService.addMessage(message2);
        conversationService.addMessage(message3);
    }
}
