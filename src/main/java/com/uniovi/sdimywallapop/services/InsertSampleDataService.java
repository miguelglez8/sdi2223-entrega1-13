package com.uniovi.sdimywallapop.services;

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
    @PostConstruct
    public void init() {
        User user1 = new User("99999990A", "Pedro", "Díaz");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("99999991B", "Lucas", "Núñez");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("99999992C", "María", "Rodríguez");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("99999993D", "Marta", "Almonte");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("99999977E", "Pelayo", "Valdes");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);

        Set user1Offers = new HashSet<Offer>() {
            {
                add(new Offer("Oferta 1", "Texto1", "Detalles1", 24, user1, "UO282337@uniovi.es"));
                add(new Offer("Oferta 2", "Texto2", "Detalles2", 150, user1, "UO282337@uniovi.es"));
                add(new Offer("Oferta 3", "Texto3", "Detalles3", 14, user1, "UO282337@uniovi.es"));
                add(new Offer("Oferta 4", "Texto4", "Detalles4", 14, user1, "UO282337@uniovi.es"));
                add(new Offer("Oferta 5", "Texto5", "Detalles5", 14, user1, "UO282337@uniovi.es"));
                add(new Offer("Oferta 6", "Texto6", "Detalles6", 14, user1, "UO282337@uniovi.es"));
            }
        };
        user1.setOffers(user1Offers);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
    }
}
